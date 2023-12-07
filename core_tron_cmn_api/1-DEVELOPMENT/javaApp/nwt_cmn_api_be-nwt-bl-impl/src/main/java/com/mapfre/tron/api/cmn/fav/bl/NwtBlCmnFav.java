/**
 * 
 */
package com.mapfre.tron.api.cmn.fav.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.men.dl.DlCmnMen;
import com.mapfre.tron.api.cmn.model.FavoritesDto;

import lombok.Data;

/**
 * @author PARLAGA
 *
 */
@Data
@NewTronAccess
public class NwtBlCmnFav implements IBlCmnFav {

    @Autowired
    IDlCmnFav iDlCmnFav;
    
    @Autowired
    DlCmnMen iDlCmnMen;
    
    /* (non-Javadoc)
     * @see com.mapfre.tron.api.cmn.fav.bl.IBlCmnFav#delFav(java.math.BigDecimal, java.lang.String, java.lang.String)
     * Delete favorite, and return new favorite list
     */
    @Override
    public List<FavoritesDto> delFav(BigDecimal cmpVal, String usrVal, String oprIdnVal) {
	if (iDlCmnFav.delFav(cmpVal,usrVal,oprIdnVal))
	    return this.getList(cmpVal,usrVal);
	else
	    throw new NwtException("Couldnt delete favorite");
    }

    /* (non-Javadoc)
     * @see com.mapfre.tron.api.cmn.fav.bl.IBlCmnFav#getFav(java.math.BigDecimal, java.lang.String)
     */
    @Override
    public List<FavoritesDto> getFav(BigDecimal cmpVal, String usrVal) {
	return this.getList(cmpVal,usrVal);
    }

    /* (non-Javadoc)
     * @see com.mapfre.tron.api.cmn.fav.bl.IBlCmnFav#postFav(java.math.BigDecimal, java.lang.String, java.lang.String)
     */
    @Override
    public List<FavoritesDto> putFav(BigDecimal cmpVal, String usrVal, String oprIdnVal) {
	if (iDlCmnFav.putFav(cmpVal,usrVal,oprIdnVal))
	    return this.getList(cmpVal, usrVal);
	else
	    throw new NwtException("Couldnt delete favorite");
    }
    
    private List<FavoritesDto> getList(BigDecimal cmpVal, String usrVal) {
	List<String> favoriteIdentifiers = iDlCmnFav.getFav(cmpVal,usrVal);
	List<String> menuIdentifiers = iDlCmnMen.getOprIdnValLst(usrVal, cmpVal);
	
	return favoriteIdentifiers.stream()
		// Dame los identificadores de favoritos distintos (evitar duplicados)
		  .distinct()
		// Filtra y deja solo los que estén en el menú
		  .filter(menuIdentifiers::contains)
		// Mapealos al dto de favoritos
		  .map(identifier -> new FavoritesDto(identifier))
		// Devuelvemelos en forma de lista
		  .collect(Collectors.toList());	
    }

}
