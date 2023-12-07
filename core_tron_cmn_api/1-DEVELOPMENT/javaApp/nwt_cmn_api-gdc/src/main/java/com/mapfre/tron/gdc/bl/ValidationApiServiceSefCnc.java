package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.acd.acc.bo.OAcdAccS;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.acd.acc.dl.IDlAcdAccDAO;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("SefCnc")
public class ValidationApiServiceSefCnc implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

	@Autowired
	IDlAcdAccDAO iDlAcdAccDAO;

	OAcdAccS oAcdAccS;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;

		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
	
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    if(!body.getDataOld().get("RTE_CNC_VAL").equals(body.getData().get("RTE_CNC_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_CNC_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("RTE_CNC_VAL").toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
				errors.add(error);
		    }
		}
	
		try {
		    iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}
	
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "DSB_ROW", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
		    errors.add(error);
		}
		
		if (body.getData().get("CLO_PYM_CNC_VAL") != null && (!"".equals(body.getData().get("CLO_PYM_CNC_VAL"))) ) {
			try {
				oAcdAccS = iDlAcdAccDAO.get(cmpVal, "PS", body.getData().get("CLO_PYM_CNC_VAL"), body.getUser().getLanguage().toUpperCase()); 
			}
			catch (NwtException e) {
				error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CLO_PYM_CNC_VAL").toString()).build();
				errors.add(error);
			}
		}
	
		return errors;
    }

}
