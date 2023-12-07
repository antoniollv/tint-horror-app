package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.grz.dl.IDlCmnGrzDAO;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.grs.znf.dl.IDlGrsZnfDAO;
import com.mapfre.tron.api.grs.zno.dl.IDlGrsZnoDAO;
import com.mapfre.tron.api.grs.znt.dl.IDlGrsZntDAO;
import com.mapfre.tron.api.grs.zof.dl.IDlGrsZofDAO;
import com.mapfre.tron.api.grs.zot.dl.IDlGrsZotDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("ZnfSbd")
public class ValidationApiServiceZnfSbd implements ValidationApiService {

	@Autowired
	IDlCmnGrzDAO iDlCmnGrzDAO;

	@Autowired
	IDlGrsZnoDAO iDlGrsZnoDAO;
	
	@Autowired
	IDlGrsZntDAO iDlGrsZntDAO;
	
	@Autowired
	IDlGrsZotDAO iDlGrsZotDAO;

	@Autowired
	IDlGrsZnfDAO iDlGrsZnfDAO;
	
	@Autowired
	IDlGrsZofDAO iDlGrsZofDAO;
	
	@Autowired
	IDlCmnLngDAO iDlCmnLngDAO;
	
	OCmnGrzS oCmnGrzS;
	OGrsZnoS oGrsZnoS;
	OGrsZntS oGrsZntS;
	OGrsZotS oGrsZotS;
	OGrsZnfS oGrsZnfS;
	OGrsZofS oGrsZofS;
	OCmnLngS lvOCmnLngS;

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
		OTrnErrS lvError;
		BigDecimal codError;
		
