package com.mapfre.tron.api.thp.usr.dl;

import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;
import com.mapfre.tron.api.cmn.dl.NewtronPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class OThpUsrPK implements NewtronPK {

    /**
     * 
     */
    private static final long serialVersionUID = 4527167678109949026L;
    
    private String usrVal;
    
    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("usrVal", usrVal);
	return map;
    }

    public static final OThpUsrPK get(OThpUsrS usrS) {
    	return OThpUsrPK.builder().usrVal(usrS.getUsrVal()).build();
    }
}
