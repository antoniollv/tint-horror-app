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
import com.mapfre.nwt.trn.ply.sdt.bo.OPlySdtS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.ply.sdt.dl.IDlPlySdtDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Sdt")
public class ValidationApiServiceSdt implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String APL_VAL = "APL_VAL";

	protected static final String PLY_VAL = "PLY_VAL";

	protected static final String PYM_DAY_VAL = "PYM_DAY_VAL";

	protected static final String VLD_DAT = "VLD_DAT";

	protected static final String DSB_ROW = "DSB_ROW";

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	IDlPlySdtDAO iDlPlySdtDAO;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errorsSdt = new ArrayList<>();
		List<OPlySdtS> oPlySdtPT;
		ValidationError errorSdt;
		BigDecimal daymin = new BigDecimal(0);
		BigDecimal daymax = new BigDecimal(30);
		String accion;
		OTrnErrS lvError;
		BigDecimal codError;
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
		BigDecimal pymDayVal = new BigDecimal(body.getData().get(PYM_DAY_VAL));
		BigDecimal aplVal = new BigDecimal(body.getData().get(APL_VAL));
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT);

			errorsSdt = compruebaErroresSdt(noModificables, body, cmpVal);
		}	

		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get(DSB_ROW) == null
				|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", DSB_ROW, cmpVal);
			errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(DSB_ROW)).build();
			errorsSdt.add(errorSdt);
		}

		// Validamos si el dia de pago es menor que 30 


		if (body.getData().get(PYM_DAY_VAL) == null || (pymDayVal.compareTo(daymin) == 0) || (pymDayVal.compareTo(daymax) > 0)) {	
			codError = new BigDecimal(70001086);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", PYM_DAY_VAL, cmpVal);
			lvError.setErrIdnVal(lvError.getErrIdnVal().concat(": 1 - 30"));
			errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(PYM_DAY_VAL)).build();
			errorsSdt.add(errorSdt);
		}

		// Validamos si existe CÓDIGO PÓLIZA
		try {
			oPlySdtPT = iDlPlySdtDAO.getPoliza(cmpVal, body.getData().get(PLY_VAL),
					body.getUser().getLanguage().toUpperCase());

			if(!oPlySdtPT.get(0).getPlyVal().equals(body.getData().get(PLY_VAL))) {
				codError = new BigDecimal(20001);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", PLY_VAL, cmpVal);
				errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(body.getData().get(PLY_VAL)).build();
				errorsSdt.add(errorSdt);
			}
		} catch (NwtException e) {
			codError = e.getErrors().get(0).getErrVal();
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", PLY_VAL, cmpVal);
			errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(PLY_VAL)).build();
			errorsSdt.add(errorSdt);

		}

		// Validamos si existe NÚMERO DE APLICACIÓN
		try {
			oPlySdtPT = iDlPlySdtDAO.getAplicacion(cmpVal, body.getData().get(PLY_VAL),
					aplVal, body.getUser().getLanguage().toUpperCase());
			
			if(oPlySdtPT.get(0).getAplVal().compareTo(aplVal) != 0) {
				codError = new BigDecimal(20001);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", APL_VAL, cmpVal);
				errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(body.getData().get(APL_VAL)).build();
				errorsSdt.add(errorSdt);
			}
			
		} catch (NwtException e) {
			codError = e.getErrors().get(0).getErrVal();
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", APL_VAL, cmpVal);
			errorSdt = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(APL_VAL)).build();
			errorsSdt.add(errorSdt);
			
		}

		return errorsSdt;
	}

	private List<ValidationError> compruebaErroresSdt(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> erroresSdt = new ArrayList<>();
		ValidationError errorSdt1;
		OTrnErrS lvError;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SDT", CMP_VAL,
						cmpVal);
				errorSdt1 = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(cmpVal.toString()).build();
				erroresSdt.add(errorSdt1);
			}
		}
		return erroresSdt;

	}
}
