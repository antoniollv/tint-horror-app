package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.pan.dl.IDlCmnPanDAO;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

@Service("Uar")
public class ValidationApiServiceUar implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String APP_VAL = "APP_VAL";

	protected static final String APP_USR_VAL = "APP_USR_VAL";

	protected static final String ROL_TYP_VAL = "ROL_TYP_VAL";

	protected static final String ROL_VAL = "ROL_VAL";

	protected static final String VLD_DAT = "VLD_DAT";

	@Autowired
	IDlTrnPrcDAO iDlTrnPrcDAO;

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	IDlCmnPanDAO iDlCmnPanDAO;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
		BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(VLD_DAT, APP_VAL, APP_USR_VAL, ROL_TYP_VAL, ROL_VAL);

			errors = compruebaErrores(noModificables, body, cmpVal);
		}	

		// Validamos si existe la APLICACION O PORTAL
		try {
			if (body.getData().get(APP_VAL) != null && !(body.getData().get(APP_VAL).equals(""))) {
				iDlCmnPanDAO.getAplicacion(cmpVal, new String(body.getData().get(APP_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(APP_VAL).toString()).build();
			errors.add(error);
		}

		// Validamos si existe el USUARIO DE LA APLICACION
		try {
			if (body.getData().get(APP_USR_VAL) != null && !(body.getData().get(APP_USR_VAL).equals(""))) {
				iDlCmnPanDAO.getUsuarioAplicacion(cmpVal, new String(body.getData().get(APP_USR_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(APP_USR_VAL).toString()).build();
			errors.add(error);
		}

		// Validamos si existe el CODIGO DEL ROL
		try {
			if (body.getData().get(ROL_VAL) != null && !(body.getData().get(ROL_VAL).equals("")) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))) {
				Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());
				iDlCmnPanDAO.getCodigoRol(cmpVal, new String(body.getData().get(ROL_VAL)), vldDat, 
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ROL_VAL).toString()).build();
			errors.add(error);
		}

		// Validamos si existe el TIPO DE ROL
		try {
			if (body.getData().get(ROL_TYP_VAL) != null && !(body.getData().get(ROL_TYP_VAL).equals(""))) {
				
				iDlCmnPanDAO.getTipoRol(cmpVal, new String(body.getData().get(ROL_TYP_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ROL_TYP_VAL).toString()).build();
			errors.add(error);
		}



		return errors;
	}

	private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		OTrnErrS lvError;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UAR", CMP_VAL,
						cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(cmpVal.toString()).build();
				errors.add(error);
			}
		}
		return errors;

	}
}
