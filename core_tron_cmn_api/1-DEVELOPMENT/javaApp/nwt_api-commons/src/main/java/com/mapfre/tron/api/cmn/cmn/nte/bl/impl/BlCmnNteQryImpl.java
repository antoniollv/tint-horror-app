package com.mapfre.tron.api.cmn.cmn.nte.bl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.nte.bl.IBlCmnNteQry;
import com.mapfre.tron.api.cmn.cmn.nte.dl.IDlTwCmnNteDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlCmnNteQryImpl extends TwBlCmnBase implements IBlCmnNteQry {

	/** The repository. */
	@Autowired
	protected IDlTwCmnNteDAO iDlCmnNteDAO;

	@Override
	public OCmnNteS getNoteDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		log.info("Tronweb business logic implementation getNoteDefinition have been called...");

		return iDlCmnNteDAO.getNoteDefinition(cmpVal, nteVal, lngVal);


	}

	@Override
	public OCmnNteS getNoteTitles(BigDecimal cmpVal, String nteVal, String lngVal) {

		log.info("Tronweb business logic implementation getNoteTitles have been called...");

		return iDlCmnNteDAO.getNoteTitles(cmpVal, nteVal, lngVal);


	}

	@Override
	public List<OCmnNteS> getNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		log.info("Tronweb business logic implementation getNotesTextDefinition have been called...");

		return iDlCmnNteDAO.getNotesTextDefinition(cmpVal, nteVal, lngVal);

	}

	@Override
	public OCmnNteS getEnrichedNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		log.info("Tronweb business logic implementation getEnrichedNotesTextDefinition have been called...");

		return iDlCmnNteDAO.getEnrichedNotesTextDefinition(cmpVal, nteVal, lngVal);


	}

}
