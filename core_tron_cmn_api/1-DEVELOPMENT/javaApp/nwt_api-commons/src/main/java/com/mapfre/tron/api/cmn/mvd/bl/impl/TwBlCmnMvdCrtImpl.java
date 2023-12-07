package com.mapfre.tron.api.cmn.mvd.bl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;
import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;
import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.nte.bl.IBlCmnNteQry;
import com.mapfre.tron.api.cmn.lss.lsi.bl.IBlOLssLsiSQry;
import com.mapfre.tron.api.cmn.lss.svo.bl.IBlLssSvoQry;
import com.mapfre.tron.api.cmn.mvd.bl.IBlCmnMvdCrt;
import com.mapfre.tron.api.cmn.mvd.bl.IBlCmnMvdQry;
import com.mapfre.tron.api.cmn.mvr.bl.IBlCmnMvrCrt;
import com.mapfre.tron.api.cmn.mvr.bl.TwBlCmnMvrCrtImpl;
import com.mapfre.tron.api.cmn.tpd.snf.bl.IBlTpdSnfQry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TwBlCmnMvdCrtImpl extends TwBlCmnBase implements IBlCmnMvdCrt {

	@Autowired
	protected IBlOLssLsiSQry iBlOLssLsiSQry;

	@Autowired
	protected IBlLssSvoQry iBlLssSvoQry;

	@Autowired
	protected IBlTpdSnfQry iBlTpdSnfQry;

	@Autowired
	protected IBlCmnMvdQry iBCmnMvdQry;

	@Autowired
	protected IBlCmnNteQry iBOCmnNteSQry;

	@Autowired
	protected IBlCmnMvrCrt iBlCmnMvrCrt;

	protected static final String COD_OK = "200";

	protected static final String COD_CREATED = "201";

	@Autowired
	protected TwBlCmnMvrCrtImpl twBlCmnMvrCrtImpl;

	@Override
	public String putSupplierNotificationComplete(BigDecimal cmpVal, String usrVal, String lngVal, Integer thpAcvVal,
			String thpDcmTypVal, String thpDcmVal, String svoVal, Integer sswVal, String oprIdnVal, String gnrTypVal,
			BigDecimal lssVal, String plyVal, Integer rskVal, List<OTrnVrbS> oTrnVrbT) {

		if (log.isInfoEnabled()) {
			log.info("The tronweb getMovementDefinition service had been called.");
		}

		// reseting session
		resetSession();

		OLssSvoS oLssSvoS = new OLssSvoS();

		if (svoVal != null && sswVal != null) {

			oLssSvoS = iBlLssSvoQry.getServiceOrderQuery(cmpVal, svoVal);

			if (lssVal == null) {
				lssVal = oLssSvoS.getLssVal();
			}

		}

		if (lssVal != null) {

			OLssLsiS oLssLsiS = iBlOLssLsiSQry.getLossQuery(lssVal, cmpVal);

			if (plyVal == null || rskVal == null) {

				plyVal = oLssLsiS.getPlyVal();

				rskVal = oLssLsiS.getRskVal().intValue();

			}

		}

		List<OTpdSnfS> lvOTpdSnfS = iBlTpdSnfQry.getSupplierNotifDefByOprGnr(cmpVal, oprIdnVal, gnrTypVal);

		if (lvOTpdSnfS == null || lvOTpdSnfS.isEmpty()) {

			return COD_OK;

		} else {

			for (OTpdSnfS oTpdSnfS : lvOTpdSnfS) {

				List<String> lvMvmIdn = new ArrayList<>();
				lvMvmIdn.add(oTpdSnfS.getMvmIdn());
				Date date = new Date();
				long longDate = date.getTime();
				List<OCmnMvdS> oCmnMvdS = iBCmnMvdQry.getMovementDefinition(lngVal, usrVal, cmpVal.intValue(), lvMvmIdn,
						longDate);

				OCmnMvrS oCmnMvrS;
				if (oCmnMvdS.get(0) != null
						&& (null == oCmnMvdS.get(0).getNteVal() || oCmnMvdS.get(0).getNteVal().equals(""))) {
					throw new NwtException("error 20003 – dato obligatorio (nteVal)");
				} else {

					List<OTrnVrbS> oTrnVrbSOut = new ArrayList<>();
					OTrnVrbS oTrnVrbS = new OTrnVrbS();

					if (null != oTrnVrbT) {
						for (OTrnVrbS oTrnVrbSInput : oTrnVrbT) {

							oTrnVrbSOut.add(oTrnVrbSInput);
						}
					}

					oTrnVrbS.setVrbNamVal("COD_CIA");
					oTrnVrbS.setVrbVal(cmpVal.toString());
					oTrnVrbSOut.add(oTrnVrbS);

					oTrnVrbS = new OTrnVrbS();
					oTrnVrbS.setVrbNamVal("COD_IDIOMA");
					oTrnVrbS.setVrbVal(lngVal);
					oTrnVrbSOut.add(oTrnVrbS);

					oTrnVrbS = new OTrnVrbS();
					oTrnVrbS.setVrbNamVal("COD_USR");
					oTrnVrbS.setVrbVal(usrVal);
					oTrnVrbSOut.add(oTrnVrbS);

					oTrnVrbS = new OTrnVrbS();
					oTrnVrbS.setVrbNamVal("COD_ACT_TERCERO");
					oTrnVrbS.setVrbVal(thpAcvVal.toString());
					oTrnVrbSOut.add(oTrnVrbS);

					oTrnVrbS = new OTrnVrbS();
					oTrnVrbS.setVrbNamVal("TIP_DOCUM");
					oTrnVrbS.setVrbVal(thpDcmTypVal);
					oTrnVrbSOut.add(oTrnVrbS);

					oTrnVrbS = new OTrnVrbS();
					oTrnVrbS.setVrbNamVal("COD_DOCUM");
					oTrnVrbS.setVrbVal(thpDcmVal);
					oTrnVrbSOut.add(oTrnVrbS);

					if (null != svoVal) {

						oTrnVrbS = new OTrnVrbS();
						oTrnVrbS.setVrbNamVal("SVO_VAL");
						oTrnVrbS.setVrbVal(svoVal);
						oTrnVrbSOut.add(oTrnVrbS);

					}

					if (null != sswVal) {

						oTrnVrbS = new OTrnVrbS();
						oTrnVrbS.setVrbNamVal("SSW_VAL");
						oTrnVrbS.setVrbVal(sswVal.toString());
						oTrnVrbSOut.add(oTrnVrbS);

					}

					if (null != lssVal) {

						oTrnVrbS = new OTrnVrbS();
						oTrnVrbS.setVrbNamVal("NUM_SINI");
						oTrnVrbS.setVrbVal(lssVal.toString());
						oTrnVrbSOut.add(oTrnVrbS);

					}

					if (oLssSvoS.getFilVal() != null) {

						oTrnVrbS = new OTrnVrbS();
						oTrnVrbS.setVrbNamVal("NUM_EXP");
						oTrnVrbS.setVrbVal(oLssSvoS.getFilVal().toString());
						oTrnVrbSOut.add(oTrnVrbS);

					}

					oCmnMvrS = twBlCmnMvrCrtImpl.postMovementRecordbyNote(cmpVal, lngVal, oTpdSnfS.getMvmIdn(), usrVal,
							oCmnMvdS.get(0).getNteVal(), lssVal, plyVal, rskVal, oTrnVrbSOut);

				}

				iBlCmnMvrCrt.mvrOch(lngVal, usrVal, cmpVal.intValue(), thpDcmTypVal, thpDcmVal, thpAcvVal, oCmnMvrS);

			}

			return COD_CREATED;

		}

	}

}
