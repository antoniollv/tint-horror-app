package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlPlySmnDocQryDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("PlySmnDocQrySfv")
public class PlySmnDocQrySfvBeanImpl extends SfvWithConditionalBeanBase {
	
	@Autowired
    protected IDlPlySmnDocQryDAO dlPlySmnDocQryDAOImpl;
	
    @Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("InsuredPartySfvBeanImpl.execute start execute operation...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        
        List<OPlySmnDocQryS> respuesta;
        if (params.containsKey("like")) {
        	respuesta = dlPlySmnDocQryDAOImpl.getDocNamLike(cmpVal, new BigDecimal("999"), getString(in.getParameters().get("NOMBRE_LISTA")));
        } else {
        	respuesta = dlPlySmnDocQryDAOImpl.getDocNam(cmpVal, new BigDecimal("999"), getString(in.getParameters().get("NOMBRE_LISTA")));
        }
        lvSfvOut.getParameters().put("LISTADO", respuesta);
        
        return lvSfvOut;
    }
	

}
