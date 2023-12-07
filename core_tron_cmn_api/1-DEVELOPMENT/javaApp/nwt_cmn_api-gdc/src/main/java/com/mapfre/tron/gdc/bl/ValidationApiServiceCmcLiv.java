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

@Service("CmcLiv")
public class ValidationApiServiceCmcLiv implements ValidationApiService {

    protected static final String FEC_VALIDEZ = "FEC_VALIDEZ";

    protected static final String COD_CIA = "COD_CIA";

    protected static final String COD_COB = "COD_COB";

    protected static final String COD_CUADRO_COM = "COD_CUADRO_COM";

    protected static final String COD_MODALIDAD = "COD_MODALIDAD";

    protected static final String COD_RAMO = "COD_RAMO";

    protected static final String DURACION_DESDE = "DURACION_DESDE";

    protected static final String DURACION_HASTA = "DURACION_HASTA";

    protected static final String MCA_INH = "MCA_INH";

    protected static final String MCA_INH2 = MCA_INH;

    protected static final String NOM_PRG = "NOM_PRG";

    protected static final String NOM_PRG_ANULACION = "NOM_PRG_ANULACION";

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
		    // No se puede modificar la Modalidad
		    if(!body.getDataOld().get(COD_MODALIDAD).equals(body.getData().get(COD_MODALIDAD))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_MODALIDAD,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_MODALIDAD).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar la Cobertura
		    if(!body.getDataOld().get(COD_COB).equals(body.getData().get(COD_COB))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_COB,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_COB).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar la Duración mínima
		    if(!body.getDataOld().get(DURACION_DESDE).equals(body.getData().get(DURACION_DESDE))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", DURACION_DESDE,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(DURACION_DESDE).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar la Duración máxima
		    if(!body.getDataOld().get(DURACION_HASTA).equals(body.getData().get(DURACION_HASTA))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", DURACION_HASTA,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(DURACION_HASTA).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar la Fecha de validez
		    if(!body.getDataOld().get(FEC_VALIDEZ).equals(body.getData().get(FEC_VALIDEZ))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", FEC_VALIDEZ,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(FEC_VALIDEZ).toString()).build();
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
		
		// Validamos si el campo Modalidad está informado
		if (body.getData().get(COD_MODALIDAD) == null || body.getData().get(COD_MODALIDAD).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_MODALIDAD, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_MODALIDAD).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Cobertura está informado
		if (body.getData().get(COD_COB) == null || body.getData().get(COD_COB).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_COB, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_COB).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Duracion minima para rango... está informado
		if (body.getData().get(DURACION_DESDE) == null || body.getData().get(DURACION_DESDE).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", DURACION_DESDE, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DURACION_DESDE).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Duracion maxima para rango de ... está informado
		if (body.getData().get(DURACION_HASTA) == null || body.getData().get(DURACION_HASTA).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", DURACION_HASTA, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DURACION_HASTA).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Fecha validez está informado
		if (body.getData().get(FEC_VALIDEZ) == null || body.getData().get(FEC_VALIDEZ).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", FEC_VALIDEZ, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FEC_VALIDEZ).toString()).build();
			errors.add(error);
		}
		
		// Validamos MARCA INHABILITADO
		if (body.getData().get(MCA_INH2) == null || (!body.getData().get(MCA_INH2).equals(CIns.YES) && !body.getData().get(MCA_INH2).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", MCA_INH2, null);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MCA_INH2).toString()).build();
		    errors.add(error);
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
		
		//Validar que existe la Modalidad
		try {
		    if(body.getData().get(COD_MODALIDAD) != null  && !(body.getData().get(COD_MODALIDAD).equals(""))){
			iDlThpCmcDAO.getCodMod(cmpVal, new BigDecimal (body.getData().get(COD_MODALIDAD)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_MODALIDAD).toString()).build();
			errors.add(error);
		}
		
		//Validar que existe la Cobertura
		try {
		    if(body.getData().get(COD_COB) != null  && !(body.getData().get(COD_COB).equals(""))  && !(body.getData().get(COD_COB).equals("9999")) ){
		       
		       iDlThpCmcDAO.getCodCob(cmpVal, new BigDecimal (body.getData().get(COD_RAMO)), new BigDecimal (body.getData().get(COD_COB)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_COB).toString()).build();
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
