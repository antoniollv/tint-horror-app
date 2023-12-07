package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.mapfre.tron.api.cmn.fav.bl.IBlCmnFav;
import com.mapfre.tron.api.cmn.model.FavoritesDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@Controller
@Api(tags={ "Common",})
public class FavoritesController implements FavoritesApi {

    @Autowired
    IBlCmnFav iBlCmnFav;
    
    //@CachePut(value="favorites", key="#a0#a1")
    @Override
    public ResponseEntity<List<FavoritesDto>> deleteFavoritesList(
	    @ApiParam(value = "Company code",required=true) @PathVariable("cmpVal") BigDecimal cmpVal,
	    @ApiParam(value = "Username",required=true) @PathVariable("usrVal") String usrVal,
	    @ApiParam(value = "Operation identifier",required=true) @PathVariable("oprIdnVal") String oprIdnVal){
	return ResponseEntity.ok(iBlCmnFav.delFav(cmpVal,usrVal,oprIdnVal));
    }
    
    //@Cacheable(value="favorites", key="#a0#a1")
    @Override
    public ResponseEntity<List<FavoritesDto>> getFavoritesList(
	    @ApiParam(value = "Company code",required=true) @PathVariable("cmpVal") BigDecimal cmpVal,
	    @ApiParam(value = "Username",required=true) @PathVariable("usrVal") String usrVal) {
	return ResponseEntity.ok(iBlCmnFav.getFav(cmpVal,usrVal));
    }
    
    //@CachePut(value="favorites", key="#a0#a1")
    @Override
    public ResponseEntity<List<FavoritesDto>> postFavoritesList(
	    @ApiParam(value = "Company code",required=true) @PathVariable("cmpVal") BigDecimal cmpVal,
	    @ApiParam(value = "Username",required=true) @PathVariable("usrVal") String usrVal,
	    @ApiParam(value = "Operation identifier",required=true) @PathVariable("oprIdnVal") String oprIdnVal) {
	return ResponseEntity.ok(iBlCmnFav.putFav(cmpVal,usrVal,oprIdnVal));
    }
}
