package com.mapfre.tron.api.thp.usr.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OThpUsrDao extends NewtronDao<OThpUsrS, OThpUsrPK>{

    BigDecimal getCodTercero(OThpUsrS userData);
}
