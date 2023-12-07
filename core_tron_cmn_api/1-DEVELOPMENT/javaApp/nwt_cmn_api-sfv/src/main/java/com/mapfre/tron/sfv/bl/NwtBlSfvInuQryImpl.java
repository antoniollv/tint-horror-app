package com.mapfre.tron.sfv.bl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;
import com.mapfre.nwt.trn.cmn.sfv.sr.ISrCmnSfvVld;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.Navigation;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.dl.IDLSfvInuDAO;
import com.mapfre.tron.sfv.pgm.beans.ISfvBean;
import com.mapfre.tron.sfv.pgm.mapper.SfvMapper;
import com.mapfre.tron.sfv.rule.model.NavRule;

import lombok.extern.slf4j.Slf4j;

/**
 * The newtron SfvInu business logic implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 11:14:06
 *
 */
@Slf4j
@Service
public class NwtBlSfvInuQryImpl extends TwBlCmnBase implements IBlSfvInuQry {

    /** The FsvInu repository.*/
    @Autowired
    protected IDLSfvInuDAO iDLSfvInuDAO;

    @Autowired
    protected ApplicationContext ctx;

    @Autowired
    protected ISrCmnSfvVld iSrCmnSfvVld;

    @Autowired
    protected SfvMapper sfvMapper;

    @Autowired
    protected IBlSfvNavRule iBlSfvNavRule;

