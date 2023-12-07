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
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.syp.bo.OTrnSypS;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.syp.dl.IDlTrnSypDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Avd")
public class ValidationApiServiceAvd implements ValidationApiService {

    protected static final String ACV_THP_COD = "ACV_THP_COD";

    protected static final String STU_GRP_VAL = "STU_GRP_VAL";

    protected static final String STU_VAL = "STU_VAL";

    protected static final String THP_ACV_VAL = "THP_ACV_VAL";

    protected static final String THP_COD_GNR = "THP_COD_GNR";

    @Autowired
    IDlTrnSypDAO iDlTrnSypDAO;

    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    OTrnSypS oTrnSypS;
    OTpdAvdS oTpdAvdS;

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


	    if(accion.equals(CTrn.ACN_TYP_MDF) && !body.getDataOld().get(THP_ACV_VAL).equals(body.getData().get(THP_ACV_VAL))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", THP_ACV_VAL, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(THP_ACV_VAL)).build();
		errors.add(error);
	    }

	// Si STU_VAL no es nulo
	if(body.getData().get(STU_VAL) != null && !body.getData().get(STU_VAL).equals("")) {

	    try {
		String usrLngVal =  body.getUser().getLanguage().toUpperCase();

		oTrnSypS = iDlTrnSypDAO.get(cmpVal, body.getData().get(STU_VAL), usrLngVal);

	    } 
	    catch (NwtException e) {
		error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(STU_VAL)).build();
		errors.add(error);
	    }
	}

	// Si STU_GRP_VAL no es nulo
	if(body.getData().get(STU_GRP_VAL) != null && !body.getData().get(STU_GRP_VAL).equals("")) {

	    try {
		String usrLngVal =  body.getUser().getLanguage().toUpperCase();

		oTpdAvdS = iDlTpdAvdDAO.get_stuGrpVal(cmpVal, body.getData().get(STU_GRP_VAL), usrLngVal);

	    } 
	    catch (NwtException e) {
		error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(STU_GRP_VAL)).build();
		errors.add(error);
	    }
	}

	// Comprobamos que ACV_THP_COD valga S o N
	if (body.getData().get(ACV_THP_COD) == null || (!body.getData().get(ACV_THP_COD).equals(CIns.YES) && !body.getData().get(ACV_THP_COD).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", ACV_THP_COD, cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(ACV_THP_COD)).build();
	    errors.add(error);
	}

	// Comprobamos que THP_COD_GNR valga S o N
	if (body.getData().get(THP_COD_GNR) == null || (!body.getData().get(THP_COD_GNR).equals(CIns.YES) && !body.getData().get(THP_COD_GNR).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", THP_COD_GNR, cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(THP_COD_GNR)).build();
	    errors.add(error);
	} else {
			// THP_COD_GNR solo puede ser S si ACV_THP_COD = S
			if(body.getData().get(THP_COD_GNR).equals(CIns.YES) && body.getData().get(ACV_THP_COD).equals(CIns.NO)){
			    codError = new BigDecimal(20003050);
			    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", ACV_THP_COD, cmpVal);
			    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(ACV_THP_COD)).build();
			    errors.add(error);
			}
	}

	// Comprobamos que SPL valga S o N
	if (body.getData().get("SPL") == null || (!body.getData().get("SPL").equals(CIns.YES) && !body.getData().get("SPL").equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", "SPL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL")).build();
	    errors.add(error);
	}
	
	// Comprobamos que DCM_GNR valga S o N
	if (body.getData().get("DCM_GNR") == null || (!body.getData().get("DCM_GNR").equals(CIns.YES) && !body.getData().get("DCM_GNR").equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", "DCM_GNR", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DCM_GNR")).build();
	    errors.add(error);
	}

	return errors;
    }

}
