package com.mapfre.tron.api.cmn.cmn.typ.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OCmnCltPK implements NewtronPK {

    private BigDecimal cmpVal;
    private String valVal;

    public static final OCmnCltPK get(BigDecimal cmpVal, String valVal) {
	return OCmnCltPK.builder().cmpVal(cmpVal).valVal(valVal).build();
    }

    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("cmpVal", cmpVal);
	map.put("valVal", valVal);
	return map;
    }
}
