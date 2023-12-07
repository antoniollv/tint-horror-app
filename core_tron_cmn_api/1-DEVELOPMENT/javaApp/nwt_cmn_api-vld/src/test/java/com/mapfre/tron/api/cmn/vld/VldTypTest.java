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
import com.mapfre.tron.api.cmn.typ.dl.DlTypDAOImpl;
import com.mapfre.tron.api.cmn.typ.dl.IDlTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldTyp;
import com.mapfre.tron.api.vld.impl.TwVldTypImpl;

/**
 * The coverage validation JUnit test.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldTypTest {

	/** The newtron nof found code. */
	private static final int ERROR_CODE = 20001;

	/** The authorization repository. */
	private IDlTypDAO iDlTypDAO;

	/** The error repository. */
	private DlTrnErr dlTrnErr;

	/** The component to test. */
	private IVldTyp iVldTyp;

	@Before
	public void setup() {
		iDlTypDAO = mock(DlTypDAOImpl.class);
		dlTrnErr = mock(DlTrnErrImpl.class);

		iVldTyp = new TwVldTypImpl(dlTrnErr, iDlTypDAO);
	}

	@Test
	public void coverageExits() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));
		BigDecimal lobVal = new BigDecimal(300);
		String valVal = "1";

		when(iDlTypDAO.count(cmpVal, lngVal, lobVal, valVal)).thenReturn(1);

		OTrnErrS lvOTrnErrS = iVldTyp.vldTyp(cmpVal, lngVal, lobVal, valVal);
		assertNull(lvOTrnErrS);
	}

	@Test
	public void coverageDoesNotExit() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));

		String temVal = "TYP";
		String prpIdn = "ISU_TYP_VAL";
		String errIdnVal = "20001: COBERTURA Cobertura CODIGO INEXISTENTE";
		String errNam = "CODIGO INEXISTENTE";
		String prpNam = "sbmVal";
		when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal))
				.thenReturn(new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

		String valVal = "1";
		BigDecimal lobVal = new BigDecimal(300);

		when(iDlTypDAO.count(cmpVal, lngVal, lobVal, valVal)).thenReturn(0);

		OTrnErrS lvOTrnErrS = iVldTyp.vldTyp(cmpVal, lngVal, lobVal, valVal);
		assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
		assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
		assertEquals(errNam, lvOTrnErrS.getErrNam());
		assertEquals(prpNam, lvOTrnErrS.getPrpNam());
	}

	@Test
	public void typeDocumentExits() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));
		String thpDcmTypVal = "DNI";

		when(iDlTypDAO.countDoc(cmpVal, lngVal, thpDcmTypVal)).thenReturn(1);

		OTrnErrS lvOTrnErrS = iVldTyp.vldTypDoc(cmpVal, lngVal, thpDcmTypVal);
		assertNull(lvOTrnErrS);
	}

	@Test
	public void typeDocumentDoesNotExit() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));
		String thpDcmTypVal = "DNI";

		String temVal = "TYP";
		String prpIdn = "THP_DCM_TYP_VAL";
		String errIdnVal = "20001: TIPO Tipo del Documento del Tercero CODIGO INEXISTENTE";
		String errNam = "CODIGO INEXISTENTE";
		String prpNam = "sbmVal";
		when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal))
				.thenReturn(new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

		when(iDlTypDAO.countDoc(cmpVal, lngVal, thpDcmTypVal)).thenReturn(0);

		OTrnErrS lvOTrnErrS = iVldTyp.vldTypDoc(cmpVal, lngVal, thpDcmTypVal);
		assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
		assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
		assertEquals(errNam, lvOTrnErrS.getErrNam());
		assertEquals(prpNam, lvOTrnErrS.getPrpNam());
	}
	
	
	
	@Test
	public void typeEnrExits() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));
        String valVal = "AA";
        BigDecimal lobVal = new BigDecimal(999);

		when(iDlTypDAO.countEnr(cmpVal, lngVal, lobVal, valVal)).thenReturn(1);

		OTrnErrS lvOTrnErrS = iVldTyp.vldEnrTypVal(cmpVal, lngVal, lobVal, valVal);
		assertNull(lvOTrnErrS);
	}

	@Test
	public void typeEnrDoesNotExit() throws Exception {
		String lngVal = "ES";
		BigDecimal cmpVal = new BigDecimal((1));
        String valVal = "AA";
        BigDecimal lobVal = new BigDecimal(999);

		String temVal = "TYP";
		String prpIdn = "ENR_TYP_VAL";
		String errIdnVal = "20001: TIPO Tipo de Suplemento CODIGO INEXISTENTE";
		String errNam = "CODIGO INEXISTENTE";
		String prpNam = "enrTypVal";
		when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal))
				.thenReturn(new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

		when(iDlTypDAO.countEnr(cmpVal, lngVal, lobVal, valVal)).thenReturn(0);

		OTrnErrS lvOTrnErrS = iVldTyp.vldEnrTypVal(cmpVal, lngVal, lobVal, valVal);
		assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
		assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
		assertEquals(errNam, lvOTrnErrS.getErrNam());
		assertEquals(prpNam, lvOTrnErrS.getPrpNam());
	}


}
