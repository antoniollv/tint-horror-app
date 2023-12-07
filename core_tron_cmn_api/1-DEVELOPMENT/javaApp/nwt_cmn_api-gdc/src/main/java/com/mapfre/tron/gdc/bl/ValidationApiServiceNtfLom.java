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
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;
import com.mapfre.nwt.trn.tpd.icf.bo.OTpdIcfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.tpd.icf.dl.IDlTpdIcfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("NtfLom")
public class ValidationApiServiceNtfLom implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlTpdIcfDAO iDlTpdIcfDAO;
    
    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;
    
    @Autowired
    IDlTrnNtfDAO iDlTrnNtfDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;
		OTpdIcfS oTpdIcfS;
		OThpCmcS oThpCmcS;
		OTrnNtfS lvOTrnNtfS;
		
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañía
		    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "CMP_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el ID de operación
		    if(!body.getDataOld().get("OPR_IDN_VAL").equals(body.getData().get("OPR_IDN_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "OPR_IDN_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("OPR_IDN_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar Primer nivel
		    if(!body.getDataOld().get("FRS_LVL_VAL").equals(body.getData().get("FRS_LVL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "FRS_LVL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("FRS_LVL_VAL")).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar Segundo nivel
		    if(!body.getDataOld().get("SCN_LVL_VAL").equals(body.getData().get("SCN_LVL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "SCN_LVL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SCN_LVL_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar Tercer nivel
		    if(!body.getDataOld().get("THR_LVL_VAL").equals(body.getData().get("THR_LVL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "THR_LVL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THR_LVL_VAL")).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar Tercer canal de distribución
		    if(!body.getDataOld().get("THR_DST_HNL_VAL").equals(body.getData().get("THR_DST_HNL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "THR_DST_HNL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THR_DST_HNL_VAL")).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar poliza grupo
		    if(!body.getDataOld().get("GPP_VAL").equals(body.getData().get("GPP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "GPP_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("GPP_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar acuerdo
		    if(!body.getDataOld().get("DEL_VAL").equals(body.getData().get("DEL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DEL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("DEL_VAL")).build();
				errors.add(error);
		    }

		    // No se puede modificar Subacuerdo
		    if(!body.getDataOld().get("SBL_VAL").equals(body.getData().get("SBL_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "SBL_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SBL_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar Ramo
		    if(!body.getDataOld().get("LOB_VAL").equals(body.getData().get("LOB_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "LOB_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LOB_VAL")).build();
				errors.add(error);
		    }

		}
		
		// Validamos si el ID de operación está informado
		if (body.getData().get("OPR_IDN_VAL") == null || body.getData().get("OPR_IDN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "OPR_IDN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("OPR_IDN_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si el Primer nivel está informado
		if (body.getData().get("FRS_LVL_VAL") == null || body.getData().get("FRS_LVL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "FRS_LVL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("FRS_LVL_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si Segundo nivel está informado
		if (body.getData().get("SCN_LVL_VAL") == null || body.getData().get("SCN_LVL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "SCN_LVL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SCN_LVL_VAL")).build();
			errors.add(error);
		}
				
		// Validacion si Tercer nivel está informado
		if (body.getData().get("THR_LVL_VAL") == null || body.getData().get("THR_LVL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "THR_LVL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("THR_LVL_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si tercer canal distribución está informado
		if (body.getData().get("THR_DST_HNL_VAL") == null || body.getData().get("THR_DST_HNL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "THR_DST_HNL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("THR_DST_HNL_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si póliza grupo está informado
		if (body.getData().get("GPP_VAL") == null || body.getData().get("GPP_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "GPP_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("GPP_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si acuerdo está informado
		if (body.getData().get("DEL_VAL") == null || body.getData().get("DEL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DEL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DEL_VAL")).build();
			errors.add(error);
		}
				
		// Validacion si Subacuerdo está informado
		if (body.getData().get("SBL_VAL") == null || body.getData().get("SBL_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "SBL_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SBL_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si ramo está informado
		if (body.getData().get("LOB_VAL") == null || body.getData().get("LOB_VAL").equals("")) {
		    	codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "LOB_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("LOB_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si plan de tramitación está informado
		if (body.getData().get("HPN_VAL") == null || body.getData().get("HPN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "HPN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("HPN_VAL")).build();
			errors.add(error);
		}
				
		// Validacion si tipo de expediente está informado
		if (body.getData().get("FIL_TYP_VAL") == null || body.getData().get("FIL_TYP_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "FIL_TYP_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("FIL_TYP_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si trámite está informado
		if (body.getData().get("PCS_VAL") == null || body.getData().get("PCS_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "PCS_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PCS_VAL")).build();
			errors.add(error);
		}
		
		// Validacion si código de documento está informado
		if (body.getData().get("DCN_VAL") == null || body.getData().get("DCN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DCN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DCN_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si fecha de validez está informado
		if (body.getData().get("VLD_DAT") == null || body.getData().get("VLD_DAT").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "VLD_DAT", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VLD_DAT")).build();
			errors.add(error);
		}
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || body.getData().get("DSB_ROW").equals("") || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DSB_ROW",cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW")).build();
		    errors.add(error);
		}
		
		//Validar si exite ID operacion
		try {
		    if((body.getData().get("OPR_IDN_VAL") != null  && !(body.getData().get("OPR_IDN_VAL").equals("")))) {
			String oprIdnVal = new String(body.getData().get("OPR_IDN_VAL"));
			
			lvOTrnNtfS = iDlTrnNtfDAO.get_Nod_002(cmpVal, oprIdnVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("OPR_IDN_VAL").toString()).build();
			errors.add(error);
	    }

		// Validar que existe nivel1
		try {
		    if(body.getData().get("FRS_LVL_VAL") != null  && !(body.getData().get("FRS_LVL_VAL").equals(""))){
			oThpCmcS = iDlThpCmcDAO.getCodNivel1(cmpVal,body.getData().get("FRS_LVL_VAL"), body.getUser().getLanguage().toUpperCase());
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("FRS_LVL_VAL").toString()).build();
		    errors.add(error);
		}
		
		// Validar que existe nivel2
		try {
		    if(body.getData().get("SCN_LVL_VAL") != null  && !(body.getData().get("SCN_LVL_VAL").equals(""))){
			oThpCmcS = iDlThpCmcDAO.getCodNivel2(cmpVal,body.getData().get("SCN_LVL_VAL"), body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SCN_LVL_VAL").toString()).build();
		    errors.add(error);
		}
		
		//validar si existe nivel 3
		try {
		    	if(body.getData().get("THR_LVL_VAL") != null  && !(body.getData().get("THR_LVL_VAL").equals("")) && body.getData().get("FRS_LVL_VAL") != null  && !(body.getData().get("FRS_LVL_VAL").equals(""))
		     	&& body.getData().get("SCN_LVL_VAL") != null  && !(body.getData().get("SCN_LVL_VAL").equals(""))) {
		     
		     	   BigDecimal frsLvlVal = new BigDecimal(body.getData().get("FRS_LVL_VAL"));
		     	   BigDecimal scnLvlVal = new BigDecimal(body.getData().get("SCN_LVL_VAL"));
		     
			   lvOTrnNtfS = iDlTrnNtfDAO.get_thrLvl(cmpVal, frsLvlVal, scnLvlVal, new BigDecimal (body.getData().get("THR_LVL_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THR_LVL_VAL").toString()).build();
		    errors.add(error);
		}
		
		
		//Validar que existe el contrato/acuerdo
		
		try {
		    	if((body.getData().get("DEL_VAL") != null  && !(body.getData().get("DEL_VAL").equals(""))) && body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals(""))) {
		    	   	    
		    	    BigDecimal delVal = new BigDecimal (body.getData().get("DEL_VAL"));
		    	    BigDecimal lobVal = new BigDecimal (body.getData().get("LOB_VAL"));
		    		
		    	    if(!body.getData().get("DEL_VAL").equals("99999")) {
		    
		    		lvOTrnNtfS = iDlTrnNtfDAO.get_delVal(cmpVal, delVal, lobVal, body.getUser().getLanguage().toUpperCase());
		    	 }
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DEL_VAL").toString()).build();
		    errors.add(error);
		}
		
		//Validar Subacuerdo

		 try {
			if((body.getData().get("SBL_VAL") != null  && !(body.getData().get("SBL_VAL").equals(""))) && body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals("")) 
			&& body.getData().get("DEL_VAL") != null  && !(body.getData().get("DEL_VAL").equals(""))) {
			    
			       BigDecimal delVal = new BigDecimal (body.getData().get("DEL_VAL"));
			       BigDecimal lobVal = new BigDecimal (body.getData().get("LOB_VAL"));
			       BigDecimal sblVal = new BigDecimal (body.getData().get("SBL_VAL"));
			       
			   if(!body.getData().get("SBL_VAL").contentEquals("99999")) {

				lvOTrnNtfS = iDlTrnNtfDAO.get_sblVal(cmpVal, lobVal, delVal, sblVal, body.getUser().getLanguage().toUpperCase());
			    }
			}
		}
		catch (NwtException e) {
			   error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DEL_VAL").toString()).build();
			   errors.add(error);
		}
		 
		//Validamos si existe el ramo
		try {
		    if(body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals(""))) {
			oTpdIcfS = iDlTpdIcfDAO.get(cmpVal, new BigDecimal (body.getData().get("LOB_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LOB_VAL").toString()).build();
			errors.add(error);
		}
		
		
		//Validar Plan tramitacion
		 try {
		    	if(body.getData().get("HPN_VAL") != null  && !(body.getData().get("HPN_VAL").equals("")) && body.getData().get("FIL_TYP_VAL") != null  && !(body.getData().get("FIL_TYP_VAL").equals(""))
		     	&& body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals(""))) {
		     
		      	  String hpnVal = new String (body.getData().get("HPN_VAL"));
		      	  BigDecimal lobVal = new BigDecimal (body.getData().get("LOB_VAL"));
		      	  String filTypVal = new String (body.getData().get("FIL_TYP_VAL"));
		       
			  lvOTrnNtfS = iDlTrnNtfDAO.get_hpnVal(cmpVal, hpnVal, lobVal, filTypVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("HPN_VAL").toString()).build();
		    errors.add(error);
		}
		
		//Validamos si existe tipo expediente
		
		try {
		    if(body.getData().get("FIL_TYP_VAL") != null  && !(body.getData().get("FIL_TYP_VAL").equals(""))) {
			lvOTrnNtfS = iDlTrnNtfDAO.get_filTypVal(cmpVal, new String (body.getData().get("FIL_TYP_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("FIL_TYP_VAL").toString()).build();
		    errors.add(error);
		}
			
			
		// Validamos si existe el trámite
		
		 try {
		    if(body.getData().get("PCS_VAL") != null  && !(body.getData().get("PCS_VAL").equals(""))) {
			lvOTrnNtfS = iDlTrnNtfDAO.get_pcsVal(cmpVal, new String (body.getData().get("PCS_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("PCS_VAL").toString()).build();
		    errors.add(error);
		}
		// Validamos si existe poliza de grupo
			
		 try {
		     if((body.getData().get("GPP_VAL") != null  && !(body.getData().get("GPP_VAL").equals("")))  && (body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals("")))) {
			 
			 String gppVal = new String(body.getData().get("GPP_VAL"));
			 BigDecimal lobVal = new BigDecimal(body.getData().get("LOB_VAL"));
			 
			lvOTrnNtfS = iDlTrnNtfDAO.get_gppVal_002(cmpVal, gppVal, lobVal, body.getUser().getLanguage().toUpperCase());
		     }
		}
		catch (NwtException e) {
			 error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("PCS_VAL").toString()).build();
			 errors.add(error);
		}
			
		 // Comprobamos que el código de documento está vigente	
		try {
		    if((body.getData().get("DCN_VAL") != null  && !(body.getData().get("DCN_VAL").equals(""))) && (body.getData().get("VLD_DAT") != null  && !(body.getData().get("VLD_DAT").equals("")))) {
			    
			String dcnVal = new String(body.getData().get("DCN_VAL"));
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get("VLD_DAT")).atZone(ZoneId.systemDefault()).toInstant());
				
			lvOTrnNtfS = iDlTrnNtfDAO.get_Ntf_003(cmpVal, dcnVal,vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DCN_VAL").toString()).build();
			errors.add(error);
		  }
		
		
		// Comprobamos que el tercer canal de distribución está vigente	
		try {
		    if((body.getData().get("THR_DST_HNL_VAL") != null  && !(body.getData().get("THR_DST_HNL_VAL").equals("")))   ) {
									
			String thrDstHnlVal = new String(body.getData().get("THR_DST_HNL_VAL"));
												
												
			lvOTrnNtfS = iDlTrnNtfDAO.get_thrDstHnlNam(cmpVal, thrDstHnlVal, body.getUser().getLanguage().toUpperCase());
			}
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THR_DST_HNL_VAL").toString()).build();
			errors.add(error);
		}
		
		return errors;
    }

}
