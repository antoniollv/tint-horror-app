package com.mapfre.tron.sfv.pgm.beans.impl.cm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.mapfre.tron.sfv.bo.NofitFilesData;
import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.PlyDocument;

public interface DocumentsComponent {

	public List<OPlySmnDocQryS> getDocsByStep(BigDecimal cmpVal, BigDecimal lobVal, String step);
	
	public List<OPlySmnDocQryS> getDocs(BigDecimal cmpVal, BigDecimal lobVal);
	
	public List<PlyDocument> getDocsByPolicy(BigDecimal cmpVal, String usrVal, String plyVal);
	
	public List<PlyDocument> getDocsByQuotation(BigDecimal cmpVal, String usrVal, String qtnVal, Integer aplVal);
	
	public Set<String> getDocNames(List<PlyDocument> plyDocs);
	
	public List<OPlySmnDocQryS> getDocsMap(BigDecimal cmpVal, BigDecimal lobVal);
	
	public List<OPlySmnDocQryS> getUnsignedDocs(List<OPlySmnDocQryS> docs, Set<String> avlDocs);
	
	public List<OPlySmnDocQryS> getPendingDocs(List<OPlySmnDocQryS> docs, Set<String> avlDocs);
	
	public void generateDocument(BigDecimal cmpVal, String usrVal, String lngVal, NofitFilesData nfd, String qtnVal, String plyVal);
	
	public List<OPlySmnDocQryS> getMandatoryDocsPending(List<OPlySmnDocQryS> docs, List<PlyDocument> gddocs, List<OPlySmnDocQryS> docsMap);
	
	public List<OPlySmnDocQryS> getSignedDocsPending(List<OPlySmnDocQryS> docs, List<PlyDocument> gddocs, List<OPlySmnDocQryS> docsMap, List<String> listsToSign, Pair<String, Boolean> cs, Pair<String, Boolean> cf);
}
