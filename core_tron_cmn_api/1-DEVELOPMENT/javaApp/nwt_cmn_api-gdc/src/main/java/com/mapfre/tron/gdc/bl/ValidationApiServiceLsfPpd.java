package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cache.CacheableAttribute;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypDao;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;
import com.mapfre.tron.api.lsf.lob.dl.IDlLsfLobDAO;
import com.mapfre.tron.api.lsf.lsf.dl.IDlLsfLsfDAO;
import com.mapfre.tron.api.lsf.ppd.dl.IDlLsfPpdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("LsfPpd")
public class ValidationApiServiceLsfPpd implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String LOB_VAL = "LOB_VAL";

    protected static final String FIL_TYP_VAL = "FIL_TYP_VAL";

    protected static final String BNF_TYP_VAL = "BNF_TYP_VAL";
    
    protected static final String THP_ACV_VAL = "THP_ACV_VAL";
    
    protected static final String TYP_APY_RCP_PND = "TYP_APY_RCP_PND";
    
    protected static final String TYP_APY_RCP_PND_PRD_TYP_VAL = "TYP_APY_RCP_PND_PRD_TYP_VAL";
    
    protected static final String TYP_APY_RCP_PND_PRD_NAM = "TYP_APY_RCP_PND_PRD_NAM";
    
    protected static final String VLD_DAT = "VLD_DAT";
    
    protected static final String DSB_ROW = "DSB_ROW";
	
	@Autowired
	IDlLsfLsfDAO iDlLsfLsfDAO;
	
	@Autowired
	IDlLsfPpdDAO iDlLsfPpdDAO;

	@Autowired
	DlTrnErr lvDlTrnError;
	
	@Autowired
	IDlLsfLobDAO iDlLsfLobDAO;
	
	@Autowired
	IDlTrnNtfDAO iDlTrnNtfDAO;
	
	@Autowired
    protected OCmnTypDao oCmnTypDao;
	
	@Autowired
    protected CacheableAttribute cacheableAttribute;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));

		validateAcnTyp(body, cmpVal, errors);

		// Validamos si existe el RAMO
		validateLobVal(body, cmpVal, errors);
		
		// Validamos si existe el TIPO DE EXPEDIENTE POR RAMO
		validateFiltypVal(body, cmpVal, errors);
		
		//Validamos si existe el TIPO BENEFICIARIO
		validateBnfTypVal(body, cmpVal, errors);
		
		// Validamos que exista la ACTIVIDAD DEL TERCERO
		validateThpAcvVal(body, cmpVal, errors);

		//Validamos que exista el TIPO DE APLICA RECIBO PENDIENTE
		validateTypApyRcpPnd(body, cmpVal, errors);
		
		//Validamos que exista el TIPO DE APLICA RECIBO PENDIENTE TIPO Y
		validateTypApyRcpPndPrd(body, cmpVal, errors);
		
		//Validamos que exista el TIPO DE APLICA RECIBO PENDIENTE DINAMICO
		validateTypApyRcpPndPrdTyp(body, cmpVal, errors);

		// Validamos que DSB_ROW tenga valor S o N
		validateDsbRow(body, cmpVal, errors);
		
		return errors;
	}
	
	private void validateAcnTyp(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		String accion;

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		} else {
			accion = CTrn.ACN_TYP_MDF;
		}

		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			List<String> noModificables = Arrays.asList(CMP_VAL, LOB_VAL, FIL_TYP_VAL, BNF_TYP_VAL, THP_ACV_VAL,
					TYP_APY_RCP_PND, VLD_DAT);

			compruebaErrores(noModificables, body, cmpVal, errors);
		}

	}

	private void compruebaErrores(List<String> noModificables, DataInExtended body, BigDecimal cmpVal,
			List<ValidationError> errors) {

		ValidationError errorPPDLob;
		OTrnErrS lvErrorPPDLob;
		BigDecimal codError;

		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvErrorPPDLob = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "PPD",
						CMP_VAL, cmpVal);
				errorPPDLob = ValidationError.builder().code(lvErrorPPDLob.getErrIdnVal())
						.field(lvErrorPPDLob.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
				errors.add(errorPPDLob);
			}
		}

	}

	private ValidationError getError(DataInExtended body, String keyMap, NwtException e) {
		return ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
				.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(keyMap)).build();
	}

	private void validateLobVal(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
				iDlLsfLsfDAO.getCodRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errors.add(getError(body, LOB_VAL, e));
		}
	}

	private void validateFiltypVal(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(FIL_TYP_VAL) != null && !(body.getData().get(FIL_TYP_VAL).equals(""))) {
				iDlLsfPpdDAO.getFilTypVal(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
						body.getData().get(FIL_TYP_VAL), body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errors.add(getError(body, FIL_TYP_VAL, e));
		}
	}

	private void validateBnfTypVal(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(BNF_TYP_VAL) != null && !(body.getData().get(BNF_TYP_VAL).equals(""))) {
				iDlLsfPpdDAO.getCmnTypS("TIP_BENEF", body.getData().get(BNF_TYP_VAL),
						body.getUser().getLanguage().toUpperCase(), cmpVal, BNF_TYP_VAL);
			}
		} catch (NwtException e) {
			errors.add(getError(body, BNF_TYP_VAL, e));
		}

	}

	private void validateThpAcvVal(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(THP_ACV_VAL) != null && !(body.getData().get(THP_ACV_VAL).equals(""))) {
				iDlTrnNtfDAO.get_thpAcvVal(cmpVal, new BigDecimal(body.getData().get(THP_ACV_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errors.add(getError(body, THP_ACV_VAL, e));
		}

	}

	private void validateTypApyRcpPnd(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(TYP_APY_RCP_PND) != null && !(body.getData().get(TYP_APY_RCP_PND).equals(""))) {
				iDlLsfPpdDAO.getCmnTypS(TYP_APY_RCP_PND, body.getData().get(TYP_APY_RCP_PND),
						body.getUser().getLanguage().toUpperCase(), cmpVal, TYP_APY_RCP_PND);
			}
		} catch (NwtException e) {
			errors.add(getError(body, TYP_APY_RCP_PND, e));
		}
	}

	private void validateTypApyRcpPndPrd(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		try {
			if (body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL) != null
					&& !(body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL).equals(""))) {
				iDlLsfPpdDAO.getCmnTypS(TYP_APY_RCP_PND_PRD_TYP_VAL, body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL),
						body.getUser().getLanguage().toUpperCase(), cmpVal, TYP_APY_RCP_PND_PRD_TYP_VAL);
			}
		} catch (NwtException e) {
			errors.add(getError(body, TYP_APY_RCP_PND_PRD_TYP_VAL, e));
		}

	}
	
	private void validateTypApyRcpPndPrdTyp(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		String lngVal = body.getUser().getLanguage().toUpperCase();

		try {

			if (isValTypApyRcpPndPrdTyp(body)) {
				handleValTypApyRcpPndPrd(body, cmpVal, lngVal, errors);
			}

		} catch (NwtException e) {
			errors.add(getError(body, TYP_APY_RCP_PND_PRD_NAM, e));
		}
	}

	private boolean isValTypApyRcpPndPrdTyp(DataInExtended body) {
		return body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL) != null
				&& !(body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL).equals(""));
	}

	private void handleValTypApyRcpPndPrd(DataInExtended body, BigDecimal cmpVal, String lngVal,
			List<ValidationError> errors) {
		String typVal = body.getData().get(TYP_APY_RCP_PND_PRD_TYP_VAL);
		
		if (typVal.equals("1") || typVal.equals("2")) {

			if (body.getData().get(TYP_APY_RCP_PND_PRD_NAM) != null
					&& !(body.getData().get(TYP_APY_RCP_PND_PRD_NAM).equals(""))) {

				errors.add(addError(body, new BigDecimal(20005), "PPD", TYP_APY_RCP_PND_PRD_NAM));

			}
		} else if (typVal.equals("3") || typVal.equals("4")) {

			if (body.getData().get(TYP_APY_RCP_PND_PRD_NAM) == null
					|| (body.getData().get(TYP_APY_RCP_PND_PRD_NAM).equals(""))) {
				errors.add(addError(body, new BigDecimal(20003), "PPD", TYP_APY_RCP_PND_PRD_NAM));
			} else {
				iDlLsfLobDAO.getNomPrg(body.getData().get(TYP_APY_RCP_PND_PRD_NAM), lngVal, cmpVal,
						TYP_APY_RCP_PND_PRD_NAM);
			}
		}
	}
	
	private void validateDsbRow(DataInExtended body, BigDecimal cmpVal, List<ValidationError> errors) {
		ValidationError error;

		if (body.getData().get(DSB_ROW) == null
				|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			BigDecimal codError = new BigDecimal(20010);
			OTrnErrS lvError = lvDlTrnError.getError(codError, body.getUser().getLanguage().toUpperCase(), "PPD",
					DSB_ROW, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
					.value(body.getData().get(DSB_ROW)).build();
			errors.add(error);
		}

	}
	
	private ValidationError addError(DataInExtended body, BigDecimal codError, String temVal, String prpIdn) {
		ValidationError error;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
		String lngVal = body.getUser().getLanguage().toUpperCase();
		
		OTrnErrS lvError = lvDlTrnError.getError(codError, lngVal, temVal, prpIdn, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
				.value(body.getData().get(prpIdn)).build();
		
		return error;
	}

} 