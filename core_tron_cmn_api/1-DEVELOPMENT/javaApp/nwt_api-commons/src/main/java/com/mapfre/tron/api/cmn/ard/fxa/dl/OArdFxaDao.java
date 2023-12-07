package com.mapfre.tron.api.cmn.ard.fxa.dl;

import java.util.List;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The fixed attributes controller.
 *
 * @author arquitectura - izhan del rio
 * @since 1.8
 * @version 12 Nov 2021 - 16:17:02
 *
 */

public interface OArdFxaDao extends NewtronDao<OArdFxaS, OArdFxaPK>{

    
    /**
     * Query Fix Attributes List.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> Fix Attributes list
     */
    
    
    List<OArdFxaS> getAllWithPK(OArdFxaPK pk);

    
    
    
}
