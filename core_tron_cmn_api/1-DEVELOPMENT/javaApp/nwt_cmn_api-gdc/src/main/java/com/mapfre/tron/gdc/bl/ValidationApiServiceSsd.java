package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.tpd.ssd.dl.IDlTpdSsdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Ssd")
public class ValidationApiServiceSsd implements ValidationApiService {

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;
    @Autowired
    IDlTpdSsdDAO iDlTpdSsdDAO;
    @Autowired
	DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
		OCmnLngS   lvOCmnLngS;
		List<OCmnTypS>   lvOCmnTypPT;
		List<OTpdSsdS>   oTpdSsdPT;
		OTpdSsdS   lvOTpdSsdS;
		OTrnErrS lvError;
		BigDecimal codError;

		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
	
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    if(!body.getDataOld().get("SSV_VAL").equals(body.getData().get("SSV_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SSV_VAL").toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get("SSV_TYP_VAL").equals(body.getData().get("SSV_TYP_VAL"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_TYP_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SSV_TYP_VAL").toString()).build();
				errors.add(error);
		    }
		}
	
		try {
		    lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"),  body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL").toString()).build();
			errors.add(error);
		}
	
    	if (accion.equals(CTrn.ACN_TYP_CRT)) {
    	    try {
	    		oTpdSsdPT = iDlTpdSsdDAO.get_ssdPT(cmpVal, body.getData().get("SSV_VAL"), body.getUser().getLanguage().toUpperCase());
	    		if(!oTpdSsdPT.get(0).getSsvTypVal().equals(body.getData().get("SSV_TYP_VAL"))) {
	    			codError = new BigDecimal(20120);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_TYP_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(oTpdSsdPT.get(0).getSsvTypVal().toString()).build();
					errors.add(error);
	    		}
    	    }
    	    catch (NwtException e) {
    		
    	    }
    	}
	
		try {
		    lvOCmnTypPT = iDlCmnTypDAO.get(cmpVal, "TIP_SERVICIO", body.getData().get("SSV_TYP_VAL"), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SSV_TYP_VAL").toString()).build();
			errors.add(error);
		}
		
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}

		return errors;
    }
}
