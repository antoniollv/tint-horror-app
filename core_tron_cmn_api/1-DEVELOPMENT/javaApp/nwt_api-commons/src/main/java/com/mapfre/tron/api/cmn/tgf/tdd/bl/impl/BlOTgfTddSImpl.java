package com.mapfre.tron.api.cmn.tgf.tdd.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;
import com.mapfre.tron.api.cmn.tgf.tdd.bl.IBlOTgfTddS;
import com.mapfre.tron.api.cmn.tgf.tdd.dl.IDlOTgfTddSDao;

@Service
public class BlOTgfTddSImpl implements IBlOTgfTddS {

	@Autowired
	protected IDlOTgfTddSDao iDlOTgfTddSDao;

	@Override
	public OTgfTddS getInoTypNam(Map<String, Object> map) {

		return iDlOTgfTddSDao.getInoTypNam(map);

	}

}
