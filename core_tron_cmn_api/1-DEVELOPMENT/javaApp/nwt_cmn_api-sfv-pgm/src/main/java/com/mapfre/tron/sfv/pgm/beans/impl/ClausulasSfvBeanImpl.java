package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.Clausula;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlA9990011DAO;

import lombok.extern.slf4j.Slf4j;

/**
 * The PaymentMethods Sfv Bean implementation.
 * Return payment methos depending on num payments per year or previous payment method
 * CACHED
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("ClausulasSfv")
public class ClausulasSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	@Autowired
    private IDlA9990011DAO iDlA9990011DAO;
	
	@Override
	public SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
            log.info("ClausulasSfvBeanImpl.execute start execute operation...");
        }
		
		SfvOut lvSfvOut = sfvMapper.map(in);
		
		String codClausula = getString(in.getParameters().get("COD_CLAUSULA"));
		
		
		try {
			List<Clausula> respuesta = iDlA9990011DAO.getClausula(cmpVal, codClausula);
			List<String> clausulas = new ArrayList<>();
			StringBuilder cl = new StringBuilder();
			Integer oldNumSecu = null;
			for (Clausula c : respuesta) {
				if (oldNumSecu == null) {
					cl.append(c.getTxtClausula());
				} else if (c.getNumSecuencia().equals(oldNumSecu + 1)) {
					cl.append(" ").append(c.getTxtClausula());
				} else {
					clausulas.add(cl.toString());
					cl.setLength(0);
					cl.append(c.getTxtClausula());
				}
				oldNumSecu = c.getNumSecuencia();
			}
			clausulas.add(cl.toString());
			lvSfvOut.getParameters().put("CLAUSULAS", clausulas);

		} catch (Exception e) {
			String msg = String.format("Error calling iDlA9990011DAO %s", e.getMessage());
			log.error(msg);
			Message lvMessagesItem = getMessage(msg, 3, 500, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		}
		
		return lvSfvOut;
	}
}
