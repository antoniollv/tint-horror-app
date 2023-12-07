package com.mapfre.tron.api.cmn.vld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.use.dl.DlUseDAOImpl;
import com.mapfre.tron.api.cmn.use.dl.IDlUseDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldUse;
import com.mapfre.tron.api.vld.impl.TwVldUseImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldUseTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlUseDAO iDlUseDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldUse iVldUse;

    @Before
    public void setup() {
        iDlUseDAO = mock(DlUseDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldUse = new TwVldUseImpl(dlTrnErr, iDlUseDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        Date vldDat = new Date();
        BigDecimal vhtVal = new BigDecimal(300);


        when(iDlUseDAO.count(cmpVal, lngVal, vhtVal, vldDat)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldUse.vldUse(cmpVal, lngVal, vhtVal, vldDat);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "USE";
        String prpIdn = "VHC_USE_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "sbmVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));


        Date vldDat = new Date();
        BigDecimal vhtVal = new BigDecimal(300);


        when(iDlUseDAO.count(cmpVal, lngVal, vhtVal, vldDat)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldUse.vldUse(cmpVal, lngVal, vhtVal, vldDat);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
	assertEquals(errNam, lvOTrnErrS.getErrNam());
	assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
