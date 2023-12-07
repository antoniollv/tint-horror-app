package com.mapfre.tron.api.cmn.thp.cst.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;
import com.mapfre.tron.api.cmn.thp.cst.bl.IBlOTpdCsgS;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSDao;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSPK;

@Service
public class TwBlOTpdCsgSImpl implements IBlOTpdCsgS {

    @Autowired
    protected OTpdCsgSDao oTdpCsgSDao;

    @Override
    public OTpdCsgS getOTpdCsgS(OTpdCsgSPK pk) {
	return oTdpCsgSDao.getOTpdCsgS(pk);

    }

    @Override
    public List<OTpdCsgS> getOTpdCsgSList(OTpdCsgSPK pk) {
	return oTdpCsgSDao.getOTpdCsgSList(pk);

    }

}
