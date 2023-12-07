package com.mapfre.tron.api.cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;
import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;
import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;
import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;
import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.nwt.trn.rcd.tsd.bo.ORcdTsdS;
import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;
import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;
import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsP;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;
import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;
import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;
import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.ard.aby.bl.IBlOArdAbvQry;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryPK;
import com.mapfre.tron.api.cmn.abd.sbm.bl.IBlAbdSbm;
import com.mapfre.tron.api.cmn.abd.sbm.dl.impl.VhtNamPK;
import com.mapfre.tron.api.cmn.abd.vhu.bl.IBlAbdVhu;
import com.mapfre.tron.api.cmn.adr.adr.bl.IBlAdrAdr;
import com.mapfre.tron.api.cmn.agn.cpe.bl.IBlAgnCpe;
import com.mapfre.tron.api.cmn.ard.fxa.bl.IBlArdFxaQry;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaPK;
import com.mapfre.tron.api.cmn.cmn.col.bl.IBlOCmnColQry;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColPK;
import com.mapfre.tron.api.cmn.cmn.typ.bl.IBlOCmnTypQry;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnCltPK;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;
import com.mapfre.tron.api.cmn.cmu.cmp.bl.IBlOCmuCmpQry;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpPK;
import com.mapfre.tron.api.cmn.cmu.fsl.bl.IBlCmuFslS;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSPK;
import com.mapfre.tron.api.cmn.cmu.thl.bl.IBlCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.bl.IBlOCmuThlQry;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlPK;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSPK;
import com.mapfre.tron.api.cmn.crn.crn.bl.IBlOCrnCrnQry;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnPK;
import com.mapfre.tron.api.cmn.dsr.hdc.bl.IBlODsrHdc;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcPK;
import com.mapfre.tron.api.cmn.grs.znf.bl.IBlOGrsZnfSQry;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfPK;
import com.mapfre.tron.api.cmn.grs.zno.bl.IBlOGrsZnoSQry;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoPK;
import com.mapfre.tron.api.cmn.grs.znt.bl.IBlOGrsZntSQry;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntPK;
import com.mapfre.tron.api.cmn.grs.zof.bl.IBlOGrsZofSQry;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofPK;
import com.mapfre.tron.api.cmn.grs.zot.bl.IBlOGrsZotSQry;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotPK;
import com.mapfre.tron.api.cmn.lng.bl.IBlOCmnLngQry;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryPK;
import com.mapfre.tron.api.cmn.lsf.fid.bl.IBlOLsfFid;
import com.mapfre.tron.api.cmn.lsf.rek.bl.IBOlLsfRekQry;
import com.mapfre.tron.api.cmn.lss.fli.bl.IBlOLssFliSQry;
import com.mapfre.tron.api.cmn.lss.fli.dl.OLssFliPK;
import com.mapfre.tron.api.cmn.lss.lsi.bl.IBlOLssLsiSQry;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiPK;
import com.mapfre.tron.api.cmn.pid.enr.bl.IBlPidEnr;
import com.mapfre.tron.api.cmn.pid.enr.dl.OPidEnrPK;
import com.mapfre.tron.api.cmn.pid.mdd.bl.IBlPidMdd;
import com.mapfre.tron.api.cmn.prt.del.bl.IBlOPrtDel;
import com.mapfre.tron.api.cmn.prt.del.dl.OPrtDelPK;
import com.mapfre.tron.api.cmn.prt.lob.bl.IBlPrtLobQry;
import com.mapfre.tron.api.cmn.prt.lob.cache.bl.IBlPrtLobQryCache;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobPK;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaPK;
import com.mapfre.tron.api.cmn.prt.sbl.bl.IBlPrtSbl;
import com.mapfre.tron.api.cmn.prt.sbl.dl.OPrtSblPK;
import com.mapfre.tron.api.cmn.prt.sbs.bl.IBlOPrtSbsQry;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK2;
import com.mapfre.tron.api.cmn.prt.sec.bl.IBlOPrtSecQry;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecPK;
import com.mapfre.tron.api.cmn.psf.psf.bl.IBlOPsfPsfQry;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfPK;
import com.mapfre.tron.api.cmn.rcd.ucd.bl.IBlORcdUcdS;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSPK;
import com.mapfre.tron.api.cmn.rcp.ecc.bl.IBlORcpEcc;
import com.mapfre.tron.api.cmn.rcp.pmr.bl.IBlORcpPmrSQry;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcdTsdSPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrPK;
import com.mapfre.tron.api.cmn.scn.lvl.bl.IBlOCmuSnlS;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSPK;
import com.mapfre.tron.api.cmn.tgf.tdd.bl.IBlOTgfTddS;
import com.mapfre.tron.api.cmn.tgf.ucd.bl.IBlOTgfUcdS;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK2;
import com.mapfre.tron.api.cmn.thp.acv.bl.IBlThpAcv;
import com.mapfre.tron.api.cmn.thp.bne.bl.IBlOThpBneS;
import com.mapfre.tron.api.cmn.thp.cst.bl.IBlOTpdCsgS;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSPK;
import com.mapfre.tron.api.cmn.thp.ent.bl.IBlOThpEntS;
import com.mapfre.tron.api.cmn.thp.pmd.bl.IBlOThpPmd;
import com.mapfre.tron.api.cmn.thp.prs.bl.IBlOThpPrsS;
import com.mapfre.tron.api.cmn.tpd.bl.IBlThpTdpQry;
import com.mapfre.tron.api.cmn.tpd.dcy.bl.IBlOTpdDcyS;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSPK;
import com.mapfre.tron.api.cmn.trn.fil.bl.IBlTrnFilTyp;
import com.mapfre.tron.api.cmn.trn.hpn.bl.IBlTrnHpn;
import com.mapfre.tron.api.cmn.trn.ntf.bl.IBlTrnNtf;
import com.mapfre.tron.api.cmn.trn.ntf.dl.OTrnNtfPK;
import com.mapfre.tron.api.cmn.trn.pcs.bl.IBlTrnPcs;

