package com.mapfre.tron.api.vld.dummy.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldMdt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The dummy modality validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 11:55:56
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class DmyVldMdtImpl implements IVldMdt {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /**
     * Validate whether modality value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param mdtVal -> Modality code
     * @return -> An error if the modality value does not exit
     */
    @Override
    public OTrnErrS vldMdtVal(final BigDecimal cmpVal, final String lngVal, final String mdtVal) {

        // dummy implementation
        if (mdtVal != null && mdtVal.trim().length() > 0) {
            return null;
        }

        return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "MDT", "MDT_VAL", cmpVal);

    }

}
