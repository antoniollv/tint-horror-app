package com.mapfre.tron.gdc.sr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.gdc.bl.HelpApiService;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.HelpValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-13T11:40:05.311Z[GMT]")
@Controller
@Api(tags={ "Help", })
public class HelpApiController implements HelpApi {

    private static final Logger log = LoggerFactory.getLogger(HelpApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired private HelpApiService srv;

    @org.springframework.beans.factory.annotation.Autowired
    public HelpApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<HelpValue>> help(@ApiParam(value = "Data input including user form and user info" ,required=true )  @Valid @RequestBody DataIn body
,@ApiParam(value = "ID of the help request",required=true) @PathVariable("idHelp") String idHelp, @ApiParam(value = "Company code", required = false) @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal
) {
        return new ResponseEntity<List<HelpValue>>(srv.help(body, idHelp, cmpVal), HttpStatus.OK);
    }

}
