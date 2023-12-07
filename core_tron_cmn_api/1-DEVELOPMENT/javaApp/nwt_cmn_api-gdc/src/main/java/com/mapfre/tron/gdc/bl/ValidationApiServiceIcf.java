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
import com.mapfre.tron.api.tpd.icf.dl.IDlTpdIcfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Icf")
public class ValidationApiServiceIcf implements ValidationApiService {

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String IDN_ICC_DYN_NAM = "IDN_ICC_DYN_NAM";

    protected static final String IDN_ICC_DYN_TYP_VAL = "IDN_ICC_DYN_TYP_VAL";

    protected static final String LOB_VAL = "LOB_VAL";

    protected static final String OPG_DAT_ICC_DFL_TYP_VAL = "OPG_DAT_ICC_DFL_TYP_VAL";

    protected static final String OPG_DAT_ICC_DYN_NAM = "OPG_DAT_ICC_DYN_NAM";

    protected static final String OPG_DAT_ICC_MDF_CHC = "OPG_DAT_ICC_MDF_CHC";

    protected static final String OPG_TME_ICC_DFL_TYP_VAL = "OPG_TME_ICC_DFL_TYP_VAL";

    protected static final String OPG_TME_ICC_DYN_NAM = "OPG_TME_ICC_DYN_NAM";

    protected static final String OPG_TME_ICC_MDF_CHC = "OPG_TME_ICC_MDF_CHC";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    IDlTpdIcfDAO iDlTpdIcfDAO;
    
    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;
    
