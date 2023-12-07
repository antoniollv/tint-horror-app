package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("CmcNotLiv")
public class ValidationApiServiceCmcNotLiv implements ValidationApiService {

    protected static final String COD_AGT = "COD_AGT";

    protected static final String COD_CANAL1 = "COD_CANAL1";

    protected static final String COD_CANAL2 = "COD_CANAL2";

    protected static final String COD_CANAL3 = "COD_CANAL3";

    protected static final String COD_CIA = "COD_CIA";

    protected static final String COD_COB = "COD_COB";

    protected static final String COD_CUADRO_COM = "COD_CUADRO_COM";

    protected static final String COD_NIVEL1 = "COD_NIVEL1";

    protected static final String COD_NIVEL2 = "COD_NIVEL2";

    protected static final String COD_NIVEL3 = "COD_NIVEL3";

    protected static final String COD_RAMO = "COD_RAMO";

    protected static final String FEC_VALIDEZ = "FEC_VALIDEZ";

    protected static final String MCA_INH = "MCA_INH";

    protected static final String NOM_PRG = "NOM_PRG";

    protected static final String NOM_PRG_ANULACION = "NOM_PRG_ANULACION";

    protected static final String PCT_AGT_CAR = "PCT_AGT_CAR";

    protected static final String PCT_AGT_NPR = "PCT_AGT_NPR";

    protected static final String PCT_ASE_CAR = "PCT_ASE_CAR";

    protected static final String PCT_ASE_NPR = "PCT_ASE_NPR";

    protected static final String PCT_RAPP_CAR = "PCT_RAPP_CAR";

    protected static final String PCT_RAPP_NPR = "PCT_RAPP_NPR";

    protected static final String VAL_COM_MAX_AGT_CAR = "VAL_COM_MAX_AGT_CAR";

    protected static final String VAL_COM_MAX_AGT_NPR = "VAL_COM_MAX_AGT_NPR";

    protected static final String VAL_COM_MAX_ASE_CAR = "VAL_COM_MAX_ASE_CAR";

    protected static final String VAL_COM_MAX_ASE_NPR = "VAL_COM_MAX_ASE_NPR";

    protected static final String VAL_COM_MAX_RAPP_CAR = "VAL_COM_MAX_RAPP_CAR";

    protected static final String VAL_COM_MAX_RAPP_NPR = "VAL_COM_MAX_RAPP_NPR";

    protected static final String VAL_COM_MIN_AGT_CAR = "VAL_COM_MIN_AGT_CAR";

    protected static final String VAL_COM_MIN_AGT_NPR = "VAL_COM_MIN_AGT_NPR";

    protected static final String VAL_COM_MIN_ASE_CAR = "VAL_COM_MIN_ASE_CAR";

    protected static final String VAL_COM_MIN_ASE_NPR = "VAL_COM_MIN_ASE_NPR";

    protected static final String VAL_COM_MIN_RAPP_CAR = "VAL_COM_MIN_RAPP_CAR";

