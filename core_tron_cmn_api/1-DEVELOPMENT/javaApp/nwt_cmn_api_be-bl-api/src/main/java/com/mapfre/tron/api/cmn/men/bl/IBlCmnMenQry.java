package com.mapfre.tron.api.cmn.men.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.cmn.model.MenuDto;

public interface IBlCmnMenQry {
    
    public MenuDto getMenu(String usrVal, String lngVal, BigDecimal cmpVal);
    
	/**
	 * Query the URL for each operation of the menu
	 *
	 * @param cmpVal    -> Company code
	 * @param usrVal    -> User value
	 * @param lngVal    -> Language code
	 * @param oPlyAtrPT -> Attributes list
	 * @return OCmnMenCPT -> Output structure data
	 */
	OCmnMenCPT getOperationsUrl(BigDecimal cmpVal, String usrVal, String lngVal, List<OPlyAtrP> oPlyAtrPT);
}
