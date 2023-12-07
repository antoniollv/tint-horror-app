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
import com.mapfre.tron.api.cmn.mak.dl.DlMakDAOImpl;
import com.mapfre.tron.api.cmn.mak.dl.IDlMakDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldMak;
import com.mapfre.tron.api.vld.impl.TwVldMakImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author arquitectura - Javier Sangil
 * @since 1.8
 * @vesion 15 jul. 2021 - 9:07:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldMakTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlMakDAO iDlMakDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldMak iVldMak;

    @Before
    public void setup() {
        iDlMakDAO = mock(DlMakDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldMak = new TwVldMakImpl(dlTrnErr, iDlMakDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);

        when(iDlMakDAO.count(cmpVal, lngVal, vldDat, makVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldMak.vldMak(cmpVal, lngVal, vldDat, makVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "MAK";
        String prpIdn = "MAK_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "makVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));


        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);
        when(iDlMakDAO.count(cmpVal, lngVal, vldDat, makVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldMak.vldMak(cmpVal, lngVal, vldDat, makVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
