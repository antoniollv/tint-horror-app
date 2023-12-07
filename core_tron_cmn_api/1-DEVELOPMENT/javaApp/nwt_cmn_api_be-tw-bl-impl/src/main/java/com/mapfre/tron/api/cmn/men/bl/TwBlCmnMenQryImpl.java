package com.mapfre.tron.api.cmn.men.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.cmn.model.MenuDto;

@TronWebAccess
public class TwBlCmnMenQryImpl implements IBlCmnMenQry {

    @Override
    public MenuDto getMenu(final String usrVal, final String lngVal, final BigDecimal cmpVal) {
        throw new UnsupportedOperationException("Unsupported Operation with tronweb profile");
    }

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
	public OCmnMenCPT getOperationsUrl(BigDecimal cmpVal, String usrVal, String lngVal, List<OPlyAtrP> oPlyAtrPT) {
		throw new UnsupportedOperationException("Unsupported Operation with tronweb profile");
	}

}
