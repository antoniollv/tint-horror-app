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
import com.mapfre.tron.api.cmn.sbm.dl.DlSbmDAOImpl;
import com.mapfre.tron.api.cmn.sbm.dl.IDlSbmDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldSbm;
import com.mapfre.tron.api.vld.impl.TwVldSbmImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldSbmTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlSbmDAO iDlSbmDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldSbm iVldSbm;

    @Before
    public void setup() {
        iDlSbmDAO = mock(DlSbmDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldSbm = new TwVldSbmImpl(dlTrnErr, iDlSbmDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);
        BigDecimal mdlVal = new BigDecimal(99999);
        BigDecimal sbmVal = new BigDecimal(99999);

        when(iDlSbmDAO.count(cmpVal, lngVal, makVal, mdlVal, sbmVal, vldDat)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldSbm.vldSbm(cmpVal, lngVal, makVal, mdlVal, sbmVal, vldDat);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "SBM";
        String prpIdn = "SBM_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "sbmVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));


        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);
        BigDecimal mdlVal = new BigDecimal(99999);
        BigDecimal sbmVal = new BigDecimal(99999);

        when(iDlSbmDAO.count(cmpVal, lngVal, makVal, mdlVal, sbmVal, vldDat)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldSbm.vldSbm(cmpVal, lngVal, makVal, mdlVal, sbmVal, vldDat);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
	assertEquals(errNam, lvOTrnErrS.getErrNam());
	assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
