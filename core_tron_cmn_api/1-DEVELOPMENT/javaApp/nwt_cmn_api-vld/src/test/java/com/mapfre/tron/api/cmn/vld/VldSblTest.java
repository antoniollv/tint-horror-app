package com.mapfre.tron.api.cmn.vld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.sbl.dl.DlSblDAOImpl;
import com.mapfre.tron.api.cmn.sbl.dl.IDlSblDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldSbl;
import com.mapfre.tron.api.vld.impl.TwVldSblImpl;

/**
 * The account validator JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 12 jul. 2021 - 9:59:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldSblTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlSblDAO iDlSblDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldSbl iVldSbl;

    @Before
    public void setup() {
        iDlSblDAO = mock(DlSblDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldSbl = new TwVldSblImpl(dlTrnErr, iDlSblDAO);
    }

    @Test
    public void sblValExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal sblVal = new BigDecimal(1);
        

        

        when(iDlSblDAO.count(cmpVal, lngVal, sblVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldSbl.vldSblVal(cmpVal, lngVal, sblVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void sblValDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal sblVal = new BigDecimal(1);


        String temVal = "SBL";
        String prpIdn = "SBL_VAL";
        String errIdnVal = "20001: SUB ACUERDO Subcontrato CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "enrVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlSblDAO.count(cmpVal, lngVal, sblVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldSbl.vldSblVal(cmpVal, lngVal, sblVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
