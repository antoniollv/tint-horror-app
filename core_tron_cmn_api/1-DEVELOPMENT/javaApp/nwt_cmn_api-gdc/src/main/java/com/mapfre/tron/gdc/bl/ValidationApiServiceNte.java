package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.nte.dl.IDlCmnNteDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Nte")
public class ValidationApiServiceNte implements ValidationApiService {

    protected static final String NTE_VAL = "NTE_VAL";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Autowired
    IDlCmnNteDAO iDlCmnNteDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	

		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();

		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		

	    // Validamos si existe el código de la nota
	      try {
		    if(body.getData().get(NTE_VAL) != null  && !(body.getData().get(NTE_VAL).equals(""))) {
		    
			String nteVal = body.getData().get(NTE_VAL);
			
			iDlCmnNteDAO.get_003(cmpVal, nteVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(NTE_VAL).toString()).build();
			errors.add(error);
	    }
		
		return errors;
    }

}

