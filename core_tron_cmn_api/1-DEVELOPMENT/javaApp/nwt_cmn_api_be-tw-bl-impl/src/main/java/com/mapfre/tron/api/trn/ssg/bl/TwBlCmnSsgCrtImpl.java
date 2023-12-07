package com.mapfre.tron.api.trn.ssg.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.trn.ssg.dl.ISrCmnSsgCrtDAO;

import lombok.EqualsAndHashCode;

/**
 * The CmnSsgCrt service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 1 feb. 2022 - 16:49:01
 *
 */
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnSsgCrtImpl extends TwBlCmnBase implements IBlCmnSsgCrt {

    /** The SsgCrt repository.*/
    @Autowired
    protected ISrCmnSsgCrtDAO iSrCmnSsgCrtDAO;

    /**
     * Create Storage From Self-Service.
     *
     * @param cmpVal               -> Company code
     * @param usrVal               -> User code
     * @param lngVal               -> Language code
     * @param inSelfServiceStorage -> Input data to new self service storage
     */
    @Transactional("transactionManagerTwDl")
    @Override
    public void postStorageFromSelfService(final Integer cmpVal, final String usrVal, final String lngVal,
            final List<OCmnSsgS> inSelfServiceStorage) {

        try {
            if (inSelfServiceStorage != null && !inSelfServiceStorage.isEmpty()) {
                for (OCmnSsgS oCmnSsgS : inSelfServiceStorage) {
                    iSrCmnSsgCrtDAO.postStorageFromSelfService(cmpVal, usrVal, lngVal, oCmnSsgS);
                }
            }

        } catch (DuplicateKeyException dke) {
            getErrorWithoutPrpIdn(lngVal, "SSG", new BigDecimal(cmpVal));
        }

    }

}
