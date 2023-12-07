package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
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

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.cmn.nte.bl.IBlCmnNteQry;
import com.mapfre.tron.api.cmn.mvd.bl.IBlCmnMvdCrt;
import com.mapfre.tron.api.cmn.mvr.bl.IBlCmnMvrCrt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Movement Record Api controller.
 *
 * @author ibermatica - Borja Ruiz
 * @since 1.8
 * @version 11 sept. 2020 12:29:51
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Movement Record",})
public class MovementRecordApiController implements MovementRecordApi {

	/** The iBlCmnMvrCrt service interface. */
	@Autowired
	protected IBlCmnMvrCrt iBlCmnMvrCrt;

	/** The iBlCmnMvrCrt service interface. */
	@Autowired
	protected IBlCmnMvdCrt iBlCmnMvdCrt;

	@Autowired
	protected IBlCmnNteQry iBOCmnNteSQry;

	/**
	 * Create movement record for a third party
	 * 
	 * @author Cristian Saball
	 * 
	 * @param lngVal           -> Language code
	 * @param usrVal           -> User code
	 * @param cmpVal           -> Company code
	 * @param thpDcmTypVal     -> Document type
	 * @param thpDcmVal        -> Document
	 * @param thpAcvVal        -> Activity
	 * @param thpAcvVal        -> Activity
	 * @param inMovementRecord -> Input data to new movement record
	 * @return -> void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<Void> postMovementRecordList(
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@NotNull @ApiParam(value = "Document type", required = true) @Valid @RequestParam(value = "thpDcmTypVal", required = true) String thpDcmTypVal,
			@NotNull @ApiParam(value = "Document", required = true) @Valid @RequestParam(value = "thpDcmVal", required = true) String thpDcmVal,
			@NotNull @ApiParam(value = "Activity", required = true) @Valid @RequestParam(value = "thpAcvVal", required = true) Integer thpAcvVal,
			@ApiParam(value = "Input data to new movement record", required = true) @Valid @RequestBody OCmnMvrS inMovementRecord) {

		log.info("The postMovementRecordList rest operation had been called...");

		iBlCmnMvrCrt.mvrOch(lngVal, usrVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, inMovementRecord);

		return new ResponseEntity("Operation successfully done!", HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<String> putSupplierNotificationComplete(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) BigDecimal cmpVal,
			@NotNull @ApiParam(value = "User value", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "Third party activity value", required = true) @Valid @RequestParam(value = "thpAcvVal", required = true) Integer thpAcvVal,
			@NotNull @ApiParam(value = "Third person document type value", required = true) @Valid @RequestParam(value = "thpDcmTypVal", required = true) String thpDcmTypVal,
			@NotNull @ApiParam(value = "Third person document number", required = true) @Valid @RequestParam(value = "thpDcmVal", required = true) String thpDcmVal,
			@NotNull @ApiParam(value = "Operation", required = true) @Valid @RequestParam(value = "oprIdnVal", required = true) String oprIdnVal,
			@NotNull @ApiParam(value = "Generation type value", required = true) @Valid @RequestParam(value = "gnrTypVal", required = true) String gnrTypVal,
			@ApiParam(value = "Service order") @Valid @RequestParam(value = "svoVal", required = false) String svoVal,
			@ApiParam(value = "Service") @Valid @RequestParam(value = "sswVal", required = false) Integer sswVal,
			@ApiParam(value = "Loss value") @Valid @RequestParam(value = "lssVal", required = false) BigDecimal lssVal,
			@ApiParam(value = "Policy value") @Valid @RequestParam(value = "plyVal", required = false) String plyVal,
			@ApiParam(value = "Risk value") @Valid @RequestParam(value = "rskVal", required = false) Integer rskVal,
			@ApiParam(value = "Input data global variables") @Valid @RequestBody List<OTrnVrbS> oTrnVrbT) {

		log.info("The putSupplierNotificationComplete rest operation had been called...");

		String response = iBlCmnMvdCrt.putSupplierNotificationComplete(cmpVal, usrVal, lngVal, thpAcvVal, thpDcmTypVal,
				thpDcmVal, svoVal, sswVal, oprIdnVal, gnrTypVal, lssVal, plyVal, rskVal, oTrnVrbT);

		if (response.equals("200")) {
			return new ResponseEntity("Operation successfully done without notifications!", HttpStatus.OK);
		} else {
			return new ResponseEntity("Operation successfully done!", HttpStatus.CREATED);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<OCmnMvrS> postMovementRecordbyNote(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) BigDecimal cmpVal,
			@NotNull @ApiParam(value = "Note Value", required = true) @Valid @RequestParam(value = "nteVal", required = true) String nteVal,
			@NotNull @ApiParam(value = "Movement Identification", required = true) @Valid @RequestParam(value = "mvmldn", required = true) String mvmldn,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Input data global variables") @Valid @RequestBody List<OTrnVrbS> oTrnVrbT) {

		OCmnMvrS oCmnMvrS = iBlCmnMvrCrt.postMovementRecordbyNote(cmpVal, lngVal, mvmldn, usrVal, nteVal, null, null,
				null, oTrnVrbT);

		return new ResponseEntity<>(oCmnMvrS, HttpStatus.OK);
	}
}
