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
import com.mapfre.tron.api.cmn.enr.dl.IDlEnrDAO;
import com.mapfre.tron.api.comn.enr.dl.DlEnrDAOImpl;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldEnr;
import com.mapfre.tron.api.vld.impl.TwVldEnrImpl;

/**
 * The account validator JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 12 jul. 2021 - 9:59:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldEnrTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlEnrDAO iDlEnrDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldEnr iVldEnr;

    @Before
    public void setup() {
        iDlEnrDAO = mock(DlEnrDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldEnr = new TwVldEnrImpl(dlTrnErr, iDlEnrDAO);
    }

    @Test
    public void accountLineOfBusinessExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal enrVal = new BigDecimal(100);
        BigDecimal enrSbdVal = new BigDecimal(1);
        BigDecimal enrScoTypVal = new BigDecimal(3);
        

        

        when(iDlEnrDAO.count(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldEnr.vldEnrVal(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void accountLineOfBusinessDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal enrVal = new BigDecimal(100);
        BigDecimal enrSbdVal = new BigDecimal(1);
        BigDecimal enrScoTypVal = new BigDecimal(3);


        String temVal = "ENR";
        String prpIdn = "ENR_VAL";
        String errIdnVal = "20001: SUPLEMENTO Codigo de Suplemento CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "enrVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlEnrDAO.count(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldEnr.vldEnrVal(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

    @Test
    public void endorsementSubcodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal enrVal = new BigDecimal(100);
        BigDecimal enrSbdVal = new BigDecimal(1);
        String enrScoTypVal = "3";
        String temVal = "SBD";

        when(iDlEnrDAO.count(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal, temVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldEnr.vldEnrSbdVal(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void endorsementSubcodeDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal enrVal = new BigDecimal(100);
        BigDecimal enrSbdVal = new BigDecimal(1);
        String enrScoTypVal = "3";
        String temVal = "SBD";
        String prpIdn = "ENR_SBD_VAL";
        String errIdnVal = "20001: SUBCODIGO Suplemento CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "enrSbdVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlEnrDAO.count(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal, temVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldEnr.vldEnrSbdVal(cmpVal, lngVal, enrVal, enrSbdVal, enrScoTypVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }
}
