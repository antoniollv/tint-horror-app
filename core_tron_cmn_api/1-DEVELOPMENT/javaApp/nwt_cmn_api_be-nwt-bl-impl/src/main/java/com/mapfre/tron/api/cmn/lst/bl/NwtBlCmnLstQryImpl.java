/**
 * 
 */
package com.mapfre.tron.api.cmn.lst.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mapfre.nwt.trn.sr.ISrTrnLstOptVal;
import com.mapfre.nwt.trn.trn.lst.bo.OTrnLstValS;
import com.mapfre.tron.api.bo.NewTronAccess;

import lombok.Data;

/**
 * The Values Option lists controller.
 *
 * @author parlaga
 * @since 1.8
 * @version 4 nov. 2020 15:30:02
 */

@Data
@NewTronAccess
public class NwtBlCmnLstQryImpl implements IBlCmnLstQry {

    @Autowired
    private ISrTrnLstOptVal srTrnLstOptVal;
    
    @Value("${default.app.cmpVal}")
    public BigDecimal cmpValDefault;
    
    @Override
    public List<OTrnLstValS> getLstVal(String lstIdn, String lstTyp, String insVal, String lstVrs, 
	    Map<String, Object> lstVal, boolean cchChc, Integer cmpVal) {
    	
    	
        BigDecimal bdCmpVal = null; 

        if(cmpVal == null){
            bdCmpVal = cmpValDefault;
        }else{
            bdCmpVal = new BigDecimal(cmpVal);
        }
    	
    	
	 return srTrnLstOptVal.getLstVal(lstIdn,lstTyp, insVal,lstVrs, null, lstVal, cchChc, bdCmpVal);
	
    }

}
