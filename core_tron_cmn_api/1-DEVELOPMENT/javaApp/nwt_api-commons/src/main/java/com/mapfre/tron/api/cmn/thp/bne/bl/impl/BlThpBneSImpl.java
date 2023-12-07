package com.mapfre.tron.api.cmn.thp.bne.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.thp.bne.bl.IBlOThpBneS;
import com.mapfre.tron.api.cmn.thp.bne.dl.IDlOThpBneS;

/**
 * The business implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:54:49
 *
 */
@Service
public class BlThpBneSImpl implements IBlOThpBneS {

    @Autowired
    protected IDlOThpBneS iDlOThpBneS;

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    @Override
    public String getOThpBneS(final Map<String, Object> map) {
        return iDlOThpBneS.getOThpBneS(map);
    }

}
