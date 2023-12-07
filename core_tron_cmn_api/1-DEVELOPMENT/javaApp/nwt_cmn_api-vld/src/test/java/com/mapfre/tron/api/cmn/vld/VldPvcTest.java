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
import com.mapfre.tron.api.cmn.pvc.dl.DlPvcDAOImpl;
import com.mapfre.tron.api.cmn.pvc.dl.IDlPvcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldPvc;
import com.mapfre.tron.api.vld.impl.TwVldPvcImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 9 jul. 2021 - 9:07:21
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldPvcTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository. */
    private IDlPvcDAO iDlPvcDAO;

    /** The error repository. */
    private DlTrnErr dlTrnErr;

    /** The component to test. */
    private IVldPvc iVldPvc;

    @Before
    public void setup() {
	iDlPvcDAO = mock(DlPvcDAOImpl.class);
	dlTrnErr = mock(DlTrnErrImpl.class);

	iVldPvc = new TwVldPvcImpl(dlTrnErr, iDlPvcDAO);
    }

    @Test
    public void coverageExits() throws Exception {
	String lngVal = "ES";
	BigDecimal cmpVal = new BigDecimal((1));
	String cnyVal = "ESP";
	BigDecimal pvcVal = new BigDecimal(20);

	when(iDlPvcDAO.count(cmpVal, lngVal, pvcVal, cnyVal)).thenReturn(1);

	OTrnErrS lvOTrnErrS = iVldPvc.vldPvc(cmpVal, lngVal, pvcVal, cnyVal);
	assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
	String lngVal = "ES";
	BigDecimal cmpVal = new BigDecimal((1));

	String temVal = "PVC";
	String prpIdn = "PVC_VAL";
	String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
	String errNam = "CODIGO INEXISTENTE";
	String prpNam = "pvcVal";
	when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal))
		.thenReturn(new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

	String cnyVal = "ESP";
	BigDecimal pvcVal = new BigDecimal(20);
	when(iDlPvcDAO.count(cmpVal, lngVal, pvcVal, cnyVal)).thenReturn(0);

	OTrnErrS lvOTrnErrS = iVldPvc.vldPvc(cmpVal, lngVal, pvcVal, cnyVal);
	assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
	assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
	assertEquals(errNam, lvOTrnErrS.getErrNam());
	assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
