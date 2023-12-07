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
import com.mapfre.tron.api.cmn.cmp.dl.DlCmpDAOImpl;
import com.mapfre.tron.api.cmn.cmp.dl.IDlCmpDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCmp;
import com.mapfre.tron.api.vld.impl.TwVldCmpImpl;

/**
 * The company validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 13 jul. 2021 - 14:01:43
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCmpTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The company repository.*/
    private IDlCmpDAO iDlCmpDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCmp iVldCmp;

    @Before
    public void setup() {
        iDlCmpDAO = mock(DlCmpDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCmp = new TwVldCmpImpl(dlTrnErr, iDlCmpDAO);
    }

    @Test
    public void companyValueExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        when(iDlCmpDAO.count(cmpVal, lngVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCmp.vldCmpVal(cmpVal, lngVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void companyValueDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((999));

        String temVal = "COD";
        String prpIdn = "CMP_VAL";
        String errIdnVal = "20001: CODIGO Compañía -- nd --";
        String errNam = "-- nd --";
        String prpNam = "cmpVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlCmpDAO.count(cmpVal, lngVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCmp.vldCmpVal(cmpVal, lngVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
