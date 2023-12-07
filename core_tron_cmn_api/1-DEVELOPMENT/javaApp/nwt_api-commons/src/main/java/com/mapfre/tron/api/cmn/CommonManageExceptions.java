package com.mapfre.tron.api.cmn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.bo.Error;
import com.mapfre.tron.api.bo.ErrorComponent;
import com.mapfre.tron.api.bo.ErrorInfo;

/**
 * Gestion de excepciones
 * 
 * @author fjlorente
 */
public final class CommonManageExceptions {
    private String errCode = "4003";
    private String errMessage = "Error interno en el servicio";
    private String errType = "Error funcional";
    private String errException = "";
    private String errApplication = "API NEWTron";
    private String errComCode = "4003";
    private String errComMessage = "Error interno en el servicio";
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    private List<ErrorInfo> getErrorInfoList(OTrnErrS pmErrS){
        List<ErrorInfo> errInfoList = new ArrayList<>();
        ErrorInfo errInfo = new ErrorInfo();
        
        errInfo.setKey("prpNam");
        errInfo.setValue(pmErrS.getPrpNam());
        errInfoList.add(errInfo);
        
        errInfo = new ErrorInfo();
        errInfo.setKey("errVal");
        errInfo.setValue(pmErrS.getErrVal().toString());
        errInfoList.add(errInfo);
        
        return errInfoList;
    }
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    private ErrorComponent getErrorComp(OTrnErrS pmErrS, String pmSrvNam){
        ErrorComponent errCom = new ErrorComponent();
        errCom.setCode(errComCode);
        errCom.setMessage(errComMessage);
        errCom.setComponent(pmSrvNam);
        errCom.setRootcase(pmErrS.getErrNam());
        errCom.setInfo(getErrorInfoList(pmErrS));
        
        return errCom;
    }
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    private List<ErrorComponent> getErrorCompList(NwtException pmNwte){
        List<ErrorComponent> errCompList = new ArrayList<>();
        if (pmNwte.getErrors() != null && !pmNwte.getErrors().isEmpty()) {
            for (OTrnErrS lvErrS : pmNwte.getErrors()) {
                errCompList.add(getErrorComp(lvErrS, pmNwte.getSrvNam()));
            }
        }
        return errCompList;
    }
    //------------------------------------------------------------------------------
    // recuperar objeto de retorno Error partiendo de una excepcion Newtron
    //------------------------------------------------------------------------------
    public Error getErrorFromNwtEx(NwtException pmNwte, String pmComponent, String pmContext){
        Error err = new Error();
        err.setCode(errCode);
        err.setMessage(errMessage);
        err.setType(errType);
        err.setContext(pmContext);
        setExpFromNwtEx(pmNwte, err);
        err.setComponent(pmComponent);
        err.setApplication(errApplication);
        err.setTimestamp(new Date());
        err.setErrors(getErrorCompList(pmNwte));
        
        return err;
    }
    
    private void setExpFromNwtEx(NwtException pmNwte, Error err) {
        if (pmNwte.getMessage() != null && pmNwte.getMessage().trim().length() > 0) {
            err.setException(pmNwte.getMessage());
        } else {
            err.setException(errException);
        }
    }
}
