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
import com.mapfre.tron.api.cmn.fil.dl.DlFilDAOImpl;
import com.mapfre.tron.api.cmn.fil.dl.IDlFilDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldFil;
import com.mapfre.tron.api.vld.impl.TwVldFilImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldFilTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlFilDAO iDlFilDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldFil iVldFil;

    @Before
    public void setup() {
        iDlFilDAO = mock(DlFilDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldFil = new TwVldFilImpl(dlTrnErr, iDlFilDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String filTypVal = "1";

        when(iDlFilDAO.count(cmpVal, lngVal, filTypVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldFil.vldFil(cmpVal, lngVal, filTypVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "FIL";
        String prpIdn = "FIL_TYP_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "filTypVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String filTypVal = "1";

        when(iDlFilDAO.count(cmpVal, lngVal, filTypVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldFil.vldFil(cmpVal, lngVal, filTypVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
	assertEquals(errNam, lvOTrnErrS.getErrNam());
	assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
