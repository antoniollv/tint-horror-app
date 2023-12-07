package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.api.thp.client.api.InsuredPartyApi;
import com.mapfre.tron.api.thp.client.model.OThpAdrP;
import com.mapfre.tron.api.thp.client.model.OThpAdrS;
import com.mapfre.tron.api.thp.client.model.OThpCntP;
import com.mapfre.tron.api.thp.client.model.OThpCntS;
import com.mapfre.tron.api.thp.client.model.OThpIpcPC;
import com.mapfre.tron.api.thp.client.model.OThpPrsS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The InsuredPartySfv SfvBean implementation. Get the insured party information
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("InsuredPartySfv")
public class InsuredPartySfvBeanImpl extends SfvWithConditionalBeanBase {

	@Autowired
	protected InsuredPartyApi insuredPartyApi;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("InsuredPartySfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String tipDocum = getString(in.getParameters().get("TIP_DOCUM"));
		String codDocum = getString(in.getParameters().get("COD_DOCUM"));
		boolean skipError = params.containsKey("skipError") && Boolean.parseBoolean(params.get("skipError").toString());
		boolean dataOutput = !params.containsKey("dataOutput")
				|| Boolean.parseBoolean(params.get("dataOutput").toString());
		String adrUseTypValRes = StringUtils.defaultIfEmpty(getString(params.get("adrUseTypValRes")), "1");
		String adrUseTypValCom = StringUtils.defaultIfEmpty(getString(params.get("adrUseTypValCom")), "3");
		String cnhTypValEmail = StringUtils.defaultIfEmpty(getString(params.get("cnhTypValEmail")), "1");
		String cnhTypValTfno = StringUtils.defaultIfEmpty(getString(params.get("cnhTypValTfno")), "2");

		if (StringUtils.isEmpty(tipDocum) || StringUtils.isEmpty(codDocum)) {
			Message lvMessagesItem = getMessage("TIP_DOCUM/COD_DOCUM", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {

			try {
				OThpIpcPC oThpIpcPC = insuredPartyApi.getInsuredpartyv1(cmpVal.intValueExact(), usrVal, lngVal,
						tipDocum, codDocum);
				innerData.set("InsuredPartySfv", oThpIpcPC);

				if (hayDatos(oThpIpcPC) && dataOutput) {
					OThpPrsS lvOThpPrsS = oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS();
					String nomTercero = getString(in.getParameters().get("NOM_TERCERO"));

					// Data not completed from previous beans
					setThrData(lvSfvOut, lvOThpPrsS, nomTercero);

					lvSfvOut.getParameters().put("COD_EST_CIVIL", lvOThpPrsS.getMrtStsVal());
					lvSfvOut.getParameters().put("MCA_PERS_EXP_POLITICA", lvOThpPrsS.getPrsPce());
					lvSfvOut.getParameters().put("COD_OCUPACION", lvOThpPrsS.getJobVal());
					lvSfvOut.getParameters().put("TIP_EMPLEO", lvOThpPrsS.getPrfVal());
					lvSfvOut.getParameters().put("COD_NACIONALIDAD", lvOThpPrsS.getNtnVal());
					lvSfvOut.getParameters().put("COD_ESTUDIOS", lvOThpPrsS.getStdVal());
					lvSfvOut.getParameters().put("COD_PAIS_NACIMIENTO", lvOThpPrsS.getBrtCnyVal());

					setDir(lvSfvOut, adrUseTypValRes, oThpIpcPC);

					setDirCom(lvSfvOut, adrUseTypValCom, oThpIpcPC);

					setContactEmail(lvSfvOut, cnhTypValEmail, oThpIpcPC);

					setContactTlf(lvSfvOut, cnhTypValTfno, oThpIpcPC);
				} else {
					processNotFound(lvSfvOut, skipError);
				}

			} catch (RestClientResponseException e) {
				processApiError(lvSfvOut, skipError, e);
			} catch (Exception e) {
				String msg = String.format("Error calling insuredPartyApi.getInsuredpartyv1 %s", e.getMessage());
				log.error(msg);
				Message lvMessagesItem = getMessage(msg, 3, 400, null);
				lvSfvOut.addMessagesItem(lvMessagesItem);
			}
		}
		return lvSfvOut;
	}

	private void setContactTlf(SfvOut lvSfvOut, String cnhTypValTfno, OThpIpcPC oThpIpcPC) {
		OThpCntS cnt = hayDatosContacto(oThpIpcPC, cnhTypValTfno);
		if (cnt != null) {
			lvSfvOut.getParameters().put("TLF_MOVIL", cnt.getCnhTxtVal());
		}
	}

	private void setContactEmail(SfvOut lvSfvOut, String cnhTypValEmail, OThpIpcPC oThpIpcPC) {
		OThpCntS cnt = hayDatosContacto(oThpIpcPC, cnhTypValEmail);
		if (cnt != null) {
			lvSfvOut.getParameters().put("TXT_EMAIL", cnt.getCnhTxtVal());
			lvSfvOut.getParameters().put("MCA_EMAIL_COMPROBADO", cnt.getCnhCck());
		}
	}

	private void setDirCom(SfvOut lvSfvOut, String adrUseTypValCom, OThpIpcPC oThpIpcPC) {
		OThpAdrS dir = hayDatosDireccion(oThpIpcPC, adrUseTypValCom);
		if (dir != null) {
			lvSfvOut.getParameters().put("COD_PAIS_COM", dir.getCnyVal());
			lvSfvOut.getParameters().put("COD_ESTADO_COM", dir.getSttVal());
			lvSfvOut.getParameters().put("COD_PROV_COM", dir.getPvcVal());
			lvSfvOut.getParameters().put("COD_LOCALIDAD_COM", dir.getTwnVal());
			lvSfvOut.getParameters().put("COD_POSTAL_COM", dir.getPslCodVal());
			lvSfvOut.getParameters().put("TIP_DOMICILIO_COM", dir.getDmlTypVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO1_COM", dir.getAdrTxtVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO2_COM", dir.getAdrNbrVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO3_COM", dir.getAdrFlrVal());
			lvSfvOut.getParameters().put("ATR_DOMICILIO4_COM", dir.getAdrDorVal());
		}
	}

	private void setDir(SfvOut lvSfvOut, String adrUseTypValRes, OThpIpcPC oThpIpcPC) {
		OThpAdrS dir = hayDatosDireccion(oThpIpcPC, adrUseTypValRes);
		if (dir != null) {
			lvSfvOut.getParameters().put("COD_PAIS", dir.getCnyVal());
			lvSfvOut.getParameters().put("COD_ESTADO", dir.getSttVal());
			lvSfvOut.getParameters().put("COD_PROV", dir.getPvcVal());
			lvSfvOut.getParameters().put("COD_LOCALIDAD", dir.getTwnVal());
			lvSfvOut.getParameters().put("COD_POSTAL", dir.getPslCodVal());
			lvSfvOut.getParameters().put("TIP_DOMICILIO", dir.getDmlTypVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO1", dir.getAdrTxtVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO2", dir.getAdrNbrVal());
			lvSfvOut.getParameters().put("NOM_DOMICILIO3", dir.getAdrFlrVal());
			lvSfvOut.getParameters().put("ATR_DOMICILIO4", dir.getAdrDorVal());
		}
	}

	private void setThrData(SfvOut lvSfvOut, OThpPrsS lvOThpPrsS, String nomTercero) {
		if (StringUtils.isEmpty(nomTercero)) {
			lvSfvOut.getParameters().put("NOM_TERCERO", lvOThpPrsS.getThpNam());
			lvSfvOut.getParameters().put("APE1_TERCERO", lvOThpPrsS.getThpFrsSrn());
			lvSfvOut.getParameters().put("APE2_TERCERO", lvOThpPrsS.getThpScnSrn());
			lvSfvOut.getParameters().put("MCA_SEXO", lvOThpPrsS.getSexVal());
			lvSfvOut.getParameters().put("MCA_FISICO", lvOThpPrsS.getPhyPrs());
			lvSfvOut.getParameters().put("FEC_NACIMIENTO", formatDate(lvOThpPrsS.getBrdDat()));
		}
	}

	private boolean hayDatos(OThpIpcPC oThpIpcPC) {
		return oThpIpcPC != null && oThpIpcPC.getOThpIpcC() != null && oThpIpcPC.getOThpIpcC().getOThpPrsP() != null
				&& oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS() != null;
	}

	private OThpAdrS hayDatosDireccion(OThpIpcPC oThpIpcPC, String adrUseTypVal) {
		if (oThpIpcPC.getOThpIpcC().getOThpAdrPT() != null && !oThpIpcPC.getOThpIpcC().getOThpAdrPT().isEmpty()) {
			Optional<OThpAdrP> d = oThpIpcPC.getOThpIpcC().getOThpAdrPT().stream()
					.filter(x -> "N".equals(x.getOThpAdrS().getDsbRow())
							&& adrUseTypVal.equals(x.getOThpAdrS().getAdrUseTypVal())
							&& "S".equals(x.getOThpAdrS().getDflAdr()))
					.findFirst();
			if (d.isPresent()) {
				return d.get().getOThpAdrS();
			}

			d = oThpIpcPC.getOThpIpcC().getOThpAdrPT().stream().filter(x -> "N".equals(x.getOThpAdrS().getDsbRow())
					&& adrUseTypVal.equals(x.getOThpAdrS().getAdrUseTypVal())).findFirst();
			if (d.isPresent()) {
				return d.get().getOThpAdrS();
			}
		}
		return null;
	}

	private OThpCntS hayDatosContacto(OThpIpcPC oThpIpcPC, String cnhTypVal) {
		if (oThpIpcPC.getOThpIpcC().getOThpAdrPT() != null && !oThpIpcPC.getOThpIpcC().getOThpCntPT().isEmpty()) {
			Optional<OThpCntP> d = oThpIpcPC.getOThpIpcC().getOThpCntPT().stream()
					.filter(x -> "N".equals(x.getOThpCntS().getDsbRow())
							&& cnhTypVal.equals(x.getOThpCntS().getCnhTypVal())
							&& "S".equals(x.getOThpCntS().getDflCnh()))
					.findFirst();
			if (d.isPresent()) {
				return d.get().getOThpCntS();
			}

			d = oThpIpcPC.getOThpIpcC().getOThpCntPT().stream().filter(
					x -> "N".equals(x.getOThpCntS().getDsbRow()) && cnhTypVal.equals(x.getOThpCntS().getCnhTypVal()))
					.findFirst();
			if (d.isPresent()) {
				return d.get().getOThpCntS();
			}
		}
		return null;
	}

	private Object formatDate(Long date) {
		return date != null ? sdf.format(new Date(date)) : "";
	}

	private void processNotFound(SfvOut lvSfvOut, boolean skipError) {
		if (!skipError) {
			Message lvMessagesItem = getMessage("NOT FOUND", 3, 20001, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		}
	}
}
