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
import com.mapfre.tron.api.tpd.ttc.dl.IDlTpdTtcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Ttc")
public class ValidationApiServiceTtc implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String DSB_ROW = "DSB_ROW";

	protected static final String LGP_VAL = "LGP_VAL"; //concepto logico a restringir

	protected static final String PHY_PRS = "PHY_PRS"; //Fisico

	protected static final String PRP_VAL = "PRP_VAL"; //Propiedad

	protected static final String ROL_VAL = "ROL_VAL"; //Rol

	protected static final String THP_ACV_VAL = "THP_ACV_VAL"; //Actividad tercero

	protected static final String TTC_ACS_TYP_VAL = "TTC_ACS_TYP_VAL"; //tipo de acceso

	protected static final String VLD_DAT = "VLD_DAT";

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	IDlTpdTtcDAO iDlTpdTtcDAO;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errorsTtc = new ArrayList<>();
		ValidationError errorTtc;
		String accion;
		OTrnErrS lvError;
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

			List<String> noModificables = Arrays.asList(CMP_VAL, THP_ACV_VAL, ROL_VAL, PHY_PRS, LGP_VAL, PRP_VAL, VLD_DAT);

			errorsTtc = compruebaErrores(noModificables, body, cmpVal);
		}	

		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get(DSB_ROW) == null
				|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "TTC", DSB_ROW, cmpVal);
			errorTtc = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(DSB_ROW).toString()).build();
			errorsTtc.add(errorTtc);
		}	

		// Validamos si existe la actividad
		try {
			if (body.getData().get(THP_ACV_VAL) != null && !(body.getData().get(THP_ACV_VAL).equals(""))) {
				iDlTpdTtcDAO.getActividad(cmpVal, new BigDecimal(body.getData().get(THP_ACV_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}

		} catch (NwtException e) {
			errorTtc = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(THP_ACV_VAL)).build();
			errorsTtc.add(errorTtc);
		}

		// Validamos si existe el rol
		try {
			if (body.getData().get(ROL_VAL) != null && !(body.getData().get(ROL_VAL).equals(""))) {
				iDlTpdTtcDAO.getRol(cmpVal, body.getData().get(ROL_VAL).toUpperCase(),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorTtc = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ROL_VAL)).build();
			errorsTtc.add(errorTtc);
		}
		
		// Validamos si el concepto logico
		try {
			if (body.getData().get(LGP_VAL) != null && !(body.getData().get(LGP_VAL).equals(""))) {
				iDlTpdTtcDAO.getConceptoLogico(cmpVal, body.getData().get(LGP_VAL).toUpperCase(),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorTtc = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LGP_VAL)).build();
			errorsTtc.add(errorTtc);
		}
		
		// Validamos si la propiedad
		try {
			if (body.getData().get(PRP_VAL) != null && !(body.getData().get(PRP_VAL).equals(""))) {
				iDlTpdTtcDAO.getPropiedad(cmpVal, body.getData().get(PRP_VAL).toUpperCase(),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorTtc = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PRP_VAL)).build();
			errorsTtc.add(errorTtc);
		}

		return errorsTtc;
	}

	private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> erroresTtc = new ArrayList<>();
		ValidationError errorTtc1;
		OTrnErrS lvError;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "TTC", CMP_VAL,
						cmpVal);
				errorTtc1 = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				erroresTtc.add(errorTtc1);
			}
		}
		return erroresTtc;

	}
}