    /**
     * Validate information sended from frontal.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param svfIn  -> Input structure data (agent, channel ...)
     * @return       -> Output structure data
     */
    @Override
    public SfvOut postValidateStep(final BigDecimal cmpVal, final String usrVal, final String lngVal,
            final SfvIn sfvIn) {

        if (log.isInfoEnabled()) {
            log.info("Newtron postValidateStep implementation had been called");
        }

        // reset session
        resetSession();

        SfvOut lvSfvOut = sfvMapper.map(sfvIn);

        try {
            // --------------------------------------- Consultar el flujo que aplica a los datos de entrada IDN_KEY ...
            List<OCmnSfvS> lvOCmnSfvST = iDLSfvInuDAO.inStreamIdnKeyDataQry(cmpVal, usrVal, lngVal, sfvIn);

            if (lvOCmnSfvST == null || lvOCmnSfvST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            // Quedarse con el primer registro que devuelva la sentencia, el IDN_KEY es parámetro para los siguientes pasos
            // Invocar al SR de ejecución de validaciones/consultas por PL
            OCmnSfvS lvOCmnSfvS = lvOCmnSfvST.get(0);
            lvOCmnSfvS.setLngVal(lngVal);
            sfvIn.setIdnKey(lvOCmnSfvS.getIdnKey());
            setPositionToOCmnSfvS(sfvIn, lvOCmnSfvS);
            List<OPlyAtrP> lvOPlyAtrPT = getOPlyAtrPT(sfvIn);

            // -------------------------------------------------------------------------------------- invoking BE SR...
            OPlyAtrCPT lvOPlyAtrCPT = iSrCmnSfvVld.vldFlw(lvOCmnSfvS, lvOPlyAtrPT);

            // recoger la salida del SR
            lvOPlyAtrCPT.getOPlyAtrPT().stream().forEach(o -> {
                if (o.getOPlyAtrS() != null && o.getOPlyAtrS().getFldNam() != null) {
                    sfvIn.getParameters().put(o.getOPlyAtrS().getFldNam(), o.getOPlyAtrS().getFldValVal());
                }
            });
            lvSfvOut = sfvMapper.map(sfvIn);

            // check errors and exec java beans
            lvSfvOut = mainProcess(cmpVal, usrVal, lngVal, sfvIn, lvSfvOut, lvOCmnSfvS, lvOPlyAtrCPT);

            // ----------------------------------------------------------------------- Process navigation and rules ...
            if (sfvIn.isNavigation() != null && sfvIn.isNavigation().booleanValue()) {
                processRules(cmpVal, usrVal, lngVal, lvSfvOut, lvOCmnSfvS);
            }

            return lvSfvOut;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SFV", cmpVal);
        }

        return null;
    }

    private SfvOut mainProcess(final BigDecimal cmpVal, final String usrVal, final String lngVal, final SfvIn sfvIn,
            SfvOut lvSfvOut, OCmnSfvS lvOCmnSfvS, OPlyAtrCPT lvOPlyAtrCPT) {
        try {
            // check errors
            NwtUtils lvNwtUtils = new NwtUtils();
            lvNwtUtils.isTrmOk(lvOPlyAtrCPT, "iSrCmnSfvVld.vldFlw");

            // no errors -> continue...
            // -------------------------------------------- Procesar los programas Java recuperados de la BBDDD ...
            lvSfvOut = processJavaBeans(cmpVal, usrVal, lngVal, sfvIn, lvSfvOut, lvOCmnSfvS);

        } catch (NwtException e) {
            // error -> process nwt error messages
            processNwtError(cmpVal, usrVal, lngVal, lvSfvOut, lvOCmnSfvS, e, sfvIn);
        }
        return lvSfvOut;
    }

    private void processRules(final BigDecimal cmpVal, final String usrVal, final String lngVal, SfvOut lvSfvOut,
            OCmnSfvS lvOCmnSfvS) {
        // getting rules from DDBB
        List<NavRule> navRules = iDLSfvInuDAO.readNavRules(cmpVal, usrVal, lngVal, lvOCmnSfvS.getIdnKey(), lvOCmnSfvS.getSteIdn());
        if (navRules != null && !navRules.isEmpty()) {
            // Processing rules...
            NavRule navRule = iBlSfvNavRule.process(navRules, lvSfvOut.getParameters());

            // Building navigation
            if (navRule != null) {
                Navigation lvNavigation = new Navigation();
                lvNavigation.setNxtSteIdn(navRule.getNxtSteIdn());
                Boolean pmnNvgPrrSte = Boolean.FALSE;
                if ("S".equalsIgnoreCase(navRule.getPmnNvgPrrSte())
                        || "Y".equalsIgnoreCase(navRule.getPmnNvgPrrSte())) {
                    pmnNvgPrrSte = Boolean.TRUE;
                }
                lvNavigation.setPmnNvgPrrSte(pmnNvgPrrSte);
                Boolean pmnNvgWhtPrrSte = Boolean.FALSE;
                if ("S".equalsIgnoreCase(navRule.getPmnNvgWhtPrrSte())
                        || "Y".equals(navRule.getPmnNvgWhtPrrSte())) {
                    pmnNvgWhtPrrSte = Boolean.TRUE;
                }
                lvNavigation.setPmnNvgWhtPrrSte(pmnNvgWhtPrrSte);

                lvSfvOut.setNavigation(lvNavigation);
            }
        }
    }

    protected SfvOut processJavaBeans(final BigDecimal cmpVal, final String usrVal, final String lngVal,
            final SfvIn sfvIn, SfvOut lvSfvOut, OCmnSfvS lvOCmnSfvS) {

        lvOCmnSfvS.setPgmTypVal("J");
        List<OCmnSfvS> lvOCmnSfvST2 = iDLSfvInuDAO.readParamProgram(lvOCmnSfvS);
        
        if (lvOCmnSfvST2 != null && !lvOCmnSfvST2.isEmpty()) {
            SfvIn lvSfvIn = sfvIn;
            lvSfvOut = execJavaBeans(cmpVal, usrVal, lngVal, sfvIn, lvSfvOut, lvOCmnSfvST2, lvSfvIn);
        }
        return lvSfvOut;
    }

    private SfvOut execJavaBeans(final BigDecimal cmpVal, final String usrVal, final String lngVal, final SfvIn sfvIn,
            SfvOut lvSfvOut, List<OCmnSfvS> lvOCmnSfvST2, SfvIn lvSfvIn) {
    	
        for (OCmnSfvS oCmnSfvS : lvOCmnSfvST2) {
            ISfvBean lvSfvBean = ctx.getBean(oCmnSfvS.getPgmNam(), ISfvBean.class);
            Map<String, Object> parametrization = new HashMap<>();
            if (StringUtils.isNotEmpty(oCmnSfvS.getMnrCpoVal())) {
            	parametrization = sfvMapper.jsonToObject(oCmnSfvS.getMnrCpoVal());
            }
            boolean skipError = parametrization.containsKey("onError") && Boolean.parseBoolean(parametrization.get("onError").toString());
            
            SfvOut lvSfvOutInner = lvSfvBean.execute(lvSfvIn, cmpVal, usrVal, lngVal, parametrization);

            Map<String, Object> params = lvSfvOutInner.getParameters();
            params.entrySet().stream()
                    .forEach(entry -> lvSfvIn.getParameters().put(entry.getKey(), entry.getValue()));
            lvSfvOut.setParameters(lvSfvIn.getParameters());

            boolean isError = false;
            if (lvSfvOutInner.getMessages() != null && !lvSfvOutInner.getMessages().isEmpty()) {
                for (Message message : lvSfvOutInner.getMessages()) {
                    oCmnSfvS = checkJavaBeansErrors(cmpVal, usrVal, lngVal, sfvIn, oCmnSfvS, message);
                    isError = isError || message.getMsgTypVal() == 3;
                    lvSfvOut.addMessagesItem(message);
                }
            }

            // Si hay error y en la parametrización no esta el indicador onError o si lo esta vale false
            if (isError && !skipError) {
                break;
            }
        }
        return lvSfvOut;
    }

    private OCmnSfvS checkJavaBeansErrors(final BigDecimal cmpVal, final String usrVal, final String lngVal,
            final SfvIn sfvIn, OCmnSfvS oCmnSfvS, Message message) {
        if (message.getFldNam() != null && message.getFldNam().trim().length() > 0) {
            oCmnSfvS.setFldNam(message.getFldNam());
        } else if (sfvIn.getPosition() != null) {
            oCmnSfvS.setFldNam(sfvIn.getPosition().getFldNam());
        }
        if (message.getMsgVal() != null) {
            try {
                oCmnSfvS.setMsgVal(new BigDecimal(message.getMsgVal()));
                oCmnSfvS = iDLSfvInuDAO.readErrMsgTxt(oCmnSfvS, cmpVal, usrVal, lngVal);
                String msj = oCmnSfvS.getMsgTxtVal();
                if (msj.contains("%s")) {
                	msj = String.format(msj, message.getMsgTxtVal());
                }
                message.setMsgTxtVal(msj);
            } catch (Exception e) {
                log.debug(String.format("Error calling iDLSfvInuDAO.readErrMsgTxt: %s", e.getMessage()));
            }
        }
        return oCmnSfvS;
    }

    protected void setPositionToOCmnSfvS(final SfvIn sfvIn, OCmnSfvS lvOCmnSfvS) {
        if (sfvIn.getPosition() != null) {
            lvOCmnSfvS.setSteIdn(sfvIn.getPosition().getSteIdn());
            lvOCmnSfvS.setFldNam(sfvIn.getPosition().getFldNam());
            lvOCmnSfvS.setScrSci(sfvIn.getPosition().getFldSet());
        }
    }

    protected void processNwtError(final BigDecimal cmpVal, final String usrVal, final String lngVal, SfvOut lvSfvOut,
            OCmnSfvS lvOCmnSfvS, NwtException nwtException, SfvIn sfvIn) {

        List<OTrnErrS> exceptions = nwtException.getErrors();
        if (exceptions != null && !exceptions.isEmpty()) {
            List<Message> messages = exceptions.stream().map(oTrnErrS -> {
                Message lvMessagesItem = new Message();
                lvMessagesItem.setMsgTypVal(Integer.valueOf(CTrn.TRM_VAL_ERR));
                lvMessagesItem.setMsgVal(Integer.valueOf(oTrnErrS.getErrVal().intValue()));
                String fldNam = sfvIn.getPosition() != null ? sfvIn.getPosition().getFldNam() : lvOCmnSfvS.getFldNam();
                if (oTrnErrS.getPrpNam() != null && oTrnErrS.getPrpNam().trim().length() > 0) {
                    fldNam = oTrnErrS.getPrpNam();
                    lvOCmnSfvS.setFldNam(fldNam);
                }else if (sfvIn.getPosition() != null) {
                	lvOCmnSfvS.setFldNam(sfvIn.getPosition().getFldNam());
                }
                lvMessagesItem.setFldNam(fldNam);
                try {
                    lvOCmnSfvS.setMsgVal(oTrnErrS.getErrVal());
                    OCmnSfvS oCmnSfvS = iDLSfvInuDAO.readErrMsgTxt(lvOCmnSfvS, cmpVal, usrVal, lngVal);
                    lvMessagesItem.setMsgTxtVal(oCmnSfvS.getMsgTxtVal());
                } catch (Exception e) {
                    lvMessagesItem.setMsgTxtVal(oTrnErrS.getErrNam());
                }
                return lvMessagesItem;
            }).collect(Collectors.toList());
            messages.stream().forEach(lvSfvOut::addMessagesItem);
        } else {
            Message lvMessagesItem = new Message();
            lvMessagesItem.setMsgTxtVal("There is no detail of error when calling backend service");
            lvMessagesItem.setMsgVal(3);
            lvSfvOut.addMessagesItem(lvMessagesItem);
        }

    }

    protected List<OPlyAtrP> getOPlyAtrPT(final SfvIn sfvIn) {
        return sfvIn.getParameters().entrySet().stream()
        		.filter(o -> o.getValue() == null || o.getValue() instanceof String)
        		.map(o -> {
		            OPlyAtrP lvOPlyAtrP = new OPlyAtrP();
		            OPlyAtrS lvOPlyAtrS = new OPlyAtrS();
		            lvOPlyAtrS.setFldNam(o.getKey());
		            String s = getString(o.getValue());
		            if (s != null && s.length() > 80) {
		            	s = s.substring(0, 80);
		            }
		            lvOPlyAtrS.setFldValVal(s);
		            lvOPlyAtrP.setOPlyAtrS(lvOPlyAtrS);
		
		            return lvOPlyAtrP;
        }).collect(Collectors.toList());
    }

    protected Message getMessageItem(final OCmnSfvS oCmnSfvS) {
        Message msg = new Message();
        msg.setFldNam(oCmnSfvS.getFldNam());
        msg.setMsgTxtVal(oCmnSfvS.getMsgTxtVal());
        if (oCmnSfvS.getMsgVal() != null) {
            msg.setMsgVal(oCmnSfvS.getMsgVal().intValue());
        }
        return msg;
    }
    
    private String getString(Object o) {
    	if (o == null) return "";
    	return o.toString();
    }

}
