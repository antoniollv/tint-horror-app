package com.mapfre.tron.api.thp.usr.dl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;
import com.mapfre.tron.api.bo.UserConnected;
import com.mapfre.tron.api.cmu.cmp.dl.OCmuCmpDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DlThpUsrImpl implements DlThpUsr {

    OThpUsrDao oThpUsrDao;
    OCmuCmpDao oCmuCmpDao;
    
    @Autowired
    public DlThpUsrImpl(OThpUsrDao oThpUsrDao, OCmuCmpDao oCmuCmpDao) {
	this.oThpUsrDao = oThpUsrDao;
	this.oCmuCmpDao = oCmuCmpDao;
    }
    
    public UserConnected getUserInfo(String usrVal) {
	// Obtener Informacion del usuario
	OThpUsrS usrData = oThpUsrDao.get(new OThpUsrPK(usrVal));
	// Obtener descripcion tercer nivel
	usrData.setThrLvlNam(oThpUsrDao.getDescription(usrData));
	
	if (usrData.getThpDcmVal() != null && usrData.getThpDcmTypVal() != null) {
		BigDecimal codTercero = null;
		try { 
			codTercero = oThpUsrDao.getCodTercero(usrData);
		} catch (Exception e) {
			log.info("Clave de producción no encontrada para " + usrVal);
		}
		 usrData.setThrLvlVal(codTercero);
	}
	    
	// Obtener codigo de compañias
	List<OCmuCmpS> cmpDataResult = oCmuCmpDao.getAll(usrData.getUsrVal());
	List<OCmuCmpS> cmpData = new ArrayList<>();
	// Obtener descripcion de compañias
	for (OCmuCmpS company : cmpDataResult) {
	    OCmuCmpS companyFinal = new OCmuCmpS();
	    companyFinal.setCmpVal(company.getCmpVal());
	    companyFinal.setCmpNam(oCmuCmpDao.getDescription(company));
	    cmpData.add(companyFinal);
	}
	return new UserConnected(usrData, cmpData);
    }
}
