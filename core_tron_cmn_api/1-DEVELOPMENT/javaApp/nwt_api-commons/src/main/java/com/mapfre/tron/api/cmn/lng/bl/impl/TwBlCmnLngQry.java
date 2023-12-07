package com.mapfre.tron.api.cmn.lng.bl.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.lng.bl.IBlOCmnLngQry;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryDAO;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryPK;

@Service
public class TwBlCmnLngQry extends TwBlCmnBase implements IBlOCmnLngQry {

    @Autowired
    protected OCmnLngQryDAO oCmnLngQryDAO;

    @Override
    public List<OCmnLngP> getOCmnLngPAll(final String lngVal, final BigDecimal cmpVal) {

        try {
            return oCmnLngQryDAO.getAll();
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LNG", cmpVal);
        }

        return Collections.emptyList();
    }

    @Override
    public OCmnLngS getLanguage(OCmnLngQryPK oCmnLngQryPK) {
        return oCmnLngQryDAO.getLanguage(oCmnLngQryPK);
    }

}
