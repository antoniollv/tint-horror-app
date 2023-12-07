package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.sfv.bo.InternalDto;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.ISfvBean;
import com.mapfre.tron.sfv.pgm.beans.impl.apierr.ApiError;
import com.mapfre.tron.sfv.pgm.mapper.SfvMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * The SfvWithConditionalBeanBase SfvBean implementation.
 * Base clase for other SFV beans to conditional executions
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j @SuppressWarnings("unchecked")
public abstract class SfvWithConditionalBeanBase implements ISfvBean {
	
	@Autowired
    protected SfvMapper sfvMapper;
	
	@Autowired
    protected InternalDto innerData;
	
	protected abstract SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params);
	
	@Override
    public SfvOut execute(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        
        boolean exec = !params.containsKey("conditions");
        if (!exec) {
        	List<Map<String, String>> conditions = (List<Map<String, String>>)params.get("conditions");
        	exec = evalConditions(in.getParameters(), conditions);
        }
        
        SfvOut out = sfvMapper.map(in);
        
        if (exec) {
        	log.debug("SfvWithConditionalBeanImpl conditions passed, executing logic...");
        	out = logic(in, cmpVal, usrVal, lngVal, params);
        }
        
        return out;
	}

	/**
	 * Evalueate conditions.
	 * @param in input service
	 * @param exec previous execution indicator
	 * @param conditions to eval
	 * @return actual execution indicator
	 */
	protected boolean evalConditions(final Map<String, Object> in, List<Map<String, String>> conditions) {
		boolean exec = false;
		for (int i = 0; i < conditions.size() && !exec; i++) {
			Map<String, String> condition = conditions.get(i);
			
			boolean execInner = true;
			for (Map.Entry<String, String> e : condition.entrySet()) {
			  String val1 = getString(in.get(e.getKey()));
			  String val2 = e.getValue();
			  execInner = evalExpression(val1, val2);
			  
			  if (!execInner) break;
			}
			
			exec = execInner;
		}
		return exec;
	}

	/**
	 * Evaluate condition.
	 * @param val1 value from input
	 * @param condition value from parametrization
	 * @return
	 */
	private boolean evalExpression(String val, String condition) {
		return (StringUtils.isEmpty(val) && StringUtils.isEmpty(condition)) // vacio (no informado)
			  || ("*".equals(condition) && StringUtils.isNotEmpty(val)) // * (informado)
			  || (!condition.startsWith("!") && condition.equals(val)) // (valor igual)
			  || (condition.startsWith("!") && !condition.substring(1).equals(val));  // "!..." (valor distinto)
	}
	
	protected Message getMessage(final String msgTxtVal, final Integer msgTypVal, final Integer msgVal, final String msgFldNam) {
        Message lvMessagesItem = new Message();
        lvMessagesItem.setMsgTxtVal(msgTxtVal);
        lvMessagesItem.setMsgTypVal(msgTypVal);
        lvMessagesItem.setMsgVal(msgVal);
        lvMessagesItem.setFldNam(msgFldNam);
        return lvMessagesItem;
    }
	
	protected String getString(Object o) {
    	if (o == null) return "";
    	return o.toString();
    }
	
	protected BigDecimal getBD(Object o) {
    	if (o == null) return null;
    	String sr = o.toString();
    	if (StringUtils.isNotEmpty(sr)) {
    		return new BigDecimal(sr);
    	}
    	return null;
    }
	
	protected void processApiError(SfvOut lvSfvOut, boolean skipError, RestClientResponseException e) {
		if (!skipError) {
			List<Message> msgs = ApiError.apiErrors(e);
			msgs.stream().forEach(lvSfvOut::addMessagesItem);
		}
	}
	
	protected int getSteIdnId(String steIdn) {
    	StringBuilder db = new StringBuilder();
    	boolean end = false;
    	for (int i = steIdn.length() - 1; i >=0  && !end; i--) {
    		if (steIdn.charAt(i) >= '0' && steIdn.charAt(i) <= '9') {
    			db.insert(0, steIdn.charAt(i));
    		} else {
    			end = true;
    		}
    	}
    	
    	return db.length() > 0 ? Integer.parseInt(db.toString()) : 0;
    }
}
