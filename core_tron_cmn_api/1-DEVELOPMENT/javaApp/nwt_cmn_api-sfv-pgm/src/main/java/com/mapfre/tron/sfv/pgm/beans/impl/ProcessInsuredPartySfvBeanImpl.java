package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.api.thp.client.api.InsuredPartyApi;
import com.mapfre.tron.api.thp.client.model.OThpAcvP;
import com.mapfre.tron.api.thp.client.model.OThpAcvS;
import com.mapfre.tron.api.thp.client.model.OThpAdrP;
import com.mapfre.tron.api.thp.client.model.OThpAdrS;
import com.mapfre.tron.api.thp.client.model.OThpCntP;
import com.mapfre.tron.api.thp.client.model.OThpCntS;
import com.mapfre.tron.api.thp.client.model.OThpInyP;
import com.mapfre.tron.api.thp.client.model.OThpInyS;
import com.mapfre.tron.api.thp.client.model.OThpIpcC;
import com.mapfre.tron.api.thp.client.model.OThpIpcPC;
import com.mapfre.tron.api.thp.client.model.OThpPcmP;
import com.mapfre.tron.api.thp.client.model.OThpPcmS;
import com.mapfre.tron.api.thp.client.model.OThpPrsP;
import com.mapfre.tron.api.thp.client.model.OThpPrsS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The ProcessInsuredPartySfv SfvBean implementation.
 * Save insured party data
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("ProcessInsuredPartySfv") @SuppressWarnings("unchecked")
public class ProcessInsuredPartySfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final String MOD_2 = "2";
	private static final String ALTA_1 = "1";
	private static final String SI = "S";
	private static final String NO = "N";

	@Autowired private InsuredPartyApi insuredPartyApi;  
    
	@Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("ProcessInsuredPartySfvBeanImpl.execute start execute operation...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        
        OThpIpcPC thr = (OThpIpcPC)innerData.get("InsuredPartySfv");
        boolean isNew = thr == null;
        Long date = new Date().getTime();
        
        String tipDocum = getString(in.getParameters().get("TIP_DOCUM"));
		String codDocum = getString(in.getParameters().get("COD_DOCUM"));
		List<Map<String, String>> mediosPago = (List<Map<String, String>>)in.getParameters().get("MEDIOS_PAGO");
		Map<String, String> inp = new HashMap<>();
		createInp(in, inp);
        
		try {
        	OThpIpcC data = new OThpIpcC();
        	data.oThpAcvP(new OThpAcvP());
        	data.getOThpAcvP().oThpAcvS(new OThpAcvS());
        	data.getOThpAcvP().getOThpAcvS().cmpVal(cmpVal);
        	data.getOThpAcvP().getOThpAcvS().thpDcmTypVal(tipDocum);
        	data.getOThpAcvP().getOThpAcvS().thpDcmVal(codDocum);
        	data.getOThpAcvP().getOThpAcvS().thpAcvVal(BigDecimal.ONE);
        	data.getOThpAcvP().getOThpAcvS().mdfDat(date);
        	data.getOThpAcvP().getOThpAcvS().spl(NO);
        	data.getOThpAcvP().getOThpAcvS().pocDat(date);
        	data.getOThpAcvP().getOThpAcvS().vldDat(date);
        	data.getOThpAcvP().getOThpAcvS().acnOrgVal(isNew ? ALTA_1 : MOD_2);
        	data.getOThpAcvP().getOThpAcvS().btcMvmTypVal(isNew ? ALTA_1 : MOD_2);
        	
        	if (isNew) {
	        	data.oThpPrsP(new OThpPrsP());
	        	data.getOThpPrsP().oThpPrsS(new OThpPrsS());
	        	data.getOThpPrsP().getOThpPrsS().cmpVal(cmpVal);
	        	data.getOThpPrsP().getOThpPrsS().thpDcmTypVal(tipDocum);
	        	data.getOThpPrsP().getOThpPrsS().thpDcmVal(codDocum);
	        	data.getOThpPrsP().getOThpPrsS().phyPrs(getString(params.get("phyPrs")));
	        	data.getOThpPrsP().getOThpPrsS().thpNam(getString(in.getParameters().get("NOM_TERCERO")));
	        	data.getOThpPrsP().getOThpPrsS().thpFrsSrn(getString(in.getParameters().get("APE1_TERCERO")));
	        	data.getOThpPrsP().getOThpPrsS().thpScnSrn(getString(in.getParameters().get("APE2_TERCERO")));
	        	data.getOThpPrsP().getOThpPrsS().mrtStsVal(getEstadoCivil(in.getParameters(), params));
	        	data.getOThpPrsP().getOThpPrsS().sexVal(getSexo(in.getParameters(), params));
	        	data.getOThpPrsP().getOThpPrsS().ntnTypVal(getString(params.get("ntnTypVal")));
	        	data.getOThpPrsP().getOThpPrsS().ntnVal(getNacionalidad(in.getParameters(), params));
	        	data.getOThpPrsP().getOThpPrsS().lngVal(lngVal);
	        	data.getOThpPrsP().getOThpPrsS().mdfDat(date);
	        	data.getOThpPrsP().getOThpPrsS().prsPce(getString(params.get("prsPce"))); // "N"
	        	data.getOThpPrsP().getOThpPrsS().thpDcmCck(getString(params.get("thpDcmCck"))); // "N"
	        	data.getOThpPrsP().getOThpPrsS().acnOrgVal(ALTA_1);

	        	OThpAdrS addr = new OThpAdrS();
	        	data.setOThpAdrPT(Arrays.asList(new OThpAdrP()));
	        	data.getOThpAdrPT().get(0).setOThpAdrS(addr);
	        	addr.cmpVal(cmpVal);
	        	addr.thpDcmTypVal(tipDocum);
	        	addr.thpDcmVal(codDocum);
	        	addr.thpAcvVal(BigDecimal.ONE);
	        	addr.adrSqnVal(BigDecimal.ONE);
	        	addr.dmlTypVal(getString(in.getParameters().get("TIP_DOMICILIO")));
	        	addr.adrTxtVal(getString(in.getParameters().get("NOM_DOMICILIO1")));
	        	addr.adrNbrVal(getBD(in.getParameters().get("NOM_DOMICILIO2")));
	        	addr.adrFlrVal(getString(in.getParameters().get("NOM_DOMICILIO3")));
	        	addr.adrDorVal(getString(in.getParameters().get("ATR_DOMICILIO4")));
	        	addr.extAdrTxtVal(getExtDireccion(in.getParameters()));
	        	addr.cnyVal(getCodPais(in.getParameters(), params));
	        	addr.sttVal(getCodEstado(in.getParameters(), params));
	        	BigDecimal codProvincia = getCodProvincia(in, addr.getSttVal());
	        	addr.pvcVal(codProvincia);
	        	BigDecimal codCiudad = getCodCiudad(in, codProvincia);
	        	addr.twnVal(codCiudad);
	        	addr.pslCodVal(getString(in.getParameters().get("COD_POSTAL")));
	        	addr.dflAdr(SI);
	        	addr.dsbRow(NO);
	        	addr.usrVal(usrVal);
	        	addr.vldDat(date);
	        	addr.mdfDat(date);
	        	addr.adrCck(NO);
	        	addr.adrUseTypVal(getString(params.get("adrUseTypVal"))); // "1"
	        	addr.acnOrgVal(ALTA_1);

	        	OThpCntS email = new OThpCntS();
	        	OThpCntS tfno = new OThpCntS();
	        	data.oThpCntPT(Arrays.asList(new OThpCntP(), new OThpCntP()));
	        	data.getOThpCntPT().get(0).oThpCntS(email);
	        	data.getOThpCntPT().get(1).oThpCntS(tfno);
	        	email.cmpVal(cmpVal);
	        	email.thpDcmTypVal(tipDocum);
	        	email.thpDcmVal(codDocum);
	        	email.thpAcvVal(BigDecimal.ONE);
	        	email.dsbRow(NO);
	        	email.usrVal(usrVal);
	        	email.vldDat(date);
	        	email.mdfDat(date);
	        	email.cnhTypVal(getString(params.get("cnhTypValEmail"))); // "1"
	        	email.cnhUseTypVal(getString(params.get("cnhUseTypValEmail"))); // "6"
	        	email.cnhTxtVal(getString(in.getParameters().get("TXT_EMAIL")));
	        	email.cnhSqnVal(BigDecimal.ONE);
	        	email.cnhCck(NO);
	        	email.cnhNam("The email");
	        	email.dflCnh(SI);
	        	email.ptyCnh(SI);
	        	email.rfrThp(NO);
	        	email.acnOrgVal(ALTA_1);
	        	tfno.cmpVal(cmpVal);
	        	tfno.thpDcmTypVal(tipDocum);
	        	tfno.thpDcmVal(codDocum);
	        	tfno.thpAcvVal(BigDecimal.ONE);
	        	tfno.dsbRow(NO);
	        	tfno.usrVal(usrVal);
	        	tfno.vldDat(date);
	        	tfno.mdfDat(date);
	        	tfno.cnhTypVal(getString(params.get("cnhTypValTfno"))); // "2"
	        	tfno.cnhUseTypVal(getString(params.get("cnhUseTypValTfno"))); // "6"
	        	tfno.cnhTxtVal(getString(in.getParameters().get("NUM_TELEFONO")));
	        	tfno.cnhSqnVal(new BigDecimal(MOD_2));
	        	tfno.cnhCck(NO);
	        	tfno.cnhNam("The phone");
	        	tfno.dflCnh(SI);
	        	tfno.ptyCnh(NO);
	        	tfno.rfrThp(NO);
	        	tfno.acnOrgVal(ALTA_1);

	        	data.oThpInyP(new OThpInyP());
	        	data.getOThpInyP().oThpInyS(new OThpInyS());
	        	data.getOThpInyP().getOThpInyS().cmpVal(cmpVal);
	        	data.getOThpInyP().getOThpInyS().thpDcmTypVal(tipDocum);
	        	data.getOThpInyP().getOThpInyS().thpDcmVal(codDocum);
	        	data.getOThpInyP().getOThpInyS().thpAcvVal(BigDecimal.ONE);
	        	data.getOThpInyP().getOThpInyS().dsbRow(NO);
	        	data.getOThpInyP().getOThpInyS().vipVal(NO);
	        	data.getOThpInyP().getOThpInyS().lylVal(NO);
        	}

        	data.oThpPcmPT(new ArrayList<>());
        	int iCnt = 0;
        	BigDecimal pcmSqnVal = null;
        	BigDecimal initPcmSqnVal = calcMaxPcmSqnVal(thr);
        	Set<String> pcmTypValDfl = getPcmTypValDfl(thr);

        	for (Map<String, String> mp : mediosPago) {
        		String mpId = mp.get("MEDIO_PAGO");
	        	Map<String, Object> mpParams = (Map<String, Object>)params.get(mpId);
	        	
	        	if (mpParams != null) {
		        	Map<String, String> mpIn = new HashMap<>(mp);
		        	mpIn.putAll(inp);
		        	
		        	String pcmTypVal = getValue(mpParams, "pcmTypVal", mpIn);
		        	String pcmCssVal = getExtValue(mpParams, "pcmCssVal", mpIn);
		        	String pcmVal = getValue(mpParams, "pcmVal", mpIn);
		        	String pcmKeyVal = getValue(mpParams, "pcmKeyVal", mpIn);
		        	pcmSqnVal = existsMp(thr, pcmTypVal, pcmCssVal, pcmVal, pcmKeyVal);
		        	
	        		if (pcmSqnVal == null) {
		        		OThpPcmP pcmt = new OThpPcmP();
		        		OThpPcmS pcm = new OThpPcmS();
		        		pcmt.setOThpPcmS(pcm);
			        	data.getOThpPcmPT().add(pcmt);
	
			        	pcm.cmpVal(cmpVal);
			        	pcm.thpDcmTypVal(tipDocum);
			        	pcm.thpDcmVal(codDocum);
			        	pcm.thpAcvVal(BigDecimal.ONE);
			        	pcm.cnyVal(getValue(mpParams, "cnyVal", mpIn));
			        	pcm.bneVal(getValue(mpParams, "bneVal", mpIn));
			        	pcm.crnVal(getBD(getValue(mpParams, "crnVal", mpIn)));
			        	pcm.vldDat(date);
			        	pcm.dsbRow(NO);
			        	pcmSqnVal = initPcmSqnVal.add(new BigDecimal(++iCnt));
			        	pcm.pcmSqnVal(pcmSqnVal);
			        	pcm.pcmTypVal(pcmTypVal); // "1"
			        	pcm.pcmCssVal(pcmCssVal); // "1"
			        	pcm.entTypVal(getBD(getValue(mpParams, "entTypVal", mpIn))); // "7"
			        	pcm.hlrNam(getValue(mpParams, "hlrNam", mpIn));
			        	pcm.mskVal(getValue(mpParams, "mskVal", mpIn));
			        	pcm.tknTypVal(getValue(mpParams, "tknTypVal", mpIn)); // "TRN"
			        	pcm.tknVal(getValue(mpParams, "tknVal", mpIn));
			        	pcm.pcmVal(pcmVal);
			        	pcm.pcmKeyVal(pcmKeyVal);
			        	pcm.mvmPcmTypVal(getValue(mpParams, "mvmPcmTypVal", mpIn)); // "1"
			        	pcm.mvmPcmUseTypVal(getValue(mpParams, "mvmPcmUseTypVal", mpIn)); // "1"
			        	pcm.expMnh(getBD(getValue(mpParams, "expMnh", mpIn)));
			        	pcm.expYer(getBD(getValue(mpParams, "expYer", mpIn)));
			        	pcm.pcmCck(SI);
			        	pcm.setObsVal(getValue(mpParams, "obsVal", mpIn));
			        	pcm.pcmFav(getPcmFav(pcmSqnVal));
			        	pcm.dflPcm(getDflPcm(pcmTypVal, pcmTypValDfl));
			        	pcm.usrVal(usrVal);
			        	pcm.vldDat(date);
			        	pcm.mdfDat(date);
			        	pcm.acnOrgVal(ALTA_1);
	        		}
	        	}
        	}

        	callApi(usrVal, lngVal, data);

        	setOutput(lvSfvOut, pcmSqnVal, tipDocum, codDocum, "S".equals(getString(in.getParameters().get("MCA_ASEGURADO_IGUAL_TOMADOR"))));

        } catch (RestClientResponseException e) {
        	processApiError(lvSfvOut, false, e);
		} catch (Exception e) {
			String msg = String.format("Error calling insuredPartyApi.postProcessInsuredPartyComplete %s", e.getMessage());
			log.error(msg);
			Message lvMessagesItem = getMessage(msg, 3, 400, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		}
        return lvSfvOut;
    }
	
	private String getExtValue(Map<String, Object> mpParams, String fld, Map<String, String> inData) {
		if (inData.containsKey("TIPO_CC")) {
			return getValue(mpParams, fld + getString(inData.get("TIPO_CC")), inData);
		}
		return getValue(mpParams, fld, inData);
	}

	private void createInp(final SfvIn in, Map<String, String> inp) {
		for (Map.Entry<String,Object> ee : in.getParameters().entrySet()) {
			if (ee.getValue() == null) {
				inp.put(ee.getKey(), "");
			} else {
				inp.put(ee.getKey(), ee.getValue().toString());
			}
		}
	}

	private void setOutput(SfvOut lvSfvOut, BigDecimal pcmSqnVal, String tipDocum, String codDocum, boolean mismoTomador) {
		if (pcmSqnVal != null) {
			lvSfvOut.getParameters().put("PCM_SQN_VAL", pcmSqnVal.toString());
		}
		if (!mismoTomador) {
			lvSfvOut.getParameters().put("TIP_DOCUM_ALT", tipDocum);
			lvSfvOut.getParameters().put("COD_DOCUM_ALT", codDocum);
		}
	}

	private void callApi(final String usrVal, final String lngVal, OThpIpcC data) {
		if (data.getOThpPcmPT() != null && !data.getOThpPcmPT().isEmpty()) {
			insuredPartyApi.postProcessInsuredPartyComplete(data, usrVal, lngVal);
		}
	}

	private BigDecimal getCodCiudad(final SfvIn in, BigDecimal codProvincia) {
		BigDecimal codCiudad = getBD(in.getParameters().get("COD_LOCALIDAD"));
		if (codCiudad == null) {
			codCiudad = codProvincia;
		}
		return codCiudad;
	}

	private BigDecimal getCodProvincia(final SfvIn in, BigDecimal codEstado) {
		BigDecimal codProvincia = getBD(in.getParameters().get("COD_PROV"));
		if (codProvincia == null) {
			codProvincia = codEstado;
		}
		return codProvincia;
	}
	
	private String getPcmFav(BigDecimal pcmSqnVal) {
		return BigDecimal.ONE.equals(pcmSqnVal) ? SI : NO;
	}
	
	private String getEstadoCivil(Map<String, Object> in, Map<String, Object> params) {
		String st = getString(params.get("mrtStsVal"));
		if (in.containsKey("COD_EST_CIVIL")) {
			st = getString(in.get("COD_EST_CIVIL"));
		}
		return st;
	}
	
	private String getSexo(Map<String, Object> in, Map<String, Object> params) {
		String st = getString(params.get("sexVal"));
		if (in.containsKey("MCA_SEXO")) {
			st = getString(in.get("MCA_SEXO"));
		}
		return st;
	}
	
	private String getNacionalidad(Map<String, Object> in, Map<String, Object> params) {
		String st = getString(params.get("ntnVal"));
		if (in.containsKey("COD_NACIONALIDAD")) {
			st = getString(in.get("COD_NACIONALIDAD"));
		}
		return st;
	}
	
	private String getCodPais(Map<String, Object> in, Map<String, Object> params) {
		String codPais = getString(params.get("cnyVal"));
		if (in.containsKey("COD_PAIS")) {
			codPais = getString(in.get("COD_PAIS"));
		}
		return codPais;
	}
	
	private BigDecimal getCodEstado(Map<String, Object> in, Map<String, Object> params) {
		BigDecimal codEstado = getBD(params.get("sttVal"));
		if (in.containsKey("COD_ESTADO")) {
			codEstado = getBD(in.get("COD_ESTADO"));
		}
		return codEstado;
	}
	
	private String getDflPcm(String pcmTypVal, Set<String> pcmTypValDfl) {
		if (pcmTypValDfl.contains(pcmTypVal)) {
			return NO;
		} else {
			pcmTypValDfl.add(pcmTypVal);
			return SI;
		}
	}
	private String getExtDireccion(Map<String, Object> in) {
		StringBuilder sb = new StringBuilder();
		String s = getString(in.get("NOM_DOMICILIO3"));
		if (StringUtils.isNotEmpty(s)) {
			sb.append(s);
		}
		s = getString(in.get("ATR_DOMICILIO4"));
		if (StringUtils.isNotEmpty(s)) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(s);
		}
		return sb.toString();
	}

	private Set<String> getPcmTypValDfl(OThpIpcPC thr) {
		Set<String> bb = new HashSet<>();
		if (thr != null && thr.getOThpIpcC() != null && thr.getOThpIpcC().getOThpPcmPT() != null) {
			bb = thr.getOThpIpcC().getOThpPcmPT().stream().map(x -> x.getOThpPcmS().getPcmTypVal()).collect(Collectors.toSet());
		}
		return bb;
	}
	
	private BigDecimal existsMp(OThpIpcPC thr,String pcmTypVal, String pcmCssVal, String pcmVal, String pcmKeyVal) {
		if (thr != null && thr.getOThpIpcC() != null && thr.getOThpIpcC().getOThpPcmPT() != null) {
			Optional<OThpPcmP> mx = thr.getOThpIpcC().getOThpPcmPT().stream().filter(x -> isEq(pcmTypVal, x.getOThpPcmS().getPcmTypVal())
					&& isEq(pcmCssVal, x.getOThpPcmS().getPcmCssVal()) && isEq(pcmVal,x.getOThpPcmS().getPcmVal()) && isEq(pcmKeyVal, x.getOThpPcmS().getPcmKeyVal())).findFirst();
			if (mx.isPresent()) {
				return mx.get().getOThpPcmS().getPcmSqnVal();
			}
		}
		return null;
	}
	
	private boolean isEq(String s1, String s2) {
		return s1 != null && s1.equals(s2);
	}

	private BigDecimal calcMaxPcmSqnVal(OThpIpcPC thr) {
		if (thr != null && thr.getOThpIpcC() != null && thr.getOThpIpcC().getOThpPcmPT() != null) {
			Optional<OThpPcmP> mx = thr.getOThpIpcC().getOThpPcmPT().stream().max((x,y) -> x.getOThpPcmS().getPcmSqnVal().compareTo(y.getOThpPcmS().getPcmSqnVal()));
			if (mx.isPresent()) {
				 return mx.get().getOThpPcmS().getPcmSqnVal();
			}
		}
		return BigDecimal.ZERO;
	}
	
	private String getValue(Map<String, Object> mpParams, String fld, Map<String, String> inData) {
		Object param = mpParams.get(fld);
		if (param != null) {
			if (param instanceof String) {
				String pparam = getString(param);
				if (pparam.contains("${")) {
					processConcats(pparam, inData);
					StringSubstitutor sub = new StringSubstitutor(inData);
			    	return sub.replace(pparam);
				} else {
					return pparam;
				}
			} else {
				Map<String, Object> complexValue = (Map<String, Object>)param;
				String inkey = getString(complexValue.get("key"));
				String key = inData.get(inkey);
				Map<String, String> params = (Map<String, String>)complexValue.get("map");
				
				return params.get(key);
			}
		}
		return null;
	}
	
	private void processConcats(String pparam, Map<String, String> values) {
		Pattern p = Pattern.compile("\\$\\{concat\\((.+?)\\)\\}");
        Matcher m = p.matcher(pparam);
        while (m.find()) {
        	String token = m.group(0);
        	String tokenname = token.substring(2, token.length() - 1);
        	String tokenData = token.substring(9, token.length() - 2);
        	String[] items = tokenData.split(",");
        	List<String> tokenJoiner = new ArrayList<>();
        	for (String item : items) {
        		String it = item.trim();
        		if (values.containsKey(it)) {
        			String val = getString(values.get(it));
        			if (StringUtils.isNotEmpty(val)) {
        				tokenJoiner.add(val);
        			}
        		}
        	}
        	String finalData = tokenJoiner.stream().collect(Collectors.joining(" "));
        	values.put(tokenname, finalData);
        }
	}
}
