package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmc.agn.dl.IDlThpCmcAgnDAO;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("CmcAgn")
public class ValidationApiServiceCmcAgn implements ValidationApiService {

    protected static final String COD_AGT = "COD_AGT";

    protected static final String COD_CUADRO_COM = "COD_CUADRO_COM";

    protected static final String COD_TRATAMIENTO = "COD_TRATAMIENTO";

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;
    
    @Autowired
    IDlThpCmcAgnDAO iDlThpCmcAgnDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("COD_CIA"));
		
		
		
		//Validar que existe el Agente
		try {
		    if(body.getData().get(COD_AGT) != null  && !(body.getData().get(COD_AGT).equals(""))){
				iDlThpCmcDAO.getCodAgente(cmpVal, new BigDecimal (body.getData().get(COD_AGT)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_AGT)).build();
		    errors.add(error);
		}
		
		//Validar que existe el Cuadro comisión
		try {
		    if(body.getData().get(COD_CUADRO_COM) != null  && !(body.getData().get(COD_CUADRO_COM).equals(""))){
			iDlThpCmcDAO.getCodCuaCom(cmpVal, new BigDecimal (body.getData().get(COD_CUADRO_COM)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_CUADRO_COM)).build();
		    errors.add(error);
		}
		
		//Validar que existe el ....
		try {
		    if(body.getData().get(COD_TRATAMIENTO) != null  && !(body.getData().get(COD_TRATAMIENTO).equals(""))){
			iDlThpCmcAgnDAO.getCodTratamiento(cmpVal, (body.getData().get(COD_TRATAMIENTO)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(COD_TRATAMIENTO)).build();
		    errors.add(error);
		}
		
		return errors;
    }

}