@Component
public class CacheableAttribute {

	protected static final String CMP_VAL = "cmpVal";

	protected static final String VLD_DAT = "vldDat";

	@Autowired
	private IBlOCrnCrnQry iBlOCrnCrnQry; // sdrCrnVal

	@Autowired
	private IBlOCmnTypQry iBlOCmnTypQry; // plyDrtNam //cinTypNam //enrTypNam //rglTypNam //tnrPlyTypNam //rnsTypNam
											// //e.nom_valor AS pm_cin_typ_nam //i.nom_valor AS pm_enr_cas_nam
											// //i.nom_valor AS pm_enr_typ_nam //j.nom_valor AS pm_rgl_typ_nam
											// //l.nom_valor AS pm_tnr_ply_typ_nam //m.nom_valor AS pm_rns_typ_nam

	@Autowired
	private IBlPrtLobQryCache iBlOPrtLobQry; // lobNam

	@Autowired
	private IBlOPrtSecQry iBlOPrtSecQry; // secNam

	@Autowired
	private IBlOPsfPsfQry iBlOPsfPsfQry; // pmsNam

	@Autowired
	private IBlOCmuThlQry iBlOCmuThlQry; // cptThrLvlNam

	@Autowired
	private IBlOCmuCmpQry iBlOCmuCmpQry; // cmpNam

	@Autowired
	private IBlOGrsZntSQry iBlOGrsZntSQry;

	@Autowired
	private IBlOCmnLngQry iBlOCmnLngQry;

	@Autowired
	private IBlOGrsZnfSQry iBlOGrsZnfSQry;

	@Autowired
	private IBlOCmnColQry iBlOCmnColQry;

	@Autowired
	private IBlOGrsZotSQry iBlOGrsZotSQry;

	@Autowired
	private IBlOGrsZnoSQry iBlOGrsZnoSQry;

	@Autowired
	private IBlOGrsZofSQry iBlOGrsZofSQry;

	@Autowired
	private IBlOArdAbvQry iBlOArdAbvQry;

	@Autowired
	private IBlPrtLobQry iBlPrtLobQry;

	@Autowired
	private IBlOLssLsiSQry iBlOLssLsiSQry;

	@Autowired
	private IBlOLssFliSQry iBlOLssFliSQry;

	@Autowired
	private IBlORcpPmrSQry iBlORcpPmrSQry;

	@Autowired
	private IBlThpTdpQry iBlThpTdpQry;

	@Autowired
	private IBlOPrtSbsQry iBlOPrtSbsQry;

	@Autowired
	private IBlOTgfUcdS iBlOTgfUcdS;

	@Autowired
	private IBlORcdUcdS iBlORcdUcdS;

	@Autowired
	private IBlOTpdCsgS iBlOTpdCsgS;

	@Autowired
	private IBlAgnCpe iBlAgnCpe;

	/** The OCmuSnlS business service. */
	@Autowired
	protected IBlOCmuSnlS iBlOCmuSnlS;

	/** The OCmuFslS business service. */
	@Autowired
	protected IBlCmuFslS iBlCmuFslS;

	/** The OCmuThlS business service. */
	@Autowired
	protected IBlCmuThlS iBlCmuThlS;

	/** The IBlArdFxaS business service. */
	@Autowired
	protected IBlArdFxaQry iBlArdFxaQry;

	/** The ODsrHdc business service. */
	@Autowired
	protected IBlODsrHdc iBlODsrHdc;

	/** The OPrtDel businesss service. */
	@Autowired
	protected IBlOPrtDel iBlOPrtDel;

	/** The PrtSbl service. */
	@Autowired
	protected IBlPrtSbl iBlPrtSbl;

	/** The PidEnr service. */
	@Autowired
	protected IBlPidEnr iBlPidEnr;

	/** The TrnNtf service. */
	@Autowired
	protected IBlTrnNtf iBlTrnNtf;

	/** The TrnHpn service. */
	@Autowired
	protected IBlTrnHpn iBlTrnHpn;

	/** The TrnHpn service. */
	@Autowired
	protected IBlTrnFilTyp iBlFilTyp;

	/** The TrnHpn service. */
	@Autowired
	protected IBlTrnPcs iBlTrnPcs;

	@Autowired
	protected IBlAbdSbm iBlAbdSbm;

	@Autowired
	protected IBlAdrAdr iBlAdrAdr;

	@Autowired
	protected IBlAbdVhu iBlAbdVhu;

	@Autowired
	protected IBlPidMdd iBlPidMdd;

	@Autowired
	protected IBlThpAcv iBlThpAcv;

	@Autowired
	protected IBlOThpBneS iBlOThpBneS;

	@Autowired
	protected IBlOThpEntS iBlOThpEntS;

	@Autowired
	protected IBlOThpPmd iBlOThpPmd;

	@Autowired
	protected IBlOTpdDcyS iBlOTpdDcyS;

	@Autowired
	protected IBlOThpPrsS iBlOThpPrsS;

	@Autowired
	protected IBlORcpEcc iBlORcpEcc;

	@Autowired
	protected IBlOLsfFid iBlOLsfFid;

	@Autowired
	protected IBOlLsfRekQry iBlOLsfRekQry;

	@Autowired
	protected IBlOTgfTddS iBlOTgfTddS;

	public String getSdrCrnVal(BigDecimal crnVal, BigDecimal cmpVal) {

		return iBlOCrnCrnQry.getSdrCrnVal(OCrnCrnPK.builder().crnVal(crnVal).cmpVal(cmpVal).build()).getSdrCrnVal();
	}

	public OCrnCrnS getOCrnCrnS(BigDecimal crnVal, BigDecimal cmpVal) {

		return iBlOCrnCrnQry.getOCrnCrnS(OCrnCrnPK.builder().crnVal(crnVal).cmpVal(cmpVal).build());
	}

	public List<OCrnCrnP> getOCrnCrnSAll(String lngVal, BigDecimal cmpVal) {

		return iBlOCrnCrnQry.getOCrnCrnSAll(OCrnCrnPK.builder().lngVal(lngVal).cmpVal(cmpVal).build());
	}

