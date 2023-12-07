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
import com.mapfre.tron.api.cmn.sec.dl.DlSecDAOImpl;
import com.mapfre.tron.api.cmn.sec.dl.IDlSecDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldSec;
import com.mapfre.tron.api.vld.impl.TwVldSecImpl;

/**
 * The sector validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 12 jul. 2021 - 13:18:17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldSecTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlSecDAO iDlSecDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldSec iVldSec;

    @Before
    public void setup() {
        iDlSecDAO = mock(DlSecDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldSec = new TwVldSecImpl(dlTrnErr, iDlSecDAO);
    }

    @Test
    public void sectorExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal secVal = new BigDecimal(1);

        when(iDlSecDAO.count(cmpVal, lngVal, secVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldSec.vldSecVal(cmpVal, lngVal, secVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void sectorDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "SEC";
        String prpIdn = "SEC_VAL";
        String errIdnVal = "20001: SECTOR Sector CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "atzLvlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal secVal = new BigDecimal(4444);
        when(iDlSecDAO.count(cmpVal, lngVal, secVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldSec.vldSecVal(cmpVal, lngVal, secVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
