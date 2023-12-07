package com.mapfre.tron.api.crn.exr.bl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;
import com.mapfre.nwt.trn.crn.exr.sr.ISrCrnExrQry;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.ResetPacakge;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NewTronAccess
public class NwtBlCrnExrQryImpl implements IBlCrnExrQry{
    
    protected ISrCrnExrQry iSrCrnExrQry;

    /** The resetPackage property. */
    protected ResetPacakge resetPackage;

    /** The newtron utils. */
    protected NwtUtils nwtUtils;
    
    @Autowired
    protected NwtBlCrnExrQryImpl(ISrCrnExrQry iSrCrnExrQry, ResetPacakge resetPackage) {
        super();
        this.iSrCrnExrQry = iSrCrnExrQry;
        this.resetPackage = resetPackage;
    }
    

    /**
     * Get the utils property.
     * 
     * @return the utils property
     */
    protected NwtUtils getNwtUtils() {
        if (nwtUtils == null) {
            nwtUtils = new NwtUtils();
        }
        return nwtUtils;
    }

    /** Reset the session. */
    protected void resetSession() {
        log.debug("Reseting session...");
        resetPackage.executeRP();
        log.debug("The session has been reset.");
    }

    
    
    @Override
    public OCrnExrP chnDat(String lngVal, String usrVal, Integer crnVal, Long exrDat, BigDecimal cmpVal) {
        
        log.info("Newtron business logic implementation FLD have been called...");
        
        // reseting session
        resetSession();

        OCrnExrP oCrnExrP = iSrCrnExrQry.chnDat(new BigDecimal(crnVal), new Date(exrDat), lngVal, cmpVal);
        getNwtUtils().isTrmOk(oCrnExrP, "iSrCrnExrQry.chnDat");
        
        return oCrnExrP;
    }
    


    
    
    
    
}
