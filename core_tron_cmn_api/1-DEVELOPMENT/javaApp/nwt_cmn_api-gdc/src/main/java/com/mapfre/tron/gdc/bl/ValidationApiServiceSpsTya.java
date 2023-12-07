package com.mapfre.tron.gdc.bl;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.mapfre.nwt.exception.NwtException;
import org.springframework.stereotype.Service;
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

@Service("SpsTya")
public class ValidationApiServiceSpsTya implements ValidationApiService {

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
		
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		}
		else {
			accion = CTrn.ACN_TYP_MDF;
		}
		
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
			
			if(!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "THP_ACV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
				errors.add(error);
			}
			
			if(!body.getDataOld().get("TYL_VAL").equals(body.getData().get("TYL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "TYL_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("TYL_VAL").toString()).build();
				errors.add(error);
			}
		}

		try {
			BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
			lvOTpdAvdS = iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL").toString()).build();
		    errors.add(error);
		}
		
		try {
			lvOTpdSpsS = iDlTpdSpsDAO.get(cmpVal, body.getData().get("TYL_VAL"), body.getUser().getLanguage().toUpperCase(), body.getUser().getLanguage().toUpperCase());
			if (lvOTpdSpsS.getDsbRow().equals(CIns.YES)) {
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
		
		if (body.getData().get("DSB_ROW") == null || !body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO)) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}
		
		return errors;
	}
}
