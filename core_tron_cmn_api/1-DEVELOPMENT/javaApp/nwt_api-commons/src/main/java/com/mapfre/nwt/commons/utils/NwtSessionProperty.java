package com.mapfre.nwt.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.tron.api.cmn.ResetPacakge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class NwtSessionProperty {

    /** The resetPackage property.*/
    @Autowired
    private ResetPacakge resetPackage;

    /** The newtron utils.*/
    private NwtUtils nwtUtils;
    
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
    
}
