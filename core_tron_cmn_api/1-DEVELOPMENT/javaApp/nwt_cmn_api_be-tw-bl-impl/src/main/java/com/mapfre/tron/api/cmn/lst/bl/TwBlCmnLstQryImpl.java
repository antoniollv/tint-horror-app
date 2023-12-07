
package com.mapfre.tron.api.cmn.lst.bl;

import java.util.List;
import java.util.Map;

import com.mapfre.nwt.trn.trn.lst.bo.OTrnLstValS;
import com.mapfre.tron.api.bo.TronWebAccess;

/**
 * The Values Option lists controller.
 *
 * @author parlaga
 * @since 1.8
 * @version 4 nov. 2020 15:30:02
 */
@TronWebAccess
public class TwBlCmnLstQryImpl implements IBlCmnLstQry {

    @Override
    public List<OTrnLstValS> getLstVal(final String lstIdn, final String lstTyp, final String insVal,
            final String lstVrs, final Map<String, Object> lstVal, boolean cchChc, final Integer cmpVal) {
        throw new UnsupportedOperationException("Unsupported Operation with tronweb profile");
    }

}