	public OCrnCrnS getOCrnCrnSCurrency(String lngVal, BigDecimal cmpVal, String sdrCrnVal) {

		return iBlOCrnCrnQry
				.getOCrnCrnSCurrency(OCrnCrnPK.builder().lngVal(lngVal).cmpVal(cmpVal).sdrCrnVal(sdrCrnVal).build());
	}

	public OCmnTypS getOCmnTypS(String fldNam, String itcVal, String lngVal, BigDecimal cmpVal) {

		return iBlOCmnTypQry
				.getOCmnTypS(OCmnTypPK.builder().fldNam(fldNam).itcVal(itcVal).lngVal(lngVal).cmpVal(cmpVal).build());
	}

	public List<OCmnTypS> getOCmnTypSTypes(BigDecimal cmpVal) {

		return iBlOCmnTypQry.getAllTypes(OCmnTypPK.builder().cmpVal(cmpVal).build());
	}

	public String getLobNam(BigDecimal cmpVal, BigDecimal lobVal) {

		return iBlOPrtLobQry.getLobNam(OPrtLobPK.builder().cmpVal(cmpVal).lobVal(lobVal).build()).getLobNam();
	}

	public String getSecNam(BigDecimal cmpVal, BigDecimal secVal) {

		return iBlOPrtSecQry.getSecNam(OPrtSecPK.builder().cmpVal(cmpVal).secVal(secVal).build()).getSecNam();
	}

	public String getPmsNam(BigDecimal cmpVal, BigDecimal pmsVal, String lngVal) {

		return iBlOPsfPsfQry.getPmsNam(OPsfPsfPK.builder().cmpVal(cmpVal).pmsVal(pmsVal).build(), lngVal).getPmsNam();
	}

	public OPsfPsfS getPaySchDef(BigDecimal cmpVal, BigDecimal pmsVal, String lngVal) {

		OPsfPsfS lvOPsfPsfS = iBlOPsfPsfQry.getPmsNam(OPsfPsfPK.builder().cmpVal(cmpVal).pmsVal(pmsVal).build(),
				lngVal);

		lvOPsfPsfS.setInmDstNam(getOCmnTypS("TIP_DST_CUOTAS", lvOPsfPsfS.getInmDstVal(), lngVal, cmpVal).getItcNam());
		lvOPsfPsfS.setInmGnrInlDatTypNam(
				getOCmnTypS("TIP_FEC_INI", lvOPsfPsfS.getInmGnrInlDatTypVal(), lngVal, cmpVal).getItcNam());
		lvOPsfPsfS.setInmGnrFnlDatTypNam(
				getOCmnTypS("TIP_FEC_FIN", lvOPsfPsfS.getInmGnrFnlDatTypVal(), lngVal, cmpVal).getItcNam());
		lvOPsfPsfS.setPtlCanInmDstNam(
				getOCmnTypS("TIP_DST_AP", lvOPsfPsfS.getPtlCanInmDstVal(), lngVal, cmpVal).getItcNam());
		lvOPsfPsfS
				.setInmDstTypNam(getOCmnTypS("TIP_DST_VCTO", lvOPsfPsfS.getInmDstTypVal(), lngVal, cmpVal).getItcNam());
		lvOPsfPsfS.setMnmInmTypNam(
				getOCmnTypS("TIP_CUOTA_MINIMA", lvOPsfPsfS.getMnmInmTypVal(), lngVal, cmpVal).getItcNam());

		return lvOPsfPsfS;
	}

	public String getCptThrLvlNam(BigDecimal cmpVal, BigDecimal thrLvlVal) {

		return iBlOCmuThlQry.getCptThrLvlNam(OCmuThlPK.builder().cmpVal(cmpVal).thrLvlVal(thrLvlVal).build())
				.getThrLvlNam();
	}

	public String getCmpNam(BigDecimal cmpVal) {

		return iBlOCmuCmpQry.getCmpNam(OCmuCmpPK.builder().cmpVal(cmpVal).build()).getCmpNam();
	}

	public List<OCmnTypS> getOCmnTypS(String fldNam, BigDecimal lobVal, String lngVal, BigDecimal cmpVal) {

		return iBlOCmnTypQry.getListOCmnTypS(
				OCmnTypPK.builder().lngVal(lngVal).fldNam(fldNam).lobVal(lobVal).cmpVal(cmpVal).build());
	}

	public List<OPrtLobS> getAllForLineOfBusiness(BigDecimal cmpVal, String lngVal) {

		return iBlOPrtLobQry.getAllForLineOfBusiness(OPrtLobPK.builder().cmpVal(cmpVal).lngVal(lngVal).build());
	}

	public List<OCmnColS> getAllColours(String lngVal, BigDecimal cmpVal) {

		return iBlOCmnColQry.getAllColours(OCmnColPK.builder().lngVal(lngVal).cmpVal(cmpVal).build());

	}

