package com.mapfre.tron.api.cmn.vld;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.vld.dl.DlZonesDAOImpl;
import com.mapfre.tron.api.cmn.vld.dl.IDlZonesDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldZones;
import com.mapfre.tron.api.vld.impl.TwVldZonesImpl;

/**
 * The zones validation junit test class.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 5 Jul 2021 - 12:39:46
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldZonesTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The zone repository.*/
    private IDlZonesDAO iDlZonesDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldZones iVldZones;

    @Before
    public void setup() {
        iDlZonesDAO = mock(DlZonesDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldZones = new TwVldZonesImpl(iDlZonesDAO, dlTrnErr);
    }

    @Test
    public void zoneFourExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String cnyVal = "ESP";
        BigDecimal pslCodVal = new BigDecimal(11110);
        BigDecimal twnVal = new BigDecimal(100);

        when(iDlZonesDAO.count(cmpVal, lngVal, cnyVal, pslCodVal, twnVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldZones.vldZoneFour(cmpVal, lngVal, cnyVal, pslCodVal, twnVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void zoneFourDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "PSL";
        String prpIdn = "COD_POSTAL";
        String errIdnVal = "20001: POSTAL --nd-- CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "codPostal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String cnyVal = "ESP";
        BigDecimal pslCodVal = new BigDecimal(11110);
        BigDecimal twnVal = new BigDecimal(999);
        when(iDlZonesDAO.count(cmpVal, lngVal, cnyVal, pslCodVal, twnVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldZones.vldZoneFour(cmpVal, lngVal, cnyVal, pslCodVal, twnVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
