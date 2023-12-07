package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.tpd.iif.dl.IDlTpdIifDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

@Service("IifMng")
public class ValidationApiServiceIifMng implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String MNG_TYP_VAL = "MNG_TYP_VAL";

	protected static final String IMC_COD_TYP_VAL = "IMC_COD_TYP_VAL";

	protected static final String VLD_DAT = "VLD_DAT";

	protected static final String DSB_ROW = "DSB_ROW";

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	IDlTpdIifDAO iDlTpdIifDAO;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errorsIifMng = new ArrayList<>();
		ValidationError errorIifMng;
		String accion;
		OTrnErrS lvErrorIifMng;
		BigDecimal codError;
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT, MNG_TYP_VAL, IMC_COD_TYP_VAL);

			errorsIifMng = compruebaErroresIifMng(noModificables, body, cmpVal);
		}

		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get(DSB_ROW) == null
				|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvErrorIifMng = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IIF", DSB_ROW, cmpVal);
			errorIifMng = ValidationError.builder().code(lvErrorIifMng.getErrIdnVal()).field(lvErrorIifMng.getPrpNam())
					.value(body.getData().get(DSB_ROW)).build();
			errorsIifMng.add(errorIifMng);
		}	

		// Validamos si existe TIPO de GESTION
		try {
			if (body.getData().get(MNG_TYP_VAL) != null && !(body.getData().get(MNG_TYP_VAL).equals(""))) {
				iDlTpdIifDAO.getTipoGestion(cmpVal, body.getData().get(MNG_TYP_VAL),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorIifMng = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(MNG_TYP_VAL)).build();
			errorsIifMng.add(errorIifMng);
		}

		// Validamos si existe CODIGO IMPACTO
		try {
			if (body.getData().get(IMC_COD_TYP_VAL) != null && !(body.getData().get(IMC_COD_TYP_VAL).equals(""))) {
				iDlTpdIifDAO.getCodigoImpacto(cmpVal, body.getData().get(IMC_COD_TYP_VAL),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorIifMng = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(IMC_COD_TYP_VAL)).build();
			errorsIifMng.add(errorIifMng);
		}

		return errorsIifMng;
	}

	private List<ValidationError> compruebaErroresIifMng(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsIifMng = new ArrayList<>();
		ValidationError errorIifMng;
		OTrnErrS lvErrorIifMng;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorIifMng = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IIF", CMP_VAL,
						cmpVal);
				errorIifMng = ValidationError.builder().code(lvErrorIifMng.getErrIdnVal()).field(lvErrorIifMng.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsIifMng.add(errorIifMng);
			}
		}
		return errorsIifMng;

	}
}
