package com.mapfre.tron.api.cmn.abd.vhu.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.abd.vhu.bl.IBlAbdVhu;
import com.mapfre.tron.api.cmn.abd.vhu.dl.IDlAbdVhuDao;

/**
 * The AbdVhu business implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 May 2022 - 12:30:38
 *
 */
@Service
public class BlAbdVhuImpl implements IBlAbdVhu {

    @Autowired
    protected IDlAbdVhuDao iDlAbdVhuDao;

    /**
     * Get the vehicle use description.
     *
     * @param map -> Company code and Vehicle use code
     * @return       -> The vehicle use description
     */
    @Override
    public String getVhuNam(final Map<String, Object> map) {
        return iDlAbdVhuDao.get(map);
    }

}
