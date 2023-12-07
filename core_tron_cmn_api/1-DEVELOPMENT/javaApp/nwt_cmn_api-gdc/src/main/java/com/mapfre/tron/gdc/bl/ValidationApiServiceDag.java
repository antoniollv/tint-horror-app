package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.acf.dag.dl.IDlAcfDagDAO;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Dag")
public class ValidationApiServiceDag implements ValidationApiService {
    
    protected static final String AGN_VAL = "AGN_VAL";

    protected static final String CLO_PYM_CNC_VAL = "CLO_PYM_CNC_VAL";

    protected static final String CMS_AMN = "CMS_AMN";

    protected static final String CMS_PER_APL = "CMS_PER_APL";

    protected static final String CMS_PRD_NAM = "CMS_PRD_NAM";

    protected static final String CRN_VAL = "CRN_VAL";

    protected static final String FNL_CMS_DAT = "FNL_CMS_DAT";

    protected static final String MNM_CLO_AMN = "MNM_CLO_AMN";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlAcfDagDAO iDlAcfDagDAO;
    
    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;
    
    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;
		
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		// Variable fecha hasta... para utilizarla en la comparacion de fechas
		Date fnlCmsDat = Date.from(LocalDateTime.parse(body.getData().get(FNL_CMS_DAT)).atZone(ZoneId.systemDefault()).toInstant());
		
		// Variable fecha desde... para utilizarla en la comparacion de fechas
		Date inlCmsDat = Date.from(LocalDateTime.parse(body.getData().get("INL_CMS_DAT")).atZone(ZoneId.systemDefault()).toInstant());

	
		// Campos que no se pueden modificar (PK)
		
		// Validamos que la fecha hasta que se concede la comision (FNL_CMS_DAT) es mayor que la fecha desde que se concede la comision (INL_CMS_DAT)
		if(body.getData().get(FNL_CMS_DAT) !=null && body.getData().get("INL_CMS_DAT") !=null &&((fnlCmsDat).before(inlCmsDat))) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", FNL_CMS_DAT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FNL_CMS_DAT)).build();
			errors.add(error);

		}
		 
		 // Validamos que el importe de comision sea mayor que cero
		if(body.getData().get(CMS_AMN) !=null && (Integer.parseInt(body.getData().get(CMS_AMN))<0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_AMN, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_AMN)).build();
			errors.add(error);

		 }
		 
		 // Validamos que porcentaje de comisiones a aplicar tenga valores entre 0 y 100
		if(body.getData().get(CMS_PER_APL) !=null && (Integer.parseInt(body.getData().get(CMS_PER_APL))) < 0 || (Integer.parseInt(body.getData().get(CMS_PER_APL))) > 100) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", CMS_PER_APL,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CMS_PER_APL)).build();
			errors.add(error);

		 }
		 
		 // Validamos que el importe minimo a cobrar para percibir la subvencion mensual sea mayor que cero
		if(body.getData().get(MNM_CLO_AMN) !=null && (Integer.parseInt(body.getData().get(MNM_CLO_AMN))<0)) {

			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DAG", MNM_CLO_AMN, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MNM_CLO_AMN)).build();
			errors.add(error);

		 }
		 
		// Validar agente existe
		try {
		    if(body.getData().get(AGN_VAL) != null  && !(body.getData().get(AGN_VAL).equals(""))){
			iDlThpCmcDAO.getCodAgente(cmpVal, new BigDecimal (body.getData().get(AGN_VAL)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(AGN_VAL)).build();
			errors.add(error);
		}
		
		// Validar moneda existe
		try {
		    if((body.getData().get(CRN_VAL) != null  && !(body.getData().get(CRN_VAL).equals("")))) {
				    
			BigDecimal crnVal = new BigDecimal(body.getData().get(CRN_VAL));
					
			iDlAcfDagDAO.get_moneda(cmpVal, crnVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CRN_VAL)).build();
			errors.add(error);
		}
		
		// Validar que el procedimiento que devuelve el importe o porcentaje de comisiones a aplicar
		try {
		    if(body.getData().get(CMS_PRD_NAM) != null  && !(body.getData().get(CMS_PRD_NAM).equals(""))){
			iDlTrnPrcDAO.getNomPrg(body.getData().get(CMS_PRD_NAM),body.getUser().getLanguage().toUpperCase(), cmpVal);
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CMS_PRD_NAM)).build();
			errors.add(error);
		}
		
		// Validar que el concepto cobro / concepto cobro pago existe
		try {
		    if(body.getData().get(CLO_PYM_CNC_VAL) != null  && !(body.getData().get(CLO_PYM_CNC_VAL).equals(""))){
			iDlAcfDagDAO.get_cobro(cmpVal,body.getData().get(CLO_PYM_CNC_VAL),body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CLO_PYM_CNC_VAL)).build();
			errors.add(error);
		}
		
		return errors;
    }

} 


