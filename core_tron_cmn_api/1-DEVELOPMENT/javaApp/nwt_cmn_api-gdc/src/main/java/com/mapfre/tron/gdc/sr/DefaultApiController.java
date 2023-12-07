package com.mapfre.tron.gdc.sr;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.DefaultValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-13T11:40:05.311Z[GMT]")
@Controller
@Api(tags={ "Default", })
public class DefaultApiController implements DefaultApi {

    private static final Logger log = LoggerFactory.getLogger(DefaultApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DefaultApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<DefaultValue> defaultValue(@ApiParam(value = "Data input including user info" ,required=true )  @Valid @RequestBody DataIn body
,@ApiParam(value = "Concept ID",required=true) @PathVariable("idConcept") String idConcept
,@ApiParam(value = "Field ID",required=true) @PathVariable("idField") String idField
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DefaultValue>(objectMapper.readValue("{\n  \"value\" : \"value\"\n}", DefaultValue.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<DefaultValue>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DefaultValue>(HttpStatus.NOT_IMPLEMENTED);
    }

}
