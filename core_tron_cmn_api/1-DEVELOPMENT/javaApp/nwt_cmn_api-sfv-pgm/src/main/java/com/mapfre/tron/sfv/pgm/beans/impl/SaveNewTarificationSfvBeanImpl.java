package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.api.SimulationApi;
import com.mapfre.tron.api.isu.client.model.InPolicy;
import com.mapfre.tron.api.isu.client.model.OPlyAtcC;
import com.mapfre.tron.api.isu.client.model.OPlyAtcPC;
import com.mapfre.tron.api.isu.client.model.OPlyAtrP;
import com.mapfre.tron.api.isu.client.model.OPlyAtrS;
import com.mapfre.tron.api.isu.client.model.OPlyCvcPC;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.api.isu.client.model.OPlySncC;
import com.mapfre.tron.api.isu.client.model.OutSimulationComplete;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.PreviousToReal;

import lombok.extern.slf4j.Slf4j;

/**
 * Save coverage tarification data from simulation to qoutation.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("SaveNewTarificationSfv")
public class SaveNewTarificationSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final int COR_CAMPO_10 = 10;
	@Autowired
	private SimulationApi simulationApi;
	@Autowired
	private IssueApi issueApi;
	@Autowired
	private PreviousToReal ptr;

	private static final String APL_ENR_SQN = "aplEnrSqn";
	private static final String APL_VAL = "aplVal";
	private static final String ENR_SQN = "enrSqn";
	private static final String COD_RAMO = "COD_RAMO";
	private static final String RSK_VAL = "rskVal";
	private static final String PED_VAL = "pedVal";

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("Save new Tarification - Start execution...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String qtnVal = getString(in.getParameters().get("NUM_PRESUPUESTO"));
		String smlVal = getString(in.getParameters().get("NUM_COTIZACION"));
		String lobVal = getString(params.get(COD_RAMO));

		if (StringUtils.isEmpty(qtnVal) || StringUtils.isEmpty(smlVal)) {
			lvSfvOut.addMessagesItem(getMessage("NUM_PRESUPUESTO/NUM_COTIZACION must be informed", 3, 400, null));

		} else {
			try {
				// Get all data from simulation
				OutSimulationComplete outSimulationComplete = simulationApi.getSimulationComplete(cmpVal.intValueExact(), usrVal, lngVal, lobVal, smlVal);
				Map<String, String> values = new HashMap<>();
				outSimulationComplete.getOPlySlpPT().stream().forEach(x -> values.put(x.getClmNam(), x.getClmVal()));

				// Call query quotation to get data
				InPolicy inPolicy = new InPolicy();
				inPolicy.setAplEnrSqn(getI(APL_ENR_SQN, params));
				inPolicy.setAplVal(getI(APL_VAL, params));
				inPolicy.setEnrSqn(getString(params.get(ENR_SQN)));
				OPlyPlyC oPlyPlyC = issueApi.getQuotationInformation(inPolicy, cmpVal.intValueExact(), usrVal, lngVal, qtnVal, null);

				// Get selected module
				Optional<OPlyAtcPC> osmnVal = oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyAtcCT().stream()
						.filter(x -> "NUM_SIMULACION".equals(x.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam()))
						.findFirst();
				if (osmnVal.isPresent()) {
					String smnVal = osmnVal.get().getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldValVal();
					BigDecimal bsmnVal = new BigDecimal(smnVal);

					Optional<OPlySncC> osmnData = outSimulationComplete.getOPlySncCT().stream()
							.filter(x -> bsmnVal.equals(x.getOPidSdfS().getSmlVal()))
							.findFirst();
					if (osmnData.isPresent()) {
						// Pass coverage data from simulation to quotation oPlyCvcC
						List<OPlyCvcPC> org = oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyCvcCT();
						passCoverageData(qtnVal, oPlyPlyC, osmnData.get(), lobVal, params);

						// Pass coverage dv data to simulation data
						passDataToQtn(cmpVal, params, qtnVal, lobVal, values, org, osmnData.get());

						// Call previous to real
						ptr.callPreviousToReal(oPlyPlyC, cmpVal, usrVal, lngVal);
					}
				}

			} catch (HttpStatusCodeException e) {
				processApiError(lvSfvOut, false, e);
			} catch (Exception e) {
				String msg = String.format("Error calling simulationApi/issueApi: %s", e.getMessage());
				log.error(msg);
				lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
			}
		}

		return lvSfvOut;
	}

	private void passCoverageData(String qtnVal, OPlyPlyC oPlyPlyC, OPlySncC osmnData, String lobVal,
			Map<String, Object> params) {
		final BigDecimal enrSqn = getBD(APL_ENR_SQN, params);
		final BigDecimal aplVal = getBD(APL_ENR_SQN, params);
		final BigDecimal aplEnrSqn = getBD(APL_ENR_SQN, params);
		final BigDecimal rskVal = getBD(RSK_VAL, params);
		final BigDecimal pedVal = getBD(PED_VAL, params);
		final BigDecimal blobVal = new BigDecimal(lobVal);

		osmnData.getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT().stream().forEach(x -> {
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().plyVal(qtnVal);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().qtnVal(qtnVal);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().enrSqn(enrSqn);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().aplVal(aplVal);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().aplEnrSqn(aplEnrSqn);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().rskVal(rskVal);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().pedVal(pedVal);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().enrCplAmn(x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCplAmn());
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().agcAmn(BigDecimal.ZERO);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().lssDcsCplAmn(BigDecimal.ZERO);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().lssDcsAcrCplAmn(BigDecimal.ZERO);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().cvrRatVal(BigDecimal.ZERO);
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().aplOpvRow("S");
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().opvRow("S");
			x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().lobVal(blobVal);
			if (x.getOPlyCvcC().getOPlyBrwPT() != null && !x.getOPlyCvcC().getOPlyBrwPT().isEmpty()) {
				x.getOPlyCvcC().getOPlyBrwPT().stream().forEach(y -> {
					y.getOPlyBrwS().qtnVal(qtnVal);
					y.getOPlyBrwS().plyVal(qtnVal);
					y.getOPlyBrwS().enrSqn(enrSqn);
					y.getOPlyBrwS().aplVal(aplVal);
					y.getOPlyBrwS().aplEnrSqn(aplEnrSqn);
					y.getOPlyBrwS().rskVal(rskVal);
					y.getOPlyBrwS().pedVal(pedVal);
					y.getOPlyBrwS().lobVal(blobVal);
				});
			}
		});
		oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().oPlyCvcCT(osmnData.getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT());
	}

	private void passDataToQtn(final BigDecimal cmpVal, Map<String, Object> params, String qtnVal, String lobVal,
			Map<String, String> values, List<OPlyCvcPC> org, OPlySncC osmnData) {

		for (OPlyCvcPC o : org) {
			BigDecimal cvrVal = o.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal();
			Optional<OPlyCvcPC> ocvr = osmnData.getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT().stream()
					.filter(x -> cvrVal.equals(x.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal()))
					.findFirst();

			if (ocvr.isPresent()) {
				// Pass variable data related with tarification
				Map<String, OPlyAtcPC> existing = createMapCvrs(o);
				final BigDecimal[] idx = { BigDecimal.ONE };
				final BigDecimal enrSqn = getBD(APL_ENR_SQN, params);
				final BigDecimal aplVal = getBD(APL_ENR_SQN, params);
				final BigDecimal aplEnrSqn = getBD(APL_ENR_SQN, params);
				final BigDecimal rskVal = getBD(RSK_VAL, params);
				final BigDecimal pedVal = getBD(PED_VAL, params);
				final BigDecimal blobVal = new BigDecimal(lobVal);
				final BigDecimal mdtVal = new BigDecimal(values.get("COD_MODALIDAD"));
				final String lvlTypVal = getString(params.get("lvlTypVal"));

				values.entrySet().stream().filter(x -> x.getKey().endsWith("_" + cvrVal)).forEach(x -> {
					OPlyAtcPC pc = new OPlyAtcPC();
					existing.put(x.getKey(), pc);
					pc.setOPlyAtcC(new OPlyAtcC());
					pc.getOPlyAtcC().setOPlyAtrP(new OPlyAtrP());
					completeCvr(existing, x, pc);
					OPlyAtrS os = new OPlyAtrS();
					pc.getOPlyAtcC().getOPlyAtrP().setOPlyAtrS(os);

					os.cmpVal(cmpVal);
					os.qtnVal(qtnVal);
					os.plyVal(qtnVal);
					os.enrSqn(enrSqn);
					os.aplVal(aplVal);
					os.aplEnrSqn(aplEnrSqn);
					os.rskVal(rskVal);
					os.pedVal(pedVal);
					os.mdtVal(mdtVal);
					os.lobVal(blobVal);
					os.lvlTypVal(lvlTypVal);
					os.fldNam(x.getKey());
					os.fldValVal(x.getValue());
					os.fldShrVal(StringUtils.isEmpty(x.getValue()) || x.getValue().length() <= COR_CAMPO_10 ? x.getValue() : x.getValue().substring(0, COR_CAMPO_10));
					os.sqnVal(idx[0]);
					idx[0] = idx[0].add(BigDecimal.ONE);
					os.rskRmv("N");
					os.opvRow("S");
					os.aplOpvRow("S");
					os.cvrVal(cvrVal);
				});

				ocvr.get().getOPlyCvcC().oPlyAtcCT(new ArrayList<>(existing.values()));
			}
		}
	}

	private void completeCvr(Map<String, OPlyAtcPC> existing, Entry<String, String> x, OPlyAtcPC pc) {
		if (existing.containsKey(x.getKey())) {
			pc.getOPlyAtcC().setAnlPreTotOcr(existing.get(x.getKey()).getOPlyAtcC().getAnlPreTotOcr());
			pc.getOPlyAtcC().setCplTotOcr(existing.get(x.getKey()).getOPlyAtcC().getCplTotOcr());
			pc.getOPlyAtcC().setOPlyOcaPT(existing.get(x.getKey()).getOPlyAtcC().getOPlyOcaPT());
		}
	}

	private Map<String, OPlyAtcPC> createMapCvrs(OPlyCvcPC o) {
		Map<String, OPlyAtcPC> existing = new HashMap<>();
		if (o.getOPlyCvcC().getOPlyAtcCT() != null && !o.getOPlyCvcC().getOPlyAtcCT().isEmpty()) {
			o.getOPlyCvcC().getOPlyAtcCT().stream()
					.forEach(x -> existing.put(x.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam(), x));
		}
		return existing;
	}

	private Integer getI(String k, Map<String, Object> vals) {
		String val = getString(vals.get(k));
		if (StringUtils.isNotEmpty(val)) {
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	private BigDecimal getBD(String k, Map<String, Object> vals) {
		String val = getString(vals.get(k));
		if (StringUtils.isNotEmpty(val)) {
			try {
				return new BigDecimal(val);
			} catch (NumberFormatException e) {
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
	}
}
