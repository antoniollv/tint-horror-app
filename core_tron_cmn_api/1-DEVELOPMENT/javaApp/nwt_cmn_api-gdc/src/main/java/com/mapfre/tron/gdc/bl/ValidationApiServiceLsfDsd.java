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

@Service("LsfDsd")
public class ValidationApiServiceLsfDsd implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String LOB_VAL = "LOB_VAL";
	
	protected static final String BNF_TYP_PRD_NAM = "BNF_TYP_PRD_NAM";
	
	protected static final String PYM_THP_ACV_PRD_NAM = "PYM_THP_ACV_PRD_NAM";
	
	protected static final String PYM_THP_PRD_NAM = "PYM_THP_PRD_NAM";
	
	protected static final String PYM_THP_DCM_TYP_PRD_NAM = "PYM_THP_DCM_TYP_PRD_NAM";
	
	protected static final String PYM_THP_DCM_PRD_NAM = "PYM_THP_DCM_PRD_NAM";
	
	protected static final String TSY_DCM_PRD_NAM = "TSY_DCM_PRD_NAM";
	
	protected static final String DCM_DAT_PRD_NAM = "DCM_DAT_PRD_NAM";
	
	protected static final String EST_PYM_DAT_PRD_NAM = "EST_PYM_DAT_PRD_NAM";
	
	protected static final String DCM_CRN_PRD_NAM = "DCM_CRN_PRD_NAM";
	
	protected static final String DCM_TAX_TYP_PRD_NAM = "DCM_TAX_TYP_PRD_NAM";
	
	protected static final String DCM_RCN_DAT_PRD_NAM = "DCM_RCN_DAT_PRD_NAM";
	
	protected static final String PYM_THR_LVL_PRD_NAM = "PYM_THR_LVL_PRD_NAM";
	
	protected static final String SND_THR_LVL_PRD_NAM = "SND_THR_LVL_PRD_NAM";
	
	protected static final String THP_TAX_TYP_PRD_NAM = "THP_TAX_TYP_PRD_NAM";
	
	protected static final String PYM_THP_BIC_PRD_NAM = "PYM_THP_BIC_PRD_NAM";

	

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

			List<String> noModificables = Arrays.asList(CMP_VAL);

			errorsLsfLob = compruebaErroresLsfLob(noModificables, body, cmpVal);
		}

		// Validamos si existe el RAMO
		try {
			if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
				iDlLsfLsfDAO.getCodRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorLsfLob = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL)).build();
			errorsLsfLob.add(errorLsfLob);
		}

		// Validamos los procedimientos
		List<String> procedimientos = Arrays.asList(BNF_TYP_PRD_NAM, PYM_THP_ACV_PRD_NAM, PYM_THP_PRD_NAM,
				PYM_THP_DCM_TYP_PRD_NAM, PYM_THP_DCM_PRD_NAM, TSY_DCM_PRD_NAM, DCM_DAT_PRD_NAM, EST_PYM_DAT_PRD_NAM,
				DCM_CRN_PRD_NAM, DCM_TAX_TYP_PRD_NAM, DCM_RCN_DAT_PRD_NAM, PYM_THR_LVL_PRD_NAM, SND_THR_LVL_PRD_NAM,
				THP_TAX_TYP_PRD_NAM, PYM_THP_BIC_PRD_NAM);

		compruebaProcedimientosLsfLob(errorsLsfLob, procedimientos, body, cmpVal);

		return errorsLsfLob;
	}

	private void compruebaProcedimientosLsfLob(List<ValidationError> errorsLsfLob, List<String> procedimientos,
			DataInExtended body, BigDecimal cmpVal) {

		ValidationError errorLsfLob;
		for (String name : procedimientos) {
			try {
				if (body.getData().get(name) != null && !(body.getData().get(name).equals(""))) {
					iDlLsfLobDAO.getNomPrg(body.getData().get(name), body.getUser().getLanguage().toUpperCase(), cmpVal,
							name.equals(PYM_THP_BIC_PRD_NAM) ? "PYM_THP_BIC_PRD": name);
				}
			} catch (NwtException e) {
				errorLsfLob = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
						.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(name)).build();
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
				lvErrorLsfLob = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF",
						CMP_VAL, cmpVal);
				errorLsfLob = ValidationError.builder().code(lvErrorLsfLob.getErrIdnVal())
						.field(lvErrorLsfLob.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
				errorsLsfLob.add(errorLsfLob);
			}
		}
		return errorsLsfLob;

	}

} 


