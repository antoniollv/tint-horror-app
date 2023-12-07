package com.mapfre.tron.api.cmn.fav.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.cmn.model.FavoritesDto;

@TronWebAccess
public class TwBlCmnFav implements IBlCmnFav{

    private static final String UNSUPPORTED_OPERATION_WITH_TRONWEB_PROFILE =
            "Unsupported Operation with tronweb profile";

    @Override
    public List<FavoritesDto> delFav(final BigDecimal cmpVal, final String usrVal, final String oprIdnVal) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_WITH_TRONWEB_PROFILE);
    }

    @Override
    public List<FavoritesDto> getFav(final BigDecimal cmpVal, final String usrVal) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_WITH_TRONWEB_PROFILE);
    }

    @Override
    public List<FavoritesDto> putFav(final BigDecimal cmpVal, final String usrVal, final String oprIdnVal) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_WITH_TRONWEB_PROFILE);
    }

}
