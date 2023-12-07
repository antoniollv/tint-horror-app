package com.mapfre.tron.api.cmu.thl.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;
import com.mapfre.nwt.trn.cmu.thl.sr.ISrCmuThlQry;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.ResetPacakge;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The Newtron BLCmuThlQry business logic service implementation.
 *
 * @author USOZALO
 * @since 1.0
 * @version 04 jun. 2020 13:16:03
 *
 */
@Data
@Slf4j
@NewTronAccess
public class NwtBlCmuThlQryImpl implements IBlCmuThlQry{
    
    @Autowired
    protected ISrCmuThlQry iSrCmuThlQry;

    /** The resetPackage property. */
    @Autowired
    protected ResetPacakge resetPackage;

    /** The newtron utils. */
    protected NwtUtils nwtUtils;
    
    
    @Autowired
    protected NwtBlCmuThlQryImpl(ISrCmuThlQry iSrCmuThlQry, ResetPacakge resetPackage) {
        super();
        this.iSrCmuThlQry = iSrCmuThlQry;
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
    public OCmuThlP get(Integer cmpVal, String lngVal, String usrVal, Integer thrLvlVal) {
       
        log.info("Newtron business logic implementation FLD have been called...");
        
        // reseting session
        resetSession();
        
        BigDecimal pmCmpVal= (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        BigDecimal pmThrLvlVal= (thrLvlVal != null) ? new BigDecimal(thrLvlVal) : null;

        OCmuThlP oCmuThlP = iSrCmuThlQry.get(pmCmpVal, pmThrLvlVal, CTrn.GET_NAM_ALL, lngVal);
        
        getNwtUtils().isTrmOk(oCmuThlP, " iSrCmuThlQry.get");
        return oCmuThlP;
        
    }

}
