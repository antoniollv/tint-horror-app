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
import com.mapfre.tron.api.cmn.rte.dl.DlRteDAOImpl;
import com.mapfre.tron.api.cmn.rte.dl.IDlRteDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldRte;
import com.mapfre.tron.api.vld.impl.TwVldRteImpl;

/**
 * The rate value validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 19 jul. 2021 - 11:50:58
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldRteTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The commission chart repository.*/
    private IDlRteDAO iDlRteDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldRte iVldRte;

    @Before
    public void setup() {
        iDlRteDAO = mock(DlRteDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldRte = new TwVldRteImpl(dlTrnErr, iDlRteDAO);
    }

    @Test
    public void commissionChartCodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal rteVal = new BigDecimal(1);

        when(iDlRteDAO.count(cmpVal, lngVal, rteVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldRte.vldRteVal(cmpVal, lngVal, rteVal);

        assertNull(lvOTrnErrS);
    }

    @Test
    public void commissionChartCodeDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "RTE";
        String prpIdn = "RTE_VAL";
        String errIdnVal = "20001: TARIFA Tarifa CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "rteVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal rteVal = new BigDecimal(9999);

        when(iDlRteDAO.count(cmpVal, lngVal, rteVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldRte.vldRteVal(cmpVal, lngVal, rteVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
