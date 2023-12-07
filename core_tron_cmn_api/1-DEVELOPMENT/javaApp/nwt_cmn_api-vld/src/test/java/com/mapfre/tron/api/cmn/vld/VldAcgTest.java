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
import com.mapfre.tron.api.cmn.acg.dl.DlAcgDAOImpl;
import com.mapfre.tron.api.cmn.acg.dl.IDlAcgDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldAcg;
import com.mapfre.tron.api.vld.impl.TwVldAcgImpl;

/**
 * The account validator JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 12 jul. 2021 - 9:59:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldAcgTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlAcgDAO iDlAcgDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldAcg iVldAcg;

    @Before
    public void setup() {
        iDlAcgDAO = mock(DlAcgDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldAcg = new TwVldAcgImpl(dlTrnErr, iDlAcgDAO);
    }

    @Test
    public void accountLineOfBusinessExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal acgLobVal = new BigDecimal(100000001);

        when(iDlAcgDAO.count(cmpVal, lngVal, acgLobVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldAcg.vldAcgLobVal(cmpVal, lngVal, acgLobVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void accountLineOfBusinessDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "ACG";
        String prpIdn = "ACG_LOB_VAL";
        String errIdnVal = "20001: CONTABILIDAD Ramo Contable  CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "atzLvlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal acgLobVal = new BigDecimal(99999);
        when(iDlAcgDAO.count(cmpVal, lngVal, acgLobVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldAcg.vldAcgLobVal(cmpVal, lngVal, acgLobVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
