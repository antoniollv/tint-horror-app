package com.mapfre.tron.api.cmn.cache.tau.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * The CacheTau commons business service interface.
 *
 * @author magarafr
 * @since 1.8
 * @vesion 16 dic. 2020 - 12:07:53
 *
 */
public interface ISrCacheTauDAO {

    /**
     * Query tabla acceso por usuario.
     *
     * @author magarafr
     * 
     * @param usrVal    -> User code
     * @return List<OCmnAedS> 
     */
    List<OCmnAedS> tabAccPorUsu(String usrVal);
}
