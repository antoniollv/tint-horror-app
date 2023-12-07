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
import com.mapfre.tron.api.cmn.stt.dl.DlSttDAOImpl;
import com.mapfre.tron.api.cmn.stt.dl.IDlSttDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldStt;
import com.mapfre.tron.api.vld.impl.TwVldSttImpl;

/**
 * The state validation test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 9 jul. 2021 - 10:09:25
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldSttTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlSttDAO iDlSttDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldStt iVldStt;

    @Before
    public void setup() {
        iDlSttDAO = mock(DlSttDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldStt = new TwVldSttImpl(dlTrnErr, iDlSttDAO);
    }

    @Test
    public void stateExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String cnyVal = "ESP";
        BigDecimal sttVal = new BigDecimal(1);

        when(iDlSttDAO.count(cmpVal, lngVal, cnyVal, sttVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldStt.vldStt(cmpVal, lngVal, cnyVal, sttVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void stateDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "STT";
        String prpIdn = "STT_VAL";
        String errIdnVal = "20001: ESTADO Zona Dos Geografica CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "cvrVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String cnyVal = "ESP";
        BigDecimal sttVal = new BigDecimal(99999);
        when(iDlSttDAO.count(cmpVal, lngVal, cnyVal, sttVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldStt.vldStt(cmpVal, lngVal, cnyVal, sttVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
