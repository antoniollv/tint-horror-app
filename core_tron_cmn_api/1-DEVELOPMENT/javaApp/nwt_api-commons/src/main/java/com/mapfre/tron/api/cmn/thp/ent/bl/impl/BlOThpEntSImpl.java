package com.mapfre.tron.api.cmn.thp.ent.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.thp.ent.bl.IBlOThpEntS;
import com.mapfre.tron.api.cmn.thp.ent.dl.IDlOThpEntS;

/**
 * The business implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:54:49
 *
 */
@Service
public class BlOThpEntSImpl implements IBlOThpEntS {

    @Autowired
    protected IDlOThpEntS iDlOThpEntS;

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    @Override
    public String getOThpEntS(final Map<String, Object> map) {
        return iDlOThpEntS.getOThpEntS(map);
    }

}
