package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.grz.dl.IDlCmnGrzDAO;
import com.mapfre.tron.api.grs.znf.dl.IDlGrsZnfDAO;
import com.mapfre.tron.api.grs.zno.dl.IDlGrsZnoDAO;
import com.mapfre.tron.api.grs.znt.dl.IDlGrsZntDAO;
import com.mapfre.tron.api.grs.zof.dl.IDlGrsZofDAO;
import com.mapfre.tron.api.grs.zot.dl.IDlGrsZotDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Agg")
public class ValidationApiServiceAgg implements ValidationApiService {

	protected static final String CNY_VAL = "CNY_VAL";

	protected static final String DSB_ROW = "DSB_ROW";

	protected static final String GRZ_VAL = "GRZ_VAL";

	protected static final String PSL_COD_VAL = "PSL_COD_VAL";

	protected static final String PVC_VAL = "PVC_VAL";

	protected static final String STT_VAL = "STT_VAL";

	protected static final String TWN_VAL = "TWN_VAL";

	protected static final String VLD_DAT = "VLD_DAT";

	@Autowired
	IDlCmnGrzDAO iDlCmnGrzDAO;

	@Autowired
	IDlGrsZnoDAO iDlGrsZnoDAO;
	
	@Autowired
	IDlGrsZntDAO iDlGrsZntDAO;
	
	@Autowired
	IDlGrsZotDAO iDlGrsZotDAO;

	@Autowired
	IDlGrsZnfDAO iDlGrsZnfDAO;
	
	@Autowired
	IDlGrsZofDAO iDlGrsZofDAO;
	
	OCmnGrzS oCmnGrzS;
	OGrsZnoS oGrsZnoS;
	OGrsZntS oGrsZntS;
	OGrsZotS oGrsZotS;
	OGrsZnfS oGrsZnfS;
	OGrsZofS oGrsZofS;

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<>();
		ValidationError error;
		String accion;
		OTrnErrS lvError;
		BigDecimal codError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));

		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		}
		else {
			accion = CTrn.ACN_TYP_MDF;
		}

		if (accion.equals(CTrn.ACN_TYP_MDF)) {

			if(!body.getDataOld().get(GRZ_VAL).equals(body.getData().get(GRZ_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", GRZ_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(GRZ_VAL).toString()).build();
				errors.add(error);
			}
	
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", VLD_DAT, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT).toString()).build();
				errors.add(error);
		    }
			
			if(!body.getDataOld().get(CNY_VAL).equals(body.getData().get(CNY_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", CNY_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CNY_VAL).toString()).build();
				errors.add(error);
			}  

			if(!body.getDataOld().get(STT_VAL).equals(body.getData().get(STT_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", STT_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(STT_VAL).toString()).build();
				errors.add(error);
			}
			
			if(!body.getDataOld().get(PVC_VAL).equals(body.getData().get(PVC_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PVC_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(PVC_VAL).toString()).build();
				errors.add(error);
			}
			
			if(!body.getDataOld().get(TWN_VAL).equals(body.getData().get(TWN_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", TWN_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(TWN_VAL).toString()).build();
				errors.add(error);
			} 
			
			if(!body.getDataOld().get(PSL_COD_VAL).equals(body.getData().get(PSL_COD_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PSL_COD_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(PSL_COD_VAL).toString()).build();
				errors.add(error);
			} 
		}

		/*
		 * Validacion de la zona
		 */
		try {
			
			String grzVal = body.getData().get(GRZ_VAL);
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oCmnGrzS = iDlCmnGrzDAO.get_grzVal(cmpVal, grzVal, usrLngVal);
			
			if (oCmnGrzS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", GRZ_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(GRZ_VAL).toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get(DSB_ROW).equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", GRZ_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(GRZ_VAL).toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(GRZ_VAL).toString()).build();
			errors.add(error);
		}

		/*
		 * Validacion del pais
		 */
		try {
			String cnyVal = body.getData().get(CNY_VAL);
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oGrsZnoS = iDlGrsZnoDAO.get(cmpVal, cnyVal, usrLngVal);
			
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CNY_VAL).toString()).build();
			errors.add(error);
		}
		
		/*
		 * Validacion del estado
		 */
		try {
			String cnyVal = body.getData().get(CNY_VAL);
			BigDecimal sttVal = new BigDecimal(body.getData().get(STT_VAL));
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oGrsZntS = iDlGrsZntDAO.get(cmpVal, cnyVal, sttVal, usrLngVal);
			
			if (oGrsZntS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", STT_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(STT_VAL).toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get(DSB_ROW).equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", STT_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(STT_VAL).toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(STT_VAL).toString()).build();
			errors.add(error);
		}
		
		/*
		 * Validacion de la provincia
		 */
		try {
			String cnyVal = body.getData().get(CNY_VAL);
			BigDecimal sttVal = new BigDecimal(body.getData().get(STT_VAL));
			BigDecimal pvcVal = new BigDecimal(body.getData().get(PVC_VAL));
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oGrsZotS = iDlGrsZotDAO.get(cmpVal, cnyVal, sttVal, pvcVal, usrLngVal);
			
			if (oGrsZotS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PVC_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PVC_VAL).toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get(DSB_ROW).equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PVC_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PVC_VAL).toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PVC_VAL).toString()).build();
			errors.add(error);
		}
		
		/*
		 * Validacion de la localidad
		 */
		try {
			String cnyVal = body.getData().get(CNY_VAL);
			BigDecimal pvcVal = new BigDecimal(body.getData().get(PVC_VAL));
			BigDecimal twnVal = new BigDecimal(body.getData().get(TWN_VAL));
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oGrsZnfS = iDlGrsZnfDAO.get(cmpVal, cnyVal, pvcVal, twnVal, usrLngVal);
			
			if (oGrsZnfS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", TWN_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(TWN_VAL).toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get(DSB_ROW).equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", TWN_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(TWN_VAL).toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(TWN_VAL).toString()).build();
			errors.add(error);
		}
		
		/*
		 * Validacion del codigo postal
		 */
		try {
			String cnyVal = body.getData().get(CNY_VAL);
			BigDecimal sttVal = new BigDecimal(body.getData().get(STT_VAL));
			BigDecimal pvcVal = new BigDecimal(body.getData().get(PVC_VAL));
			BigDecimal twnVal = new BigDecimal(body.getData().get(TWN_VAL));
			String pslCodVal = body.getData().get(PSL_COD_VAL);
			String usrLngVal =  body.getUser().getLanguage().toUpperCase();
			
			oGrsZofS = iDlGrsZofDAO.get(cmpVal, cnyVal, sttVal, pvcVal, twnVal, pslCodVal, usrLngVal);
			
			if (oGrsZnfS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PSL_COD_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PSL_COD_VAL).toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get(DSB_ROW).equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", PSL_COD_VAL, cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PSL_COD_VAL).toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PSL_COD_VAL).toString()).build();
			errors.add(error);
		}
		
		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AGG", DSB_ROW, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW).toString()).build();
			errors.add(error);
		}

		return errors;
	}

}
