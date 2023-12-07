package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.api.SimulationApi;
import com.mapfre.tron.api.isu.client.model.InPolicy;
import com.mapfre.tron.api.isu.client.model.OPlyAtcPC;
import com.mapfre.tron.api.isu.client.model.OPlyCvcPC;
import com.mapfre.tron.api.isu.client.model.OPlyGniS;
import com.mapfre.tron.api.isu.client.model.OPlyInaP;
import com.mapfre.tron.api.isu.client.model.OPlyOcaP;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.api.isu.client.model.OPlyPmcPC;
import com.mapfre.tron.api.isu.client.model.OPlySncC;
import com.mapfre.tron.api.isu.client.model.OutDataFormulary;
import com.mapfre.tron.api.isu.client.model.OutSimulationComplete;
import com.mapfre.tron.api.thp.client.api.InsuredPartyApi;
import com.mapfre.tron.api.thp.client.model.OThpIpcPC;
import com.mapfre.tron.api.thp.client.model.OThpPrsP;
import com.mapfre.tron.api.thp.client.model.OThpPrsS;
import com.mapfre.tron.sfv.bo.BasicData;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.ThpApiProxy;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.Coverages;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.Insured;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.Participants;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.PolicyHolder;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.PolicyHolderLegalRepresentative;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionRequest;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.StayAbroad;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Risk Selection prepare data in case of simulation.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionSimulationDataSfv")
public class RiskSelectionSimulationDataSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final String PMS_VAL = "PMS_VAL";
	private static final String PRIMA_TOTAL = "RECEIPT_AMOUNT";
	private static final String CUESTIONARIOS = "CUESTIONARIOS";
	private static final String MCA_ESTANCIAS_EN_EXTRANJERO = "MCA_ESTANCIAS_EN_EXTRANJERO";
	private static final String FEC_NACIMIENTO = "FEC_NACIMIENTO";
	private static final String COD_PAIS_RESIDENTE_FISCAL = "COD_PAIS_RESIDENTE_FISCAL";
	private static final String COD_RAMO = "COD_RAMO";
	private static final String NUM_COTIZACION = "NUM_COTIZACION";
	private static final String NUM_SIMULACION = "NUM_SIMULACION";
	private static final String NUM_PRESUPUESTO = "NUM_PRESUPUESTO";
	private static final String PROCESS_TYPE_ID = "processTypeId";
	private static final String PROCESS_TYPE_ID_PROPUESTA = "8";
	private static final String APL_ENR_SQN = "aplEnrSqn";
	private static final String APL_VAL = "aplVal";
	private static final String ENR_SQN = "enrSqn";
	private static final String RSK_VAL = "rskVal";

	@Autowired
	private SimulationApi simulationApi;

	@Autowired
	private IssueApi issueApi;

	@Autowired
	protected InsuredPartyApi insuredPartyApi;
	
	@Autowired
	protected ThpApiProxy genericThirdPartyApi;

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("Risk Selection Get data from simulation - Start execution...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String processTypeId = getString(params.get(PROCESS_TYPE_ID));

		if (!processTypeId.equals(PROCESS_TYPE_ID_PROPUESTA) && in.getParameters().get(NUM_COTIZACION) == null) {
			lvSfvOut.addMessagesItem(getMessage("Simulation code must be informed (NUM_COTIZACION)", 3, 400, null));

		} else if (processTypeId.equals(PROCESS_TYPE_ID_PROPUESTA) && in.getParameters().get(NUM_PRESUPUESTO) == null) {
				lvSfvOut.addMessagesItem(getMessage("Quotation code must be informed (NUM_PRESUPUESTO)", 3, 400, null));

		} else {
			String qteVal = getString(in.getParameters().get(NUM_PRESUPUESTO));
			String smlVal = getString(in.getParameters().get(NUM_COTIZACION));

			try {
				RiskSelectionRequest riskSelectionRequest = new RiskSelectionRequest();
				BasicData d = BasicData.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).processTypeId(processTypeId)
						.lobVal(getString(params.get(COD_RAMO)))
						.pmsVal(getString(params.get(PMS_VAL)))
						.build();

				if (processTypeId.equals(PROCESS_TYPE_ID_PROPUESTA)) {
					processProposal(qteVal, smlVal, d, params, lvSfvOut, riskSelectionRequest);
				} else {
					processNotProposal(smlVal, d, params, lvSfvOut, riskSelectionRequest);
				}

				innerData.set("RiskSelectionData", riskSelectionRequest);
			} catch (HttpStatusCodeException e) {
				processApiError(lvSfvOut, false, e);
			} catch (Exception e) {
				String msg = String.format("Error calling simulationApi.getSimulationComplete: %s", e.getMessage());
				log.error(msg);
				lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
			}
		}

		return lvSfvOut;
	}

	private void processNotProposal(String smlVal, BasicData d, Map<String, Object> params,
			SfvOut lvSfvOut, RiskSelectionRequest riskSelectionRequest) {
		
		OutSimulationComplete outSimulationComplete = simulationApi.getSimulationComplete(
				d.getCmpVal().intValueExact(), d.getUsrVal(), d.getLngVal(), getString(params.get(COD_RAMO)), smlVal);
		
		if (hayDatos(outSimulationComplete)) {
			 Map<String, Object> vals = outSimulationComplete.getOPlySlpPT().stream().collect(HashMap<String, Object>::new,
					(m, v) -> m.put(v.getClmNam(), getEmptyString(v.getClmVal())), HashMap::putAll);
		
			setRiskSelectionRequest(riskSelectionRequest, d.getCmpVal(), d.getLngVal(), params, vals, d.getProcessTypeId());
			
			BigDecimal smnVal = getBD(vals.get(NUM_SIMULACION));
			riskSelectionRequest.setCoverages(new HashMap<>()); // A revisar si no hay coberturas da error
			if (hayDatosCvr(outSimulationComplete)) {
				loadCvrData(outSimulationComplete, smnVal, riskSelectionRequest);
			}
			
			procesaPrimas(outSimulationComplete.getOPlySncCT().get(0), d, riskSelectionRequest);
		} else {
			lvSfvOut.addMessagesItem(getMessage("Simulation not found", 3, 20001, null));
		}
	}

	private void processProposal(String qteVal, String smlVal, final BasicData d,
			Map<String, Object> params, SfvOut lvSfvOut, RiskSelectionRequest riskSelectionRequest) {

		InPolicy inPolicy = new InPolicy();
		inPolicy.setAplEnrSqn(getI(APL_ENR_SQN, params));
		inPolicy.setAplVal(getI(APL_VAL, params));
		inPolicy.setEnrSqn(getString(params.get(ENR_SQN)));

		OPlyPlyC oPlyPlyC = issueApi.getQuotationInformation(inPolicy, d.getCmpVal().intValueExact(), d.getUsrVal(),
				d.getLngVal(), qteVal, null);

		if (hayDatosQuotation(oPlyPlyC)) {
			setRiskSelectionRequestQuotation(riskSelectionRequest, d, qteVal, params, oPlyPlyC, d.getProcessTypeId());

			riskSelectionRequest.setCoverages(new HashMap<>()); // A revisar si no hay coberturas da error
			if (hayDatosCvrQuotation(oPlyPlyC)) {
				loadCvrDataQuotation(oPlyPlyC, riskSelectionRequest);
			}
			
			procesaPrimas(smlVal, d, oPlyPlyC, riskSelectionRequest);
		} else {
			lvSfvOut.addMessagesItem(getMessage("Quotation not found", 3, 20001, null));
		}
	}
	
	private void procesaPrimas(String smlVal, BasicData d, OPlyPlyC oPlyPlyC, RiskSelectionRequest riskSelectionRequest) {
		if (StringUtils.isNotEmpty(smlVal)) {
			
			Optional<OPlyAtcPC> atrSmnVal = oPlyPlyC.getOPlyAtcCT().stream().filter(x -> NUM_SIMULACION.equals(x.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam())).findFirst();			
			if (!atrSmnVal.isPresent()) {
				atrSmnVal = oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyAtcCT().stream().filter(x -> NUM_SIMULACION.equals(x.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam())).findFirst();			
			}
			
			if (atrSmnVal.isPresent()) {
				String smnVal = atrSmnVal.get().getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldValVal();
				
				OutSimulationComplete outSimulationComplete = simulationApi.getSimulationComplete(
						d.getCmpVal().intValueExact(), d.getUsrVal(), d.getLngVal(), d.getLobVal(), smlVal);
				
				Optional<OPlySncC> ct = outSimulationComplete.getOPlySncCT().stream().filter(x -> smnVal.equals(x.getOPidSdfS().getSmlVal().toPlainString())).findFirst();
				if (ct.isPresent()) {
					procesaPrimas(ct.get(), d, riskSelectionRequest);
				}
			}
		}
	}
	
	private void procesaPrimas(OPlySncC ct, BasicData d, RiskSelectionRequest riskSelectionRequest) {
		Optional<OPlyPmcPC> pr = ct.getOPlyPmcCT().stream().filter(x -> d.getPmsVal().equals(x.getOPlyPmcC().getOPidIpsS().getPmsVal().toPlainString())).findFirst();
		if (pr.isPresent()) {
			riskSelectionRequest.getVariableData().put(PRIMA_TOTAL, pr.get().getOPlyPmcC().getORcpErcCT().get(0).getORcpErcC().getORcpPmrP().getORcpPmrS().getRcpAmn().toPlainString());
		}
	}

	private void loadCvrData(OutSimulationComplete outSimulationComplete, BigDecimal smlVal,
			RiskSelectionRequest riskSelectionRequest) {
		OPlySncC po = null;
		if (smlVal != null) {
			Optional<OPlySncC> poo = outSimulationComplete.getOPlySncCT().stream()
					.filter(x -> smlVal.equals(x.getOPidSdfS().getSmlVal())).findFirst();
			if (poo.isPresent()) {
				po = poo.get();
			}
		}
		if (po == null) {
			po = outSimulationComplete.getOPlySncCT().get(0);
		}

		if (po != null && po.getOPlyPlyPc() != null && po.getOPlyPlyPc().getOPlyPlyC() != null) {
			for (OPlyCvcPC cvr : po.getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT()) {
				riskSelectionRequest.getCoverages().put(
						cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal().intValueExact(),
						new Coverages(cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrNam(),
								cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCplAmn(), 0));
			}
		}
	}

	private void loadCvrDataQuotation(OPlyPlyC oPlyPlyC, RiskSelectionRequest riskSelectionRequest) {
		for (OPlyCvcPC cvr : oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyCvcCT()) {
			riskSelectionRequest.getCoverages().put(
					cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal().intValueExact(),
					new Coverages(cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal().toString(),
							cvr.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCplAmn(), 0));
		}
	}

	private boolean hayDatos(OutSimulationComplete outSimulationComplete) {
		return outSimulationComplete != null && outSimulationComplete.getOPlySlpPT() != null;
	}

	private boolean hayDatosQuotation(OPlyPlyC oPlyPlyC) {
		return oPlyPlyC != null && oPlyPlyC.getOPlyGniP() != null && oPlyPlyC.getOPlyGniP().getOPlyGniS() != null;
	}

	private boolean hayDatosCvr(OutSimulationComplete outSimulationComplete) {
		return outSimulationComplete != null && outSimulationComplete.getOPlySncCT() != null
				&& !outSimulationComplete.getOPlySncCT().isEmpty()
				&& outSimulationComplete.getOPlySncCT().get(0).getOPlyPlyPc() != null
				&& outSimulationComplete.getOPlySncCT().get(0).getOPlyPlyPc().getOPlyPlyC() != null
				&& outSimulationComplete.getOPlySncCT().get(0).getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT() != null
				&& !outSimulationComplete.getOPlySncCT().get(0).getOPlyPlyPc().getOPlyPlyC().getOPlyCvcCT().isEmpty();
	}

	private boolean hayDatosCvrQuotation(OPlyPlyC oPlyPlyC) {
		return oPlyPlyC.getOPlyRkcCT() != null && oPlyPlyC.getOPlyRkcCT().get(0) != null
				&& oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC() != null
				&& oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyCvcCT() != null;
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

	private LocalDate getLD(String k, Map<String, Object> vals) {
		String val = getString(vals.get(k));
		if (StringUtils.isNotEmpty(val)) {
			return LocalDate.parse(val, DateTimeFormatter.ofPattern("ddMMyyyy"));
		}
		return LocalDate.now();
	}

	private String getString(Map<String, Object> vals, String... ks) {
		for (String k : ks) {
			String val = getString(vals.get(k));
			if (StringUtils.isNotEmpty(val)) {
				return val;
			}
		}
		return "";
	}

	private String getEmptyString(String s) {
		return StringUtils.isNotEmpty(s) ? s : "";
	}

	private void setRiskSelectionRequest(RiskSelectionRequest riskSelectionRequest, final BigDecimal cmpVal,
			final String lngVal, Map<String, Object> params, Map<String, Object> vals, String processTypeId) {
		riskSelectionRequest.setLanguageId(lngVal);
		riskSelectionRequest.setCompanyId(cmpVal.intValue());
		riskSelectionRequest.setBranchId(Integer.parseInt(getString(params.get(COD_RAMO))));
		riskSelectionRequest.setExecutionType(getString(params.get("executionType")));
		riskSelectionRequest.setProcessTypeId(Integer.parseInt(processTypeId));
		riskSelectionRequest.setProcessStep(Integer.parseInt(getString(params.get("processStep"))));
		riskSelectionRequest.setOperationNumber(ThreadLocalRandom.current().nextLong(999999999));
		riskSelectionRequest.setOperationDate(Instant.now().getEpochSecond());

		riskSelectionRequest.setProductId(getI("COD_MODALIDAD", vals));

		riskSelectionRequest.setParticipants(new Participants());
		riskSelectionRequest.getParticipants().setPolicyHolder(new PolicyHolder());
		riskSelectionRequest.getParticipants().getPolicyHolder()
				.setDocumentType(getString(vals, "TIP_DOCUM_TOMADOR", "TIP_DOCUM_0"));
		riskSelectionRequest.getParticipants().getPolicyHolder()
				.setDocumentCode(getString(vals, "COD_DOCUM_TOMADOR", "COD_DOCUM_0"));
		riskSelectionRequest.getParticipants().getPolicyHolder().setTypeOfPerson(getString(vals.get("MCA_FISICO")));
		riskSelectionRequest.getParticipants().getPolicyHolder()
				.setName(getString(vals, "NOM_TERCERO_TOMADOR", "NOM_TERCERO_0") + " "
						+ getString(vals, "APE1_TERCERO_TOMADOR", "APE1_TERCERO_0") + " "
						+ getString(vals, "APE2_TERCERO_TOMADOR", "APE2_TERCERO_0"));
		riskSelectionRequest.getParticipants().getPolicyHolder().setBirthDate(getLD(FEC_NACIMIENTO, vals));
		riskSelectionRequest.getParticipants().getPolicyHolder()
				.setNationality(getString(vals.get(COD_PAIS_RESIDENTE_FISCAL)));
		riskSelectionRequest.getParticipants().getPolicyHolder()
				.setSameAsInsured(getString(vals.get("MCA_ASEGURADO_IGUAL_TOMADOR")));
		riskSelectionRequest.getParticipants().getPolicyHolder().setGender(0); // A revisar estos campos si no estan
		// informados con algo da error
		riskSelectionRequest.getParticipants().getPolicyHolder().setCountryOfBirth("");
		riskSelectionRequest.getParticipants().getPolicyHolder().setMaritalStatusCode(0);
		riskSelectionRequest.getParticipants().getPolicyHolder().setHasPublicServiceResponsibility("");
		riskSelectionRequest.getParticipants().getPolicyHolder().setUsesLegalRepresentative("");

		riskSelectionRequest.getParticipants()
				.setPolicyHolderLegalRepresentative(new PolicyHolderLegalRepresentative()); // A revisar esta estrctura
		emptyLegalRepresentative(riskSelectionRequest);

		riskSelectionRequest.getParticipants().setBeneficiaries(new HashMap<>());

		riskSelectionRequest.setInsured(new Insured());
		riskSelectionRequest.getInsured().setTheInsured(0);
		riskSelectionRequest.getInsured().setBirthDate(getLD(FEC_NACIMIENTO, vals));
		riskSelectionRequest.getInsured().setGender(getI("MCA_SEXO", vals));
		riskSelectionRequest.getInsured().setOccupationCode(getI("COD_OCUPACION", vals));
		riskSelectionRequest.getInsured().setResident(getString(vals.get("MCA_RESIDENTE_FISCAL")));
		riskSelectionRequest.getInsured().setResidenceCountry(getString(vals.get(COD_PAIS_RESIDENTE_FISCAL)));
		riskSelectionRequest.getInsured().setDoesSports(getString(vals.get("MCA_PRACTICA_DEPORTES")));
		riskSelectionRequest.getInsured().setNonFederatedSports(getI("COD_DEPORTE_NO_FEDERADO", vals));
		riskSelectionRequest.getInsured().setFederatedSports(getI("COD_DEPORTE_COMPETICION", vals));
		riskSelectionRequest.getInsured().setRidesMotorcycle(getString(vals.get("MCA_USO_MOTO")));
		riskSelectionRequest.getInsured().setMotorcycleCC(getI("VAL_CC", vals));
		riskSelectionRequest.getInsured().setMotorcycleHP(getI("VAL_POTENCIA", vals));
		riskSelectionRequest.getInsured().setStaysAbroadPlanned(getString(vals.get(MCA_ESTANCIAS_EN_EXTRANJERO)));
		if (getString(vals.get(MCA_ESTANCIAS_EN_EXTRANJERO)).equals("S")) {
			StayAbroad sa = new StayAbroad();
			sa.setDuration(getI("NUM_DURACION_EXTRANJERO", vals));
			sa.setReason(getI("TXT_MOTIVO_EXTRANJERO", vals));
			riskSelectionRequest.getInsured()
					.setStaysAbroad(Collections.singletonMap(getString(vals.get("COD_PAIS_EXTRANJERO")), sa));
		} else {
			riskSelectionRequest.getInsured().setStaysAbroad(new HashMap<>());
		}

		riskSelectionRequest.getInsured().setFinalNaturalAge(getI("EDAD_NATURAL_FIN", vals));
		riskSelectionRequest.getInsured().setAge(getI("EDAD_ACTUARIAL_INI", vals));
		riskSelectionRequest.getInsured()
				.setAuthorizesPersonalDataUse(getString(vals.get("MCA_AUTORIZA_USO_DATOS_ASEG")));
		riskSelectionRequest.getInsured().setMaritalStatusCode(0); // A revisar estos campos si no estan informados con
		// algo da error
		riskSelectionRequest.getInsured().setEducationLevelCode(0);
		riskSelectionRequest.getInsured().setTypeOfEmploymentCode(0);
		riskSelectionRequest.getInsured().setQuestionnaires(new HashMap<>());
		riskSelectionRequest.setFixedData(new HashMap<>());
		riskSelectionRequest.setVariableData(vals);
	}

	private void setRiskSelectionRequestQuotation(RiskSelectionRequest riskSelectionRequest, final BasicData d, String qteVal,
			Map<String, Object> params, OPlyPlyC oPlyPlyC, String processTypeId) {

		Map<String, Object> valsAtcTot;

		riskSelectionRequest.setLanguageId(d.getLngVal());
		riskSelectionRequest.setCompanyId(d.getCmpVal().intValue());
		riskSelectionRequest.setBranchId(Integer.parseInt(getString(params.get(COD_RAMO))));
		riskSelectionRequest.setExecutionType(getString(params.get("executionType")));
		riskSelectionRequest.setProcessTypeId(Integer.parseInt(processTypeId));
		riskSelectionRequest.setProcessStep(Integer.parseInt(getString(params.get("processStep"))));
		riskSelectionRequest.setOperationNumber(ThreadLocalRandom.current().nextLong(999999999));
		riskSelectionRequest.setOperationDate(Instant.now().getEpochSecond());

		valsAtcTot = oPlyPlyC.getOPlyAtcCT().stream()
				.collect(HashMap<String, Object>::new,
						(m, v) -> m.put(v.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam(),
								getEmptyString(v.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldValVal())),
						HashMap::putAll);
		
		oPlyPlyC.getOPlyAtcCT().stream()
		        .filter(x -> x.getOPlyAtcC().getOPlyOcaPT() != null && !x.getOPlyAtcC().getOPlyOcaPT().isEmpty())
		        .map(x -> x.getOPlyAtcC().getOPlyOcaPT())
		        .flatMap(Collection::stream)
		        .forEach(x -> addOcurrence(valsAtcTot, x));
		
		setDatosTomador(riskSelectionRequest, d, oPlyPlyC);

		// Asegurado
		if (hayDatosAsegurado(oPlyPlyC)) {

			List<OPlyAtcPC> lstOPlyAtcC = oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyAtcCT();

			Map<String, Object> valsAtc = lstOPlyAtcC.stream().collect(HashMap<String, Object>::new,
					(m, v) -> m.put(v.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldNam(),
							getEmptyString(v.getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldValVal())),
					HashMap::putAll);

			valsAtcTot.putAll(valsAtc);
			
			lstOPlyAtcC.stream()
	        	.filter(x -> x.getOPlyAtcC().getOPlyOcaPT() != null && !x.getOPlyAtcC().getOPlyOcaPT().isEmpty())
	        	.map(x -> x.getOPlyAtcC().getOPlyOcaPT())
	        	.flatMap(Collection::stream)
	        	.forEach(x -> addOcurrence(valsAtcTot, x));

			riskSelectionRequest.setInsured(new Insured());

			riskSelectionRequest.getInsured().setTheInsured(0);
			riskSelectionRequest.getInsured().setBirthDate(getLD(FEC_NACIMIENTO, valsAtc));
			riskSelectionRequest.getInsured().setGender(getI("MCA_SEXO", valsAtc));
			riskSelectionRequest.getInsured().setOccupationCode(getI("COD_OCUPACION", valsAtc));
			riskSelectionRequest.getInsured().setResident(getString(valsAtc.get("MCA_RESIDENTE_FISCAL")));
			riskSelectionRequest.getInsured().setResidenceCountry(getString(valsAtc.get(COD_PAIS_RESIDENTE_FISCAL)));
			riskSelectionRequest.getInsured().setDoesSports(getString(valsAtc.get("MCA_PRACTICA_DEPORTES")));
			riskSelectionRequest.getInsured().setNonFederatedSports(getI("COD_DEPORTE_NO_FEDERADO", valsAtc));
			riskSelectionRequest.getInsured().setFederatedSports(getI("COD_DEPORTE_COMPETICION", valsAtc));
			riskSelectionRequest.getInsured().setRidesMotorcycle(getString(valsAtc.get("MCA_USO_MOTO")));
			riskSelectionRequest.getInsured().setMotorcycleCC(getI("VAL_CC", valsAtc));
			riskSelectionRequest.getInsured().setMotorcycleHP(getI("VAL_POTENCIA", valsAtc));
			String staysAbroadPlanned = getString(valsAtc.get(MCA_ESTANCIAS_EN_EXTRANJERO));
			riskSelectionRequest.getInsured().setStaysAbroadPlanned(staysAbroadPlanned);
			if (staysAbroadPlanned.equals("S")) {
				// NUM_ESTANCIAS_EN_EXTRANJERO
				Optional<OPlyAtcPC> oPlyAtcPCEst = lstOPlyAtcC.stream().filter(x -> x.getOPlyAtcC().getOPlyAtrP()
						.getOPlyAtrS().getFldNam().equals("NUM_ESTANCIAS_EN_EXTRANJERO")).findFirst();
				Map<String, StayAbroad> staysAbroad = new HashMap<>();
				if (oPlyAtcPCEst.isPresent() && oPlyAtcPCEst.get().getOPlyAtcC() != null
						&& oPlyAtcPCEst.get().getOPlyAtcC().getOPlyOcaPT() != null) {
					int numEstancias = Integer
							.parseInt(oPlyAtcPCEst.get().getOPlyAtcC().getOPlyAtrP().getOPlyAtrS().getFldValVal());
					List<OPlyOcaP> lstOPlyOcaCPT = oPlyAtcPCEst.get().getOPlyAtcC().getOPlyOcaPT();
					for (int i = 1; i <= numEstancias; i++) {
						String codPaisExt = lstOPlyOcaCPT.get((i * 3) - 3).getOPlyOcaS().getFldValVal(); // COD_PAIS_EXTRANJERO
						String motivo = lstOPlyOcaCPT.get((i * 3) - 2).getOPlyOcaS().getFldValVal(); // TXT_MOTIVO_EXTRANJERO
						String duracion = lstOPlyOcaCPT.get((i * 3) - 1).getOPlyOcaS().getFldValVal(); // NUM_DURACION_EXTRANJERO

						StayAbroad sa = new StayAbroad();
						sa.setDuration(Integer.parseInt(duracion));
						sa.setReason(Integer.parseInt(motivo));

						staysAbroad.put(codPaisExt, sa);
					}
				}
				riskSelectionRequest.getInsured().setStaysAbroad(staysAbroad);
			} else {
				riskSelectionRequest.getInsured().setStaysAbroad(new HashMap<>());
			}
			riskSelectionRequest.getInsured().setFinalNaturalAge(getI("EDAD_NATURAL_FIN", valsAtc));
			riskSelectionRequest.getInsured().setAge(getI("EDAD_ACTUARIAL_INI", valsAtc));
			riskSelectionRequest.getInsured()
					.setAuthorizesPersonalDataUse(getString(valsAtc.get("MCA_AUTORIZA_USO_DATOS_ASEG")));
			riskSelectionRequest.getInsured().setMaritalStatusCode(0); // A revisar estos campos si no estan informados
			// con algo da error
			riskSelectionRequest.getInsured().setEducationLevelCode(0);
			riskSelectionRequest.getInsured().setTypeOfEmploymentCode(0);
			riskSelectionRequest.getInsured().setQuestionnaires(new HashMap<>());
			
			processQuestionnaires(riskSelectionRequest.getInsured().getQuestionnaires(), params, qteVal, d);
		}

		// Beneficiarios
		// List<OPlyIneP> lstOPlyIneP =
		riskSelectionRequest.getParticipants().setBeneficiaries(new HashMap<>());
		riskSelectionRequest.setFixedData(setFixedDataQuotation(oPlyPlyC));
		riskSelectionRequest.setProductId(getI("COD_MODALIDAD", valsAtcTot));
		riskSelectionRequest.setVariableData(valsAtcTot);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addOcurrence(Map<String, Object> valsAtcTot, OPlyOcaP x) {
		if (!valsAtcTot.containsKey(x.getOPlyOcaS().getFldNam())) {
			valsAtcTot.put(x.getOPlyOcaS().getFldNam(), new ArrayList<>());
		}
		((List)valsAtcTot.get(x.getOPlyOcaS().getFldNam())).add(x.getOPlyOcaS().getFldValVal());
	}

	private void setDatosTomador(RiskSelectionRequest riskSelectionRequest, final BasicData d, OPlyPlyC oPlyPlyC) {
		// Tomador
		if (hayDatosTomador(oPlyPlyC)) {
			riskSelectionRequest.setParticipants(new Participants());
			riskSelectionRequest.getParticipants().setPolicyHolder(new PolicyHolder());
			String policyHolderDocumentType = oPlyPlyC.getOPlyInePT().get(0).getOPlyIneS().getThpDcmTypVal();
			String policyHolderDocumentCode = oPlyPlyC.getOPlyInePT().get(0).getOPlyIneS().getThpDcmVal();

			OThpIpcPC oThpIpcPC = insuredPartyApi.getInsuredpartyv1(d.getCmpVal().intValueExact(), d.getUsrVal(), d.getLngVal(),
					policyHolderDocumentType, policyHolderDocumentCode);
			if (hayDatosInsuredParty(oThpIpcPC)) {
				OThpPrsS lvOThpPrsS = oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS();

				riskSelectionRequest.getParticipants().getPolicyHolder().setDocumentType(policyHolderDocumentType);
				riskSelectionRequest.getParticipants().getPolicyHolder().setDocumentCode(policyHolderDocumentCode);
				riskSelectionRequest.getParticipants().getPolicyHolder().setTypeOfPerson(getStringOrEmpty(lvOThpPrsS.getPhyPrs()));
				riskSelectionRequest.getParticipants().getPolicyHolder().setName(getStringOrEmpty(lvOThpPrsS.getThpNam()));
				riskSelectionRequest.getParticipants().getPolicyHolder().setBirthDate(getLD(lvOThpPrsS.getBrdDat()));
				riskSelectionRequest.getParticipants().getPolicyHolder().setNationality("");
				setNationality(riskSelectionRequest, lvOThpPrsS);
				riskSelectionRequest.getParticipants().getPolicyHolder().setSameAsInsured("N");
				riskSelectionRequest.getParticipants().getPolicyHolder().setGender(0);
				setSexVal(riskSelectionRequest, lvOThpPrsS);
				riskSelectionRequest.getParticipants().getPolicyHolder().setCountryOfBirth(getStringOrEmpty(lvOThpPrsS.getBrtCnyVal()));
				riskSelectionRequest.getParticipants().getPolicyHolder().setMaritalStatusCode(getMaritalStstusCode(lvOThpPrsS.getMrtStsVal()));
				riskSelectionRequest.getParticipants().getPolicyHolder()
						.setHasPublicServiceResponsibility(getStringOrEmpty(lvOThpPrsS.getPrsPce()));
				riskSelectionRequest.getParticipants().getPolicyHolder().setUsesLegalRepresentative("");

				riskSelectionRequest.getParticipants()
						.setPolicyHolderLegalRepresentative(new PolicyHolderLegalRepresentative());

				setParticipantsData(riskSelectionRequest, d, oThpIpcPC);
			}
		}
	}
	
	private int getMaritalStstusCode(String mrt) {
		if (StringUtils.isNotEmpty(mrt)) {
			switch (mrt) {
			case "S": return 1; // soltero
			case "C": return 2; // casado
			case "V": return 3; // viudo
			case "D": return 4; // divorciado
			case "P": return 5; // separado
			case "L": return 6; // ley comun
			case "U": return 7; // union civil
			default: return 0;
			}
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private void processQuestionnaires(Map<String, Map<String, Object>> questionnaires, Map<String, Object> params, String qteVal, final BasicData d) {
		if (params.containsKey(CUESTIONARIOS)) {
			List<Integer> cods = (List<Integer>)params.get(CUESTIONARIOS);
			for (Integer cod : cods) {
				try {
					List<OutDataFormulary> odfs = issueApi.getFormularyQuotation(d.getCmpVal().intValueExact(), qteVal, getI(ENR_SQN, params), getI(RSK_VAL, params), cod, d.getUsrVal(), d.getLngVal(), null);
					Map<String, Object> qd = new HashMap<>();
					questionnaires.put("" + cod, qd);
					for (OutDataFormulary odf : odfs) {
						qd.put(odf.getFldNam(), odf.getFldValVal());
					}
				} catch(Exception e) {
					log.info(e.getMessage());
				}
			}
		}
	}

	private void setParticipantsData(RiskSelectionRequest riskSelectionRequest, final BasicData d, OThpIpcPC oThpIpcPC) {
		if (oThpIpcPC.getOThpIpcC().getOThpLgrPT() != null && !oThpIpcPC.getOThpIpcC().getOThpLgrPT().isEmpty()
				&& oThpIpcPC.getOThpIpcC().getOThpLgrPT().get(0) != null
				&& oThpIpcPC.getOThpIpcC().getOThpLgrPT().get(0).getOThpLgrS() != null) {
			riskSelectionRequest.getParticipants().getPolicyHolder().setUsesLegalRepresentative("S");

			String legalRepresentativeDocumentCode = oThpIpcPC.getOThpIpcC().getOThpLgrPT().get(0).getOThpLgrS()
					.getLgrDcmVal();
			String legalRepresentativeDocumentType = oThpIpcPC.getOThpIpcC().getOThpLgrPT().get(0).getOThpLgrS()
					.getLgrDcmTypVal();

			OThpPrsP oThpIpcPCLR = genericThirdPartyApi.thirdPartybyActivity(d.getCmpVal().intValueExact(), 
					legalRepresentativeDocumentType, legalRepresentativeDocumentCode, d.getUsrVal(), 45, d.getLngVal());
			
	
			if (oThpIpcPCLR != null && oThpIpcPCLR.getOThpPrsS() != null) {
				OThpPrsS lvOThpPrsSLR = oThpIpcPCLR.getOThpPrsS();

				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setBirthDate(getLD(lvOThpPrsSLR.getBrdDat()));
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setCountryOfResidence("");
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setTypeOfPerson(getEmptyString(lvOThpPrsSLR.getPhyPrs()));
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setDocumentCode(legalRepresentativeDocumentCode);
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setDocumentType(legalRepresentativeDocumentType);
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setGender(0);
				setSexValLR(riskSelectionRequest, lvOThpPrsSLR);
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setHasPublicServiceResponsibility(getEmptyString(lvOThpPrsSLR.getPrsPce()));
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setName(getEmptyString(lvOThpPrsSLR.getThpNam()));
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setNationality(getEmptyString(lvOThpPrsSLR.getNtnVal()));
				riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
						.setMaritalStatusCode(getMaritalStstusCode(lvOThpPrsSLR.getMrtStsVal()));
			} else {
				emptyLegalRepresentative(riskSelectionRequest);
			}
		} else {
			emptyLegalRepresentative(riskSelectionRequest);
		}
	}
	
	private void setSexValLR(RiskSelectionRequest riskSelectionRequest, OThpPrsS lvOThpPrsS) {
		if (lvOThpPrsS.getSexVal() != null && !lvOThpPrsS.getSexVal().isEmpty()) {
			riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
					.setGender(Integer.parseInt(lvOThpPrsS.getSexVal()));
		}
	}

	private void emptyLegalRepresentative(RiskSelectionRequest riskSelectionRequest) {
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
				.setBirthDate(LocalDate.now());
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
				.setCountryOfResidence("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setDocumentCode("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setDocumentType("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setGender(0);
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
				.setHasPublicServiceResponsibility("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setName("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative().setNationality("");
		riskSelectionRequest.getParticipants().getPolicyHolderLegalRepresentative()
				.setMaritalStatusCode(0);
	}

	private void setSexVal(RiskSelectionRequest riskSelectionRequest, OThpPrsS lvOThpPrsS) {
		if (lvOThpPrsS.getSexVal() != null && !lvOThpPrsS.getSexVal().isEmpty()) {
			riskSelectionRequest.getParticipants().getPolicyHolder()
					.setGender(Integer.parseInt(lvOThpPrsS.getSexVal()));
		}
	}

	private void setNationality(RiskSelectionRequest riskSelectionRequest, OThpPrsS lvOThpPrsS) {
		if (lvOThpPrsS.getNtnVal() != null && !lvOThpPrsS.getNtnVal().isEmpty()) {
			riskSelectionRequest.getParticipants().getPolicyHolder().setNationality(lvOThpPrsS.getNtnVal());
		}
	}

	private Map<String, Object> setFixedDataQuotation(OPlyPlyC oPlyPlyC) {
		Map<String, Object> fixedData = new HashMap<>();

		// ina
		if (oPlyPlyC.getOPlyInaPT() != null && oPlyPlyC.getOPlyInaPT().stream().findFirst().isPresent()) {
			Optional<OPlyInaP> oPlyInaP = oPlyPlyC.getOPlyInaPT().stream().findFirst();
			if (oPlyInaP.isPresent()) {
				fixedData.put("level1", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getFrsLvlVal()));
				fixedData.put("level2", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getScnLvlVal()));
				fixedData.put("level3", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getThrLvlVal()));
				fixedData.put("channel1", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getFrsDstHnlVal()));
				fixedData.put("channel2", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getScnDstHnlVal()));
				fixedData.put("channel3", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getThrDstHnlVal()));
				fixedData.put("thirdPartyCode", getObjectOrEmpty(oPlyInaP.get().getOPlyInaS().getThpVal()));
			}
		}

		// gni
		if (oPlyPlyC.getOPlyGniP() != null && oPlyPlyC.getOPlyGniP().getOPlyGniS() != null) {
			OPlyGniS oPlyGniS = oPlyPlyC.getOPlyGniP().getOPlyGniS();

			fixedData.put("contractNumber", getObjectOrEmpty(oPlyGniS.getDelVal()));
			fixedData.put("subcontract", getObjectOrEmpty(oPlyGniS.getSblVal()));
			fixedData.put("groupPolicyNumber", getObjectOrEmpty(oPlyGniS.getGppVal()));
			fixedData.put("supplementCode", getObjectOrEmpty(oPlyGniS.getEnrVal()));
			fixedData.put("supplementSubcode", getObjectOrEmpty(oPlyGniS.getEnrSbdVal()));
			fixedData.put("supplementNumber", getObjectOrEmpty(oPlyGniS.getEnrSqn()));
			fixedData.put("supplementType", getObjectOrEmpty(oPlyGniS.getEnrTypVal()));
			fixedData.put("clientPolicy", getObjectOrEmpty(oPlyGniS.getClpVal()));
			fixedData.put("policyNumber", getObjectOrEmpty(oPlyGniS.getPlyVal()));
			fixedData.put("validityDate", getObjectOrEmpty(oPlyGniS.getVldDat()));
			fixedData.put("policyEffectiveDate", getObjectOrEmpty(oPlyGniS.getPlyEfcDat()));
			fixedData.put("policyExpirationDate", getObjectOrEmpty(oPlyGniS.getPlyExpDat()));
			fixedData.put("supplementEffectiveDate", getObjectOrEmpty(oPlyGniS.getEnrEfcDat()));
			fixedData.put("supplementExpirationDate", getObjectOrEmpty(oPlyGniS.getEnrExpDat()));
			fixedData.put("currency", getObjectOrEmpty(oPlyGniS.getCrnVal()));
			fixedData.put("applyProrata", getObjectOrEmpty(oPlyGniS.getPtaPly()));
			fixedData.put("applicationNumber", getObjectOrEmpty(oPlyGniS.getAplVal()));
			fixedData.put("supplementApplicationNumber", getObjectOrEmpty(oPlyGniS.getAplEnrSqn()));
			fixedData.put("policyDuration", getObjectOrEmpty(oPlyGniS.getPlyDrtVal()));
			fixedData.put("paymentModality", getObjectOrEmpty(oPlyGniS.getPmsVal()));
			fixedData.put("renovationsNumber", getObjectOrEmpty(oPlyGniS.getRnwVal()));
			fixedData.put("applyRegularization", getObjectOrEmpty(oPlyGniS.getPlyRgl()));
			fixedData.put("regularizationType", getObjectOrEmpty(oPlyGniS.getRglTypVal()));
			fixedData.put("regularizationPercentage", getObjectOrEmpty(oPlyGniS.getRglPer()));
			fixedData.put("regularizationIndex", getObjectOrEmpty(oPlyGniS.getRglIdxVal()));
			fixedData.put("manualPremium", getObjectOrEmpty(oPlyGniS.getMnlPre()));
			fixedData.put("temporalSupplement", getObjectOrEmpty(oPlyGniS.getTmpEnr()));
			fixedData.put("policyType", getObjectOrEmpty(oPlyGniS.getTnrPlyTypVal()));
			fixedData.put("changeRate", getObjectOrEmpty(oPlyGniS.getExrVal()));
			fixedData.put("sector", getObjectOrEmpty(oPlyGniS.getSecVal()));
			fixedData.put("totalRisk", getObjectOrEmpty(oPlyGniS.getTotRskVal()));
			fixedData.put("coinsuranceType", getObjectOrEmpty(oPlyGniS.getCinTypVal()));
			fixedData.put("supplementMotivation", getObjectOrEmpty(oPlyGniS.getEnrCasVal()));
			fixedData.put("sendType", getObjectOrEmpty(oPlyGniS.getSndVal()));
			fixedData.put("manualReinsurance", getObjectOrEmpty(oPlyGniS.getMnlRnsDst()));
			fixedData.put("captureThirdLevel", getObjectOrEmpty(oPlyGniS.getCptThrLvlVal()));
			fixedData.put("reinsuranceType", getObjectOrEmpty(oPlyGniS.getRnsTypVal()));

			fixedData.put("businessType", "");
			fixedData.put("initialQuota", "");
			fixedData.put("declarationPolicy", "");
			fixedData.put("commissionTable", "");
		}

		return fixedData;
	}

	private Object getObjectOrEmpty(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj;
	}
	
	private String getStringOrEmpty(String obj) {
		if (obj == null) {
			return "";
		}
		return obj;
	}

	private LocalDate getLD(Long date) {
		if (date != null) {
			return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return LocalDate.now();
	}

	private boolean hayDatosTomador(OPlyPlyC oPlyPlyC) {
		return oPlyPlyC.getOPlyInePT() != null && oPlyPlyC.getOPlyInePT().get(0) != null
				&& oPlyPlyC.getOPlyInePT().get(0).getOPlyIneS() != null;
	}

	private boolean hayDatosInsuredParty(OThpIpcPC oThpIpcPC) {
		return oThpIpcPC != null && oThpIpcPC.getOThpIpcC() != null && oThpIpcPC.getOThpIpcC().getOThpPrsP() != null
				&& oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS() != null;
	}

	private boolean hayDatosAsegurado(OPlyPlyC oPlyPlyC) {
		return oPlyPlyC.getOPlyRkcCT().get(0) != null && oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC() != null
				&& oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyInePT() != null;
	}
}
