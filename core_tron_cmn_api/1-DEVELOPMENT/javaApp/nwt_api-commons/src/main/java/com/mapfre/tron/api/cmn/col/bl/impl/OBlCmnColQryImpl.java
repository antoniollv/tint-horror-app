
package com.mapfre.tron.api.cmn.col.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.tron.api.cmn.col.bl.IBlOCmnColQry;
import com.mapfre.tron.api.cmn.col.dl.OCmnColQryDAO;
import com.mapfre.tron.api.cmn.col.dl.OCmnColQryPK;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class OBlCmnColQryImpl implements IBlOCmnColQry {

    @Autowired
    protected OCmnColQryDAO oCmnColQryDAO;


    @Override
    @Transactional("transactionManagerTwDl")
    public OCmnColP col(OCmnColQryPK pmOCmnColQryPK) {
        log.info("Tronweb business logic implementation FLD have been called...");

        return oCmnColQryDAO.get(pmOCmnColQryPK);
    }

}
