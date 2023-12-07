package com.mapfre.tron.api.cmn.tdn.dl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DlCmnTdnImpl implements DlCmnTdn {

    private OCmnTdnDao oCmnTdnDao;
    
    @Autowired
    public DlCmnTdnImpl(OCmnTdnDao oCmnTdnDao) {
	super();
	this.oCmnTdnDao = oCmnTdnDao;
    }

    @Override
    public Map<String, String> getTags(String lngVal) {
	return oCmnTdnDao.get(lngVal);
    }
    


    
}
