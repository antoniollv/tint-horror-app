package com.mapfre.tron.api.cmn.cmn.nte.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public interface IDlTwCmnNteDAO {
	
	OCmnNteS get_003(BigDecimal cmpVal, String nteVal, String usrLngVal);
        
	OCmnNteS getNoteDefinition(BigDecimal cmpVal, String nteVal, String lngVal);
	
	OCmnNteS getNoteTitles(BigDecimal cmpVal, String nteVal, String lngVal);
	
	List<OCmnNteS> getNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal);
	
	OCmnNteS getEnrichedNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal);

}
