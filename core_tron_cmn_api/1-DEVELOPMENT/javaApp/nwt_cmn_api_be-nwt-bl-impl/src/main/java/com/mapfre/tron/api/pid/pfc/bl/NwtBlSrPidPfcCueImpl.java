package com.mapfre.tron.api.pid.pfc.bl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mapfre.nwt.annotation.NwtService;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.sr.ISrCrnCrnQry;
import com.mapfre.nwt.trn.pid.ivf.bo.OPidIvfP;
import com.mapfre.nwt.trn.pid.pfc.bo.OPidPfcP;
import com.mapfre.nwt.trn.pid.pfm.bo.OPidPfmP;
import com.mapfre.nwt.trn.pid.psp.bo.OPidPspP;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;

import lombok.Data;


@NwtService
@Data
public class NwtBlSrPidPfcCueImpl implements IBlPidPfcCue {

	@Autowired
	ISrCrnCrnQry srCrnCrnQry;
	
	protected final RestTemplate rs = new RestTemplate();
    protected final JsonParser jp = new JsonParser();
    
    @Value("${app.env.infocestas.url}")
    protected String infocestasUrl;
    
    @Value("${app.env.infocestas.username}")
    protected String infocestasUsername;
    
    @Value("${app.env.infocestas.password}")
    protected String infocestasPassword;
	
