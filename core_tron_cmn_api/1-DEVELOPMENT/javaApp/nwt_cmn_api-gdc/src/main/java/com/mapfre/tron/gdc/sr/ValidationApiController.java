package com.mapfre.tron.gdc.sr;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.gdc.bl.ValidationApiService;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-13T11:40:05.311Z[GMT]")
@Controller
@Api(tags={ "Validation",})
public class ValidationApiController implements ValidationApi {

    private static final Logger log = LoggerFactory.getLogger(ValidationApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired private ApplicationContext ctx;

    @org.springframework.beans.factory.annotation.Autowired
    public ValidationApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<ValidationError>> conceptValidation(@ApiParam(value = "Data input including user form and user info" ,required=true )  @Valid @RequestBody DataInExtended body
,@ApiParam(value = "Concept ID",required=true) @PathVariable("idConcept") String idConcept
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
          ValidationApiService val = ctx.getBean(idConcept, ValidationApiService.class);
          if (val != null) {
            return new ResponseEntity<List<ValidationError>>(val.conceptValidation(body), HttpStatus.OK);


          }
        }

        return new ResponseEntity<List<ValidationError>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<ValidationError>> fieldValidation(@ApiParam(value = "Data input including user form and user info" ,required=true )  @Valid @RequestBody DataIn body
,@ApiParam(value = "Concept ID",required=true) @PathVariable("idConcept") String idConcept
,@ApiParam(value = "Field ID",required=true) @PathVariable("idField") String idField
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<ValidationError>>(objectMapper.readValue("[ {\n  \"vformat\" : \"vformat\",\n  \"code\" : \"code\",\n  \"field\" : \"field\",\n  \"format\" : \"format\",\n  \"value\" : \"value\"\n}, {\n  \"vformat\" : \"vformat\",\n  \"code\" : \"code\",\n  \"field\" : \"field\",\n  \"format\" : \"format\",\n  \"value\" : \"value\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<ValidationError>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<ValidationError>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<ValidationError>> flowValidation(@ApiParam(value = "Data input including user form and user info" ,required=true )  @Valid @RequestBody DataIn body
,@ApiParam(value = "Flow ID",required=true) @PathVariable("idFlow") String idFlow
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<ValidationError>>(objectMapper.readValue("[ {\n  \"vformat\" : \"vformat\",\n  \"code\" : \"code\",\n  \"field\" : \"field\",\n  \"format\" : \"format\",\n  \"value\" : \"value\"\n}, {\n  \"vformat\" : \"vformat\",\n  \"code\" : \"code\",\n  \"field\" : \"field\",\n  \"format\" : \"format\",\n  \"value\" : \"value\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<ValidationError>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<ValidationError>>(HttpStatus.NOT_IMPLEMENTED);
    }

}

