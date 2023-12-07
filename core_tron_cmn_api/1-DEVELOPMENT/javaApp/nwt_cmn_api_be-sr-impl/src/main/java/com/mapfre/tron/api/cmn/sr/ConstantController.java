package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmn.cnn.bo.OCmnCnnS;
import com.mapfre.tron.api.cmn.cmn.cmn.bl.IBlCmnCnnQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Constants controller.
 *
 * @author SCIKER9
 * @version 20/03/2023
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Constant",})
public class ConstantController implements ConstantApi {

	@Autowired
	protected IBlCmnCnnQry iBlCmnCnnQry;

	/**
	 * Query Constant definition. It will return the information of a constant.
	 * 
	 * @param cmpVal      -> Company code
	 * @param vrbNam      -> Variable name
	 * @param usrVal      -> User code
	 * @param lngVal      -> Language code
	 * @param inDataQuery -> Input data to get constant value
	 * @param vldDat      -> Validation Date
	 * @return List<OCmnCnnS>
	 * 
	 */
	@Override
	public ResponseEntity<List<OCmnCnnS>> postConstantDefinition(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@NotNull @ApiParam(value = "variable name", required = true) @Valid @RequestParam(value = "vrbNam", required = true) String vrbNam,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat,
			@ApiParam(value = "Input data to get constant value") @Valid @RequestBody(required = false) OCmnCnnS inDataQuery) {

		log.info("The postConstantDefinition rest operation had been called...");

		List<OCmnCnnS> oCmnCnnSLst = iBlCmnCnnQry.postConstantDefinition(cmpVal, vrbNam, usrVal, lngVal, inDataQuery,
				vldDat);

		return new ResponseEntity<>(oCmnCnnSLst, HttpStatus.OK);
	}

}
