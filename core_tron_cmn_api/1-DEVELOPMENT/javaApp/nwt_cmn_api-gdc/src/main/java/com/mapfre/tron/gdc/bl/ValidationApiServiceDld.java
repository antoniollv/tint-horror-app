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

@Service("Dld")
public class ValidationApiServiceDld implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String LOB_VAL = "LOB_VAL";

	protected static final String DFL_OCR_DAT_PRD_NAM = "DFL_OCR_DAT_PRD_NAM";

	protected static final String DFL_OCR_TME_PRD_NAM = "DFL_OCR_TME_PRD_NAM"; 

	protected static final String DFL_COT_DAT_PRD_NAM = "DFL_COT_DAT_PRD_NAM";

	protected static final String DFL_COT_TME_PRD_NAM = "DFL_COT_TME_PRD_NAM";

	protected static final String DFL_PLY_PRD_NAM = "DFL_PLY_PRD_NAM";

	protected static final String DFL_RSK_PRD_NAM = "DFL_RSK_PRD_NAM";

	protected static final String DFL_APL_PRD_NAM = "DFL_APL_PRD_NAM";

	@Autowired
	IDlLsfLsfDAO iDlLsfLsfDAO;

	@Autowired
	DlTrnErr lvDlTrnError;
	
	@Autowired
	IDlLsfLobDAO iDlLsfLobDAO;

	

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {


		ValidationError errorDld;
		List<ValidationError> errorsDld = new ArrayList<>();		
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

			errorsDld = compruebaErroresDld(noModificables, body, cmpVal);
		}

		// Validamos si existe el RAMO
		try {
		    if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
			iDlLsfLsfDAO.getCodRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
				body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    errorDld = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL)).build();
		    errorsDld.add(errorDld);
		}

		//Validamos los procedimientos
		List<String> procedimientos = Arrays.asList(DFL_OCR_DAT_PRD_NAM, DFL_OCR_TME_PRD_NAM, DFL_COT_DAT_PRD_NAM, DFL_COT_TME_PRD_NAM, DFL_PLY_PRD_NAM, DFL_RSK_PRD_NAM, DFL_APL_PRD_NAM);

		compruebaProcedimientosDld(errorsDld, procedimientos, body, cmpVal);
		

		return errorsDld;
	}

	private void compruebaProcedimientosDld(List<ValidationError> errorsDld, List<String> procedimientos, DataInExtended body,
			BigDecimal cmpVal) {
		
		ValidationError errorDld;
		for (String name : procedimientos) {
			try {
				if(body.getData().get(name) != null  && !(body.getData().get(name).equals(""))){
					iDlLsfLobDAO.getNomPrg(body.getData().get(name),body.getUser().getLanguage().toUpperCase(), cmpVal, name);
				}
			}
			catch (NwtException e) {
				errorDld = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(name)).build();
				errorsDld.add(errorDld);
			}
		}

	}

	private List<ValidationError> compruebaErroresDld(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsDld = new ArrayList<>();
		ValidationError errorDld;
		OTrnErrS lvErrorDld;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorDld = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF", CMP_VAL,
						cmpVal);
				errorDld = ValidationError.builder().code(lvErrorDld.getErrIdnVal()).field(lvErrorDld.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsDld.add(errorDld);
			}
		}
		return errorsDld;

	}

} 


