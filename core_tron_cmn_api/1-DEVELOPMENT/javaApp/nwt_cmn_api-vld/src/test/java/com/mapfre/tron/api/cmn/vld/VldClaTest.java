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
import com.mapfre.tron.api.cmn.cla.dl.DlClaDAOImpl;
import com.mapfre.tron.api.cmn.cla.dl.IDlClaDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCla;
import com.mapfre.tron.api.vld.impl.TwVldClauImpl;

/**
 * The clause validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 8 jul. 2021 - 9:06:52
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldClaTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The clause repository.*/
    private IDlClaDAO iDlClaDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCla iVldCla;

    @Before
    public void setup() {
        iDlClaDAO = mock(DlClaDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCla = new TwVldClauImpl(dlTrnErr, iDlClaDAO);
    }

    @Test
    public void clauseExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String claVal = "UL000001";
        Date vldDat = new Date();

        when(iDlClaDAO.count(cmpVal, lngVal, claVal, vldDat)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCla.vldClaVal(cmpVal, lngVal, claVal, vldDat);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void clauseDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CLA";
        String prpIdn = "CLA_VAL";
        String errIdnVal = "20001: CLAUSULA Clausula CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "atzLvlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String claVal = "ZZZZZZZ";
        Date vldDat = new Date();
        when(iDlClaDAO.count(cmpVal, lngVal, claVal, vldDat)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCla.vldClaVal(cmpVal, lngVal, claVal, vldDat);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
