package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.rcd.exd.bo.ORcdExdS;
import com.mapfre.nwt.trn.thp.acv.bo.OThpAcvS;
import com.mapfre.nwt.trn.thp.bno.bo.OThpBnoS;
import com.mapfre.nwt.trn.tpd.icf.bo.OTpdIcfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.crn.crn.dl.IDlCrnCrnDAO;
import com.mapfre.tron.api.rcd.exd.dl.IDlRcdExdDAO;
import com.mapfre.tron.api.thp.acv.dl.IDlThpAcvDAO2;
import com.mapfre.tron.api.tpd.icf.dl.IDlTpdIcfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;


@Service("RcdExdDel")
public class ValidationApiServiceRcdExdDel implements ValidationApiService {

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;	

    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;	
    
    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlTpdIcfDAO iDlTpdIcfDAO;

    @Autowired
    IDlRcdExdDAO iDlRcdExdDAO;

    @Autowired
    IDlTrnNtfDAO iDlTrnNtfDAO;

    @Autowired
    IDlCrnCrnDAO iDlCrnCrnDAO;
    
    @Autowired
    IDlThpAcvDAO2 iDlThpAcvDAO2;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError  error;
		String           accion;
		OCmnLngS         lvOCmnLngS;
		OTrnPrcS         oTrnPrcS;
		List<OCmnTypS>   oCmnTypPT;
		OTrnErrS         lvError;
		BigDecimal       codError;
		OTpdIcfS         oTpdIcfS;
		OTrnNtfS         lvOTrnNtfS;
		OThpAcvS         lvOThpAcvS;
		OThpBnoS         lvOThpBnoS;
		OCmuThlS         lvOCmuThlS;
		ORcdExdS         oRcdExdS;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RCD", "CMP_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL").toString()).build();
				errors.add(error);
		    }
    
		    // No se puede modificar Fecha validez
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RCD", "VLD_DAT", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
				errors.add(error);
		    }
		}
		
		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RCD", "DSB_ROW", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
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

		// Existe moneda
		try {
		    iDlCrnCrnDAO.get(cmpVal, body.getData().get("CRN_VAL"), body.getUser().getLanguage().toUpperCase());
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CRN_VAL").toString()).build();
		    errors.add(error);
		}
	
		
		//Validar que existe el contrato/acuerdo
		try {
		    if((body.getData().get("DEL_VAL") != null  && !(body.getData().get("DEL_VAL").equals(""))) && body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals(""))) {
				     
			BigDecimal delVal = new BigDecimal (body.getData().get("DEL_VAL"));
			BigDecimal lobVal = new BigDecimal (body.getData().get("LOB_VAL"));
			
			if(!body.getData().get("DEL_VAL").equals("99999"))	
			
			    lvOTrnNtfS = iDlTrnNtfDAO.get_delVal(cmpVal, delVal, lobVal, body.getUser().getLanguage().toUpperCase());
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
			
			if(!body.getData().get("SBL_VAL").equals("99999"))		    
			    lvOTrnNtfS = iDlTrnNtfDAO.get_sblVal(cmpVal, lobVal, delVal, sblVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DEL_VAL").toString()).build();
			errors.add(error);
		}
		
		//Validamos si existe el Agente
		try {
		    if(body.getData().get("AGN_VAL") != null  && !(body.getData().get("AGN_VAL").equals(""))) {
			oRcdExdS = iDlRcdExdDAO.get(cmpVal, new BigDecimal (body.getData().get("AGN_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("AGN_VAL").toString()).build();
		    errors.add(error);
		}
		
		//Validamos si existe el Sector
		try {
		    if(body.getData().get("SEC_VAL") != null  && !(body.getData().get("SEC_VAL").equals(""))) {
			oRcdExdS = iDlRcdExdDAO.getSector(cmpVal, new BigDecimal (body.getData().get("SEC_VAL")), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("SEC_VAL").toString()).build();
		    errors.add(error);
		}
		
		// Comprobamos que la poliza de grupo está vigente	
		try {
		    if((body.getData().get("GPP_VAL") != null  && !(body.getData().get("GPP_VAL").equals(""))) && (body.getData().get("LOB_VAL") != null  && !(body.getData().get("LOB_VAL").equals(""))) ) {
										
			String gppVal = new String(body.getData().get("GPP_VAL"));
			BigDecimal lobVal = new BigDecimal (body.getData().get("LOB_VAL"));										
													
			lvOTrnNtfS = iDlTrnNtfDAO.get_gppVal_002(cmpVal, gppVal, lobVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("GPP_VAL").toString()).build();
			errors.add(error);
		}

		// Comprobamos que el tipo de gestor está vigente	
		try {
		    if(body.getData().get("MNR_TYP_VAL") != null  && !(body.getData().get("MNR_TYP_VAL").equals(""))) {
			String mnrTypVal = new String(body.getData().get("MNR_TYP_VAL"));
			
			lvOTrnNtfS = iDlTrnNtfDAO.get_MnrTypVal(cmpVal, mnrTypVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("MNR_TYP_VAL").toString()).build();
			errors.add(error);
		}
		
		// Validamos el gestor (MNR_VAL) dependiendo del tipo de gestor (MNR_TYP_VAL)
		try {
		    if(body.getData().get("MNR_TYP_VAL") != null  && !(body.getData().get("MNR_TYP_VAL").equals(""))) {
			
			String mnrVal = new String(body.getData().get("MNR_VAL"));
			
			if(body.getData().get("MNR_TYP_VAL").equals("AG")) {
			    
			    lvOThpAcvS = iDlThpAcvDAO2.get_AG(cmpVal, mnrVal, body.getUser().getLanguage().toUpperCase());
			    
			}else if (body.getData().get("MNR_TYP_VAL").equals("CI") || body.getData().get("MNR_TYP_VAL").equals("CO") || 
				body.getData().get("MNR_TYP_VAL").equals("CP")) {
			    
			    lvOThpAcvS = iDlThpAcvDAO2.get_CICOCP(cmpVal, mnrVal, body.getUser().getLanguage().toUpperCase());
			    
			}else if (body.getData().get("MNR_TYP_VAL").equals("GP")) {
			    
			    lvOThpAcvS = iDlThpAcvDAO2.get_GP(cmpVal, mnrVal, body.getUser().getLanguage().toUpperCase());
			    
			}else if (body.getData().get("MNR_TYP_VAL").equals("BA") || body.getData().get("MNR_TYP_VAL").equals("DB") || 
				body.getData().get("MNR_TYP_VAL").equals("TA")) {
			    
			    lvOThpBnoS = iDlThpAcvDAO2.get_BADBTA(cmpVal, mnrVal, body.getUser().getLanguage().toUpperCase());
			    
			}else if (body.getData().get("MNR_TYP_VAL").equals("GD") || body.getData().get("MNR_TYP_VAL").equals("OF")) {
			    
			    lvOCmuThlS = iDlThpAcvDAO2.get_GDOF(cmpVal, mnrVal, body.getUser().getLanguage().toUpperCase());
			    
			}
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("MNR_VAL").toString()).build();
			errors.add(error);
		}

		// Validar que el procedimiento exista en la base de datos
		try {
		    if(body.getData().get("PYM_EXP_DAT_PRD_NAM") != null  && !(body.getData().get("PYM_EXP_DAT_PRD_NAM").equals(""))){
			oTrnPrcS = iDlTrnPrcDAO.getNomPrg(body.getData().get("PYM_EXP_DAT_PRD_NAM"),body.getUser().getLanguage().toUpperCase(), cmpVal); 
		    }
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("PYM_EXP_DAT_PRD_NAM").toString()).build();
			errors.add(error);
		}		
		
		return errors;
    }
}
