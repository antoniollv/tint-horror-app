package com.mapfre.nwt.commons.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.bo.ObjCPT;
import com.mapfre.nwt.trn.bo.ObjNwt;
import com.mapfre.nwt.trn.bo.ObjP;
import com.mapfre.nwt.trn.bo.ObjS;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

/**
 * Agrupación de funcionalidades comunes
 * 
 * @author fjlorente
 */
public final class NwtUtils {

    //------------------------------------------------------------------------------
    // comprueba terminacion OTrnPrcS
    //------------------------------------------------------------------------------
    public void isTrmOk(OTrnPrcS pmPrcS, String pmSrvNam) {  
        if(pmPrcS.getTrmVal() != null && !CTrn.TRM_VAL_OK.equals(pmPrcS.getTrmVal())) {
            NwtException nwtEx = null;
            if (!pmPrcS.getOTrnErrT().isEmpty() ) {
                nwtEx = new NwtException(pmPrcS.getOTrnErrT().get(0).toString());
            } else {
                nwtEx = new NwtException("There is no detail of error when calling backend service");
            }
        	nwtEx.setSrvNam(pmSrvNam);
        	nwtEx.addAll(pmPrcS.getOTrnErrT());
            throw nwtEx;
        }
    }
    //------------------------------------------------------------------------------
    // comprueba terminacion P
    //------------------------------------------------------------------------------
    public void isTrmOk(ObjP pmObjNwt, String pmSrvNam) {  
        String trm = pmObjNwt.getOTrnPrcS().getTrmVal();
        if(trm != null && !CTrn.TRM_VAL_OK.equals(trm)) {
            NwtException nwtEx = null;
            if (!pmObjNwt.getOTrnPrcS().getOTrnErrT().isEmpty()) {
                nwtEx = new NwtException(pmObjNwt.getOTrnPrcS().getOTrnErrT().get(0).toString());
            } else {
                nwtEx = new NwtException("There is no detail of error when calling backend service");
            }
        	nwtEx.setSrvNam(pmSrvNam);
        	nwtEx.addAll(pmObjNwt.getOTrnPrcS().getOTrnErrT());
        	if (!nwtEx.getErrors().isEmpty()) {
                throw nwtEx;
			}
        }
    }
    //------------------------------------------------------------------------------
    // comprueba terminacion CPT
    //------------------------------------------------------------------------------
    public void isTrmOk(ObjCPT pmObjNwt, String pmSrvNam) {  
        String trm = pmObjNwt.getPrcTrmVal();
        if(trm != null && !CTrn.TRM_VAL_OK.equals(trm)) {
        	NwtException nwtEx = new NwtException();
        	nwtEx.setSrvNam(pmSrvNam);
            List<OTrnPrcS> lvPrcT = getPrcList(pmObjNwt.getObjT());
            for (OTrnPrcS lvPrcS : lvPrcT) {
				if (!lvPrcS.getOTrnErrT().isEmpty()) {
					nwtEx.addAll(lvPrcS.getOTrnErrT());
				}
			}
            if (!nwtEx.getErrors().isEmpty()) {
                throw nwtEx;
			}
        }
    }
    //------------------------------------------------------------------------------
    // asigna los valores del segundo objeto al primero
    // lo que no este nulo en el objeto origen se machaca sobre el objeto destino
    //------------------------------------------------------------------------------
    public ObjS assignValuesObjS(ObjS pmObjDst, ObjS pmObjOrg) {
        // si destino es nulo, se devuelve objeto origen
        if (pmObjDst == null) {
            pmObjDst = pmObjOrg;
        // si origen es nulo, se devuelve objeto destino
        } else if(pmObjOrg != null) {
            BeanWrapper objNwtOrgWrapper = new BeanWrapperImpl(pmObjOrg);
            BeanWrapper objNwtDstWrapper = new BeanWrapperImpl(pmObjDst);
            // Se obtienen las propiedades del objeto origen
            PropertyDescriptor[] orgProps = objNwtOrgWrapper.getPropertyDescriptors();

            // Se recorren las propiedades del objeto origen
            for (PropertyDescriptor orgProp : orgProps) {
                // Se obtiene el nombre de la propiedad
                String propName = orgProp.getName();
                // Se obtiene el valor de la propiedad en destino y origen
                Object objOrgProp = objNwtOrgWrapper.getPropertyValue(propName);

                // Si no es nulo se asigna el valor
                if (objOrgProp != null && !"class".equals(propName)) {
                    objNwtDstWrapper.setPropertyValue(propName, objOrgProp);
                }
            }
        }
        return pmObjDst;
    }
    //------------------------------------------------------------------------------
    // recibe una lista de objetos y devuelve todos los OTrnPrcS que encuentre dentro
    //------------------------------------------------------------------------------
    public List<OTrnPrcS> getPrcList(List<?> pmObjT) {
    	List<OTrnPrcS> rtPrcT = new ArrayList<>();
        // se recorre la lista PT o CT
        for (Object lvObj : pmObjT) {
        	rtPrcT.addAll(getPrcList(lvObj));
        }
        return rtPrcT;
    }
    // recibe un objeto y devuelve todos los OTrnPrcS que encuentre dentro
    public List<OTrnPrcS> getPrcList(Object pmObj){
    	List<OTrnPrcS> rtPrcT = new ArrayList<>();
    	Class<? extends Object> lvClass = pmObj.getClass();
    	// si el objeto es newtron
    	if(ObjNwt.class.isAssignableFrom(lvClass)){
    		// si el objeto es proceso
    		if(OTrnPrcS.class.isAssignableFrom(lvClass)){
    			rtPrcT.add((OTrnPrcS) pmObj);
    		// si el objeto no es simple S
    		}else if(!ObjS.class.isAssignableFrom(lvClass)){
    	        BeanWrapper objNwtWrapper = new BeanWrapperImpl(pmObj);
    	        // Se obtienen las propiedades del objeto
    	        PropertyDescriptor[] props = objNwtWrapper.getPropertyDescriptors();
    	        // Se recorren las propiedades del objeto
    	        getPrcListAux(rtPrcT, objNwtWrapper, props);
    		}
    	}
    	return rtPrcT;
    }
    private void getPrcListAux(List<OTrnPrcS> rtPrcT, BeanWrapper objNwtWrapper, PropertyDescriptor[] props) {
        for (PropertyDescriptor prop : props) {
            // Se obtiene el nombre de la propiedad
            String propName = prop.getName();
            // Se obtiene el valor de la propiedad
            Object objProp = objNwtWrapper.getPropertyValue(propName);
            // Si el objeto es no nulo y no es el propio objeto
            if (objProp != null && !"class".equals(propName)) {
                // Si es una lista
                if (objProp.getClass().isAssignableFrom(ArrayList.class)) {
                	rtPrcT.addAll(getPrcList((List<?>) objProp));
                } // Para el resto de objetos NWT
                else if (ObjNwt.class.isAssignableFrom(objProp.getClass())) {
                	rtPrcT.addAll(getPrcList(objProp));
                }
            }
        }
    }

}