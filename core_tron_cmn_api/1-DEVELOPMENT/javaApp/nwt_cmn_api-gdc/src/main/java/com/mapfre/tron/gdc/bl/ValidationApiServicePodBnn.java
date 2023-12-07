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
import com.mapfre.tron.api.pod.bnn.dl.IDlPodBnnDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("PodBnn")
public class ValidationApiServicePodBnn implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String BNE_VAL = "BNE_VAL";

	protected static final String BNK_NSW_VAL = "BNK_NSW_VAL";

	protected static final String LNG_VAL = "LNG_VAL";	
	
	protected static final String PYM_ATD = "PYM_ATD";

	@Autowired
	IDlPodBnnDAO iDlPodBnnDAO;

	@Autowired
	DlTrnErr lvDlTrnError;
	

	

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {


		ValidationError errorPodBnn;
		List<ValidationError> errorsPodBnn = new ArrayList<>();		
		String accion;

		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, BNE_VAL, BNK_NSW_VAL, LNG_VAL);

			errorsPodBnn = compruebaErroresPodBnn(noModificables, body, cmpVal);
		}

		// Validamos si existe la Entidad Bancaria
		try {
		    if (body.getData().get(BNE_VAL) != null && !(body.getData().get(BNE_VAL).equals(""))) {
		    	iDlPodBnnDAO.getEntidadBancaria(cmpVal, body.getData().get(BNE_VAL), body.getData().get(LNG_VAL),
				body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    errorPodBnn = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(BNE_VAL)).build();
		    errorsPodBnn.add(errorPodBnn);
		}
		
		// Validamos si Marca Pago Autorizado tiene una 'S' para un mismo banco		
		if(accion.equals(CTrn.ACN_TYP_CRT) || !(body.getData().get(PYM_ATD).equals(body.getDataOld().get(PYM_ATD)))){
			try {
				if (body.getData().get(BNE_VAL) != null && body.getData().get(PYM_ATD) != null && !(body.getData().get(BNE_VAL).equals("")) && !(body.getData().get(PYM_ATD).equals("")) && body.getData().get(PYM_ATD).equals("S")) {
					iDlPodBnnDAO.getMarcaPagoAutorizado(cmpVal, body.getData().get(BNE_VAL), body.getData().get(LNG_VAL),
							body.getUser().getLanguage().toUpperCase());
				}
			} catch (NwtException e) {
				errorPodBnn = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PYM_ATD)).build();
				errorsPodBnn.add(errorPodBnn);
			}
		}
		

		return errorsPodBnn;
	}

	

	private List<ValidationError> compruebaErroresPodBnn(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> errorsPodBnn = new ArrayList<>();
		ValidationError errorPodBnn;
		OTrnErrS lvErrorPodBnn;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorPodBnn = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "POD", name,
						cmpVal);
				errorPodBnn = ValidationError.builder().code(lvErrorPodBnn.getErrIdnVal()).field(lvErrorPodBnn.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				errorsPodBnn.add(errorPodBnn);
			}
		}
		return errorsPodBnn;

	}

} 


