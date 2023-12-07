package com.mapfre.tron.api.cmn.cache.tau.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * The PlyGniQry commons business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 12:07:53
 *
 */
public interface IBlCacheTau {

    List<OCmnAedS> tabAccPorUsu(String usrVal);
}
