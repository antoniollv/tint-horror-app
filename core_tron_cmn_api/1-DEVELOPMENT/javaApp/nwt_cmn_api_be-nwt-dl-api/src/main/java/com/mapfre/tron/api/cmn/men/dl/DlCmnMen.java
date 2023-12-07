package com.mapfre.tron.api.cmn.men.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;

public interface DlCmnMen {
    
    public List<OCmnMenS> getMenu(String usrVal, String lngVal, BigDecimal cmpVal);

    public String getUrlBase(String urlBase);
    
    public List<String> getOprIdnValLst(String usrVal, BigDecimal cmpVal);
}
