package com.mapfre.tron.api.cmu.cmp.dl;

import java.util.HashMap;
import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class OCmuCmpPK implements NewtronPK {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String usrVal;
    
    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("usrVal", usrVal);
	return map;
    }

}
