package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.acd.acc.bo.OAcdAccS;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.acd.acc.dl.IDlAcdAccDAO;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.tpd.sps.dl.IDlTpdSpsDAO;
import com.mapfre.tron.api.tpd.ssd.dl.IDlTpdSsdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("SsdTca")
public class ValidationApiServiceSsdTca implements ValidationApiService {

	@Autowired
	IDlTpdAvdDAO iDlTpdAvdDAO;

	@Autowired
	IDlTpdSpsDAO iDlTpdSpsDAO;

	@Autowired
	IDlTpdSsdDAO iDlTpdSsdDAO;

	@Autowired
	IDlAcdAccDAO iDlAcdAccDAO;

	OTpdSpsS lvOTpdSpsS;

	OTpdAvdS lvOTpdAvdS;

	OTpdSsdS oTpdSsdS;

	OAcdAccS oAcdAccS;

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
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

			if(!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "THP_ACV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
				errors.add(error);
			}
	
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "VLD_DAT", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
				errors.add(error);
		    }
			
			if(!body.getDataOld().get("TYL_VAL").equals(body.getData().get("TYL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "TYL_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("TYL_VAL").toString()).build();
				errors.add(error);
			}  

			if(!body.getDataOld().get("SPL_CTG_VAL").equals(body.getData().get("SPL_CTG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SPL_CTG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SPL_CTG_VAL").toString()).build();
				errors.add(error);
			}
			if(!body.getDataOld().get("SSV_VAL").equals(body.getData().get("SSV_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SSV_VAL").toString()).build();
				errors.add(error);
			} 
		}

		try {
			BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
			lvOTpdAvdS = iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL")).build();
			errors.add(error);
		}

		/*
		 * Validando Tipologia, Categoria
		 */
		try {
			BigDecimal thpAcvVal = new BigDecimal(body.getData().get("THP_ACV_VAL"));
			String tylVal = body.getData().get("TYL_VAL");
			String splCtgVal = body.getData().get("SPL_CTG_VAL");
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get("VLD_DAT")).atZone(ZoneId.systemDefault()).toInstant());
			lvOTpdSpsS = iDlTpdSpsDAO.get_spsTca(cmpVal, thpAcvVal, tylVal, splCtgVal, vldDat, body.getUser().getLanguage().toUpperCase());
			if (lvOTpdSpsS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SPL_CTG_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
					errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SPL_CTG_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
					errors.add(error);
				} 
			}
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SPL_CTG_VAL").toString()).build();
			errors.add(error);
		} 

		/*
		 * Validacion del servicio
		 */
		try {
			String ssvVal = body.getData().get("SSV_VAL");
			String lngVal =  body.getUser().getLanguage().toUpperCase();
			oTpdSsdS = iDlTpdSsdDAO.get(cmpVal, ssvVal, lngVal, lngVal); 
			if (oTpdSsdS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SSV_VAL").toString()).build();
					errors.add(error);
				} 
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
					codError = new BigDecimal(20020);
					lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "SSV_VAL", cmpVal);
					error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SSV_VAL").toString()).build();
					errors.add(error);
				}
			}
		} 
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SSV_VAL").toString()).build();
			errors.add(error);
		}

		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SSD", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}
		
		if (body.getData().get("CLO_PYM_CNC_VAL") != null) {
			try {
				oAcdAccS = iDlAcdAccDAO.get(cmpVal, "PS", body.getData().get("CLO_PYM_CNC_VAL"), body.getUser().getLanguage().toUpperCase()); 
			}
			catch (NwtException e) {
				error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CLO_PYM_CNC_VAL").toString()).build();
				errors.add(error);
			}
		}

		return errors;
	}

}
