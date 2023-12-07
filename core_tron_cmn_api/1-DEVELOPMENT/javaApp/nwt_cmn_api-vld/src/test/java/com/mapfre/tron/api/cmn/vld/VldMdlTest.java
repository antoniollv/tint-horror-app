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
import com.mapfre.tron.api.cmn.mdl.dl.DlMdlDAOImpl;
import com.mapfre.tron.api.cmn.mdl.dl.IDlMdlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldMdl;
import com.mapfre.tron.api.vld.impl.TwVldMdlImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 9 jul. 2021 - 9:07:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldMdlTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlMdlDAO iDlMdlDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldMdl iVldMdl;

    @Before
    public void setup() {
        iDlMdlDAO = mock(DlMdlDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldMdl = new TwVldMdlImpl(dlTrnErr, iDlMdlDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);
        BigDecimal mdlVal = new BigDecimal(99999);

        when(iDlMdlDAO.count(cmpVal, lngVal, vldDat, makVal, mdlVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldMdl.vldMdl(cmpVal, lngVal, vldDat, makVal, mdlVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "MDL";
        String prpIdn = "MDL_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "mdlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));


        Date vldDat = new Date();
        BigDecimal makVal = new BigDecimal(300);
        BigDecimal mdlVal = new BigDecimal(99999);
        when(iDlMdlDAO.count(cmpVal, lngVal, vldDat, makVal, mdlVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldMdl.vldMdl(cmpVal, lngVal, vldDat, makVal, mdlVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
