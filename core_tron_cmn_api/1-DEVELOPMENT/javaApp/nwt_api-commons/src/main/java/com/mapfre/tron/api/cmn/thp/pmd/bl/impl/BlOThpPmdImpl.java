package com.mapfre.tron.api.cmn.thp.pmd.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.thp.pmd.bl.IBlOThpPmd;
import com.mapfre.tron.api.cmn.thp.pmd.dl.IDlOThpPmd;

/**
 * The business implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:54:49
 *
 */
@Service
public class BlOThpPmdImpl implements IBlOThpPmd {

    @Autowired
    protected IDlOThpPmd iDlOThpPmd;

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    @Override
    public String getOThpPmd(final Map<String, Object> map) {
        return iDlOThpPmd.getOThpPmd(map);
    }

}
