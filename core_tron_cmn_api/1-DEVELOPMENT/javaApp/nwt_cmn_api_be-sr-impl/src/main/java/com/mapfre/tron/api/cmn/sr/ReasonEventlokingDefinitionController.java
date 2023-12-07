package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;
import com.mapfre.tron.api.lsf.rek.bl.IBlLsfRekQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
@Api(tags={ "Reason event locking definition",})
public class ReasonEventlokingDefinitionController implements ReasonEventlockingDefinitionApi {

	@Autowired
	protected IBlLsfRekQry iBlLsfRekQry;

	/**
	 * Query Reason event locking definition
	 * 
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * 
	 * @return List<OLsfRekS>
	 */
	@Override
	public ResponseEntity<List<OLsfRekS>> getReasonEventlockingDefinition(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) BigDecimal cmpVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

		log.info("The getReasonEventlockingDefinition rest operation had been called...");

		List<OLsfRekS> oLsfRekSLst = iBlLsfRekQry.getReasonEventlockingDefinition(cmpVal, lngVal, usrVal);

		return new ResponseEntity<>(oLsfRekSLst, HttpStatus.OK);
	}
}
