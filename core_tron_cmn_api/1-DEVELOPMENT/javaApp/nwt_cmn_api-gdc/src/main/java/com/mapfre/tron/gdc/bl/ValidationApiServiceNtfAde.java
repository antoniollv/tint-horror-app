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
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.tpd.sps.dl.IDlTpdSpsDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("NtfAde")
public class ValidationApiServiceNtfAde implements ValidationApiService {

    protected static final String TYL_VAL = "TYL_VAL";

    protected static final String THP_ACV_VAL = "THP_ACV_VAL";

    protected static final String OPR_IDN_VAL = "OPR_IDN_VAL";

    protected static final String MNR_TYP_VAL = "MNR_TYP_VAL";

    protected static final String LVL_VAL = "LVL_VAL";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String SQN_VAL = "SQN_VAL";

    protected static final String NTF_ADE_TYP_VAL = "NTF_ADE_TYP_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String DCN_VAL = "DCN_VAL";

    protected static final String CMP_VAL = "CMP_VAL";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;	
    
    @Autowired
    IDlTpdSpsDAO iDlTpdSpsDAO;
    
    @Autowired
    IDlTrnNtfDAO iDlTrnNtfDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañía
		    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", CMP_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar ID de operación
		    if(!body.getDataOld().get(OPR_IDN_VAL).equals(body.getData().get(OPR_IDN_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", OPR_IDN_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(OPR_IDN_VAL)).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar el código del documento
		    if(!body.getDataOld().get(DCN_VAL).equals(body.getData().get(DCN_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DCN_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(DCN_VAL)).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar fecha de validez
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", VLD_DAT,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
				errors.add(error);
		    }
		    
		// No se puede modificar tipo destinatario notificación
		   if(!body.getDataOld().get(NTF_ADE_TYP_VAL).equals(body.getData().get(NTF_ADE_TYP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", NTF_ADE_TYP_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(NTF_ADE_TYP_VAL)).build();
				errors.add(error);
		    }
		   
			
		// No se puede modificar la secuencia
		   if(!body.getDataOld().get(SQN_VAL).equals(body.getData().get(SQN_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", SQN_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(SQN_VAL)).build();
				errors.add(error);
		   }
		}
		
		// Validamos si el ID de operación está informado
		if (body.getData().get(OPR_IDN_VAL) == null || body.getData().get(OPR_IDN_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", OPR_IDN_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(OPR_IDN_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si el código del documento está informado
		if (body.getData().get(DCN_VAL) == null || body.getData().get(DCN_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DCN_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DCN_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si fecha de validez está informado
		if (body.getData().get(VLD_DAT) == null || body.getData().get(VLD_DAT).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", VLD_DAT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VLD_DAT)).build();
			errors.add(error);
		}
		
		// Validamos si el tipo destinatario notificación está informado
		if (body.getData().get(NTF_ADE_TYP_VAL) == null || body.getData().get(NTF_ADE_TYP_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", NTF_ADE_TYP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(NTF_ADE_TYP_VAL)).build();
			errors.add(error);
		}
			
		// Validamos si la secuencia está informado
		if (body.getData().get(SQN_VAL) == null || body.getData().get(SQN_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", SQN_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(SQN_VAL)).build();
			errors.add(error);
		}
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DSB_ROW,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
		    errors.add(error);
		}
		
		// Comprobamos que la actividad está vigente

		try {
		    if(body.getData().get(THP_ACV_VAL) != null  && !(body.getData().get(THP_ACV_VAL).equals(""))) {
			BigDecimal thpAcvVal = new BigDecimal(body.getData().get(THP_ACV_VAL));
			iDlTrnNtfDAO.get_thpAcvVal(cmpVal, thpAcvVal, body.getUser().getLanguage().toUpperCase());
			}
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(THP_ACV_VAL)).build();
			errors.add(error);
	    	}
		
		// Comprobamos que la tipología está vigente	
		try {
		    if((body.getData().get(THP_ACV_VAL) != null  && !(body.getData().get(THP_ACV_VAL).equals("")))) {
			BigDecimal thpAcvVal = new BigDecimal(body.getData().get(THP_ACV_VAL));
			iDlTpdSpsDAO.get_tylTya(cmpVal, thpAcvVal, body.getData().get(TYL_VAL), body.getUser().getLanguage().toUpperCase());
			}
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(TYL_VAL)).build();
			errors.add(error);
	    }
		
		 // Comprobamos que la categoría está vigente	
		try {
		    if((body.getData().get(THP_ACV_VAL) != null  && !(body.getData().get(THP_ACV_VAL).equals(""))) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))) {
			
			BigDecimal thpAcvVal = new BigDecimal(body.getData().get(THP_ACV_VAL));
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());
			iDlTpdSpsDAO.get_007(cmpVal, thpAcvVal, body.getData().get(TYL_VAL), body.getData().get("CTG_VAL"), vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CTG_VAL")).build();
			errors.add(error);
	    }
			
		
		 // Comprobamos que el ID de operación está vigente	
		try {
		    if(body.getData().get(OPR_IDN_VAL) != null  && !(body.getData().get(OPR_IDN_VAL).equals(""))) {
			String oprIdnVal = body.getData().get(OPR_IDN_VAL);
			
			iDlTrnNtfDAO.get_Nod_002(cmpVal, oprIdnVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPR_IDN_VAL)).build();
			errors.add(error);
	    }
	    
	     // Comprobamos que el tipo de gestor está vigente	
		try {
		    if(body.getData().get(MNR_TYP_VAL) != null  && !(body.getData().get(MNR_TYP_VAL).equals(""))) {
			String mnrTypVal = body.getData().get(MNR_TYP_VAL);
			
			iDlTrnNtfDAO.get_MnrTypVal(cmpVal, mnrTypVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(MNR_TYP_VAL)).build();
			errors.add(error);
	    }
	    
	     // Comprobamos que el nivel está vigente	
		try {
		    if(body.getData().get(LVL_VAL) != null  && !(body.getData().get(LVL_VAL).equals(""))) {
			String lvlVal = body.getData().get(LVL_VAL);
			
			iDlTrnNtfDAO.get_lvlVal(cmpVal, lvlVal, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LVL_VAL)).build();
			errors.add(error);
	    }
		
		 // Comprobamos que el código de documento está vigente	
		try {
		    if((body.getData().get(DCN_VAL) != null  && !(body.getData().get(DCN_VAL).equals(""))) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))) {
		    
			String dcnVal = body.getData().get(DCN_VAL);
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());
			
			iDlTrnNtfDAO.get_Ntf_003(cmpVal, dcnVal,vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(DCN_VAL)).build();
			errors.add(error);
	    }
		    	
		return errors;
    }

}