    protected static final String VAL_COM_MIN_RAPP_NPR = "VAL_COM_MIN_RAPP_NPR";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;
    
    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get(COD_CIA));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get(COD_CIA).equals(body.getData().get(COD_CIA))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CIA, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CIA).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el Cuadro comision
		    if(!body.getDataOld().get(COD_CUADRO_COM).equals(body.getData().get(COD_CUADRO_COM))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CUADRO_COM,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CUADRO_COM).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el Ramo	
		    if(!body.getDataOld().get(COD_RAMO).equals(body.getData().get(COD_RAMO))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_RAMO,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_RAMO).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar la Cobertura
		    if(!body.getDataOld().get(COD_COB).equals(body.getData().get(COD_COB))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_COB,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_COB).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el código de agente
		    if(!body.getDataOld().get(COD_AGT).equals(body.getData().get(COD_AGT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_AGT,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_AGT).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el código nivel1
		    if(!body.getDataOld().get(COD_NIVEL1).equals(body.getData().get(COD_NIVEL1))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL1,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_NIVEL1).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el código nivel2
		    if(!body.getDataOld().get(COD_NIVEL2).equals(body.getData().get(COD_NIVEL2))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL2,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_NIVEL2).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el código nivel3
		    if(!body.getDataOld().get(COD_NIVEL3).equals(body.getData().get(COD_NIVEL3))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL3,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_NIVEL3).toString()).build();
				errors.add(error);
		    }		    
		    // No se puede modificar la fecha validez
		    if(!body.getDataOld().get(FEC_VALIDEZ).equals(body.getData().get(FEC_VALIDEZ))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", FEC_VALIDEZ,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(FEC_VALIDEZ).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el codigo canal1
		    if(!body.getDataOld().get(COD_CANAL1).equals(body.getData().get(COD_CANAL1))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL1,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CANAL1).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el codigo canal2
		    if(!body.getDataOld().get(COD_CANAL2).equals(body.getData().get(COD_CANAL2))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL2,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CANAL2).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el codigo canal3
		    if(!body.getDataOld().get(COD_CANAL3).equals(body.getData().get(COD_CANAL3))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL3,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CANAL3).toString()).build();
				errors.add(error);
		    }
	
		}
		
		// Validamos si el campo Cuadro comision está informado
		if (body.getData().get(COD_CUADRO_COM) == null || body.getData().get(COD_CUADRO_COM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CUADRO_COM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_CUADRO_COM).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Ramo está informado
		if (body.getData().get(COD_RAMO) == null || body.getData().get(COD_RAMO).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_RAMO, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_RAMO).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Cobertura está informado
		if (body.getData().get(COD_COB) == null || body.getData().get(COD_COB).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_COB, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_COB).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje de nueva produccion del agente principal está informado
		if (body.getData().get(PCT_AGT_NPR) == null || body.getData().get(PCT_AGT_NPR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_AGT_NPR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_AGT_NPR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje de nueva produccion del inspector está informado
		if (body.getData().get(PCT_RAPP_NPR) == null || body.getData().get(PCT_RAPP_NPR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_RAPP_NPR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_RAPP_NPR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje de nueva produccion del asesor está informado
		if (body.getData().get(PCT_ASE_NPR) == null || body.getData().get(PCT_ASE_NPR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_ASE_NPR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_ASE_NPR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje de cartera del agente principal está informado
		if (body.getData().get(PCT_AGT_CAR) == null || body.getData().get(PCT_AGT_CAR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_AGT_CAR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_AGT_CAR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje de cartera del inspetor está informado
		if (body.getData().get(PCT_RAPP_CAR) == null || body.getData().get(PCT_RAPP_CAR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_RAPP_CAR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_RAPP_CAR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Porcentaje cartera del asesor está informado
		if (body.getData().get(PCT_ASE_CAR) == null || body.getData().get(PCT_ASE_CAR).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", PCT_ASE_CAR, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PCT_ASE_CAR).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Agente está informado
		if (body.getData().get(COD_AGT) == null || body.getData().get(COD_AGT).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_AGT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_AGT).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo nivel1 está informado
		if (body.getData().get(COD_NIVEL1) == null || body.getData().get(COD_NIVEL1).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL1, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_NIVEL1).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo nivel2 está informado
		if (body.getData().get(COD_NIVEL2) == null || body.getData().get(COD_NIVEL2).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL2, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_NIVEL2).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo nivel3 está informado
		if (body.getData().get(COD_NIVEL3) == null || body.getData().get(COD_NIVEL3).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_NIVEL3, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_NIVEL3).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo canal1 está informado
		if (body.getData().get(COD_CANAL1) == null || body.getData().get(COD_CANAL1).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL1, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_CANAL1).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo canal2 está informado
		if (body.getData().get(COD_CANAL2) == null || body.getData().get(COD_CANAL2).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL2, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_CANAL2).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo codigo canal3 está informado
		if (body.getData().get(COD_CANAL3) == null || body.getData().get(COD_CANAL3).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CANAL3, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_CANAL3).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Fecha validez está informado
		if (body.getData().get(FEC_VALIDEZ) == null || body.getData().get(FEC_VALIDEZ).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", FEC_VALIDEZ, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FEC_VALIDEZ).toString()).build();
			errors.add(error);
		}
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get(MCA_INH) == null || (!body.getData().get(MCA_INH).equals(CIns.YES) && !body.getData().get(MCA_INH).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", MCA_INH, null);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MCA_INH).toString()).build();
		    errors.add(error);
		}
		
		// Validacion VAL_COM_MAX_AGT_NPR  es mayor que VAL_COM_MIN_AGT_NPR
		if(body.getData().get(VAL_COM_MAX_AGT_NPR) != null  && !(body.getData().get(VAL_COM_MAX_AGT_NPR).equals("")) &&
			body.getData().get(VAL_COM_MIN_AGT_NPR) != null  && !(body.getData().get(VAL_COM_MIN_AGT_NPR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_AGT_NPR))) < (new Integer(body.getData().get(VAL_COM_MIN_AGT_NPR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_AGT_NPR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_AGT_NPR).toString()).build();
				errors.add(error);
			}
		}

		// Validacion VAL_COM_MAX_AGT_CAR  es mayor que VAL_COM_MIN_AGT_CAR
		if(body.getData().get(VAL_COM_MAX_AGT_CAR) != null  && !(body.getData().get(VAL_COM_MAX_AGT_CAR).equals("")) &&
			body.getData().get(VAL_COM_MIN_AGT_CAR) != null  && !(body.getData().get(VAL_COM_MIN_AGT_CAR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_AGT_CAR))) < (new Integer(body.getData().get(VAL_COM_MIN_AGT_CAR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_AGT_CAR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_AGT_CAR).toString()).build();
				errors.add(error);
			}
		}

		// Validacion VAL_COM_MAX_RAPP_NPR  es mayor que VAL_COM_MIN_RAPP_NPR
		if(body.getData().get(VAL_COM_MAX_RAPP_NPR) != null  && !(body.getData().get(VAL_COM_MAX_RAPP_NPR).equals("")) &&
			body.getData().get(VAL_COM_MIN_RAPP_NPR) != null  && !(body.getData().get(VAL_COM_MIN_RAPP_NPR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_RAPP_NPR))) < (new Integer(body.getData().get(VAL_COM_MIN_RAPP_NPR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_RAPP_NPR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_RAPP_NPR).toString()).build();
				errors.add(error);
			}
		}

		// Validacion VAL_COM_MAX_RAPP_CAR  es mayor que VAL_COM_MIN_RAPP_CAR
		if(body.getData().get(VAL_COM_MAX_RAPP_CAR) != null  && !(body.getData().get(VAL_COM_MAX_RAPP_CAR).equals("")) &&
			body.getData().get(VAL_COM_MIN_RAPP_CAR) != null  && !(body.getData().get(VAL_COM_MIN_RAPP_CAR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_RAPP_CAR))) < (new Integer(body.getData().get(VAL_COM_MIN_RAPP_CAR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_RAPP_CAR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_RAPP_CAR).toString()).build();
				errors.add(error);
			}
		}

		// Validacion VAL_COM_MAX_ASE_NPR  es mayor que VAL_COM_MIN_ASE_NPR
		 if(body.getData().get(VAL_COM_MAX_ASE_NPR) != null  && !(body.getData().get(VAL_COM_MAX_ASE_NPR).equals("")) &&
			body.getData().get(VAL_COM_MIN_ASE_NPR) != null  && !(body.getData().get(VAL_COM_MIN_ASE_NPR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_ASE_NPR))) < (new Integer(body.getData().get(VAL_COM_MIN_ASE_NPR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_ASE_NPR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_ASE_NPR).toString()).build();
				errors.add(error);
			}
		}

		// Validacion VAL_COM_MAX_ASE_CAR  es mayor que VAL_COM_MIN_ASE_CAR
		if(body.getData().get(VAL_COM_MAX_ASE_CAR) != null  && !(body.getData().get(VAL_COM_MAX_ASE_CAR).equals("")) &&
			body.getData().get(VAL_COM_MIN_ASE_CAR) != null  && !(body.getData().get(VAL_COM_MIN_ASE_CAR).equals("")) ){
			if((new Integer(body.getData().get(VAL_COM_MAX_ASE_CAR))) < (new Integer(body.getData().get(VAL_COM_MIN_ASE_CAR)))){
				codError = new BigDecimal(20005);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", VAL_COM_MAX_ASE_CAR, null);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VAL_COM_MAX_ASE_CAR).toString()).build();
				errors.add(error);
			}
		}
		
		
		//Validar que existe el Cuadro comisión
		try {
		    if(body.getData().get(COD_CUADRO_COM) != null  && !(body.getData().get(COD_CUADRO_COM).equals(""))){
			 iDlThpCmcDAO.getCodCuaCom(cmpVal, new BigDecimal (body.getData().get(COD_CUADRO_COM)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_CUADRO_COM).toString()).build();
			errors.add(error);
		}
		
		//Validar que existe el Ramo
		try {
		    if(body.getData().get(COD_RAMO) != null  && !(body.getData().get(COD_RAMO).equals(""))){
			 iDlThpCmcDAO.getCodRamo(cmpVal, new BigDecimal (body.getData().get(COD_RAMO)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_RAMO).toString()).build();
			errors.add(error);
		}
		
		//Validar que existe la Cobertura
		try {
		    if(body.getData().get(COD_COB) != null  && !(body.getData().get(COD_COB).equals("")) && !(body.getData().get(COD_COB).equals("9999")) ){
		       
		        iDlThpCmcDAO.getCodCob(cmpVal, new BigDecimal (body.getData().get(COD_RAMO)), new BigDecimal (body.getData().get(COD_COB)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_COB).toString()).build();
			errors.add(error);
		}
		
		//Validar que existe el Agente
		try {
		    if(body.getData().get(COD_AGT) != null  && !(body.getData().get(COD_AGT).equals(""))){
			 iDlThpCmcDAO.getCodAgente(cmpVal, new BigDecimal (body.getData().get(COD_AGT)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_AGT).toString()).build();
			errors.add(error);
		}
		
		// Validar que existe nivel1
		try {
		    if(body.getData().get(COD_NIVEL1) != null  && !(body.getData().get(COD_NIVEL1).equals(""))){
			 iDlThpCmcDAO.getCodNivel1(cmpVal,body.getData().get(COD_NIVEL1), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_NIVEL1).toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe nivel2
		try {
		    if(body.getData().get(COD_NIVEL2) != null  && !(body.getData().get(COD_NIVEL2).equals(""))){
			iDlThpCmcDAO.getCodNivel2(cmpVal,body.getData().get(COD_NIVEL2), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_NIVEL2).toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe nivel3
		try {
		    if(body.getData().get(COD_NIVEL3) != null  && !(body.getData().get(COD_NIVEL3).equals(""))){
			 iDlThpCmcDAO.getCodNivel3(cmpVal,body.getData().get(COD_NIVEL3), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_NIVEL3).toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe canal1
		try {
		    if(body.getData().get(COD_CANAL1) != null  && !(body.getData().get(COD_CANAL1).equals(""))){
			 iDlThpCmcDAO.getCodCanal1(cmpVal,body.getData().get(COD_CANAL1), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_CANAL1).toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe canal2
		try {
		    if(body.getData().get(COD_CANAL2) != null  && !(body.getData().get(COD_CANAL2).equals(""))){
			 iDlThpCmcDAO.getCodCanal2(cmpVal,body.getData().get(COD_CANAL2), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_CANAL2).toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe canal3
		try {
		    if(body.getData().get(COD_CANAL3) != null  && !(body.getData().get(COD_CANAL3).equals(""))){
			 iDlThpCmcDAO.getCodCanal3(cmpVal,body.getData().get(COD_CANAL3), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_CANAL3).toString()).build();
		    errors.add(error);
		}
		
		// Validar que el procedimiento exista en la base de datos
		try {
		    if(body.getData().get(NOM_PRG) != null  && !(body.getData().get(NOM_PRG).equals(""))){
			 iDlTrnPrcDAO.getNomPrg(body.getData().get(NOM_PRG),body.getUser().getLanguage().toUpperCase(), cmpVal); 
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(NOM_PRG).toString()).build();
			errors.add(error);
		}
		
		// Validar que el procedimiento anulación exista en la base de datos
		try {
		    if(body.getData().get(NOM_PRG_ANULACION) != null  && !(body.getData().get(NOM_PRG_ANULACION).equals(""))){
			 iDlTrnPrcDAO.getNomPrg(body.getData().get(NOM_PRG_ANULACION),body.getUser().getLanguage().toUpperCase(), cmpVal);
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(NOM_PRG_ANULACION).toString()).build();
			errors.add(error);
		}
	
		return errors;
    }
}