	// Zone Two
	public OGrsZntS getZoneTwo(BigDecimal cmpVal, String usrVal, String lngVal, String cnyVal, BigDecimal sttVal) {

		return iBlOGrsZntSQry.getOGrsZntS(
				OgrsZntPK.builder().cnyVal(cnyVal).sttVal(sttVal).cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal).build());
	}

	// Zone Two List
	public List<OGrsZntS> getAllZoneTwo(String cnyVal, String lngVal, BigDecimal cmpVal) {

		return iBlOGrsZntSQry.getAllOGrsZntS(OgrsZntPK.builder().cnyVal(cnyVal).cmpVal(cmpVal).build());

	}

	public List<OCmnLngP> getAllLanguages(String lngVal, BigDecimal cmpVal) {

		return iBlOCmnLngQry.getOCmnLngPAll(lngVal, cmpVal);
	}

	public List<OCmuCmpS> getAllCompanies(String usrVal, String lngVal) {

		return iBlOCmuCmpQry.getCmpNamList(usrVal, lngVal);
	}

	public OCmuCmpS getCompany(String usrVal, String lngVal, BigDecimal cmpVal) {
		return iBlOCmuCmpQry.getCmpNam(usrVal, lngVal, OCmuCmpPK.builder().cmpVal(cmpVal).build());
	}

	public List<OArdAbvS> getAttributeValues(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal mdtVal, String fldNam,
			String lngVal) {

		return iBlOArdAbvQry.tbl(OArdAbvQryPK.builder().fldNam(fldNam).mdtVal(mdtVal).lobVal(lobVal).cmpVal(cmpVal)
				.lngVal(lngVal).build());
	}

	// Query line of business validation date
	public List<OPrtLobS> getOPrtLobS(BigDecimal cmpVal, BigDecimal lobVal, Date vldDat, String lngVal) {

		return iBlPrtLobQry
				.get(OPrtLobVdaPK.builder().vldDat(vldDat).lobVal(lobVal).cmpVal(cmpVal).lngVal(lngVal).build());
	}

	public OCmnColP getColor(BigDecimal colVal, String lngVal, BigDecimal cmpVal) {

		return iBlOCmnColQry.col(OCmnColPK.builder().colVal(colVal).lngVal(lngVal).cmpVal(cmpVal).build());

	}

	// Zone Three
	public OGrsZotS getZoneThree(String cnyVal, BigDecimal pvcVal, String lgnVal, BigDecimal cmpVal) {

		return iBlOGrsZotSQry
				.getOGrsZotS(OgrsZotPK.builder().cnyVal(cnyVal).pvcVal(pvcVal).lngVal(lgnVal).cmpVal(cmpVal).build());

	}

	// Zone Three List
	public List<OGrsZotS> getAllZoneThree(String cnyVal, BigDecimal sttVal, String lngVal, BigDecimal cmpVal) {

		return iBlOGrsZotSQry.getAllOGrsZotS(
				OgrsZotPK.builder().cnyVal(cnyVal).sttVal(sttVal).lngVal(lngVal).cmpVal(cmpVal).build());

	}

	// Zone One
	public OGrsZnoS getZoneOne(BigDecimal cmpVal, String usrVal, String lngVal, String cnyVal) {

		return iBlOGrsZnoSQry
				.getOGrsZnoS(OgrsZnoPK.builder().cnyVal(cnyVal).lngVal(lngVal).usrVal(usrVal).cmpVal(cmpVal).build());
	}

	// Zone One list
	public List<OGrsZnoS> getAllZoneOne(String lngVal, BigDecimal cmpVal) {

		return iBlOGrsZnoSQry.getAllOGrsZnoS(OgrsZnoPK.builder().lngVal(lngVal).build());

	}

	// Zone four
	public OGrsZnfS getZoneFour(BigDecimal cmpVal, String usrVal, String lngVal, String cnyVal, BigDecimal pvcVal,
			BigDecimal twnVal) {

		return iBlOGrsZnfSQry.getOGrsZnfS(OgrsZnfPK.builder().cnyVal(cnyVal).pvcVal(pvcVal).twnVal(twnVal)
				.cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build());
	}

	// Zone four list
	public List<OGrsZnfS> getAllZoneFour(String cnyVal, BigDecimal pvcVal, String lngVal, BigDecimal cmpVal) {

		return iBlOGrsZnfSQry.getAllOGrsZnfS(
				OgrsZnfPK.builder().cnyVal(cnyVal).pvcVal(pvcVal).lngVal(lngVal).cmpVal(cmpVal).build());
	}

	// Zone Five
	public List<OGrsZofS> getZoneFiveByPslCodVal(String pslCodVal, String usrVal, String lngVal, String reaPsc,
			BigDecimal cmpVal) {

		List<OGrsZofS> oGrsZofSList = iBlOGrsZofSQry.getZoneFiveByPslCodVal(OgrsZofPK.builder().pslCodVal(pslCodVal)
				.reaPsc(reaPsc).lngVal(lngVal).usrVal(usrVal).cmpVal(cmpVal).build());

		for (OGrsZofS oGrsZofS : oGrsZofSList) {
			oGrsZofS.setCnyNam(getZoneOne(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal()).getCnyNam());
			oGrsZofS.setSttNam(
					getZoneTwo(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal(), oGrsZofS.getSttVal()).getSttNam());
			oGrsZofS.setPvcNam(getZoneThree(oGrsZofS.getCnyVal(), oGrsZofS.getPvcVal(), lngVal, cmpVal).getPvcNam());
			oGrsZofS.setTwnNam(getZoneFour(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal(), oGrsZofS.getPvcVal(),
					oGrsZofS.getTwnVal()).getTwnNam());

		}

		return oGrsZofSList;
	}

	// Zone Five List
	public List<OGrsZofS> getAllZoneFive(BigDecimal cmpVal, String usrVal, String lngVal, String cnyVal,
			BigDecimal sttVal, BigDecimal pvcVal, BigDecimal twnVal, String reaPsc) {

		List<OGrsZofS> oGrsZofSList = iBlOGrsZofSQry
				.getZoneFiveList(OgrsZofPK.builder().cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal).cnyVal(cnyVal)
						.sttVal(sttVal).pvcVal(pvcVal).twnVal(twnVal).reaPsc(reaPsc.toUpperCase()).build());

		for (OGrsZofS oGrsZofS : oGrsZofSList) {
			oGrsZofS.setCnyNam(getZoneOne(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal()).getCnyNam());
			oGrsZofS.setSttNam(
					getZoneTwo(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal(), oGrsZofS.getSttVal()).getSttNam());
			oGrsZofS.setPvcNam(getZoneThree(oGrsZofS.getCnyVal(), oGrsZofS.getPvcVal(), lngVal, cmpVal).getPvcNam());
			oGrsZofS.setTwnNam(getZoneFour(cmpVal, usrVal, lngVal, oGrsZofS.getCnyVal(), oGrsZofS.getPvcVal(),
					oGrsZofS.getTwnVal()).getTwnNam());

		}

		return oGrsZofSList;
	}

	// Cacheable for lss g7000200 table lssRsnNam
	public OLssLsiS getOLssLsiSRsnNam(BigDecimal cmpVal, BigDecimal lssRsnVal, String lngVal) {

		return iBlOLssLsiSQry
				.getOLssLsiSRsnNam(OLssLsiPK.builder().lssRsnVal(lssRsnVal).lngVal(lngVal).cmpVal(cmpVal).build());
	}

	// Cacheable for lss g7000090 table FilTypNam
	public OLssFliS getOLssFliSTypNam(BigDecimal cmpVal, String filTypVal, String lngVal) {

		return iBlOLssFliSQry
				.getOLssFliSTypNam(OLssFliPK.builder().cmpVal(cmpVal).filTypVal(filTypVal).lngVal(lngVal).build());
	}

	// Cacheable for lss A5020500 table TypNam
	public ORcpPmrS getORcpPmrSTypNam(BigDecimal cmpVal, String TypVal, String lngVal) {

		return iBlORcpPmrSQry
				.getORcpPmrSTypNam(ORcpPmrPK.builder().cmpVal(cmpVal).typVal(TypVal).lngVal(lngVal).build());
	}

	// Cacheable for lss A1000400 table CrnNam
	public ORcpPmrS getORcpPmrSCrnNam(BigDecimal cmpVal, BigDecimal crnVal, String lngVal) {

		return iBlORcpPmrSQry
				.getORcpPmrSCrnNam(ORcpPmrPK.builder().cmpVal(cmpVal).crnVal(crnVal).lngVal(lngVal).build());
	}

	// Cacheable sector table a1000200
	public List<OPrtSecS> getSecList(BigDecimal cmpVal, String lngVal, String usrVal) {
		return iBlOPrtSecQry.getSecList(OPrtSecPK.builder().cmpVal(cmpVal).build());
	}

	// Cacheable sector table a1000200
	public OPrtSecS getSector(BigDecimal cmpVal, String lngVal, String usrVal, BigDecimal secVal) {
		return iBlOPrtSecQry.getSecNam(OPrtSecPK.builder().cmpVal(cmpVal).secVal(secVal).build());
	}

	// Cacheable subsector table a1000250
	public List<OPrtSbsS> getSubSectorList(BigDecimal cmpVal, BigDecimal secVal, String lngVal, String usrVal) {
		return iBlOPrtSbsQry.getSubSectorList(OPrtSbsPK.builder().cmpVal(cmpVal).secVal(secVal).build());
	}

	// Cacheable subsector table a1000250
	public OPrtSbsS getSubSector(BigDecimal cmpVal, BigDecimal secVal, BigDecimal sbsVal, String lngVal,
			String usrVal) {
		return iBlOPrtSbsQry.getSubSector(OPrtSbsPK2.builder().cmpVal(cmpVal).secVal(secVal).sbsVal(sbsVal).build());
	}

	public OCmnLngS getLanguage(String lngVal, BigDecimal cmpVal) {

		return iBlOCmnLngQry.getLanguage(OCmnLngQryPK.builder().cmpVal(cmpVal).lngVal(lngVal).build());
	}

	// Cacheable CollectorType table a5020200
	public OCmnTypS getCollectorType(String usrVal, String lngVal, BigDecimal cmpVal, String valVal) {
		return iBlOCmnTypQry.getCollectorType(OCmnCltPK.builder().cmpVal(cmpVal).valVal(valVal).build());
	}

	// Cacheable for lss A5020500 table
	public List<ORcdTsdS> getORcdTsdSList(BigDecimal cmpVal, String lngVal, String usrVal) {

		return iBlORcpPmrSQry.getORcdTsdSList();
	}

	// Cacheable for tsy A5020500 table
	public ORcdTsdS getORcdTsdS(BigDecimal cmpVal, String lngVal, String usrVal, String rcpStsTypVal) {

		return iBlORcpPmrSQry.getORcdTsdS(ORcdTsdSPK.builder().rcpStsTypVal(rcpStsTypVal).build());
	}

	public List<OTpdAgtS> getOTpdAgtSList(BigDecimal cmpVal, String lngVal, String usrVal) {
		return iBlThpTdpQry.getAgt(OTpdAgtSPK.builder().cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal).build());
	}

	public OTpdAgtS getOTpdAgtS(BigDecimal cmpVal, String lngVal, String usrVal, String agnTypVal) {
		return iBlThpTdpQry.getAgentType(
				OTpdAgtSPK.builder().cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal).agnTypVal(agnTypVal).build());
	}

	// Cacheable for G1010031 table
	public OCmnTypS getOCmnTypS(String fldNam, BigDecimal lobVal, String lngVal, BigDecimal cmpValValue,
			String itcVal) {

		return iBlOCmnTypQry.getOCmnTypS(OCmnTypPK.builder().cmpVal(cmpValValue).lngVal(lngVal).fldNam(fldNam)
				.lobVal(lobVal).itcVal(itcVal).build());

	}

	public List<OTgfUcdS> getCauses(BigDecimal cmpVal, String lngVal, String usrVal, String tscTypVal) {
		return iBlOTgfUcdS.getCauses(
				OTgfUcdSPK.builder().cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal).tscTypVal(tscTypVal).build());
	}

	public OTgfUcdS getCause(BigDecimal cmpVal, String lngVal, String usrVal, String tscTypVal, String tsyCasVal) {
		return iBlOTgfUcdS.getCause(OTgfUcdSPK2.builder().cmpVal(cmpVal).usrVal(usrVal).lngVal(lngVal)
				.tscTypVal(tscTypVal).tsyCasVal(tsyCasVal).build());
	}

	public ORcdTmdS getCollectorTypeV1(String usrVal, String lngVal, BigDecimal cmpVal, String mnrTypVal) {
		return iBlORcdUcdS.getCollectorTypeV1(
				ORcdTmdSPK.builder().usrVal(usrVal).cmpVal(cmpVal).lngVal(lngVal).mnrTypVal(mnrTypVal).build());
	}

	public List<ORcdTmdS> getCollectorTypesV1(String usrVal, String lngVal, BigDecimal cmpVal) {
		return iBlORcdUcdS
				.getCollectorTypesV1(ORcdTmdSPK.builder().usrVal(usrVal).cmpVal(cmpVal).lngVal(lngVal).build());
	}

	// Cacheable for df_thp_nwt_xx_cgt table
	public OTpdCsgS getOTpdCsgS(BigDecimal pmCmpValBD, String lngVal, String usrVal, String csgTypVal) {
		return iBlOTpdCsgS.getOTpdCsgS(
				OTpdCsgSPK.builder().cmpVal(pmCmpValBD).lngVal(lngVal).usrVal(usrVal).csgTypVal(csgTypVal).build());
	}

	// Cacheable for df_thp_nwt_xx_cgt table
	public List<OTpdCsgS> getOTpdCsgSList(BigDecimal pmCmpValBD, String lngVal, String usrVal) {
		return iBlOTpdCsgS
				.getOTpdCsgSList(OTpdCsgSPK.builder().cmpVal(pmCmpValBD).lngVal(lngVal).usrVal(usrVal).build());
	}

	// Cacheable for a1000701
	/**
	 * Query Second Level List.
	 *
	 * @param cmpVal    -> Company code
	 * @param lngVal    -> Language code
	 * @param usrVal    -> User code
	 * @param frsLvlVal -> First Level
	 * @return -> The second levels list
	 */
	public List<OCmuSnlS> getSecondLevels(final Integer cmpVal, final String lngVal, final String usrVal,
			final BigDecimal frsLvlVal) {
		return iBlOCmuSnlS.scnLvlQry(
				OCmuSnlSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).frsLvlVal(frsLvlVal).build());
	}

	// Cacheable for a1000701 - PoC-OCmuSnlS
	/**
	 * Query Second Level by PK.
	 *
	 * @param cmpVal    -> Company code
	 * @param lngVal    -> Language code
	 * @param usrVal    -> User code
	 * @param frsLvlVal -> First Level
	 * @return -> The second level
	 */
	public OCmuSnlS getSecondLevel(final Integer cmpVal, final String lngVal, final String usrVal,
			final BigDecimal frsLvlVal, final BigDecimal scnLvlVal) {

		return iBlOCmuSnlS.get(OCmuSnlSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).frsLvlVal(frsLvlVal)
				.scnLvlVal(scnLvlVal).build());
	}

	// Cacheable for a1000700
	/**
	 * Query First Level List.
	 *
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * @return -> The first level list
	 */
	public List<OCmuFslS> getFirstLevels(final Integer cmpVal, final String lngVal, final String usrVal) {
		return iBlCmuFslS.fstLvlQry(OCmuFslSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build());
	}

	// Cacheable for a1000700 - PoC-OCmuFslS
	/**
	 * Query First Level by PK.
	 *
	 * @param cmpVal    -> Company code
	 * @param lngVal    -> Language code
	 * @param usrVal    -> User code
	 * @param frsLvlVal -> First level value
	 * @return -> The first level
	 */
	public OCmuFslS getFirstLevel(final Integer cmpVal, final String lngVal, final String usrVal,
			final BigDecimal frsLvlVal) {

		return iBlCmuFslS
				.get(OCmuFslSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).frsLvlVal(frsLvlVal).build());
	}

	// Cacheable for a1000702
	/**
	 * Query Third Level List.
	 *
	 * @param cmpVal    -> Company code
	 * @param lngVal    -> Language code
	 * @param usrVal    -> User code
	 * @param frsLvlVal -> First Level
	 * @param scnLvlVal -> Second Level
	 * @return -> The third levels list
	 */
	public List<OCmuThlS> getThirdLevels(final Integer cmpVal, final String lngVal, final String usrVal,
			final BigDecimal frsLvlVal, final BigDecimal scnLvlVal) {

		return iBlCmuThlS.thdLvlQry(OCmuThlSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal)
				.frsLvlVal(frsLvlVal).scnLvlVal(scnLvlVal).build());
	}

	// Cacheable for a1000702 - PoC-OCmuThlSNam
	/**
	 * Query Third Level by PK.
	 *
	 * @param cmpVal    -> Company code
	 * @param lngVal    -> Language code
	 * @param usrVal    -> User code
	 * @param frsLvlVal -> First Level
	 * @param scnLvlVal -> Second Level
	 * @param thrLvlVal -> Third level value
	 * @return -> The third levels list
	 */
	public OCmuThlS getThirdLevel(final Integer cmpVal, final String lngVal, final String usrVal,
			final BigDecimal frsLvlVal, final BigDecimal scnLvlVal, final BigDecimal thrLvlVal) {

		return iBlCmuThlS.get(OCmuThlSPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).frsLvlVal(frsLvlVal)
				.scnLvlVal(scnLvlVal).thrLvlVal(thrLvlVal).build());
	}

	/**
	 * Query Fix Attributes.
	 *
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * @return -> Fixed Attributes list
	 */
	public List<OArdFxaS> getOArdFxaS(String lngVal, BigDecimal cmpVal, String usrVal) {

		return iBlArdFxaQry.getListOArdFxaS(OArdFxaPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build());
	}

	public String getAgnCpeNam(BigDecimal cmpVal, BigDecimal agnVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("agnVal", agnVal);
		return iBlAgnCpe.getAgnCpeNam(map);
	}

	// Cacheable for a1000723 - PoC-ODsrHdcS
	/**
	 * Obtener los datos del Tercer nivel del Canal de Distribuicion.
	 *
	 * @param cmpVal       -> Company code
	 * @param thrDstHnlVal -> Third distribution channel code
	 * @return -> Third distribution channel
	 */
	public ODsrHdcS getTercerNivelCanalDistribucion(final BigDecimal cmpVal, final String thrDstHnlVal) {

		return iBlODsrHdc.get(ODsrHdcPK.builder().cmpVal(cmpVal).thrDstHnlVal(thrDstHnlVal).build());
	}

	// Cacheable for g2990001 - PoC-OPrtDelS
	/**
	 * Obtener la información de un acuerdo por compañia.
	 *
	 * @param cmpVal -> Código de compañía
	 * @param delVal -> Número de contrato
	 * @return -> La información de un acuerdo por compañia
	 */
	public OPrtDelS getObtenerInformacionAcuerdoCompannia(final BigDecimal cmpVal, final BigDecimal delVal) {
		return iBlOPrtDel.get(OPrtDelPK.builder().cmpVal(cmpVal).delVal(delVal).build());
	}

	// Cacheable for g2990018 - PoC-OPrtSblS
	/**
	 * Obtener la información del subacuerdo por compañía.
	 *
	 * @param cmpVal -> Código de compañía
	 * @param sblVal -> Número de subacuerdo
	 * @return -> La información del subacuerdo por compañía
	 */
	public OPrtSblS getSubAcuerdo(final BigDecimal cmpVal, final BigDecimal sblVal) {
		return iBlPrtSbl.get(OPrtSblPK.builder().cmpVal(cmpVal).sblVal(sblVal).build());
	}

	// Cacheable for a2991800 - PoC-OPidEnrS
	/**
	 * Obtener la información del suplemento.
	 *
	 * @param cmpVal    -> Código de compañía.
	 * @param enrVal    -> Código de suplemento.
	 * @param enrSbdVal -> SubCódigo de suplemento.
	 * @return -> La información del suplemento
	 */
	public OPidEnrS getSuplemento(final BigDecimal cmpVal, final BigDecimal enrVal, final BigDecimal enrSbdVal) {
		return iBlPidEnr.get(OPidEnrPK.builder().cmpVal(cmpVal).enrVal(enrVal).enrSbdVal(enrSbdVal).build());
	}

	// Cacheable for df_trn_nwt_xx_ntf_dsp - PoC-OTrnNtfS
	/**
	 * Obtiene la informacion de OTrnNtf.
	 *
	 * @param oTrnNtfPK -> La clave primaria
	 * @return -> La información de OTrnNtf
	 */
	public OTrnNtfS getTrnNtfDsp(final BigDecimal cmpVal, final String lngVal, final String dcnVal) {
		return iBlTrnNtf.get(OTrnNtfPK.builder().cmpVal(cmpVal).dcnVal(dcnVal).lngVal(lngVal).build());
	}

	// Cacheable for df_trn_nwt_xx_nod - PoC-OprIdnNam
	/**
	 * Get the operation identification description.
	 *
	 * @param lngVal    -> Language code
	 * @param oprIdnVal -> Operation identification code
	 * @return -> Operation identification description
	 */
	public String getOprIdnNam(final String lngVal, final String oprIdnVal) {
		return iBlTrnNtf.getOprIdnNam(OTrnNtfPK.builder().lngVal(lngVal).oprIdnVal(oprIdnVal).build());
	}

	// Cacheable for G7500000 - PoC-HpnNam
	public String getHpnNam(final BigDecimal cmpVal, final String hpnVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("hpnVal", hpnVal);
		return iBlTrnHpn.getHpnNam(map);
	}

	// Cacheable for G7000090 - PoC-FilTypNam
	public String getFilTypNam(final BigDecimal cmpVal, final String filTypVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("filTypVal", filTypVal);
		return iBlFilTyp.getFilTypNam(map);
	}

	// Cacheable for G7500020 - PoC-PcsNam

	public String getPcsNam(final BigDecimal cmpVal, final String pcsVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("pcsVal", pcsVal);
		return iBlTrnPcs.getPcsNam(map);
	}

	// Cacheable for A5100715 - PoC-EncNam
	/**
	 * Get the operation identification description.
	 *
	 * @param cmpVal -> Código de compañía.
	 * @param encVal -> Operation identification code
	 * @return -> Operation identification description
	 */
	public String getEncNam(final BigDecimal cmpVal, final String encVal) {
		return iBlTrnNtf.getEncNam(OTrnNtfPK.builder().cmpVal(cmpVal).encVal(encVal).build());
	}

	// NOM_MARCA A2100400
	public String getMakNam(BigDecimal cmpVal, String makVal, Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("makVal", makVal);
		map.put(VLD_DAT, vldDat);
		return iBlAbdSbm.getMakNam(map);
	}

	// NOM_MODELO A2100410
	public String getMdlNam(BigDecimal cmpVal, String makVal, Integer mdlVal, Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("makVal", makVal);
		map.put("mdlVal", mdlVal);
		map.put(VLD_DAT, vldDat);
		return iBlAbdSbm.getMdlNam(map);
	}

	// NOM_TIP_VEHI A2100100
	public String getVhtNam(BigDecimal cmpVal, Integer vhtVal, Date vldDat) {

		return iBlAbdSbm.getVhtNam(VhtNamPK.builder().cmpVal(cmpVal).vhtVal(vhtVal).vldDat(vldDat).build());
	}

	// NOM_TRACCION G2100030
	public String getTctNam(BigDecimal cmpVal, BigDecimal tctVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("tctVal", tctVal);
		return iBlAbdSbm.getTctNam(map);
	}

	// VHC_CTG_NAM DF_ABD_NWT_XX_SBM_CTG
	public String getVhcCtgNam(BigDecimal cmpVal, BigDecimal vhcCtgVal, Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("vhcCtgVal", vhcCtgVal);
		map.put(VLD_DAT, vldDat);
		return iBlAbdSbm.getVhcCtgNam(map);
	}

	// VHC_CRB_NAM DF_ABD_NWT_XX_SBM_CRB
	public String getVhcCrbNam(BigDecimal cmpVal, BigDecimal vhcCrbVal, Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("vhcCrbVal", vhcCrbVal);
		map.put(VLD_DAT, vldDat);
		return iBlAbdSbm.getVhcCrbNam(map);
	}

	public String getFldNamVal(Integer cmpVal, String fldNam) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("fldNam", fldNam);
		return iBlAdrAdr.getFldNamVal(map);
	}

	/**
	 * Get the vehicle use description. Cache PoC-VhuNam
	 *
	 * @param cmpVal -> Company code
	 * @param vhuVal -> Vehicle use code
	 * @param vldDat -> Validation date
	 * @return -> The vehicle use description
	 */
	public String getVhuNam(final BigDecimal cmpVal, final BigDecimal vhuVal, final Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("vhuVal", vhuVal);
		map.put(VLD_DAT, vldDat);

		return iBlAbdVhu.getVhuNam(map);
	}

	public String getMdtNam(BigDecimal pmCmpVal, Integer mdtVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, pmCmpVal);
		map.put("mdtVal", mdtVal);

		return iBlPidMdd.getMdtNam(map);
	}

	// TRON-9168 @Cacheable("PoC-ThpAcvNam")
	/**
	 * Get the Thirdpard activity description.
	 *
	 * @param pmCmpVal    -> Company code
	 * @param pmThpAcvVal -> Thirpard activity code
	 * @return -> The Thirdpard activity description
	 */
	public String getThpAcvNam(final BigDecimal pmCmpVal, final BigDecimal pmThpAcvVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, pmCmpVal);
		map.put("thpAcvVal", pmThpAcvVal);

		return iBlThpAcv.getThpAcvNam(map);
	}

	// Cacheable bneNam ( o_thp_bne ) a5020900
	public String getOThpBneS(final BigDecimal pmCmpVal, final String bneVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, pmCmpVal);
		map.put("bneVal", bneVal);

		return iBlOThpBneS.getOThpBneS(map);
	}

	// Cacheable entNam (o_thp_ent) df_thp_nwt_xx_ent
	public String getOThpEntS(final BigDecimal pmCmpVal, String lngVal, final BigDecimal entTypVal, Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, pmCmpVal);
		map.put("entTypVal", entTypVal);
		map.put("lngVal", lngVal);
		map.put(VLD_DAT, vldDat);

		return iBlOThpEntS.getOThpEntS(map);
	}

	// Cacheable pcmCssNam (oThpPmd) df_thp_nwt_xx_pmd
	public String getOThpPmd(final BigDecimal pmCmpVal, String lngVal, final String pcmTypVal, final String pcmCssVal,
			Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, pmCmpVal);
		map.put("pcmTypVal", pcmTypVal);
		map.put("pcmCssVal", pcmCssVal);
		map.put("lngVal", lngVal);
		map.put(VLD_DAT, vldDat);

		return iBlOThpPmd.getOThpPmd(map);
	}

	public OTpdDcyS getOTpdDcyS(final BigDecimal cmpVal, final String usrVal, final String lngVal,
			final String thpDcmTypVal) {

		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("usrVal", usrVal);
		map.put("thpDcmTypVal", thpDcmTypVal);
		map.put("lngVal", lngVal);

		return iBlOTpdDcyS.getOTpdDcyS(map);
	}

	public OTpdSsdS getOTpdSsdS(final BigDecimal cmpVal, final String usrVal, final String lngVal,
			final String ssvVal) {

		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("lngVal", lngVal);
		map.put("ssvVal", ssvVal);

		return iBlOTpdDcyS.getOTpdSsdS(map);
	}

	public OThpPrsP getOThpPrsP(String thpDcmTypVal, String thpDcmVal, BigDecimal thpAcvVal, String usrVal,
			String lngVal, BigDecimal cmpVal) {
		return iBlOThpPrsS.get(thpDcmTypVal, thpDcmVal, thpAcvVal, usrVal, lngVal, cmpVal);
	}

	// TRON-10148 @Cacheable("PoC-EccNam") ORcpEccS - g2000161
	/**
	 * Get the economic concept description.
	 *
	 * @param cmpVal -> Company code
	 * @param eccVal -> Economic concept code
	 * @return -> The economic concept description
	 */
	public String getEccNam(final BigDecimal cmpVal, final BigDecimal eccVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("eccVal", eccVal);
		return iBlORcpEcc.getEccNam(map);
	}

	// cacheable g7000090 PoC-LsfFid
	public OLsfFidS getFileTypeDefinition(Integer cmpVal, String usrVal, String lngVal, String filTypVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("filTypVal", filTypVal);
		return iBlOLsfFid.getOLsfFid(map);
	}

	// cacheable PoC-OLsfRekS - TRON-12826
	public List<OLsfRekS> getReasonEventlockingDefinition(BigDecimal cmpVal, String lngVal, String usrVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("lngVal", lngVal);
		map.put("usrVal", usrVal);

		List<OLsfRekS> oLsfRekSList = iBlOLsfRekQry.getOLsfRekSList(map);
		for (OLsfRekS oLsfRekS : oLsfRekSList) {
			oLsfRekS.setStsSswNam(getOCmnTypS("STS_SSW_VAL", oLsfRekS.getStsSswVal(), lngVal, cmpVal).getItcNam());
		}
		return oLsfRekSList;
	}

	// NOM_SUBMODELO A2100420
	public String getSbmNam(final BigDecimal cmpVal, final String makVal, final Integer mdlVal, final Integer sbmVal,
			final Date vldDat) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("makVal", makVal);
		map.put("mdlVal", mdlVal);
		map.put("sbmVal", sbmVal);
		map.put(VLD_DAT, vldDat);

		return iBlAbdSbm.getSbmNam(map);
	}

	public OTgfTddS getInoTypNam(final BigDecimal cmpVal, final String lngVal, final String inoTypVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("lngVal", lngVal);
		map.put("inoTypVal", inoTypVal);

		return iBlOTgfTddS.getInoTypNam(map);
	}

	public OThpPrsS getCtgNam(final String cmpVal, final String splCtgVal, final String lngVal) {
		Map<String, Object> map = new HashMap<>();
		map.put(CMP_VAL, cmpVal);
		map.put("lngVal", lngVal);
		map.put("splCtgVal", splCtgVal);

		return iBlOThpPrsS.getCtgNam(map);
	}
}
