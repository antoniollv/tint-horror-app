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
import com.mapfre.tron.api.cmn.dcm.dl.DlDcmDAOImpl;
import com.mapfre.tron.api.dcm.fld.dl.IDlDcmDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldDcm;
import com.mapfre.tron.api.vld.impl.TwVldDcmImpl;

/**
 * The document validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 16 jul. 2021 - 14:10:16
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldDcmTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The commission chart repository.*/
    private IDlDcmDAO iDlDcmDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldDcm iVldDcm;

    @Before
    public void setup() {
        iDlDcmDAO = mock(DlDcmDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldDcm = new TwVldDcmImpl(dlTrnErr, iDlDcmDAO);
    }


    @Test
    public void thirdPersonDocumentNumberCodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        String thpDcmVal = "1";
        String thpDcmTypVal = "DNI";

        when(iDlDcmDAO.count(cmpVal, lngVal, thpDcmVal, thpDcmTypVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldDcm.vldThpDcmVal(cmpVal, lngVal, thpDcmVal, thpDcmTypVal);

        assertNull(lvOTrnErrS);
    }

    @Test
    public void thirdPersonDocumentNumberDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "DCM";
        String prpIdn = "THP_DCM_VAL";
        String errIdnVal = "20001: DOCUMENTO Documento CODIGO INEXISTENTE, errVal=20001, errNam=CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "thpDcmVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        String thpDcmVal = "99999999999";
        String thpDcmTypVal = "DNI";
        when(iDlDcmDAO.count(cmpVal, lngVal, thpDcmVal, thpDcmTypVal)).thenReturn(0);


        OTrnErrS lvOTrnErrS = iVldDcm.vldThpDcmVal(cmpVal, lngVal, thpDcmVal, thpDcmTypVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
