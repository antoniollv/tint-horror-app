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
import com.mapfre.tron.api.cmn.thr.dl.DlThrLvlDAOImpl;
import com.mapfre.tron.api.cmn.thr.dl.IDlThrLvlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldThr;
import com.mapfre.tron.api.vld.impl.TwVldThrImpl;

/**
 * The third level validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 7 jul. 2021 - 12:18:28
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldThrLvlTest {


    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The third level repository.*/
    private IDlThrLvlDAO iDlThrLvlDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldThr iVldThr;

    @Before
    public void setup() {
        iDlThrLvlDAO = mock(DlThrLvlDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldThr = new TwVldThrImpl(dlTrnErr, iDlThrLvlDAO);
    }

    @Test
    public void thirdLevelExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thrLvlVal = new BigDecimal(1000);

        when(iDlThrLvlDAO.count(cmpVal, lngVal, thrLvlVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldThr.vldThrLvl(cmpVal, lngVal, thrLvlVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirdLevelDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "THR";
        String prpIdn = "THR_LVL_VAL";
        String errIdnVal = "20001: TERCERO (3Âº) Tercer Nivel CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "atzLvlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal thrLvlVal = new BigDecimal(1);
        when(iDlThrLvlDAO.count(cmpVal, lngVal, thrLvlVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldThr.vldThrLvl(cmpVal, lngVal, thrLvlVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
