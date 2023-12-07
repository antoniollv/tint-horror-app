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
import com.mapfre.tron.api.cmn.gpp.dl.DlGppDAOImpl;
import com.mapfre.tron.api.cmn.gpp.dl.IDlGppDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldGpp;
import com.mapfre.tron.api.vld.impl.TwVldGppImpl;

/**
 * The account validator JUnit test.
 *
 * @author arquitectura - majoguam
 * @since 1.8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldGppTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlGppDAO iDlGppDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldGpp iVldGpp;

    @Before
    public void setup() {
        iDlGppDAO = mock(DlGppDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldGpp = new TwVldGppImpl(dlTrnErr, iDlGppDAO);
    }

    @Test
    public void accountLineOfBusinessExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String gppVal = "1";


        

        when(iDlGppDAO.count(cmpVal, lngVal, gppVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldGpp.vldGppVal(cmpVal, lngVal, gppVal);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void accountLineOfBusinessDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String gppVal = "1";


        String temVal = "GPP";
        String prpIdn = "GPP_VAL";
        String errIdnVal = "20001: POLIZA GRUPO Poliza Grupo CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "gppVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        when(iDlGppDAO.count(cmpVal, lngVal, gppVal)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldGpp.vldGppVal(cmpVal, lngVal, gppVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
