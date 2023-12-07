package com.mapfre.tron.api.cmn.cmn.col.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColPK;

public interface IBlOCmnColQry {

    List<OCmnColS> getAllColours(OCmnColPK pk);

    OCmnColP col(OCmnColPK build);
}
