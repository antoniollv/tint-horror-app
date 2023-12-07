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
import com.mapfre.tron.api.cmn.err.dl.DlErrDAOImpl;
import com.mapfre.tron.api.cmn.err.dl.IDlErrDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldErr;
import com.mapfre.tron.api.vld.impl.TwVldErrImpl;

/**
 * The error validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 19 jul. 2021 - 9:19:49
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldErrTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The commission chart repository.*/
    private IDlErrDAO iDlErrDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldErr iVldErr;

    @Before
    public void setup() {
        iDlErrDAO = mock(DlErrDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldErr = new TwVldErrImpl(dlTrnErr, iDlErrDAO);
    }

    @Test
    public void commissionChartCodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal errVal = new BigDecimal(1001);

        when(iDlErrDAO.count(cmpVal, lngVal, errVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldErr.vldErrVal(cmpVal, lngVal, errVal);

        assertNull(lvOTrnErrS);
    }

    @Test
    public void commissionChartCodeDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "ERR";
        String prpIdn = "ERR_VAL";
        String errIdnVal = "20001: ERROR Error CODIGO INEXISTENTE, errVal=20001";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "errVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal errVal = new BigDecimal(9999);

        when(iDlErrDAO.count(cmpVal, lngVal, errVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldErr.vldErrVal(cmpVal, lngVal, errVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
