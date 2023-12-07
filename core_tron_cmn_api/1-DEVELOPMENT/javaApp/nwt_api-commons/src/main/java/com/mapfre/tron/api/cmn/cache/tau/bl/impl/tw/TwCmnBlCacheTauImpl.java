package com.mapfre.tron.api.cmn.cache.tau.bl.impl.tw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;
import com.mapfre.tron.api.cmn.cache.tau.bl.IBlCacheTau;
import com.mapfre.tron.api.cmn.cache.tau.dl.ISrCacheTauDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb tabla acceso por usuario business service implementation.
 *
 * @author magarafr
 * @since 1.8
 * @version 16 dec. 2020 12:41:13
 *
 */
@Slf4j
@Service
public class TwCmnBlCacheTauImpl implements IBlCacheTau {

    @Autowired
    ISrCacheTauDAO iSrCacheTauDAO;

    @Override
    @Cacheable(value = "tablaAccesoUsuario")
    public List<OCmnAedS> tabAccPorUsu(final String usrVal) {

        log.info("Tabla acceso por usuario has being called from Tronweb...");

        return iSrCacheTauDAO.tabAccPorUsu(usrVal);
    }

}
