package com.mapfre.tron.api.cmn.fav.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.tron.api.cmn.model.FavoritesDto;

public interface IBlCmnFav {

    public List<FavoritesDto> delFav(BigDecimal cmpVal,String usrVal, String oprIdnVal);
    
    public List<FavoritesDto> getFav(BigDecimal cmpVal,String usrVal);
    
    public List<FavoritesDto> putFav(BigDecimal cmpVal,String usrVal, String oprIdnVal);
}
