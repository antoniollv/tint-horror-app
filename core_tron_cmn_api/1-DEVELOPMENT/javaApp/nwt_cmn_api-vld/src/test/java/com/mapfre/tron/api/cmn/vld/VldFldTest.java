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
import com.mapfre.tron.api.cmn.fld.dl.DlFldDAOImpl;
import com.mapfre.tron.api.cmn.fld.dl.IDlFldDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldFld;
import com.mapfre.tron.api.vld.impl.TwVldFldImpl;

/**
 * The field validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 13 jul. 2021 - 9:03:00
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldFldTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The account repository.*/
    private IDlFldDAO iDlFldDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldFld iVldFld;

    @Before
    public void setup() {
        iDlFldDAO = mock(DlFldDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldFld = new TwVldFldImpl(dlTrnErr, iDlFldDAO);
    }

    @Test
    public void fieldNameExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(300);
        BigDecimal mdtVal = new BigDecimal(99999);
        String fldNam = "COD_MARCA";

        when(iDlFldDAO.count(cmpVal, lngVal, lobVal, mdtVal, fldNam)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldFld.vldFldVal(cmpVal, lngVal, lobVal, mdtVal, fldNam);
        assertNull(lvOTrnErrS);
    }

    @Test
    public void fieldNameDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "FLD";
        String prpIdn = "FLD_NAM";
        String errIdnVal = "20001: CAMPO Codigo De Campo CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "fldNam";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal lobVal = new BigDecimal(300);
        BigDecimal mdtVal = new BigDecimal(99999);
        String fldNam = "NON_EXISTS";
        when(iDlFldDAO.count(cmpVal, lngVal, lobVal, mdtVal, fldNam)).thenReturn(0);

        OTrnErrS lvOTrnErrS = iVldFld.vldFldVal(cmpVal, lngVal, lobVal, mdtVal, fldNam);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
