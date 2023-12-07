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
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Dxd")
public class ValidationApiServiceDxd implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";
 
	protected static final String DFL_EML_PRD_NAM = "DFL_EML_PRD_NAM";

	protected static final String DFL_INY_RLN_TYP_VAL_PRD_NAM = "DFL_INY_RLN_TYP_VAL_PRD_NAM";

	protected static final String DFL_THP_DCM_TYP_VAL_PRD_NAM = "DFL_THP_DCM_TYP_VAL_PRD_NAM";

	protected static final String DFL_THP_DCM_VAL_PRD_NAM = "DFL_THP_DCM_VAL_PRD_NAM";

	protected static final String DFL_THP_NAM_PRD_NAM = "DFL_THP_NAM_PRD_NAM";

	protected static final String DFL_THP_SRN_PRD_NAM = "DFL_THP_SRN_PRD_NAM";

	protected static final String DFL_TLP_ARE_PRD_NAM = "DFL_TLP_ARE_PRD_NAM";

	protected static final String DFL_TLP_CNY_VAL_PRD_NAM = "DFL_TLP_CNY_VAL_PRD_NAM";

	protected static final String DFL_TLP_VAL_PRD_NAM = "DFL_TLP_VAL_PRD_NAM";

	@Autowired
	DlTrnErr lvDlTrnError;
	
	@Autowired
	IDlLsfLobDAO iDlLsfLobDAO;
	

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {

		List<ValidationError> errorsDxd = new ArrayList<>();		
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

			errorsDxd = compruebaErroresDxd(noModificables, body, cmpVal);
		}		

		//Validamos los procedimientos
		List<String> procedimientos = Arrays.asList(DFL_EML_PRD_NAM, DFL_INY_RLN_TYP_VAL_PRD_NAM, DFL_THP_DCM_TYP_VAL_PRD_NAM, DFL_THP_DCM_VAL_PRD_NAM, DFL_THP_NAM_PRD_NAM, DFL_THP_SRN_PRD_NAM, DFL_TLP_ARE_PRD_NAM, DFL_TLP_CNY_VAL_PRD_NAM, DFL_TLP_VAL_PRD_NAM);

		compruebaProcedimientosDxd(errorsDxd, procedimientos, body, cmpVal);		

		return errorsDxd;
	}

	private void compruebaProcedimientosDxd(List<ValidationError> errorsDxd, List<String> procedimientos, DataInExtended body,
			BigDecimal cmpVal) {
		
		ValidationError errorDxd;
		for (String name : procedimientos) {
			try {
				if(body.getData().get(name) != null  && !(body.getData().get(name).equals(""))){
					iDlLsfLobDAO.getNomPrg(body.getData().get(name),body.getUser().getLanguage().toUpperCase(), cmpVal, name);
				}
			}
			catch (NwtException e) {
				errorDxd = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(name)).build();
				errorsDxd.add(errorDxd);
			}
		}

	}

	private List<ValidationError> compruebaErroresDxd(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsDxd = new ArrayList<>();
		ValidationError errorDxd;
		OTrnErrS lvErrorDxd;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorDxd = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF", CMP_VAL,
						cmpVal);
				errorDxd = ValidationError.builder().code(lvErrorDxd.getErrIdnVal()).field(lvErrorDxd.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsDxd.add(errorDxd);
			}
		}
		return errorsDxd;

	}

} 