		// No tenemos campo CMP_VAL así que obtenemos la compañía del usuario
		BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		}
		else {
			accion = CTrn.ACN_TYP_MDF;
		}

		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    	// Pk Subcodigo zona cuatro greográfica real no se puede modificar
			if(!body.getDataOld().get("DIT_VAL").equals(body.getData().get("DIT_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "DIT_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("DIT_VAL")).build();
				errors.add(error);
			}
			
			// Pk Idioma no se puede modificar
			if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
		    		codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL")).build();
				errors.add(error);
			}
		    
			// Pk zona uno geográfica no se puede modificar 
			if(!body.getDataOld().get("CNY_VAL").equals(body.getData().get("CNY_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "CNY_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CNY_VAL")).build();
				errors.add(error);
			}  

			// Pk zona dos geográfica no se puede modificar
			if(!body.getDataOld().get("STT_VAL").equals(body.getData().get("STT_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "STT_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("STT_VAL")).build();
				errors.add(error);
			}
			
			// Pk zona tres geográfica no se puede modificar
			if(!body.getDataOld().get("PVC_VAL").equals(body.getData().get("PVC_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "PVC_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("PVC_VAL")).build();
				errors.add(error);
			}
			
			// Pk zona cuatro geográfica no se puede modificar
			if(!body.getDataOld().get("TWN_VAL").equals(body.getData().get("TWN_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "TWN_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("TWN_VAL")).build();
				errors.add(error);
			} 
			
		}
		
		
		// Validamos si el campo Subcodigo zona cuatro greográfica real está informado 
		   if (body.getData().get("DIT_VAL") == null || body.getData().get("DIT_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "DIT_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DIT_VAL")).build();
			errors.add(error);
		   }
		
		// Validamos si el campo Fecha validez está informado 
		   if (body.getData().get("VLD_DAT") == null || body.getData().get("VLD_DAT").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "VLD_DAT", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VLD_DAT")).build();
			errors.add(error);
		   }
		   
		// Validamos si el Distrito está informado 
		   if (body.getData().get("DIT_NAM") == null || body.getData().get("DIT_NAM").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "DIT_NAM", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DIT_NAM")).build();
			errors.add(error);
		   }
		   

		/*
		 * Validacion del pais
		 */
		try {
		    
			// Validamos si el campo pais está informado
			   if(body.getData().get("CNY_VAL") == null || body.getData().get("CNY_VAL").equals("")) {
			       	codError = new BigDecimal(20003);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "CNY_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("CNY_VAL")).build();
				errors.add(error);
			   }
			   else {// Si su contenido no es nulo ni vacio, lo creamos
			       String cnyVal = body.getData().get("CNY_VAL");
			       String usrLngVal =  body.getUser().getLanguage().toUpperCase();
				
				oGrsZnoS = iDlGrsZnoDAO.get(cmpVal, cnyVal, usrLngVal);
			   }
			
		} 
		catch (NwtException e) {
		    
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CNY_VAL")).build();
			errors.add(error);
		}
		   
		
		/*
		 * Validacion del estado (Zona dos geográfica)
		 */
		try {

			// Validamos si el campo zona dos geográfica está informado
			   if(body.getData().get("STT_VAL") == null || body.getData().get("STT_VAL").equals("")) {
				codError = new BigDecimal(20003);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "STT_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("STT_VAL")).build();
				errors.add(error);
			   }
			   else {// Si su contenido no es nulo ni vacio, lo creamos
			       	String cnyVal = body.getData().get("CNY_VAL");
				BigDecimal sttVal = new BigDecimal(body.getData().get("STT_VAL"));
				String usrLngVal =  body.getUser().getLanguage().toUpperCase();
				
				oGrsZntS = iDlGrsZntDAO.get(cmpVal, cnyVal, sttVal, usrLngVal);
				
				if (oGrsZntS.getDsbRow().equals(CIns.YES)) {
					if (accion.equals(CTrn.ACN_TYP_CRT)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "STT_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("STT_VAL")).build();
						errors.add(error);
					} 
					if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "STT_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("STT_VAL")).build();
						errors.add(error);
					}
				}
			   } 
			
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("STT_VAL")).build();
			errors.add(error);
		}
		
		/*
		 * Validacion de la provincia (zona tres geográfica)
		 */
		try {
			// Validamos si el campo zona tres geográfica está informado
			   if(body.getData().get("PVC_VAL") == null || body.getData().get("PVC_VAL").equals("")) {
			       codError = new BigDecimal(20003);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "PVC_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PVC_VAL")).build();
				errors.add(error);
			   }
			   else {// Si su contenido no es nulo ni vacio, lo creamos
			       	String cnyVal = body.getData().get("CNY_VAL");
				BigDecimal sttVal = new BigDecimal(body.getData().get("STT_VAL"));
				BigDecimal pvcVal = new BigDecimal(body.getData().get("PVC_VAL"));
				String usrLngVal =  body.getUser().getLanguage().toUpperCase();
				
				oGrsZotS = iDlGrsZotDAO.get(cmpVal, cnyVal, sttVal, pvcVal, usrLngVal);
				
				if (oGrsZotS.getDsbRow().equals(CIns.YES)) {
					if (accion.equals(CTrn.ACN_TYP_CRT)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "PVC_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PVC_VAL")).build();
						errors.add(error);
					} 
					if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "PVC_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PVC_VAL")).build();
						errors.add(error);
					}
				}
			   }
			
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("PVC_VAL")).build();
			errors.add(error);
		}
		
		/*
		 * Validacion de la localidad (zona cuatro geográfica)
		 */
		try {

			// Validamos si el campo zona cuatro greográfica real está informado 
			   if (body.getData().get("TWN_VAL") == null || body.getData().get("TWN_VAL").equals("")) {
			       	codError = new BigDecimal(20003);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "TWN_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TWN_VAL")).build();
				errors.add(error);
			   }
			   else {// Si su contenido no es nulo ni vacio, lo creamos
			    	String cnyVal = body.getData().get("CNY_VAL");
				BigDecimal pvcVal = new BigDecimal(body.getData().get("PVC_VAL"));
				BigDecimal twnVal = new BigDecimal(body.getData().get("TWN_VAL"));
				String usrLngVal =  body.getUser().getLanguage().toUpperCase();
				
				oGrsZnfS = iDlGrsZnfDAO.get(cmpVal, cnyVal, pvcVal, twnVal, usrLngVal);
				
				if (oGrsZnfS.getDsbRow().equals(CIns.YES)) {
					if (accion.equals(CTrn.ACN_TYP_CRT)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "TWN_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TWN_VAL")).build();
						errors.add(error);
					} 
					if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
						codError = new BigDecimal(20020);
						lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "TWN_VAL", cmpVal);
						error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TWN_VAL")).build();
						errors.add(error);
					}
				}
				
			   }
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("TWN_VAL")).build();
			errors.add(error);
		}
		
		
		// Validacion IDIOMA
		try {
		    	// Validamos si el campo zona cuatro greográfica real está informado 
			  if (body.getData().get("LNG_VAL") == null || body.getData().get("LNG_VAL").equals("")) {
			       codError = new BigDecimal(20003);
			       lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "LNG_VAL", cmpVal);
			       error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("LNG_VAL")).build();
			       errors.add(error);
			   }
			   else {// Si su contenido no es nulo ni vacio, lo creamos
			       	lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"), body.getUser().getLanguage().toUpperCase());
			   }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}
		
		// Comprobamos que REA_GRS_DIT valga S o N
		if (body.getData().get("REA_GRS_DIT") == null || (!body.getData().get("REA_GRS_DIT").equals(CIns.YES) && !body.getData().get("REA_GRS_DIT").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "GRS_FRT_LVL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("REA_GRS_DIT")).build();
			errors.add(error);
		}
		
		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW")).build();
			errors.add(error);
		}

		return errors;
	}

}
