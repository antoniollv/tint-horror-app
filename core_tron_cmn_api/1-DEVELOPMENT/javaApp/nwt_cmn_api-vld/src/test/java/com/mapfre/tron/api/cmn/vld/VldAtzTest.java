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
import com.mapfre.tron.api.cmn.atz.dl.DlAtzDAOImpl;
import com.mapfre.tron.api.cmn.atz.dl.IDlAtzDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldAtz;
import com.mapfre.tron.api.vld.impl.TwVldAtzImpl;

/**
 * The authorization validation junit test class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 7 jul. 2021 - 9:13:05
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldAtzTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlAtzDAO iDlAtzDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldAtz iVldAtz;

    @Before
    public void setup() {
        iDlAtzDAO = mock(DlAtzDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldAtz = new TwVldAtzImpl(dlTrnErr, iDlAtzDAO);
    }

    @Test
    public void authorizationLevelExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal atzLvlVal = new BigDecimal(0);

        when(iDlAtzDAO.count(cmpVal, lngVal, atzLvlVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldAtz.vldAtzLvl(cmpVal, lngVal, atzLvlVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void authorizationLevelDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "ATZ";
        String prpIdn = "ATZ_LVL_VAL";
        String errIdnVal = "20001: AUTORIZACION Nivel Autorizacion CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "atzLvlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal atzLvlVal = new BigDecimal(99);
        when(iDlAtzDAO.count(cmpVal, lngVal, atzLvlVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldAtz.vldAtzLvl(cmpVal, lngVal, atzLvlVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
