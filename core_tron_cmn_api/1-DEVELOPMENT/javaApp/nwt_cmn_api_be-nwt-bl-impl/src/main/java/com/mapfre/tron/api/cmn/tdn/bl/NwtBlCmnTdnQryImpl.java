package com.mapfre.tron.api.cmn.tdn.bl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.trn.bo.TagsNwt;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.tdn.dl.DlCmnTdn;

import lombok.Data;

@Data
@NewTronAccess
public class NwtBlCmnTdnQryImpl implements IBlCmnTdnQry {
    
    
    DlCmnTdn dlCmnTdn;
    
    @Autowired
    public NwtBlCmnTdnQryImpl(DlCmnTdn dlCmnTdn) {
	this.dlCmnTdn = dlCmnTdn;
    }
    
    public TagsNwt getTags(String lngVal) {
	return this.toDto(dlCmnTdn.getTags(lngVal));
    }
    protected TagsNwt toDto(Map<String, String> tags) {
	TagsNwt tagsNwt = new TagsNwt();
	tagsNwt.setTags(tags);
	return tagsNwt;
    }
}
