package com.mapfre.tron.api.cmn.mvr.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mapfre.nwt.trn.cmn.mvr.sr.ISrCmnMvrCue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.cmn.c.CCmn;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrP;
import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbCPT;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbP;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.nte.bl.IBlCmnNteQry;
import com.mapfre.tron.api.cmn.lss.lsi.bl.IBlOLssLsiSQry;
import com.mapfre.tron.api.cmn.lss.svo.bl.IBlLssSvoQry;
import com.mapfre.tron.api.cmn.mvr.dl.IBlCmnMvrCrtDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ibermatica
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnMvrCrtImpl extends TwBlCmnBase implements IBlCmnMvrCrt {

	/** The BlCmnMvrCrt respository */
	@Autowired
	protected IBlCmnMvrCrtDAO iBlCmnMvrCrtDAO;

	@Autowired
	protected IBlCmnNteQry iBOCmnNteSQry;

	@Autowired
	protected IBlOLssLsiSQry iBlOLssLsiSQry;

	@Autowired
	protected IBlLssSvoQry iBlLssSvoQry;
	
	@Autowired
	protected ISrCmnMvrCue iSrCmnMvrCue;

	/**
	 * Create movement record for a third party
	 * 
	 * @author Cristian Saball
	 * 
	 * @param lngVal           -> Language code
	 * @param usrVal           -> User code
	 * @param cmpVal           -> Company code
	 * @param thpDcmTypVal     -> Document type
	 * @param thpDcmVal        -> Document
	 * @param thpAcvVal        -> Activity
	 * @param thpAcvVal        -> Activity
	 * @param inMovementRecord -> Input data to new movement record
	 * @return -> void
	 */
	@Override
	public void mvrOch(final String lngVal, final String usrVal, final Integer cmpVal, final String thpDcmTypVal,
			final String thpDcmVal, final Integer thpAcvVal, final OCmnMvrS inMovementRecord) {

		log.debug("Tronweb business logic implementation PRC have been called...");

		// reseting session
		resetSession();

		int lvVod = 0;

		try {
			if (inMovementRecord.getSqnVal() != null) {
				lvVod = iBlCmnMvrCrtDAO.mvrOchUpdate(lngVal, usrVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal,
						inMovementRecord);

				if (lvVod != 1) {
					throw new NwtException("Error updating or inserting Create movement record for a third party");
				}
			}

			if (inMovementRecord.getSqnVal() == null) {
				lvVod = iBlCmnMvrCrtDAO.mvrOchInsert(lngVal, usrVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal,
						inMovementRecord);

				if (lvVod != 1) {
					throw new NwtException("Error updating or inserting Create movement record for a third party");
				}
			}

		} catch (IncorrectResultSizeDataAccessException e) {
			getError(lngVal, BigDecimal.valueOf(cmpVal));
		}

	}

	@Override
	public OCmnMvrS postMovementRecordbyNote(BigDecimal cmpVal, String lngVal, String mvmldn, 
			String usrVal, String nteVal, BigDecimal lssVal, String plyVal, Integer rskVal, List<OTrnVrbS> oTrnVrbT) {

		OCmnNteS oCmnNteSDefinition = iBOCmnNteSQry.getNoteDefinition(cmpVal, nteVal, lngVal);
		OCmnMvrS oCmnMvrS = new OCmnMvrS();

		if (oCmnNteSDefinition.getDsbRow().equals(CIns.YES)) {
			throw new NwtException("error 20020 – Código inhabilitado");
		} else {

			oCmnMvrS.setCmpVal(cmpVal);
			oCmnMvrS.setMvmIdn(mvmldn);
			oCmnMvrS.setMvmPbl(CIns.YES);
			oCmnMvrS.setMvmSttTypVal(CCmn.NOT_RED_MVM_STT_TYP_VAL);
			oCmnMvrS.setMvmSttDat(new Date());
			if (null != lssVal && null != plyVal && null != rskVal) {
				oCmnMvrS.setLssVal(lssVal);
				oCmnMvrS.setPlyVal(plyVal);
				oCmnMvrS.setRskVal(new BigDecimal(rskVal));
			}
			
			List<OTrnVrbP> oTrnVrbPOut = new ArrayList<>();
			
			
			for(OTrnVrbS oTrnVrbS : oTrnVrbT) {
				OTrnVrbP oTrnVrbP = new OTrnVrbP();
				
				oTrnVrbP.setOTrnVrbS(oTrnVrbS);
				oTrnVrbPOut.add(oTrnVrbP);
			}
			
			OTrnVrbCPT oTrnVrbCpt = new OTrnVrbCPT();
			oTrnVrbCpt.setOTrnVrbPT(oTrnVrbPOut);
			oTrnVrbCpt.setPrcTrmVal(CTrn.TRM_VAL_OK);

			OCmnMvrP oCmnNteSTitle = iSrCmnMvrCue.nteTitGet(oTrnVrbCpt, nteVal, lngVal, cmpVal, usrVal);
			OCmnMvrP oCmnNteSDspVal = iSrCmnMvrCue.nteSbjGet(oTrnVrbCpt, nteVal, lngVal, cmpVal, usrVal);
			
			oCmnMvrS.setNteVal(nteVal);

			oCmnMvrS.setTitVal(oCmnNteSTitle.getOCmnMvrS().getTitVal());
			oCmnMvrS.setDspVal(oCmnNteSDspVal.getOCmnMvrS().getDspVal());
		}

		return oCmnMvrS;

	}
}
