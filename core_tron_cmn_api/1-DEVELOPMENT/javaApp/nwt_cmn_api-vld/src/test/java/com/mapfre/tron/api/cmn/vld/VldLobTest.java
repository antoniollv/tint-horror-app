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
import com.mapfre.tron.api.cmn.lob.dl.DlLobDAOImpl;
import com.mapfre.tron.api.cmn.lob.dl.IDlLobDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldLob;
import com.mapfre.tron.api.vld.impl.TwVldLobImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 9 jul. 2021 - 9:07:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldLobTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlLobDAO iDlLobDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldLob iVldLob;

    @Before
    public void setup() {
        iDlLobDAO = mock(DlLobDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldLob = new TwVldLobImpl(dlTrnErr, iDlLobDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        BigDecimal lobVal = new BigDecimal(300);

        when(iDlLobDAO.count(cmpVal, lngVal, lobVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldLob.vldLob(cmpVal, lngVal, lobVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "LOB";
        String prpIdn = "LOB_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "lobVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal lobVal = new BigDecimal(1);
        when(iDlLobDAO.count(cmpVal, lngVal, lobVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldLob.vldLob(cmpVal, lngVal, lobVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
