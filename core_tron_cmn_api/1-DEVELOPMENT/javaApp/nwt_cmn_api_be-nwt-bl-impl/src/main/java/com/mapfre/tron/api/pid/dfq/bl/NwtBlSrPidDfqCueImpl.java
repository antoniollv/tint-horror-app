package com.mapfre.tron.api.pid.dfq.bl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mapfre.nwt.trn.pid.dfq.bo.OPidDfqP;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;

import lombok.Data;


@Service
@Data
public class NwtBlSrPidDfqCueImpl implements IBlPidDfqCue {
    
    protected final RestTemplate rs = new RestTemplate();
    protected final JsonParser jp = new JsonParser();
    
    @Value("${app.env.infovl.url}")
    protected String infovlUrl;
    
    @Value("${app.env.infovl.username}")
    protected String infovlUsername;
    
    @Value("${app.env.infovl.password}")
    protected String infovlPassword;
    
    @Override
    public List<OPidDfqP> savSle(OTrnCnxS pmOTrnCnxS) throws MalformedURLException, ParseException {
        
    	List<OPidDfqP> lvOPidDfqPT;
        ResponseEntity<String> lvRestResponse;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
        String plainCreds = infovlUsername.trim() + ":" + infovlPassword.trim();
        byte[] plainCredsBytes = plainCreds.getBytes();
        String base64Creds = Base64.getEncoder().encodeToString(plainCredsBytes);
        String auth = "Basic " + base64Creds;
        // set headers       
        headers.add("Authorization", auth);
        HttpEntity<String> request = new HttpEntity<>(headers);
        // Realizamos llamada al Servicio rest
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(infovlUrl);
        
            lvRestResponse = rs.exchange(builder.build().toUriString() ,HttpMethod.POST, request, String.class);
            JsonArray arrayAttributes = jp.parse(lvRestResponse.getBody()).getAsJsonObject()
                    .getAsJsonObject("respuesta")
                    .getAsJsonArray("Valores");
            // instanciamos el objeto de retorno con el mismo tamaño del array que devuelve el servicio
            lvOPidDfqPT = new ArrayList<>(arrayAttributes.size());
            
            // Si la llamada nos ha devuelto un 200 significa que estaba bien la llamada 
            if (arrayAttributes.size() > 0 && lvRestResponse.getStatusCode().equals(HttpStatus.OK)) {
                
                for (int i = 0; i < arrayAttributes.size(); i++) {
                OPidDfqP lvOPidDfqP = new OPidDfqP();
                    // creamos una fecha tipo Date a partir del string obtenido
                     String stringDate = arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("FechaValor").getAsString();
                     String pattern = "yyyyMMdd";
                     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                     Date date = simpleDateFormat.parse(stringDate);
                    
                    lvOPidDfqP.getOPidDfqS().setCmpVal(pmOTrnCnxS.getCmpVal());
                    lvOPidDfqP.getOPidDfqS().setIvfVal(arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("CodCesta").getAsString());
                    lvOPidDfqP.getOPidDfqS().setIfqDat(date);
                    BigDecimal ifqVal = (arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("ValorLiquidativo") != null) ?
                	    new BigDecimal(arrayAttributes.get(i).getAsJsonObject().getAsJsonPrimitive("ValorLiquidativo").getAsString().trim()) : null;
                    lvOPidDfqP.getOPidDfqS().setIfqVal(ifqVal);
                    lvOPidDfqPT.add(i, lvOPidDfqP);
                }
            }
            return lvOPidDfqPT;
        
        } catch (IllegalArgumentException iae) {
            throw new MalformedURLException("URL in pom.properties is not right, please fix it." + infovlUrl);
        }
    }

}
