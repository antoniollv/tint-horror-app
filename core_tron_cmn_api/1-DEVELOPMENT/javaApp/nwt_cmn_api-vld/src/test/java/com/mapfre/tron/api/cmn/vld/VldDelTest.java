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
import com.mapfre.tron.api.cmn.del.dl.DlDelDAOImpl;
import com.mapfre.tron.api.cmn.del.dl.IDlDelDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldDel;
import com.mapfre.tron.api.vld.impl.TwVldDelImpl;

/**
 * The account validator JUnit test.
 *
 * @author arquitectura - majoguam
 * @since 1.8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldDelTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlDelDAO iDlDelDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldDel iVldDel;

    @Before
    public void setup() {
        iDlDelDAO = mock(DlDelDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldDel = new TwVldDelImpl(dlTrnErr, iDlDelDAO);
    }

    @Test
    public void accountLineOfBusinessExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(1);
        BigDecimal delVal = new BigDecimal(3);
        Date vldDat = new Date();
        

        

        when(iDlDelDAO.count(cmpVal, lngVal, lobVal, vldDat, delVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldDel.vldDelVal(cmpVal, lngVal, lobVal, vldDat, delVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void accountLineOfBusinessDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(1);
        BigDecimal delVal = new BigDecimal(3);
        Date vldDat = new Date();


        String temVal = "DEL";
        String prpIdn = "DEL_VAL";
        String errIdnVal = "20001: ACUERDO Contrato CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "delVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlDelDAO.count(cmpVal, lngVal, lobVal, vldDat, delVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldDel.vldDelVal(cmpVal, lngVal, lobVal, vldDat, delVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
