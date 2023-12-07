package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.tpd.c.CTpd;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.tpd.sib.dl.IDlTpdSibDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("TpdSib")
public class ValidationApiServiceTpdSib implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;
    @Autowired
    IDlTpdSibDAO iDlTpdSibDAO;

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
	} else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	// Si la acion es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {
	    if (!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "CMP_VAL",
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("CMP_VAL").toString()).build();
		errors.add(error);
	    }

	    if (!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
			"THP_ACV_VAL", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
		errors.add(error);
	    }

	    if (!body.getDataOld().get("ROL_VAL").equals(body.getData().get("ROL_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "ROL_VAL",
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("ROL_VAL").toString()).build();
		errors.add(error);
	    }
	}

	// Comprobamos si existe la actividad
	try {
	    BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
	    iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL").toString())
		    .build();
	    errors.add(error);
	}
	
	// Comprobamos si existe el rol
	try {
	    String rol = body.getData().get("ROL_VAL");
	    iDlTpdSibDAO.get_Rol(cmpVal, rol, body.getUser().getLanguage().toUpperCase());
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("ROL_VAL").toString())
		    .build();
	    errors.add(error);
	}

	// Comprobamos valores de acciones permitidas
	if (body.getData().get("SPL_RTE_ACS_TYP_VAL") == null
		|| (!body.getData().get("SPL_RTE_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("SPL_RTE_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("SPL_RTE_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "SPL_RTE_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("SPL_RTE_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("AZO_ACS_TYP_VAL") == null 
		|| (!body.getData().get("AZO_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("AZO_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("AZO_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "AZO_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("AZO_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("SSV_NRM_ACS_TYP_VAL") == null
		|| (!body.getData().get("SSV_NRM_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("SSV_NRM_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("SSV_NRM_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "SSV_NRM_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("SSV_NRM_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("SSV_ADD_VAL_ACS_TYP_VAL") == null
		|| (!body.getData().get("SSV_ADD_VAL_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("SSV_ADD_VAL_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("SSV_ADD_VAL_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "SSV_ADD_VAL_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("SSV_ADD_VAL_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("ATE_DAA_ACS_TYP_VAL") == null 
		|| (!body.getData().get("ATE_DAA_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("ATE_DAA_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("ATE_DAA_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "ATE_DAA_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("ATE_DAA_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("CTY_ACS_TYP_VAL") == null 
		|| (!body.getData().get("CTY_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("CTY_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("CTY_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "CTY_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("CTY_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("ICC_ACS_TYP_VAL") == null 
		|| (!body.getData().get("ICC_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("ICC_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("ICC_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "ICC_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("ICC_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("FRD_ACS_TYP_VAL") == null 
		|| (!body.getData().get("FRD_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("FRD_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("FRD_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "FRD_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("FRD_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("VLT_ACS_TYP_VAL") == null 
		|| (!body.getData().get("VLT_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("VLT_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("VLT_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "VLT_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("VLT_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	if (body.getData().get("TNG_ACS_TYP_VAL") == null 
		|| (!body.getData().get("TNG_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_S)
			&& !body.getData().get("TNG_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_N)
			&& !body.getData().get("TNG_ACS_TYP_VAL").equals(CTpd.SIB_ACS_TYP_C))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "TNG_ACS_TYP_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("TNG_ACS_TYP_VAL").toString()).build();
	    errors.add(error);
	}

	return errors;
    }

}
