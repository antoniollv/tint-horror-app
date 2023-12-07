package com.mapfre.tron.api.cmn.men.dl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;

@Service
public class DlCmnMenImpl implements DlCmnMen {

    private OCmnMenDao oCmnMenDao;
    
    @Autowired
    public DlCmnMenImpl(OCmnMenDao oCmnMenDao) {
	super();
	this.oCmnMenDao = oCmnMenDao;
    }
    
    @Override
    public List<OCmnMenS> getMenu(String usrVal, String lngVal, BigDecimal cmpVal) {
	return oCmnMenDao.getAll(cmpVal, usrVal, lngVal);
    }

    @Override
    public String getUrlBase(String urlBase) {
	return oCmnMenDao.getUrlBase(urlBase);
    }
    
    @Override
    public List<String> getOprIdnValLst(String usrVal, BigDecimal cmpVal) {
	return oCmnMenDao.getOprIdnValLst(usrVal, cmpVal);
    }
    

    
}
