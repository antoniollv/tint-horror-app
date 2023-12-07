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
import com.mapfre.tron.api.cmn.cny.dl.DlCnyDAOImpl;
import com.mapfre.tron.api.cmn.cny.dl.IDlCnyDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCny;
import com.mapfre.tron.api.vld.impl.TwVldCnyImpl;

/**
 * The country validation junit test class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 7 jul. 2021 - 14:36:14
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCnyTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The country repository.*/
    private IDlCnyDAO iDlCnyDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCny iVldCny;

    @Before
    public void setup() {
        iDlCnyDAO = mock(DlCnyDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCny = new TwVldCnyImpl(dlTrnErr, iDlCnyDAO);
    }

    @Test
    public void countryCodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String cnyVal = "ESP";

        when(iDlCnyDAO.count(cmpVal, lngVal, cnyVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCny.vldThrLvl(cmpVal, lngVal, cnyVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void countryCodeDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CNY";
        String prpIdn = "CNY_VAL";
        String errIdnVal = "20001: PAIS Pais CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "cnyVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String cnyVal = "ZZZ";
        when(iDlCnyDAO.count(cmpVal, lngVal, cnyVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCny.vldThrLvl(cmpVal, lngVal, cnyVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
