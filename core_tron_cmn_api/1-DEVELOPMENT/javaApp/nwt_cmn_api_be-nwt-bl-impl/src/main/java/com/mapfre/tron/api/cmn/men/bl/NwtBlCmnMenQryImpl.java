package com.mapfre.tron.api.cmn.men.bl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenCPT;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;
import com.mapfre.nwt.trn.cmn.men.sr.ISrCmnMenQry;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.IssueCommonServices;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.cmn.men.dl.DlCmnMen;
import com.mapfre.tron.api.cmn.model.MenuDto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NewTronAccess
public class NwtBlCmnMenQryImpl implements IBlCmnMenQry {

	@Autowired
	private ISrCmnMenQry iSrCmnMenQry;

	private DlCmnMen dlCmnMen;
	
	/** Servicio común para inicializar el contexto. */
    @Autowired
    protected IssueCommonServices iCSrv;

    /** The resetPackage property. */
    @Autowired
    protected ResetPacakge resetPackage;

    /** The newtron utils. */
    protected NwtUtils nwtUtils;

    /**
     * Get the utils property.
     * 
     * @return the utils property
     */
    protected NwtUtils getNwtUtils() {
        if (nwtUtils == null) {
            nwtUtils = new NwtUtils();
        }
        return nwtUtils;
    }

    /** Reset the session. */
    protected void resetSession() {
        log.debug("Reseting session...");
        resetPackage.executeRP();
        log.debug("The session has been reset.");
    }

	@Autowired
	public NwtBlCmnMenQryImpl(DlCmnMen dlCmnMen) {
		super();
		this.dlCmnMen = dlCmnMen;
	}

	@Override
	public MenuDto getMenu(String usrVal, String lngVal, BigDecimal cmpVal) {
		String urlBase = dlCmnMen.getUrlBase("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		List<OCmnMenS> oCmnMenT = dlCmnMen.getMenu(usrVal.toUpperCase(), lngVal.toUpperCase(), cmpVal);
		return this.toDto(oCmnMenT, urlBase);
	}

	protected MenuDto toDto(List<OCmnMenS> oCmnMenT, String urlBase) {
		List<OCmnMenS> header = this.createHeader(urlBase);
		List<OCmnMenS> list = oCmnMenT;
		return new MenuDto(header, list);
	}

	protected List<OCmnMenS> createHeader(String urlBase) {
		OCmnMenS s = new OCmnMenS();
		s.setOprIdnVal("jspool");
		s.setOprIdnNam("jspool");
		s.setPgmDsb("N");
		s.setMenSqnVal(new BigDecimal(1));
		s.setFunOprUrl(urlBase + "/visualizacionReportesFlw");
		return Arrays.asList(s);
	}

	/**
	 * Query the URL for each operation of the menu
	 *
	 * @param cmpVal    -> Company code
	 * @param usrVal    -> User value
	 * @param lngVal    -> Language code
	 * @param oPlyAtrPT -> Attributes list
	 * @return OCmnMenCPT -> Output structure data
	 */
	@Override
	public OCmnMenCPT getOperationsUrl(BigDecimal cmpVal, String usrVal, String lngVal, List<OPlyAtrP> oPlyAtrPT) {

		log.info("Newtron business logic implementation getOperationsUrl have been called...");

		resetSession();

		OTrnCnxS lvOTrnCnxS = iCSrv.getCnxSrv(lngVal, cmpVal, usrVal);

		OCmnMenCPT lvOCmnMenCPT = iSrCmnMenQry.funOpr(oPlyAtrPT, lngVal, usrVal, lvOTrnCnxS);

		getNwtUtils().isTrmOk(lvOCmnMenCPT, "iSrCmnMenQry.funOpr");

		return lvOCmnMenCPT;
	}

}
