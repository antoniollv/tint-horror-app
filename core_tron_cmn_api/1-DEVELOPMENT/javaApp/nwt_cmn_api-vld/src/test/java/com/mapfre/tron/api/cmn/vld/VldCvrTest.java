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
import com.mapfre.tron.api.cmn.cvr.dl.DlCvrDAOImpl;
import com.mapfre.tron.api.cmn.cvr.dl.IDlCvrDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCvr;
import com.mapfre.tron.api.vld.impl.TwVldCvrImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 9 jul. 2021 - 9:07:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCvrTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlCvrDAO iDlCvrDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCvr iVldCvr;

    @Before
    public void setup() {
        iDlCvrDAO = mock(DlCvrDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCvr = new TwVldCvrImpl(dlTrnErr, iDlCvrDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal cvrVal = new BigDecimal(1030);
        Date vldDat = new Date();
        BigDecimal lobVal = new BigDecimal(300);
        String mdtVal = "99999";

        when(iDlCvrDAO.count(cmpVal, lngVal, cvrVal, vldDat, lobVal, mdtVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCvr.vldCvrVal(cmpVal, lngVal, cvrVal, vldDat, lobVal, mdtVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CVR";
        String prpIdn = "CVR_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "cvrVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal cvrVal = new BigDecimal(1030);
        Date vldDat = new Date();
        BigDecimal lobVal = new BigDecimal(300);
        String mdtVal = "ZZZZZ";
        when(iDlCvrDAO.count(cmpVal, lngVal, cvrVal, vldDat, lobVal, mdtVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldCvr.vldCvrVal(cmpVal, lngVal, cvrVal, vldDat, lobVal, mdtVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
