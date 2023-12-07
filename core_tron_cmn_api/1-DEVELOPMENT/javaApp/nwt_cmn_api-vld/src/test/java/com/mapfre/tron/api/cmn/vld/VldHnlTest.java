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
import com.mapfre.tron.api.cmn.hnl.dl.DlHnlDAOImpl;
import com.mapfre.tron.api.cmn.hnl.dl.IDlHnlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldHnl;
import com.mapfre.tron.api.vld.impl.TwVldHnlImpl;

/**
 * The channel validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 13 jul. 2021 - 10:08:08
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldHnlTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The channel repository.*/
    private IDlHnlDAO iDlHnlDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldHnl iVldHnl;

    @Before
    public void setup() {
        iDlHnlDAO = mock(DlHnlDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldHnl = new TwVldHnlImpl(dlTrnErr, iDlHnlDAO);
    }

    @Test
    public void thirdDistributionChannelExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String thdDstHnlVal = "1001";

        when(iDlHnlDAO.count(cmpVal, lngVal, thdDstHnlVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldHnl.vldThrDstHnlVal(cmpVal, lngVal, thdDstHnlVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirdDistributionChannelDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "HNL";
        String prpIdn = "THR_DST_HNL_VAL";
        String errIdnVal = "20001: CANAL TERCER CANAL DISTRIBUCION CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thrDstHnlVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String thdDstHnlVal = "NON_EXISTS";
        when(iDlHnlDAO.count(cmpVal, lngVal, thdDstHnlVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldHnl.vldThrDstHnlVal(cmpVal, lngVal, thdDstHnlVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
