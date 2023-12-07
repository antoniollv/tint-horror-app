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
import com.mapfre.tron.api.cmn.dct.dl.DlDctDAOImpl;
import com.mapfre.tron.api.cmn.dct.dl.IDlDctDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldDct;
import com.mapfre.tron.api.vld.impl.TwVldDctImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 27 Jul 2021 - 09:33:29
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldDctTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The authorization repository. */
    private IDlDctDAO iDlDctDAO;

    /** The error repository. */
    private DlTrnErr dlTrnErr;

    /** The component to test. */
    private IVldDct iVldDct;

    @Before
    public void setup() {
	iDlDctDAO = mock(DlDctDAOImpl.class);
	dlTrnErr = mock(DlTrnErrImpl.class);

	iVldDct = new TwVldDctImpl(dlTrnErr, iDlDctDAO);
    }

    @Test
    public void coverageExits() throws Exception {
	String lngVal = "ES";
	BigDecimal cmpVal = new BigDecimal((1));
	BigDecimal dctVal = new BigDecimal((1));
	BigDecimal crnVal = new BigDecimal((1));
	BigDecimal dctTypVal = new BigDecimal((1));

	when(iDlDctDAO.count(cmpVal, lngVal, dctVal, crnVal, dctTypVal)).thenReturn(1);

	OTrnErrS lvOTrnErrS = iVldDct.vldDct(cmpVal, lngVal, dctVal, crnVal, dctTypVal);
	assertNull(lvOTrnErrS);
    }

    @Test
    public void coverageDoesNotExit() throws Exception {
	String lngVal = "ES";
	BigDecimal cmpVal = new BigDecimal((1));

	String temVal = "DCT";
	String prpIdn = "DCT_TYP_VAL";
	String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
	String errNam = "CODIGO INEXISTENTE";
	String prpNam = "dctVal";
	when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal))
		.thenReturn(new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

	BigDecimal dctVal = new BigDecimal((1));
	BigDecimal crnVal = new BigDecimal((1));
	BigDecimal dctTypVal = new BigDecimal((1));
	
	when(iDlDctDAO.count(cmpVal, lngVal, dctVal, crnVal, dctTypVal)).thenReturn(0);

	OTrnErrS lvOTrnErrS = iVldDct.vldDct(cmpVal, lngVal, dctVal, crnVal, dctTypVal);
	assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
	assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
	assertEquals(errNam, lvOTrnErrS.getErrNam());
	assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
