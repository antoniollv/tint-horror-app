package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.lsf.lob.dl.IDlLsfLobDAO;
import com.mapfre.tron.api.lsf.lsf.dl.IDlLsfLsfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("LsfLob")
public class ValidationApiServiceLsfLob implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String LOB_VAL = "LOB_VAL";

	protected static final String FUT_LSS_PRD_NAM = "FUT_LSS_PRD_NAM";

	protected static final String UML_PRD_NAM = "UML_PRD_NAM";

	protected static final String LSS_AMN_PRD_NAM = "LSS_AMN_PRD_NAM";

	protected static final String OPV_POS_PRD_NAM = "OPV_POS_PRD_NAM";

	protected static final String LSS_MDF_DAT_PRD_NAM = "LSS_MDF_DAT_PRD_NAM";

	protected static final String EVT_TMP_ENR_PRD_NAM = "EVT_TMP_ENR_PRD_NAM";

	protected static final String MNM_DAA_PLY_PRD_NAM = "MNM_DAA_PLY_PRD_NAM";

	protected static final String PEM_PLY_IOP_NTS_PRD_NAM = "PEM_PLY_IOP_NTS_PRD_NAM";

	protected static final String PEM_RSK_IOP_NTS_PRD_NAM = "PEM_RSK_IOP_NTS_PRD_NAM";

	protected static final String GET_SPV_PRD_NAM = "GET_SPV_PRD_NAM";

	protected static final String TSD_RCP_PRD_NAM = "TSD_RCP_PRD_NAM";	

	protected static final String INY_RLN_TYP_PRD_NAM = "INY_RLN_TYP_PRD_NAM";	

	protected static final String FNL_LSS_OPE_PRD_NAM = "FNL_LSS_OPE_PRD_NAM";	

	protected static final String FNL_LSS_MDF_PRD_NAM = "FNL_LSS_MDF_PRD_NAM";

	

	@Autowired
	IDlLsfLsfDAO iDlLsfLsfDAO;

	@Autowired
	DlTrnErr lvDlTrnError;
	
	@Autowired
	IDlLsfLobDAO iDlLsfLobDAO;

	

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {


		ValidationError errorLsfLob;
		List<ValidationError> errorsLsfLob = new ArrayList<>();		
		String accion;

		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, LOB_VAL);

			errorsLsfLob = compruebaErroresLsfLob(noModificables, body, cmpVal);
		}

		// Validamos si existe el RAMO
		try {
		    if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
			iDlLsfLsfDAO.getCodRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
				body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    errorLsfLob = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL)).build();
		    errorsLsfLob.add(errorLsfLob);
		}

		//Validamos los procedimientos
		List<String> procedimientos = Arrays.asList(FUT_LSS_PRD_NAM, UML_PRD_NAM, LSS_AMN_PRD_NAM, OPV_POS_PRD_NAM, LSS_MDF_DAT_PRD_NAM, EVT_TMP_ENR_PRD_NAM, MNM_DAA_PLY_PRD_NAM, PEM_PLY_IOP_NTS_PRD_NAM, PEM_RSK_IOP_NTS_PRD_NAM, GET_SPV_PRD_NAM, TSD_RCP_PRD_NAM, INY_RLN_TYP_PRD_NAM, FNL_LSS_OPE_PRD_NAM, FNL_LSS_MDF_PRD_NAM);

		compruebaProcedimientosLsfLob(errorsLsfLob, procedimientos, body, cmpVal);
		

		return errorsLsfLob;
	}

	private void compruebaProcedimientosLsfLob(List<ValidationError> errorsLsfLob, List<String> procedimientos, DataInExtended body,
			BigDecimal cmpVal) {
		
		ValidationError errorLsfLob;
		for (String name : procedimientos) {
			try {
				if(body.getData().get(name) != null  && !(body.getData().get(name).equals(""))){
					iDlLsfLobDAO.getNomPrg(body.getData().get(name),body.getUser().getLanguage().toUpperCase(), cmpVal, name);
				}
			}
			catch (NwtException e) {
				errorLsfLob = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(name)).build();
				errorsLsfLob.add(errorLsfLob);
			}
		}

	}

	private List<ValidationError> compruebaErroresLsfLob(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsLsfLob = new ArrayList<>();
		ValidationError errorLsfLob;
		OTrnErrS lvErrorLsfLob;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorLsfLob = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF", CMP_VAL,
						cmpVal);
				errorLsfLob = ValidationError.builder().code(lvErrorLsfLob.getErrIdnVal()).field(lvErrorLsfLob.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsLsfLob.add(errorLsfLob);
			}
		}
		return errorsLsfLob;

	}

} 


