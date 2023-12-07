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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.gdc.bl.FlowMdService;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdValue;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-11T07:20:33.285Z[GMT]")
@Controller
@Api(tags={ "Flow-md",})
public class FlowMdApiController implements FlowMdApi {

    private static final Logger log = LoggerFactory.getLogger(FlowMdApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired private FlowMdService srv;

    @org.springframework.beans.factory.annotation.Autowired
    public FlowMdApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<ValidationError>> flowMdAction(@ApiParam(value = "Flow ID",required=true) @PathVariable("idComposition") String idComposition,
        @ApiParam(value = "Flow ID",required=true) @PathVariable("idStep") Integer idStep
,@ApiParam(value = "Data input including user info and screen data" ,required=true )  @Valid @RequestBody FlowMdIn body
) {
      return new ResponseEntity<List<ValidationError>>(srv.flowMdAction(idComposition, idStep, body), HttpStatus.OK);
    }

    public ResponseEntity<List<FlowMdValue>> flowMdPrevious(@ApiParam(value = "Flow ID",required=true) @PathVariable("idComposition") String idComposition,
        @ApiParam(value = "Flow ID",required=true) @PathVariable("idStep") Integer idStep
,@ApiParam(value = "Data input including user info" ,required=true )  @Valid @RequestBody DataIn body
) {
        return new ResponseEntity<List<FlowMdValue>>(srv.flowMdPrevious(idComposition, idStep, body), HttpStatus.OK);
    }

}
