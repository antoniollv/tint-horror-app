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
import com.mapfre.tron.api.cmn.cmc.dl.DlCmcDAOImpl;
import com.mapfre.tron.api.cmn.cmc.dl.IDlCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.vld.api.IVldCmc;
import com.mapfre.tron.api.vld.impl.TwVldCmcImpl;

/**
 * The commission chart validation JUnit test.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 jul. 2021 - 14:58:08
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VldCmcTest {

    /** The newtron nof found code. */
    private static final int ERROR_CODE = 20001;

    /** The commission chart repository.*/
    private IDlCmcDAO iDlCmcDAO;

    /** The error repository.*/
    private DlTrnErr dlTrnErr;

    /** The component to test.*/
    private IVldCmc iVldCmc;

    @Before
    public void setup() {
        iDlCmcDAO = mock(DlCmcDAOImpl.class);
        dlTrnErr = mock(DlTrnErrImpl.class);

        iVldCmc = new TwVldCmcImpl(dlTrnErr, iDlCmcDAO);
    }

    @Test
    public void commissionChartCodeExits() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));
        BigDecimal lobVal = new BigDecimal(300);
        Date vldDat = new Date();
        BigDecimal thpVal = null;
        BigDecimal cmcVal = new BigDecimal(1);
        String frsDstHnlVal = null;
        String scnDstHnlVal = null;
        String thrDstHnlVal = null;
        BigDecimal frsLvlVal = null;
        BigDecimal scnLvlVal = null;
        BigDecimal thrLvlVal = null;

        when(iDlCmcDAO.count(cmpVal, lngVal, lobVal, vldDat, thpVal, cmcVal, frsDstHnlVal, scnDstHnlVal, thrDstHnlVal,
                frsLvlVal, scnLvlVal, thrLvlVal)).thenReturn(1);

        OTrnErrS lvOTrnErrS = iVldCmc.vldFldVal(cmpVal, lngVal, lobVal, vldDat, thpVal, cmcVal, frsDstHnlVal,
                scnDstHnlVal, thrDstHnlVal, frsLvlVal, scnLvlVal, thrLvlVal);

        assertNull(lvOTrnErrS);
    }

    @Test
    public void commissionChartCodeDoesNotExit() throws Exception {
        String lngVal = "ES";
        BigDecimal cmpVal = new BigDecimal((1));

        String temVal = "CMC";
        String prpIdn = "CMC_VAL";
        String errIdnVal = "20001: CUADRO COMISION Cuadro Comision CODIGO INEXISTENTE";
        String errNam = "CODIGO INEXISTENTE";
        String prpNam = "cmcVal";
        when(dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, temVal, prpIdn, cmpVal)).thenReturn(
                new OTrnErrS(null, errIdnVal, new BigDecimal(ERROR_CODE), errNam, prpNam));

        BigDecimal lobVal = new BigDecimal(300);
        Date vldDat = new Date();
        BigDecimal thpVal = null;
        BigDecimal cmcVal = new BigDecimal(999);
        String frsDstHnlVal = null;
        String scnDstHnlVal = null;
        String thrDstHnlVal = null;
        BigDecimal frsLvlVal = null;
        BigDecimal scnLvlVal = null;
        BigDecimal thrLvlVal = null;

        when(iDlCmcDAO.count(cmpVal, lngVal, lobVal, vldDat, thpVal, cmcVal, frsDstHnlVal, scnDstHnlVal, thrDstHnlVal,
                frsLvlVal, scnLvlVal, thrLvlVal)).thenReturn(0);


        OTrnErrS lvOTrnErrS = iVldCmc.vldFldVal(cmpVal, lngVal, lobVal, vldDat, thpVal, cmcVal, frsDstHnlVal,
                scnDstHnlVal, thrDstHnlVal, frsLvlVal, scnLvlVal, thrLvlVal);
        assertEquals(errIdnVal, lvOTrnErrS.getErrIdnVal());
        assertEquals(ERROR_CODE, lvOTrnErrS.getErrVal().intValue());
        assertEquals(errNam, lvOTrnErrS.getErrNam());
        assertEquals(prpNam, lvOTrnErrS.getPrpNam());
    }

}
