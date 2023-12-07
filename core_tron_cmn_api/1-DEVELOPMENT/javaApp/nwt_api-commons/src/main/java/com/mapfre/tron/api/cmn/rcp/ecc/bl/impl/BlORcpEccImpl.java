package com.mapfre.tron.api.cmn.rcp.ecc.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.rcp.ecc.bl.IBlORcpEcc;
import com.mapfre.tron.api.cmn.rcp.ecc.dl.IDlORcpEcc;

/**
 * The RcpEcc business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 18 Jan 2023 - 17:30:58
 *
 */
@Service
public class BlORcpEccImpl implements IBlORcpEcc {

    @Autowired
    protected IDlORcpEcc iDlORcpEcc;

    /**
     * Get the economic concept description.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    @Override
    public String getEccNam(final Map<String, Object> map) {
        return iDlORcpEcc.getEccNam(map);
    }

}
