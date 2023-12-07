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
import com.mapfre.tron.api.cmn.cnc.dl.DlCncDAOImpl;
import com.mapfre.tron.api.cmn.cnc.dl.IDlCncDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCnc;
import com.mapfre.tron.api.vld.impl.TwVldCncImpl;

/**
 * The concept validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 14 jul. 2021 - 13:12:35
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCncTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The country repository.*/
    private IDlCncDAO iDlCncDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCnc iVldCnc;

    @Before
    public void setup() {
        iDlCncDAO = mock(DlCncDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCnc = new TwVldCncImpl(dlTrnErr, iDlCncDAO);
    }

    @Test
    public void reserveConceptValueExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal rsvCncVal = new BigDecimal(1);

        when(iDlCncDAO.count(cmpVal, lngVal, rsvCncVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCnc.vldRsvCncVal(cmpVal, lngVal, rsvCncVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void reserveConceptValueDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CNC";
        String prpIdn = "RSV_CNC_VAL";
        String errIdnVal = "20001: CONCEPTO Concepto Reserva CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "rsvCncVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal rsvCncVal = new BigDecimal(999999);
        when(iDlCncDAO.count(cmpVal, lngVal, rsvCncVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCnc.vldRsvCncVal(cmpVal, lngVal, rsvCncVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