	@Override
	public OPidPfcEitDto getPfcSle(OTrnCnxS pmOTrnCnxS) throws MalformedURLException, ParseException {
		
		OPidPfcEitDto lvOPidPfcEitDto = new OPidPfcEitDto();
		// fondo inversión conjunto
		List<OPidIvfP> lvOPidIvfPT;
		// cesta fondo composicion conjunto
		List<OPidPfcP> lvOPidPfcPT;
		// cesta fondo distribucion conjunto
		List<OPidPspP> lvOPidPspPT;
		// cesta fondo documento conjunto
		List<OPidPfmP> lvOPidPfmPT;
	    // fondo inversión
	    OPidIvfP lvOPidIvfP;
	    // cesta fondo composicion
	    OPidPfcP lvOPidPfcP;
	    // cesta fondo distribucion
	    OPidPspP lvOPidPspP;
		// cesta fondo documento
		OPidPfmP lvOPidPfmP;
	    // moneda
	    OCrnCrnP lvOCrnCrnP;
	    ResponseEntity<String> lvRestResponse;
	    HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
		String plainCreds = infocestasUsername.trim() + ":" + infocestasPassword.trim();
        byte[] plainCredsBytes = plainCreds.getBytes();
        String base64Creds = Base64.getEncoder().encodeToString(plainCredsBytes);
        String auth = "Basic " + base64Creds;
        // set headers       
        headers.add("Authorization", auth);
        HttpEntity<String> request = new HttpEntity<>(headers);
		
		// Realizamos llamada al Servicio rest
		try {
		    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(infocestasUrl);
		    lvRestResponse = rs.exchange(builder.build().toUriString() ,HttpMethod.POST, request, String.class);
		    JsonArray arrayAttributes = jp.parse(lvRestResponse.getBody()).getAsJsonObject().getAsJsonObject("respuesta").getAsJsonArray("Cestas");
		    
		    // Inicializacion de objetos locales necesarios para el tratamiento del JSON recuperado en el servicio REST
		    lvOPidIvfPT = new ArrayList<>();
		    lvOPidPfcPT = new ArrayList<>();
		    lvOPidPspPT = new ArrayList<>();
		    lvOPidPfmPT =  new ArrayList<>();
		    
		    // Si la llamada nos ha devuelto un 200 significa que estaba bien la llamada 
			if (arrayAttributes.size() > 0 && lvRestResponse.getStatusCode().equals(HttpStatus.OK)) {
				// Mientras la estructura devuelta tenga valor
				for (int i = 0; i < arrayAttributes.size(); i++) {
					lvOPidIvfP = new OPidIvfP();
					lvOCrnCrnP = new OCrnCrnP();	
					
					// se extraen los arrays que se va a tratar
				    JsonArray arrayDocCesta = arrayAttributes.get(i).getAsJsonObject().getAsJsonArray("DocCesta");
				    JsonArray arrayComposicion = arrayAttributes.get(i).getAsJsonObject().getAsJsonArray("Composicion");
				    JsonArray arrayDistAreas = arrayAttributes.get(i).getAsJsonObject().getAsJsonArray("DistribucionesAreasGeograficas");
				    JsonArray arrayDistSec = arrayAttributes.get(i).getAsJsonObject().getAsJsonArray("DistribucionesSectores");
				    JsonArray arrayDistTipAct = arrayAttributes.get(i).getAsJsonObject().getAsJsonArray("DistribucionesTiposActivos");
				    
					// creamos una fecha tipo Date a partir del string obtenido
			    	String stringDate = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("UltimaFechaDisponible").getAsString();
			    	String pattern = "yyyyMMdd";
			    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			    	Date lvVldDat = simpleDateFormat.parse(stringDate);
			    	simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
			    	Date lvIvfInlDat = simpleDateFormat.parse("01/01/1990");
			    	Date lvIvfExpDat = simpleDateFormat.parse("31/12/2099");
			    	
			    	
			    	// Se obtiene la moneda
			    	String lvSrdCrnVal = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("DivisaMoneda").getAsString();
			    	// CMN-2916 - OFRECER CONSULTAR moneda por moneda estandar
			    	lvOCrnCrnP = srCrnCrnQry.srdCrnVal(lvSrdCrnVal,
			    									   pmOTrnCnxS.getLngVal(), pmOTrnCnxS.getCmpVal());
			    	
			    	String lvIvfVal = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("CodCesta").getAsString();
			    	String lvIvfNam = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("NombreCesta").getAsString();
			    	String lvIvfPflTypVal = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("PerfilInversionCesta").getAsString();
			    	String lvAciIvfTypVal = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("TipoActivo").getAsString();
			    	
			    	// Asignar a objeto fondo inversión definicion conjunto
			    	lvOPidIvfP.getOPidIvfS().setCmpVal(pmOTrnCnxS.getCmpVal());
			    	lvOPidIvfP.getOPidIvfS().setIvfVal(lvIvfVal);
			    	lvOPidIvfP.getOPidIvfS().setIvfNam(lvIvfNam);
			    	lvOPidIvfP.getOPidIvfS().setVldDat(lvVldDat);
			    	lvOPidIvfP.getOPidIvfS().setIvfInlDat(lvIvfInlDat);
			    	lvOPidIvfP.getOPidIvfS().setIvfExpDat(lvIvfExpDat);
			    	lvOPidIvfP.getOPidIvfS().setUsrVal(pmOTrnCnxS.getUsrVal());
			    	lvOPidIvfP.getOPidIvfS().setMdfDat(new Date());
			    	lvOPidIvfP.getOPidIvfS().setCrnVal(lvOCrnCrnP.getOCrnCrnS().getCrnVal());
			    	lvOPidIvfP.getOPidIvfS().setIvfPflTypVal(lvIvfPflTypVal);        
			    	lvOPidIvfP.getOPidIvfS().setAciIvfTypVal(lvAciIvfTypVal);
			    	lvOPidIvfPT.add(lvOPidIvfP);
			    	
			    	// Si estructura DocCesta tiene valor
			    	// Mientras estructura DocCesta [Conjunto] tenga valor
				    for (int x = 0; x < arrayDocCesta.size(); x++) {
				    	lvOPidPfmP = new OPidPfmP();
				    	
				    	lvOPidPfmP.getOPidPfmS().setCmpVal(pmOTrnCnxS.getCmpVal());
				    	lvOPidPfmP.getOPidPfmS().setIvfVal(lvIvfVal);
				    	lvOPidPfmP.getOPidPfmS().setIvfEdtVal(new BigDecimal(1));
				    	lvOPidPfmP.getOPidPfmS().setIvfShtIdn(arrayDocCesta.get(x).getAsJsonObject().getAsJsonPrimitive("IdDocumento").getAsString());
				    	lvOPidPfmP.getOPidPfmS().setIvfShtNam(arrayDocCesta.get(x).getAsJsonObject().getAsJsonPrimitive("NombreDocumento").getAsString());
				    	lvOPidPfmP.getOPidPfmS().setIvfShtAdr(arrayDocCesta.get(x).getAsJsonObject().getAsJsonPrimitive("Documento").getAsString());
				    	lvOPidPfmP.getOPidPfmS().setUsrVal(pmOTrnCnxS.getUsrVal());
				    	lvOPidPfmP.getOPidPfmS().setMdfDat(new Date());
				    	
				    	lvOPidPfmPT.add(lvOPidPfmP);
				    }
				    // Si estructura composición tiene valor
			    	// Mientras estructura Composicion [Conjunto] tenga valor
				    for (int z = 0; z < arrayComposicion.size(); z++) {
				    	lvOPidPfcP = new OPidPfcP();
				    	
				    	// Asignar a cesta fondo composicion conjunto
				    	lvOPidPfcP.getOPidPfcS().setCmpVal(pmOTrnCnxS.getCmpVal());
				    	lvOPidPfcP.getOPidPfcS().setIvfVal(lvIvfVal);
				    	lvOPidPfcP.getOPidPfcS().setIvfEdtVal(new BigDecimal(1));
				    	lvOPidPfcP.getOPidPfcS().setIvfMpoShtAdr(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("FichaFondo").getAsString());
				    	lvOPidPfcP.getOPidPfcS().setMpoIvfVal(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("IdFondo").getAsString());
				    	lvOPidPfcP.getOPidPfcS().setItlScyIdtNbr(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("ISIN").getAsString());
				    	lvOPidPfcP.getOPidPfcS().setMpoIvfNam(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("NombreFondo").getAsString());
				    	String porcentajeFondo = arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("PorcentajeFondo").getAsString();
				    	if(porcentajeFondo!=null && !porcentajeFondo.equals("")) {
					    	lvOPidPfcP.getOPidPfcS().setIvfValPer(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("PorcentajeFondo").getAsBigDecimal());
						} else {
					    	lvOPidPfcP.getOPidPfcS().setIvfValPer(new BigDecimal(0));
				    	}
						String rentaMes = arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("RentaMes").getAsString();
						if(rentaMes != null && !rentaMes.equals("")) {
							lvOPidPfcP.getOPidPfcS().setMnyPfbPerVal(arrayComposicion.get(z).getAsJsonObject().getAsJsonPrimitive("RentaMes").getAsBigDecimal());
						} else {
							lvOPidPfcP.getOPidPfcS().setMnyPfbPerVal(new BigDecimal(0));
						}
				    	lvOPidPfcP.getOPidPfcS().setUsrVal(pmOTrnCnxS.getUsrVal());
				    	lvOPidPfcP.getOPidPfcS().setMdfDat(new Date());
				    	
				    	lvOPidPfcPT.add(lvOPidPfcP);
				    } // Fin Mientras estructura Composicion [Conjunto] tenga valor
				    
				    // Si estructura DistribucionesAreasGeograficas tiene valor
				    // Mientras estructura DistribucionesAreasGeograficas [Conjunto] tenga valor
				    for (int j = 0; j < arrayDistAreas.size(); j++) {
				    	lvOPidPspP = new OPidPspP();
				    	
				    	// Asignar a cesta fondo distribucion conjunto
				    	lvOPidPspP.getOPidPspS().setCmpVal(pmOTrnCnxS.getCmpVal());
				    	lvOPidPspP.getOPidPspS().setIvfVal(lvIvfVal);
				    	lvOPidPspP.getOPidPspS().setIvfEdtVal(new BigDecimal(1));
				    	lvOPidPspP.getOPidPspS().setDstElmVal(arrayDistAreas.get(j).getAsJsonObject().getAsJsonPrimitive("IdArea").getAsString());
				    	lvOPidPspP.getOPidPspS().setDstElmNam(arrayDistAreas.get(j).getAsJsonObject().getAsJsonPrimitive("DescripcionArea").getAsString());
				    	lvOPidPspP.getOPidPspS().setPtfDstTypVal("2");
				    	lvOPidPspP.getOPidPspS().setPtfDstPer(arrayDistAreas.get(j).getAsJsonObject().getAsJsonPrimitive("PorcentajeArea").getAsBigDecimal());
				    	lvOPidPspP.getOPidPspS().setPtfDstDat(new Date());
				    	
				    	lvOPidPspPT.add(lvOPidPspP);
					} // Fin Mientras estructura DistribucionesAreasGeograficas [Conjunto] tenga valor
				    
				    // Se recorre el array de distribuciones por sectores
				    for (int j = 0; j < arrayDistSec.size(); j++) {
				    	lvOPidPspP = new OPidPspP();
				    	
				    	// Asignar a cesta fondo distribucion conjunto
				    	lvOPidPspP.getOPidPspS().setCmpVal(pmOTrnCnxS.getCmpVal());
				    	lvOPidPspP.getOPidPspS().setIvfVal(lvIvfVal);
				    	lvOPidPspP.getOPidPspS().setIvfEdtVal(new BigDecimal(1));
				    	lvOPidPspP.getOPidPspS().setDstElmVal(arrayDistSec.get(j).getAsJsonObject().getAsJsonPrimitive("IdSector").getAsString());
				    	lvOPidPspP.getOPidPspS().setDstElmNam(arrayDistSec.get(j).getAsJsonObject().getAsJsonPrimitive("DescripcionSector").getAsString());
				    	lvOPidPspP.getOPidPspS().setPtfDstTypVal("3");
				    	lvOPidPspP.getOPidPspS().setPtfDstPer(arrayDistSec.get(j).getAsJsonObject().getAsJsonPrimitive("PorcentajeSector").getAsBigDecimal());
				    	lvOPidPspP.getOPidPspS().setPtfDstDat(new Date());
				    	
				    	lvOPidPspPT.add(lvOPidPspP);
					}
				    
				    // Si estructura DistribucionesTiposActivos tiene valor
				    // Mientras estructura DistribucionesTiposActivos [Conjunto] tenga valor
				    for (int j = 0; j < arrayDistTipAct.size(); j++) {
				    	lvOPidPspP = new OPidPspP();
				    	
				    	// Asignar a cesta fondo distribucion conjunto
				    	lvOPidPspP.getOPidPspS().setCmpVal(pmOTrnCnxS.getCmpVal());
				    	lvOPidPspP.getOPidPspS().setIvfVal(lvIvfVal);
				    	lvOPidPspP.getOPidPspS().setIvfEdtVal(new BigDecimal(1));
				    	lvOPidPspP.getOPidPspS().setDstElmVal(arrayDistTipAct.get(j).getAsJsonObject().getAsJsonPrimitive("IdTipoActivo").getAsString());
				    	lvOPidPspP.getOPidPspS().setDstElmNam(arrayDistTipAct.get(j).getAsJsonObject().getAsJsonPrimitive("DescripcionTipoActivo").getAsString());
				    	lvOPidPspP.getOPidPspS().setPtfDstTypVal("1");
				    	lvOPidPspP.getOPidPspS().setPtfDstPer(arrayDistTipAct.get(j).getAsJsonObject().getAsJsonPrimitive("PorcentajeTipoActivo").getAsBigDecimal());
				    	lvOPidPspP.getOPidPspS().setPtfDstDat(new Date());
				    	
				    	lvOPidPspPT.add(lvOPidPspP);
					} // Fin Mientras estructura DistribucionesTiposActivos [Conjunto] tenga valor
				}
				
				lvOPidPfcEitDto.setOPidIvfPT(lvOPidIvfPT);
				lvOPidPfcEitDto.setOPidPfcPT(lvOPidPfcPT);
				lvOPidPfcEitDto.setOPidPspPT(lvOPidPspPT);
				lvOPidPfcEitDto.setOPidPfmPT(lvOPidPfmPT);
			}
		    return lvOPidPfcEitDto;
		
		} catch (IllegalArgumentException iae) {
		    throw new MalformedURLException("URL in pom.properties is not right, please fix it.");
		}
	}

}
