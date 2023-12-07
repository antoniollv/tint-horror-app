package com.mapfre.tron.api.cmn.tdn.bl;

import com.mapfre.nwt.trn.bo.TagsNwt;
import com.mapfre.tron.api.bo.TronWebAccess;

import lombok.Data;

@Data
@TronWebAccess
public class TwBlCmnTdnQryImpl implements IBlCmnTdnQry {
    
    public TagsNwt getTags(String lngVal) {
	throw new UnsupportedOperationException();
    }
}
