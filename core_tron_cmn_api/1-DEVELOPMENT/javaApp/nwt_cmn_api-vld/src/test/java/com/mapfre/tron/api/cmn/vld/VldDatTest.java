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
import com.mapfre.tron.api.cmn.dat.dl.DlDatDAOImpl;
import com.mapfre.tron.api.cmn.dat.dl.IDlDatDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldDat;
import com.mapfre.tron.api.vld.impl.TwVldDatImpl;

/**
 * The date validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 23 jul. 2021 - 10:44:57
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldDatTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlDatDAO iDlDatDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldDat iVldDat;

    @Before
    public void setup() {
        iDlDatDAO = mock(DlDatDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldDat = new TwVldDatImpl(dlTrnErr, iDlDatDAO);
    }

    @Test
    public void accountLineOfBusinessExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(300);
        BigDecimal cvrVal = new BigDecimal(1030);
        BigDecimal mdtVal = new BigDecimal(99999);
        Date vldDat = new Date();

        when(iDlDatDAO.count(cmpVal, lngVal, lobVal, cvrVal, mdtVal, vldDat)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldDat.vldDat(cmpVal, lngVal, lobVal, cvrVal, mdtVal, vldDat);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void accountLineOfBusinessDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(300);
        BigDecimal cvrVal = new BigDecimal(1030);
        BigDecimal mdtVal = new BigDecimal(777777);
        Date vldDat = new Date();

        String temVal = "DAT";
        String prpIdn = "VLD_DAT";
        String errIdnVal = "20001: FECHA Fecha Validez CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "vldDat";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal acgLobVal = new BigDecimal(99999);
        when(iDlDatDAO.count(cmpVal, lngVal, lobVal, cvrVal, mdtVal, vldDat)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldDat.vldDat(cmpVal, lngVal, lobVal, cvrVal, mdtVal, vldDat);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
