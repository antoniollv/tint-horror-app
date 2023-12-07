package com.mapfre.tron.api.cmn.agn.cpe.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.agn.cpe.bl.IBlAgnCpe;
import com.mapfre.tron.api.cmn.agn.cpe.dl.OAgnCpeDao;

/**
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 17 nov 2021 - 16:57:19
 *
 */
@Service
public class BlAgnCpeImpl implements IBlAgnCpe {

    @Autowired
    protected OAgnCpeDao oAgnCpeDao;



    @Override
    public String getAgnCpeNam(Map<String, Object> map) {
	return oAgnCpeDao.getAgnCpeNam(map);    }

}
