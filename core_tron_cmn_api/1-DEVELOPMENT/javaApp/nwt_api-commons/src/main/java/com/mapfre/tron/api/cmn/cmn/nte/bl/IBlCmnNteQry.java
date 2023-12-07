package com.mapfre.tron.api.cmn.cmn.nte.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

@Service
public interface IBlCmnNteQry {
	
	
	OCmnNteS getNoteDefinition(BigDecimal cmpVal, String nteVal, String lngVal);
	
	OCmnNteS getNoteTitles(BigDecimal cmpVal, String nteVal, String lngVal);
	
	List<OCmnNteS> getNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal);
	
	OCmnNteS getEnrichedNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal);
		
	
}
