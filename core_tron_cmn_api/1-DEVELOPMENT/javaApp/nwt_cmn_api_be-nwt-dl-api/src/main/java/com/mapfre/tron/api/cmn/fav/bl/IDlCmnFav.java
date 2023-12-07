package com.mapfre.tron.api.cmn.fav.bl;

import java.math.BigDecimal;
import java.util.List;


public interface IDlCmnFav {

    public boolean delFav(BigDecimal cmpVal, String usrVal, String oprIdnVal);
    
    public List<String> getFav(BigDecimal cmpVal, String usrVal);
    
    public boolean putFav(BigDecimal cmpVal, String usrVal, String oprIdnVal);
}
