package com.mapfre.tron.api.trn.err.dl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mapfre.nwt.trn.gls.bo.OTrnGlsTxtS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.tron.api.trn.err.dl.impl.DlTrnErrImpl;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsDao;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsPK;
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpDao;
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpPK;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("Pruebas clase gestión de errores")
public class GestionErroresTest {


    @Mock private OTrnErrDao descripcionesError;
    @Mock private OTrnGlsDao glosarioDescripciones;
    @Mock private OTrnPrpDao descripcionesCampo;
    @InjectMocks private DlTrnErrImpl escaparateErrores;
    
    @Test
    @DisplayName("Llamar a gestion de errores con todos los datos")
    public void givenAllParamsWhenGetObjectErrorThenObjectErrorIsOk() {
	// GIVEN
	OTrnGlsS descripcionGlosario = new OTrnGlsS();
	descripcionGlosario.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null, null, "Descripcion de glosario"))));
	OTrnGlsS descripcionCampo = new OTrnGlsS();
	descripcionCampo.setPrpIdn("PROPIEDAD1");
	descripcionCampo.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null, null, "Descripcion de propiedad"))));
	
	// WHEN
	when(descripcionesError.get(any(OTrnErrPK.class))).thenReturn(new OTrnErrS(null, null, new BigDecimal(20001), "Descripcion de Error"));
	when(glosarioDescripciones.get(any(OTrnGlsPK.class))).thenReturn(descripcionGlosario);
	when(descripcionesCampo.get(any(OTrnPrpPK.class))).thenReturn(descripcionCampo);
    
	OTrnErrS objetoError = escaparateErrores.getError(new BigDecimal(1), "ES", "GNI", "CMP_VAL", null);
	// THEN
	assertEquals("20001: Descripcion de glosario Descripcion de propiedad Descripcion de Error", objetoError.getErrIdnVal());
	assertEquals("PROPIEDAD1", objetoError.getPrpNam());
	
    }
    
    @Test
    @DisplayName("Llamar a gestion de errores sin nombre del termino")
    public void givenThreeParamsWhenGetObjectErrorThenObjectErrorIsOk() {
	// GIVEN
	OTrnGlsS descripcionGlosario = new OTrnGlsS();
	descripcionGlosario.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null, null, null))));
	OTrnGlsS descripcionCampo = new OTrnGlsS();
	descripcionCampo.setPrpIdn("PROPIEDAD1");
	descripcionCampo.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null, null, "Descripcion de propiedad"))));
	
	// WHEN
	when(descripcionesError.get(any(OTrnErrPK.class))).thenReturn(new OTrnErrS(null, null, new BigDecimal(20001), "Descripcion de Error"));
	when(glosarioDescripciones.get(any(OTrnGlsPK.class))).thenReturn(descripcionGlosario);
	when(descripcionesCampo.get(any(OTrnPrpPK.class))).thenReturn(descripcionCampo);
    
	OTrnErrS objetoError = escaparateErrores.getErrorWithoutTemVal(new BigDecimal(1), "ES", "CMP_VAL", null);
	// THEN
	assertEquals("20001: Descripcion de propiedad Descripcion de Error", objetoError.getErrIdnVal());
	assertEquals("PROPIEDAD1", objetoError.getPrpNam());
	
    }
    
    @Test
    @DisplayName("Llamar a gestion de errores sin propiedad")
    public void givenThreeParamsWhenGetObjectErrorThenErrIdnValIsOk() {
	// GIVEN
	OTrnGlsS descripcionGlosario = new OTrnGlsS();
	descripcionGlosario.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null, null, "Descripcion de glosario"))));
	OTrnGlsS descripcionCampo = new OTrnGlsS();
	
	// WHEN
	when(descripcionesError.get(any(OTrnErrPK.class))).thenReturn(new OTrnErrS(null, null, new BigDecimal(20001), "Descripcion de Error"));
	when(glosarioDescripciones.get(any(OTrnGlsPK.class))).thenReturn(descripcionGlosario);
	when(descripcionesCampo.get(any(OTrnPrpPK.class))).thenReturn(descripcionCampo);
    
	OTrnErrS objetoError = escaparateErrores.getErrorWithoutPrpIdn(new BigDecimal(1), "ES", "GNI", null);
	// THEN
	assertEquals("20001: Descripcion de glosario Descripcion de Error", objetoError.getErrIdnVal());
    }
    
    @Test
    @DisplayName("Llamar a gestion de errores con datos obligatorios")
    public void givenTwoParamsWhenGetObjectErrorThenObjectErrorIsOk() {
	// GIVEN
	OTrnGlsS descripcionGlosario = new OTrnGlsS();
	OTrnGlsS descripcionCampo = new OTrnGlsS();
	
	// WHEN
	when(descripcionesError.get(any(OTrnErrPK.class))).thenReturn(new OTrnErrS(null, null, new BigDecimal(20001), "Descripcion de Error"));
	when(glosarioDescripciones.get(any(OTrnGlsPK.class))).thenReturn(descripcionGlosario);
	when(descripcionesCampo.get(any(OTrnPrpPK.class))).thenReturn(descripcionCampo);
    
	OTrnErrS objetoError = escaparateErrores.getError(new BigDecimal(1), "ES", null);
	// THEN
	assertEquals("20001: Descripcion de Error", objetoError.getErrIdnVal());
    }

}