    @Autowired
	DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<>();
		ValidationError error;
		String accion;
		OTrnErrS lvError;
		BigDecimal codError;

		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		// Validar que no se puedan modificar los siguientes campos PK
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    if(!body.getDataOld().get(LOB_VAL).equals(body.getData().get(LOB_VAL))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", LOB_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LOB_VAL).toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", VLD_DAT, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT).toString()).build();
				errors.add(error);
		    }
		}	
		
		try {
        		// Si el tipo de identificador de iqrf dinamico seleccionado es igual a PROCEDIMINTO DINAMICO 
        		if (body.getData().get(IDN_ICC_DYN_TYP_VAL) != null  && !(body.getData().get(IDN_ICC_DYN_TYP_VAL).equals("")) && (body.getData().get(IDN_ICC_DYN_TYP_VAL).equals("2"))) {
        		    // Si el Identificador de iqrf dinamico es igual null o vacío, retornar un error. 
        		    if (body.getData().get(IDN_ICC_DYN_NAM) == null || body.getData().get(IDN_ICC_DYN_NAM).equals("")) {
        			codError = new BigDecimal(20005);
        			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", IDN_ICC_DYN_NAM, cmpVal);
        			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(IDN_ICC_DYN_NAM)).build();
        			errors.add(error);
        		    }
        		}//Si el Tipo de identificador de iqrf dinamico seleccionado es igual a SECUENCIAL
    			else if((body.getData().get(IDN_ICC_DYN_TYP_VAL) != null  && !(body.getData().get(IDN_ICC_DYN_TYP_VAL).equals("")) && (body.getData().get(IDN_ICC_DYN_TYP_VAL).equals("1"))) &&(body.getData().get(IDN_ICC_DYN_NAM) != null  && !(body.getData().get(IDN_ICC_DYN_NAM).equals("")))) {
    			    	// Si Tipo de identificador de iqrf dinamico está informado, retornar un error. 

        				codError = new BigDecimal(20005);
        				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", IDN_ICC_DYN_NAM, cmpVal);
        				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(IDN_ICC_DYN_NAM)).build();
        				errors.add(error);

    			}
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(IDN_ICC_DYN_NAM)).build();
			errors.add(error);
		}

		try {
    			//Si el Tipo de fecha apertura iqrf defecto seleccionado es igual a PROCEDIMINTO DINAMICO
    			if (body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL) != null  &&  !(body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals("")) && body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals("1")) {
    			    // Si la Fecha apertura iqrf dinamico es igual a null o vacío, retornar un error. 
    			    if (body.getData().get(OPG_DAT_ICC_DYN_NAM) == null  || body.getData().get(OPG_DAT_ICC_DYN_NAM).equals("")) {
    				codError = new BigDecimal(20005);
    				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_DAT_ICC_DYN_NAM, cmpVal);
    				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_DAT_ICC_DYN_NAM)).build();
    				errors.add(error);
    			    }
    			}//Si el Tipo de fecha apertura iqrf defecto seleccionado es igual a SISTEMA o NINGUNO
    			else if(body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL) != null  && !(body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals("")) && 
    				((body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals("2")) ||
    				(body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals("3"))) &&(body.getData().get(OPG_DAT_ICC_DYN_NAM) != null  && !(body.getData().get(OPG_DAT_ICC_DYN_NAM).equals("")))) {
    			    	// Si la Fecha apertura iqrf dinamico está informada, retornar un error. 

        				codError = new BigDecimal(20005);
        				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_DAT_ICC_DYN_NAM, cmpVal);
        				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_DAT_ICC_DYN_NAM)).build();
        				errors.add(error);

    			}
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPG_DAT_ICC_DYN_NAM)).build();
			errors.add(error);
		}
			
		try {
    			//Si el Tipo hora apertura iqrf defecto seleccionado es igual a PROCEDIMINTO DINAMICO
    			if (body.getData().get(OPG_TME_ICC_DFL_TYP_VAL) != null  && !(body.getData().get(OPG_TME_ICC_DFL_TYP_VAL).equals("")) && body.getData().get(OPG_TME_ICC_DFL_TYP_VAL).equals("1")) {
    			    // Si la Hora apertura iqrf dinamico es igual a vacío o null, retornar un error. 
    			    if (body.getData().get(OPG_TME_ICC_DYN_NAM) == null || body.getData().get(OPG_TME_ICC_DYN_NAM).equals("")) {
    				codError = new BigDecimal(20005);
    				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_TME_ICC_DYN_NAM, cmpVal);
    				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_TME_ICC_DYN_NAM)).build();
    				errors.add(error);
    			    }
    			}
    			//Si el Tipo hora apertura iqrf defecto seleccionado es igual a SISTEMA o NINGUNO
    			else if((body.getData().get(OPG_TME_ICC_DFL_TYP_VAL) != null  && !(body.getData().get(OPG_DAT_ICC_DFL_TYP_VAL).equals(""))&& 
    				((body.getData().get(OPG_TME_ICC_DFL_TYP_VAL).equals("2")) ||
    				(body.getData().get(OPG_TME_ICC_DFL_TYP_VAL).equals("3")))) && (body.getData().get(OPG_TME_ICC_DYN_NAM) != null && !body.getData().get(OPG_TME_ICC_DYN_NAM).equals(""))) {
    			    	// Si la Hora apertura iqrf dinamico está informada, retornar un error. 

        				codError = new BigDecimal(20005);
        				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_TME_ICC_DYN_NAM, cmpVal);
        				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_TME_ICC_DYN_NAM)).build();
        				errors.add(error);

    			}
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPG_TME_ICC_DYN_NAM)).build();
			errors.add(error);
		}
		
		//Validamos si existe el ramo
		try {
		   iDlTpdIcfDAO.get(cmpVal, new BigDecimal (body.getData().get(LOB_VAL)), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL).toString()).build();
			errors.add(error);
		}
		
		// Validamos si existe el procedimiento Identificador de iqrf dinamico
		try {
		    if(body.getData().get(IDN_ICC_DYN_NAM) != null  && !(body.getData().get(IDN_ICC_DYN_NAM).equals(""))){
			iDlTrnPrcDAO.getNomPrg(body.getData().get(IDN_ICC_DYN_NAM),body.getUser().getLanguage().toUpperCase(), cmpVal); 
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(IDN_ICC_DYN_NAM).toString()).build();
			errors.add(error);
		}
		
		// Validamos si existe el procedimiento Fecha apertura iqrf dinamico
		try {
		    if(body.getData().get(OPG_DAT_ICC_DYN_NAM) != null  && !(body.getData().get(OPG_DAT_ICC_DYN_NAM).equals(""))){
			iDlTrnPrcDAO.getNomPrg(body.getData().get(OPG_DAT_ICC_DYN_NAM),body.getUser().getLanguage().toUpperCase(), cmpVal);
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPG_DAT_ICC_DYN_NAM).toString()).build();
			errors.add(error);
		}
		
		// Validamos si existe el procedimiento Hora apertura iqrf dinamico
		try {
		    if(body.getData().get(OPG_TME_ICC_DYN_NAM) != null  && !(body.getData().get(OPG_TME_ICC_DYN_NAM).equals(""))){
			iDlTrnPrcDAO.getNomPrg(body.getData().get(OPG_TME_ICC_DYN_NAM),body.getUser().getLanguage().toUpperCase(), cmpVal);
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPG_TME_ICC_DYN_NAM).toString()).build();
			errors.add(error);
		}

		//Marca fecha apertura iqrf modificable
		if (body.getData().get(OPG_DAT_ICC_MDF_CHC) == null || (!body.getData().get(OPG_DAT_ICC_MDF_CHC).equals(CIns.YES) && !body.getData().get(OPG_DAT_ICC_MDF_CHC).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", DSB_ROW, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW).toString()).build();
			errors.add(error);
		}
		
		//Marca fecha apertura iqrf modificable
		if (body.getData().get(OPG_DAT_ICC_MDF_CHC) == null || (!body.getData().get(OPG_DAT_ICC_MDF_CHC).equals(CIns.YES) && !body.getData().get(OPG_DAT_ICC_MDF_CHC).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_DAT_ICC_MDF_CHC, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_DAT_ICC_MDF_CHC).toString()).build();
			errors.add(error);
		}
		
		//Marca hora apertura iqrf modificable
		if (body.getData().get(OPG_TME_ICC_MDF_CHC) == null || (!body.getData().get(OPG_TME_ICC_MDF_CHC).equals(CIns.YES) && !body.getData().get(OPG_TME_ICC_MDF_CHC).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", OPG_TME_ICC_MDF_CHC, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPG_TME_ICC_MDF_CHC).toString()).build();
			errors.add(error);
		}
		
		//Marca inhabilitado
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", DSB_ROW, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW).toString()).build();
			errors.add(error);
		}

		return errors;
    }
}
