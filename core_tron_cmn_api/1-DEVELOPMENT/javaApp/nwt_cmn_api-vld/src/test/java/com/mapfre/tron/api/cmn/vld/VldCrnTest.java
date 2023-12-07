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
import com.mapfre.tron.api.cmn.crn.dl.DlCrnDAOImpl;
import com.mapfre.tron.api.cmn.crn.dl.IDlCrnDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCrn;
import com.mapfre.tron.api.vld.impl.TwVldCrnImpl;

/**
 * The currency validation junit test class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 6 jul. 2021 - 19:11:43
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCrnTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The currency repository.*/
    private IDlCrnDAO iDlCnrDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCrn iVldCrn;

    @Before
    public void setup() {
        iDlCnrDAO = mock(DlCrnDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCrn = new TwVldCrnImpl(dlTrnErr, iDlCnrDAO);
    }

    @Test
    public void currencyExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        BigDecimal crnVal = new BigDecimal(1);
        BigDecimal lobVal = new BigDecimal(300);
        Date vldDat = new Date();

        when(iDlCnrDAO.count(cmpVal, lngVal, crnVal, vldDat, lobVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCrn.vldCrnVal(cmpVal, lngVal, crnVal, vldDat, lobVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void currencyDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CRN";
        String prpIdn = "CRN_VAL";
        String errIdnVal = "20001: MONEDA Moneda CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "crnVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal crnVal = new BigDecimal(1);
        BigDecimal lobVal = new BigDecimal(300);
        Date vldDat = new Date();
        when(iDlCnrDAO.count(cmpVal, lngVal, crnVal, vldDat, lobVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCrn.vldCrnVal(cmpVal, lngVal, crnVal, vldDat, lobVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
