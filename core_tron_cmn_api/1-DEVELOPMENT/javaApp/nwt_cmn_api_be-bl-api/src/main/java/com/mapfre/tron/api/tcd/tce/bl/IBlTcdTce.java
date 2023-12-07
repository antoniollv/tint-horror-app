package com.mapfre.tron.api.tcd.tce.bl;

import java.util.List;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;


public interface IBlTcdTce {


    /**
     * Query Technical Control Definition
     *
     * @author Javier Sangil
     * 
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * @param errValList -> Error List Value
     * 
     * @return List<OTcdTceS>
     * 
     */
    List<OTcdTceS> getTechnicalControlDefinition(Integer cmpVal, String lngVal, String usrVal, List<String> errValList);
}
