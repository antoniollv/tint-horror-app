package com.mapfre.tron.api.cmn.sr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.bo.TagsNwt;
import com.mapfre.tron.api.cmn.tdn.bl.IBlCmnTdnQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The address type controller.
 *
 * @author parlaga
 * @since 1.8
 * @version 11 mar. 2021 12:29:51
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Traductions",})
public class TraductionsController implements TraductionsApi {

    @Autowired
    IBlCmnTdnQry blCmnTdn;
    
    @Override
    public ResponseEntity<Map<String, String>> getTraductions(@ApiParam(value = "Language code",required=true) @PathVariable("lngVal") String lngVal){
	log.info("The getTraductions rest operation had been called...");

        try {

            TagsNwt traductions = blCmnTdn.getTags(lngVal.toUpperCase());
            return new ResponseEntity<>(traductions.getTags(), HttpStatus.OK);

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }
    }
}
