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
import com.mapfre.tron.api.cmn.thp.dl.DlThpAcvDAOImpl;
import com.mapfre.tron.api.cmn.thp.dl.IDlThpAcvDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldThp;
import com.mapfre.tron.api.vld.impl.TwVldThpImpl;

/**
 * The thirdpart validation junit test class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 6 jul. 2021 - 10:38:19
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldThpTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The thirdpart respository.*/
    private IDlThpAcvDAO iDlThpAcvDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldThp iVldThp;

    @Before
    public void setup() {
        iDlThpAcvDAO = mock(DlThpAcvDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldThp = new TwVldThpImpl(iDlThpAcvDAO, dlTrnErr);
    }

    @Test
    public void thirpartActivityValueExits() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpAcvVal = new BigDecimal(1);

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpAcvVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpAcvVal(cmpVal, lngVal, thpAcvVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirpartActivityValueDoesNotExit() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpAcvVal = new BigDecimal(999);

        String temVal = "ACV";
        String prpIdn = "THP_ACV_VAL";
        String errIdnVal = "20001: ACTIVIDAD Actividad Tercero CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thpAcvVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpAcvVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpAcvVal(cmpVal, lngVal, thpAcvVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

    @Test
    public void thirpartAgentValueExits() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(1);
        Date vldDat = new Date();

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, vldDat)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpVal(cmpVal, lngVal, thpVal, vldDat);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirpartAgentValueDoesNotExit() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(7777777);
        Date vldDat = new Date();

        String temVal = "AGT";
        String prpIdn = "THP_VAL";
        String errIdnVal = "20001: TIPO AGENTE Codigo de Tercero CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thpVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, vldDat)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpVal(cmpVal, lngVal, thpVal, vldDat);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

    @Test
    public void thirpartValueExits() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(1);
        BigDecimal thpAcvVal = new BigDecimal(1);

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, thpAcvVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpVal(cmpVal, lngVal, thpVal, thpAcvVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirpartValueDoesNotExit() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(1);
        BigDecimal thpAcvVal = new BigDecimal(9999);

        String temVal = "THP";
        String prpIdn = "THP_VAL";
        String errIdnVal = "20001: TERCERO Codigo de Tercero CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thpVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, thpAcvVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpVal(cmpVal, lngVal, thpVal, thpAcvVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }
    
    
    
    
    
    @Test
    public void thirpartValuesExits() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(1);

        when(iDlThpAcvDAO.countTramitador(cmpVal, lngVal, thpVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpValTramitador(cmpVal, lngVal, thpVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirpartValuesDoesNotExit() throws Exception {

        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal thpVal = new BigDecimal(1);

        String temVal = "THP";
        String prpIdn = "THP_VAL";
        String errIdnVal = "20001: TERCERO Codigo de Tercero CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thpVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlThpAcvDAO.countTramitador(cmpVal, lngVal, thpVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldThp.vldThpValTramitador(cmpVal, lngVal, thpVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }
    
    

}
