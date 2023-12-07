package com.mapfre.tron.api.cmn.tgf.ucd.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;
import com.mapfre.tron.api.cmn.tgf.ucd.bl.IBlOTgfUcdS;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSDao;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK2;

@Service
public class TwBlOTgfUcdSImpl implements IBlOTgfUcdS {

	@Autowired
	protected OTgfUcdSDao oTgfUcdSDao;
	
	@Override
	public List<OTgfUcdS> getCauses(OTgfUcdSPK pk) {
		return oTgfUcdSDao.getCauses(pk);
	}

	@Override
	public OTgfUcdS getCause(OTgfUcdSPK2 pk) {
		return oTgfUcdSDao.getCause(pk);
	}

}
