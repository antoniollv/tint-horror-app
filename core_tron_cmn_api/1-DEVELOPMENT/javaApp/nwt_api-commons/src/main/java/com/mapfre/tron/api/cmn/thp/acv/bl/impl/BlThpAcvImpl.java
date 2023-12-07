package com.mapfre.tron.api.cmn.thp.acv.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.thp.acv.bl.IBlThpAcv;
import com.mapfre.tron.api.cmn.thp.acv.dl.IDlThpAcv;

/**
 * The thirdpard activity business implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 14:54:49
 *
 */
@Service
public class BlThpAcvImpl implements IBlThpAcv {

    @Autowired
    protected IDlThpAcv iDlThpAcv;

    /**
     * Get the Thirdpard activity description.
     *
     * @param map -> The map with the query params values
     * @return    -> The Thirdpard activity description
     */
    @Override
    public String getThpAcvNam(final Map<String, Object> map) {
        return iDlThpAcv.getThpAcvNam(map);
    }

}
