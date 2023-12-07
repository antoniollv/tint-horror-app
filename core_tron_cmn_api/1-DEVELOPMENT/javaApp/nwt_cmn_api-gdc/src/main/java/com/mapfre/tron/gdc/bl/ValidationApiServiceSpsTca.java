package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.tpd.sps.dl.IDlTpdSpsDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;


@Service("SpsTca")
public class ValidationApiServiceSpsTca implements ValidationApiService {

    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;

    @Autowired
    IDlTpdSpsDAO iDlTpdSpsDAO;

    @Autowired
	DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
		OTpdAvdS lvOTpdAvdS;
		OTpdSpsS lvOTpdSpsS;
		OTrnErrS lvError;
		BigDecimal codError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
	
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
	
			// No se puede modificar la actividad
		    if(!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "THP_ACV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
				errors.add(error);
		    }
	
		    // No se puede modificar la fecha
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "VLD_DAT", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
				errors.add(error);
		    }
	
		    // No se puede modificar la tipologia
		    if(!body.getDataOld().get("TYL_VAL").equals(body.getData().get("TYL_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "TYL_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("TYL_VAL").toString()).build();
				errors.add(error);
		    }
	
		    // No se puede modificar la categoria
		    if(!body.getDataOld().get("SPL_CTG_VAL").equals(body.getData().get("SPL_CTG_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "SPL_CTG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SPL_CTG_VAL").toString()).build();
				errors.add(error);
		    }
		}
	
		// Comprobamos si existe la actividad
		try {
		    BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
		    lvOTpdAvdS = iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL").toString()).build();
        	errors.add(error);
		}
		
                // Comprobamos que la tipología está vigente	
		try {
			BigDecimal thpAcvVal = new BigDecimal(body.getData().get("THP_ACV_VAL"));
			lvOTpdSpsS = iDlTpdSpsDAO.get_tylTya(cmpVal, thpAcvVal, body.getData().get("TYL_VAL"), body.getUser().getLanguage().toUpperCase());
			if (lvOTpdSpsS.getDsbRow().contentEquals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "TYL_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TYL_VAL").toString()).build();
					errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "TYL_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TYL_VAL").toString()).build();
					errors.add(error);
				}
			}
	    }
	    catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("TYL_VAL").toString()).build();
        	errors.add(error);
	    }
		
        // Comprobamos que la categoria está vigente	
		try {
			lvOTpdSpsS = iDlTpdSpsDAO.get_splCtg(cmpVal, body.getData().get("SPL_CTG_VAL"), body.getUser().getLanguage().toUpperCase(), body.getUser().getLanguage().toUpperCase());
			if (lvOTpdSpsS.getDsbRow().contentEquals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "SPL_CTG_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
					errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "SPL_CTG_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
					errors.add(error);
				}
			}
	    }
	    catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
        	errors.add(error);
	    }
	
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}
	
	    return errors;
    }
}
