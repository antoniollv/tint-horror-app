package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.acf.dag.dl.IDlAcfDagDAO;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("DagCms")
public class ValidationApiServiceDagCms implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String AGN_VAL = "AGN_VAL";

	protected static final String CMS_CSS_TYP_VAL = "CMS_CSS_TYP_VAL";

	protected static final String CLO_PYM_CNC_VAL = "CLO_PYM_CNC_VAL";

	protected static final String INL_CMS_DAT = "INL_CMS_DAT";

	protected static final String FNL_CMS_DAT = "FNL_CMS_DAT";

	protected static final String VLD_DAT = "VLD_DAT";

	protected static final String CMS_AMN = "CMS_AMN";

	protected static final String CMS_PER_APL = "CMS_PER_APL";

	protected static final String CMS_PRD_NAM = "CMS_PRD_NAM";

	protected static final String CRN_VAL = "CRN_VAL";    

	protected static final String MNM_CLO_AMN = "MNM_CLO_AMN";

	protected static final String CMS_APL_TYP_VAL = "CMS_APL_TYP_VAL";

	@Autowired
	IDlThpCmcDAO dlThpCmcDAO;

	@Autowired
	DlTrnErr lvDlTrnError;

	@Autowired
	IDlTrnPrcDAO dlTrnPrcDAO;

	@Autowired
	IDlAcfDagDAO dlAcfDagDAO;

	

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {


		ValidationError errorDagCms;
		List<ValidationError> errorsDagCms = new ArrayList<>();		
		String accion;

		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, AGN_VAL, CMS_CSS_TYP_VAL, CLO_PYM_CNC_VAL, INL_CMS_DAT, FNL_CMS_DAT, VLD_DAT);

			errorsDagCms = compruebaErroresDagCms(noModificables, body, cmpVal);
		}

		// Variable fecha hasta... para utilizarla en la comparacion de fechas
		Date fnlCmsDat = Date.from(LocalDateTime.parse(body.getData().get(FNL_CMS_DAT)).atZone(ZoneId.systemDefault()).toInstant());

		// Variable fecha desde... para utilizarla en la comparacion de fechas
		Date inlCmsDat = Date.from(LocalDateTime.parse(body.getData().get(INL_CMS_DAT)).atZone(ZoneId.systemDefault()).toInstant());

		validacionesExtras(fnlCmsDat, inlCmsDat, body, errorsDagCms, cmpVal);


		// Validar agente existe
		try {
			if(body.getData().get(AGN_VAL) != null  && !(body.getData().get(AGN_VAL).equals(""))){
				dlThpCmcDAO.getCodAgente(cmpVal, new BigDecimal (body.getData().get(AGN_VAL)), body.getUser().getLanguage().toUpperCase());
			}
		}
		catch (NwtException e) {
			errorDagCms = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(AGN_VAL)).build();
			errorsDagCms.add(errorDagCms);
		}

		// Validar moneda existe
		try {
			if((body.getData().get(CRN_VAL) != null  && !(body.getData().get(CRN_VAL).equals("")))) {

				BigDecimal crnVal = new BigDecimal(body.getData().get(CRN_VAL));

				dlAcfDagDAO.get_moneda(cmpVal, crnVal, body.getUser().getLanguage().toUpperCase());
			}
		}
		catch (NwtException e) {
			errorDagCms = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CRN_VAL)).build();
			errorsDagCms.add(errorDagCms);
		}

		// Validar que el procedimiento que devuelve el importe o porcentaje de comisiones a aplicar
		try {
			if(body.getData().get(CMS_PRD_NAM) != null  && !(body.getData().get(CMS_PRD_NAM).equals(""))){
				dlTrnPrcDAO.getNomPrg(body.getData().get(CMS_PRD_NAM),body.getUser().getLanguage().toUpperCase(), cmpVal);
			}
		}
		catch (NwtException e) {
			errorDagCms = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CMS_PRD_NAM)).build();
			errorsDagCms.add(errorDagCms);
		}		

		//Validamos CMS_CSS_TYP_VAL
		comprobarCmsCssTypVal(body, cmpVal, errorsDagCms);		

		return errorsDagCms;
	}

	private void validacionesExtras(Date fnlCmsDat, Date inlCmsDat, DataInExtended body,
			List<ValidationError> errorsDagCms, BigDecimal cmpVal) {

		ValidationError errorDagCms;
		BigDecimal codError;
		OTrnErrS lvError;

		// Validamos que la fecha hasta que se concede la comision (FNL_CMS_DAT) es mayor que la fecha desde que se concede la comision (INL_CMS_DAT)
		if(body.getData().get(FNL_CMS_DAT) !=null && body.getData().get(INL_CMS_DAT) !=null &&((fnlCmsDat).before(inlCmsDat))) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", FNL_CMS_DAT, cmpVal);
			errorDagCms = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FNL_CMS_DAT)).build();
			errorsDagCms.add(errorDagCms);

		}
		// Validamos que el importe de comision sea mayor que cero
		if(body.getData().get(CMS_AMN) !=null &&
				((new BigDecimal(body.getData().get(CMS_AMN))).compareTo(BigDecimal.ZERO)) < 0
				){
			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_AMN, cmpVal);
			errorDagCms = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_AMN)).build();
			errorsDagCms.add(errorDagCms);

		}

		// Validamos que porcentaje de comisiones a aplicar tenga valores entre 0 y 100
		if(body.getData().get(CMS_PER_APL) !=null && 
				(((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(BigDecimal.ZERO)) < 0 || ((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(new BigDecimal(100))) > 0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PER_APL,cmpVal);
			errorDagCms = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PER_APL)).build();
			errorsDagCms.add(errorDagCms);

		}

		// Validamos que el importe minimo a cobrar para percibir la subvencion mensual sea mayor que cero
		if(body.getData().get(MNM_CLO_AMN) !=null && 
				((new BigDecimal(body.getData().get(MNM_CLO_AMN))).compareTo(BigDecimal.ZERO)) < 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", MNM_CLO_AMN, cmpVal);
			errorDagCms = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MNM_CLO_AMN)).build();
			errorsDagCms.add(errorDagCms);

		}

	}

	private void comprobarCmsCssTypVal(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errorsDagCms){

		if(body.getData().get(CMS_CSS_TYP_VAL) !=null && (body.getData().get(CMS_CSS_TYP_VAL).equals("2"))) {

			if (body.getData().get(CMS_APL_TYP_VAL) != null && body.getData().get(CMS_APL_TYP_VAL).equals("1")) {
				comprobarCmsAmnUno(body, cmpVal, errorsDagCms);

			} else if (body.getData().get(CMS_APL_TYP_VAL) != null && body.getData().get(CMS_APL_TYP_VAL).equals("2")) {
				comprobarCmsAmnDos(body, cmpVal, errorsDagCms);

			} else if (body.getData().get(CMS_APL_TYP_VAL) != null && body.getData().get(CMS_APL_TYP_VAL).equals("3")) {
				comprobarCmsAmnTres(body, cmpVal, errorsDagCms);

			}
		}

	}

	private void comprobarCmsAmnUno(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errorsDagCms){

		ValidationError errorCmsAmnUno = null;
		BigDecimal codError;
		OTrnErrS lvError;

		if (body.getData().get(CMS_AMN) !=null && 
				(new BigDecimal(body.getData().get(CMS_AMN)).compareTo(BigDecimal.ZERO)) < 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_AMN, cmpVal);
			errorCmsAmnUno = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_AMN)).build();
			errorsDagCms.add(errorCmsAmnUno);

		}
		if (body.getData().get(CRN_VAL) !=null && (Integer.parseInt(body.getData().get(CRN_VAL))<0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CRN_VAL, cmpVal);
			errorCmsAmnUno = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CRN_VAL)).build();
			errorsDagCms.add(errorCmsAmnUno);

		}
		if (body.getData().get(MNM_CLO_AMN) !=null && 
				(new BigDecimal(body.getData().get(MNM_CLO_AMN)).compareTo(BigDecimal.ZERO)) < 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", MNM_CLO_AMN, cmpVal);
			errorCmsAmnUno = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MNM_CLO_AMN)).build();
			errorsDagCms.add(errorCmsAmnUno);

		}
		if (body.getData().get(CMS_PRD_NAM) !=null && !"".equals(body.getData().get(CMS_PRD_NAM))) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PRD_NAM, cmpVal);
			errorCmsAmnUno = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PRD_NAM)).build();
			errorsDagCms.add(errorCmsAmnUno);

		}
		if (body.getData().get(CMS_PER_APL) !=null && (((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(BigDecimal.ZERO)) < 0 || ((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(BigDecimal.ZERO)) > 0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PER_APL, cmpVal);
			errorCmsAmnUno = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PER_APL)).build();
			errorsDagCms.add(errorCmsAmnUno);

		}
	}

	private void comprobarCmsAmnDos(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errorsDagCms){

		ValidationError errorCmsAmnDos = null;
		BigDecimal codError;
		OTrnErrS lvError;

		if (body.getData().get(CMS_AMN) !=null && 
				(new BigDecimal(body.getData().get(CMS_AMN)).compareTo(BigDecimal.ZERO)) != 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_AMN, cmpVal);
			errorCmsAmnDos = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_AMN)).build();
			errorsDagCms.add(errorCmsAmnDos);

		} 
		if (body.getData().get(CRN_VAL) !=null && (Integer.parseInt(body.getData().get(CRN_VAL)) != 1)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CRN_VAL, cmpVal);
			errorCmsAmnDos = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CRN_VAL)).build();
			errorsDagCms.add(errorCmsAmnDos);

		} 
		if (body.getData().get(MNM_CLO_AMN) !=null && 
				(new BigDecimal(body.getData().get(MNM_CLO_AMN)).compareTo(BigDecimal.ZERO)) != 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", MNM_CLO_AMN, cmpVal);
			errorCmsAmnDos = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MNM_CLO_AMN)).build();
			errorsDagCms.add(errorCmsAmnDos);

		} 
		if (body.getData().get(CMS_PRD_NAM) !=null && !"".equals(body.getData().get(CMS_PRD_NAM))) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PRD_NAM, cmpVal);
			errorCmsAmnDos = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PRD_NAM)).build();
			errorsDagCms.add(errorCmsAmnDos);

		} 
		if (body.getData().get(CMS_PER_APL) !=null && (((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(BigDecimal.ZERO)) < 0 || ((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(new BigDecimal(100))) > 0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PER_APL, cmpVal);
			errorCmsAmnDos = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PER_APL)).build();
			errorsDagCms.add(errorCmsAmnDos);

		}
	}

	private void comprobarCmsAmnTres(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errorsDagCms){

		ValidationError errorCmsAmnTres = null;
		BigDecimal codError;
		OTrnErrS lvError;

		if (body.getData().get(CMS_AMN) !=null && 
				(new BigDecimal(body.getData().get(CMS_AMN)).compareTo(BigDecimal.ZERO)) != 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_AMN, cmpVal);
			errorCmsAmnTres = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_AMN)).build();
			errorsDagCms.add(errorCmsAmnTres);

		}
		if (body.getData().get(CRN_VAL) !=null && (Integer.parseInt(body.getData().get(CRN_VAL)) != 1)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CRN_VAL, cmpVal);
			errorCmsAmnTres = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CRN_VAL)).build();
			errorsDagCms.add(errorCmsAmnTres);

		}
		if (body.getData().get(MNM_CLO_AMN) !=null && 
				(new BigDecimal(body.getData().get(MNM_CLO_AMN)).compareTo(BigDecimal.ZERO)) != 0
				) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", MNM_CLO_AMN, cmpVal);
			errorCmsAmnTres = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MNM_CLO_AMN)).build();
			errorsDagCms.add(errorCmsAmnTres);

		}
		if (body.getData().get(CMS_PRD_NAM) == null || "".equals(body.getData().get(CMS_PRD_NAM))) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PRD_NAM, cmpVal);
			errorCmsAmnTres = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PRD_NAM)).build();
			errorsDagCms.add(errorCmsAmnTres);

		}
		if (body.getData().get(CMS_PER_APL) !=null && (((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(BigDecimal.ZERO)) < 0 || ((new BigDecimal(body.getData().get(CMS_PER_APL))).compareTo(new BigDecimal(100))) > 0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PER_APL, cmpVal);
			errorCmsAmnTres = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PER_APL)).build();
			errorsDagCms.add(errorCmsAmnTres);

		}
	}



	private List<ValidationError> compruebaErroresDagCms(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsDagCms = new ArrayList<>();
		ValidationError errorDagCms;
		OTrnErrS lvErrorDagCms;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorDagCms = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMP_VAL,
						cmpVal);
				errorDagCms = ValidationError.builder().code(lvErrorDagCms.getErrIdnVal()).field(lvErrorDagCms.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsDagCms.add(errorDagCms);
			}
		}
		return errorsDagCms;

	}

} 


