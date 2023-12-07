package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.cmn.men.bl.IBlCmnMenQry;
import com.mapfre.tron.api.cmn.model.InAttribute;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
public class OperationsUrlsController implements OperationsUrlsApi {

	@Autowired
	protected IBlCmnMenQry iBlCmnMenQry;

	/**
	 * Query the URL for each operation of the menu
	 *
	 * @param cmpVal    -> Company code
	 * @param usrVal    -> User value
	 * @param lngVal    -> Language code
	 * @param oPlyAtrPT -> Attributes list
	 * @return OCmnMenCPT -> Output structure data
	 */
	@Override
	public ResponseEntity<OCmnMenCPT> getOperationsUrl(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) BigDecimal cmpVal,
			@NotNull @ApiParam(value = "User value", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@ApiParam(value = "Attributes", required = true) @Valid @RequestBody List<InAttribute> inAttributes) { 

		log.info("The getOperationsUrl rest operation had been called...");

		OCmnMenCPT lvOCmnMenCPT = iBlCmnMenQry.getOperationsUrl(cmpVal, usrVal, lngVal, getPlyAtrPT(inAttributes));

		return new ResponseEntity<>(lvOCmnMenCPT, HttpStatus.OK);
	}
	
	/**
     * Get the PlyAtrPT property.
     * 
     * @param inAttributes the data property
     * @return List<OPlyAtrP>
     */
    protected List<OPlyAtrP> getPlyAtrPT(final List<InAttribute> inAttributes) {
        // se construye la lista de atributos a partir de los datos de entrada
        List<OPlyAtrP> lvOPlyAtrPT = new ArrayList<>();
        OPlyAtrP oPlyAtrP;
        if (inAttributes != null && !inAttributes.isEmpty()) {
            for (InAttribute inPlyAtr : inAttributes) {
                oPlyAtrP = new OPlyAtrP();
                oPlyAtrP.getOPlyAtrS().setFldNam(inPlyAtr.getFldNam());
                oPlyAtrP.getOPlyAtrS().setFldValVal(inPlyAtr.getFldValVal());

                lvOPlyAtrPT.add(oPlyAtrP);
            }
        }
        return lvOPlyAtrPT;
    }

}
