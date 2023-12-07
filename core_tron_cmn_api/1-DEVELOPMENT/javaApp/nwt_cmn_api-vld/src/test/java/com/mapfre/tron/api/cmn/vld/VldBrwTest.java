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
import com.mapfre.tron.api.cmn.brw.dl.DlBrwDAOImpl;
import com.mapfre.tron.api.cmn.brw.dl.IDlBrwDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldBrw;
import com.mapfre.tron.api.vld.impl.TwVldBrwImpl;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 15 Jul 2021
*
*/
@RunWith(MockitoJUnitRunner.class)
public class VldBrwTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository.*/
    private IDlBrwDAO iDlBrwDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldBrw iVldBrw;

    @Before
    public void setup() {
        iDlBrwDAO = mock(DlBrwDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldBrw = new TwVldBrwImpl(dlTrnErr, iDlBrwDAO);
    }

    @Test
    public void coverageExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        Date vldDat = new Date();
        BigDecimal mdtVal = new BigDecimal(300);
        BigDecimal lobVal = new BigDecimal(99999);
        BigDecimal ecmBrwCncVal = new BigDecimal(99999);
        BigDecimal cvrVal = new BigDecimal(99999);

        when(iDlBrwDAO.count(cmpVal, lngVal, vldDat, mdtVal, lobVal, ecmBrwCncVal, cvrVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldBrw.vldBrw(cmpVal, lngVal, vldDat, mdtVal, lobVal, ecmBrwCncVal, cvrVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "BRW";
        String prpIdn = "ECM_BRW_CNC_VAL";
        String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "mdlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));


        Date vldDat = new Date();
        BigDecimal mdtVal = new BigDecimal(300);
        BigDecimal lobVal = new BigDecimal(99999);
        BigDecimal ecmBrwCncVal = new BigDecimal(99999);
        BigDecimal cvrVal = new BigDecimal(99999);
        when(iDlBrwDAO.count(cmpVal, lngVal, vldDat, mdtVal, lobVal, ecmBrwCncVal, cvrVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldBrw.vldBrw(cmpVal, lngVal, vldDat, mdtVal, lobVal, ecmBrwCncVal, cvrVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
