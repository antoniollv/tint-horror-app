package com.mapfre.tron.api.cmn;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.mapfre.nwt.ard.c.CArd;
import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.pid.c.CPid;
import com.mapfre.nwt.tcd.c.CTcd;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.ply.acr.bo.OPlyAcrP;
import com.mapfre.nwt.trn.ply.acr.bo.OPlyAcrS;
import com.mapfre.nwt.trn.ply.agc.bo.OPlyAgcP;
import com.mapfre.nwt.trn.ply.agc.bo.OPlyAgcS;
import com.mapfre.nwt.trn.ply.anx.bo.OPlyAnxP;
import com.mapfre.nwt.trn.ply.anx.bo.OPlyAnxS;
import com.mapfre.nwt.trn.ply.atc.bo.OPlyAtcC;
import com.mapfre.nwt.trn.ply.atc.bo.OPlyAtcPC;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.ply.avc.bo.OPlyAvcPC;
import com.mapfre.nwt.trn.ply.brw.bo.OPlyBrwP;
import com.mapfre.nwt.trn.ply.brw.bo.OPlyBrwS;
import com.mapfre.nwt.trn.ply.c1c.bo.OPlyC1cC;
import com.mapfre.nwt.trn.ply.c1c.bo.OPlyC1cPC;
import com.mapfre.nwt.trn.ply.cci.bo.OPlyCciP;
import com.mapfre.nwt.trn.ply.cci.bo.OPlyCciS;
import com.mapfre.nwt.trn.ply.cet.bo.OPlyCetP;
import com.mapfre.nwt.trn.ply.cvc.bo.OPlyCvcPC;
import com.mapfre.nwt.trn.ply.cvr.bo.OPlyCvrP;
import com.mapfre.nwt.trn.ply.cvr.bo.OPlyCvrS;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaP;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaS;
import com.mapfre.nwt.trn.ply.ine.bo.OPlyIneP;
import com.mapfre.nwt.trn.ply.ine.bo.OPlyIneS;
import com.mapfre.nwt.trn.ply.oca.bo.OPlyOcaP;
import com.mapfre.nwt.trn.ply.rsk.bo.OPlyRskP;
import com.mapfre.nwt.trn.ply.rsk.bo.OPlyRskS;
import com.mapfre.nwt.trn.ply.utc.bo.OPlyUtcCPT;
import com.mapfre.nwt.trn.ply.utc.bo.OPlyUtcP;
import com.mapfre.nwt.trn.trn.dpn.bo.OTrnDpnS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.fld.bo.OTrnFldS;

/**
 * Agrupación de funcionalidades comunes reutilizadas en diferentes clases
 * 
 * @author fjlorente
 */
public final class IssueUtils {
    
    final NwtUtils nwtUtils = new NwtUtils();

    //------------------------------------------------------------------------------
    // comprueba terminacion y controles tecnicos de rechazo 
    //------------------------------------------------------------------------------
    public void isUtcOk(OPlyUtcCPT pmUtcCPT, String pmSrvNam){  
        nwtUtils.isTrmOk(pmUtcCPT, pmSrvNam);
        for (OPlyUtcP lvUtcP : pmUtcCPT.getOPlyUtcPT()) {
            if(lvUtcP.getOPlyUtcS().getRjcTypVal().equals(CTcd.RJC_TYP_RJC)){
                OTrnErrS lvErrS = new OTrnErrS();
                lvErrS.setErrIdnVal(lvUtcP.getOPlyUtcS().getRjcTypNam());
                lvErrS.setErrNam(lvUtcP.getOPlyUtcS().getErrNam());
                lvErrS.setErrVal(lvUtcP.getOPlyUtcS().getErrVal());
                lvErrS.setPrpNam(lvUtcP.getOPlyUtcS().getErrObsVal());
                NwtException nwtEx = new NwtException();
                nwtEx.add(lvErrS);
                nwtEx.setSrvNam(pmSrvNam);
                throw nwtEx;
            }
        }
    }

    //------------------------------------------------------------------------------------
    // funcionalidad comun ISU
    //------------------------------------------------------------------------------------
    
    //------------------------------------------------------------------------------
    // Obtener tomador
    //------------------------------------------------------------------------------
    public OPlyIneP getInePlh(List<OPlyIneP> pmInePT){
        List<OPlyIneP> lvOPlyInePT = new ArrayList<OPlyIneP>();
        
        if (pmInePT != null && !pmInePT.isEmpty()) {
            for (OPlyIneP lvIneP : pmInePT) {
                if(lvIneP.getOPlyIneS().getBnfTypVal().equals(CPid.INE_TYP_PLH)){
                    lvOPlyInePT.add(lvIneP);
                    break;
                }
            }
        }
        
        return lvOPlyInePT.get(0);
    }
    //------------------------------------------------------------------------------
    // Obtener las intervenciones correspondientes del listado 
    //------------------------------------------------------------------------------
    public List<OPlyIneP> getBnfTypPT(List<OPlyIneP> pmInePT, 
                                       List<OPlyIneP> lvBnfTypPT, 
                                       BigDecimal pmRskVal) {
        List<OPlyIneP> lvOPlyInePT = new ArrayList<OPlyIneP>();
        
        if (pmInePT != null && !pmInePT.isEmpty()) {
            for (OPlyIneP lvIneP : pmInePT) {
                if(lvIneP.getOPlyIneS().getRskVal().equals(pmRskVal)){
                    getBnfTypPTAux(lvBnfTypPT, lvOPlyInePT, lvIneP);
                }
            }
        }
        
        return lvOPlyInePT;
    }

    //------------------------------------------------------------------------------
    // Obtener las intervenciones correspondientes del listado 
    //------------------------------------------------------------------------------
    private void getBnfTypPTAux(List<OPlyIneP> lvBnfTypPT, List<OPlyIneP> lvOPlyInePT, OPlyIneP lvIneP) {
        for(OPlyIneP lvBnf : lvBnfTypPT){
            if(lvIneP.getOPlyIneS().getBnfTypVal().equals(lvBnf.getOPlyIneS().getBnfTypVal())){
                lvOPlyInePT.add(lvIneP);
            }
        }
    }

    //------------------------------------------------------------------------------
    // Obtener un array(Hashtable) con los atributos separados por nivel, subnivel y riesgo
    //------------------------------------------------------------------------------
    public Hashtable<String, List<OPlyAtcC>> getHashTableAtc(List<OPlyAtcC> pmAtcCT, String pmPlyVal){
        Hashtable<String, List<OPlyAtcC>> lvAtcCTHash = new Hashtable<String, List<OPlyAtcC>>();
        
        // se ordenan por numero de secuencia para asegurar que se validan en el orden correcto
        Collections.sort(pmAtcCT, new OPlyAtcCComparator());
        
        if (pmAtcCT != null && !pmAtcCT.isEmpty()) {
            for (OPlyAtcC lvAtcC : pmAtcCT) {
                //---------------------------------
                lvAtcC.getOPlyAtrP().getOPlyAtrS().setPlyVal(pmPlyVal);
                lvAtcC.getOPlyAtrP().getOPlyAtrS().setQtnVal(pmPlyVal);
                lvAtcC.getOPlyAtrP().getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_MDF);
                for (OPlyOcaP lvOcaP : lvAtcC.getOPlyOcaPT()) {
                    lvOcaP.getOPlyOcaS().setPlyVal(pmPlyVal);
                    lvOcaP.getOPlyOcaS().setQtnVal(pmPlyVal);
               	    lvOcaP.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_MDF);
                }
                //---------------------------------
                String name = getAtcId(lvAtcC.getOPlyAtrP().getOPlyAtrS().getLvlTypVal(), 
                                       lvAtcC.getOPlyAtrP().getOPlyAtrS().getSvlTypVal(),
                                       lvAtcC.getOPlyAtrP().getOPlyAtrS().getRskVal());
                if(!lvAtcCTHash.containsKey(name)){
                    lvAtcCTHash.put(name, new ArrayList<OPlyAtcC>());
                }
                lvAtcCTHash.get(name).add(lvAtcC);
            }
        }
        return lvAtcCTHash;
    }
    //------------------------------------------------------------------------------
    // Obtener indice atributo para Hashtable
    //------------------------------------------------------------------------------
    public String getAtcId(String pmLvlTypVal, String pmSvlTypVal, BigDecimal pmRskVal){
        return new StringBuilder()
                .append("L")
                .append(pmLvlTypVal)
                .append("S")
                .append(pmSvlTypVal)
                .append("R")
                .append(pmRskVal)
                .toString();
    }
    //------------------------------------------------------------------------------
    // Obtener los accesorios correspondientes a un riesgo
    //------------------------------------------------------------------------------
    public List<OPlyAcrP> getRskAcr(List<OPlyAcrP> pmAcrPT, BigDecimal pmRskVal){
        List<OPlyAcrP> rtAcrPT = new ArrayList<OPlyAcrP>();
        
        if (pmAcrPT != null && !pmAcrPT.isEmpty()) {
            for (OPlyAcrP lvAcrP : pmAcrPT) {
                if (lvAcrP.getOPlyAcrS().getRskVal() == pmRskVal) {
                    rtAcrPT.add(lvAcrP);
                }
            }
        }
        return rtAcrPT;
    }
    //------------------------------------------------------------------------------
    // Obtener un objeto agravantes complejo
    //------------------------------------------------------------------------------
    public ArrayList<OPlyAvcPC> getAvcCT(BigDecimal pmRskVal, 
                                     BigDecimal pmCvrVal, 
                                     List<OPlyAgcP> pmAgcPT, 
                                     Hashtable<String, List<OPlyAtcC>> pmAtcCTHash){
        String atrLvlName;
        ArrayList<OPlyAvcPC> rtAvcCT = new ArrayList<OPlyAvcPC>();
        // creamos objeto para iteraciones de Avc
        OPlyAvcPC lvAvcPC;
        List<OPlyAtrP> lvAtrPT;
        // se recorren los agravantes
        // oPlyAvc  CT 
        // oPlyAgc  P --------------------------------------------------------
        if (pmAgcPT != null && !pmAgcPT.isEmpty()) {
            for (OPlyAgcP lvAgcP : pmAgcPT) {
                // si corresponde al riesgo y cobertura que queremos tratar
                if (lvAgcP.getOPlyAgcS().getRskVal() == pmRskVal &&
                    lvAgcP.getOPlyAgcS().getCvrVal() == pmCvrVal) {
                    // se inicializa el objeto agravante completo
                    lvAvcPC = new OPlyAvcPC();
                    // se asigna el agravante
                    lvAgcP.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_CRT);
                    lvAvcPC.getOPlyAvcC().setOPlyAgcP(lvAgcP);
                    // se buscan los atributos que pudiera tener asociados  (nivel '4' Agravante / subnivel '1' Comun)
                    // oPlyAtr  PT --------------------------------------------------------
                    lvAtrPT = new ArrayList<OPlyAtrP>();
                    atrLvlName = getAtcId(CArd.LVL_TYP_AGC, CArd.SVL_TYP_CMN, pmRskVal);
                    if (pmAtcCTHash.get(atrLvlName) != null && !pmAtcCTHash.get(atrLvlName).isEmpty()) {
                        getAvcCTAux(pmAtcCTHash, atrLvlName, lvAtrPT, lvAgcP);
                        lvAvcPC.getOPlyAvcC().setOPlyAtrPT(lvAtrPT);
                    }
                    // se asigna el objeto a la lista de retorno
                    rtAvcCT.add(lvAvcPC);
                }
            }
        }
        return rtAvcCT;
    }
    //------------------------------------------------------------------------------
    // Obtener un objeto agravantes complejo
    //------------------------------------------------------------------------------
    private void getAvcCTAux(Hashtable<String, List<OPlyAtcC>> pmAtcCTHash, String atrLvlName, List<OPlyAtrP> lvAtrPT,
            OPlyAgcP lvAgcP) {
        OPlyAtrP lvAtrP;
        for(OPlyAtcC lvAtcC : pmAtcCTHash.get(atrLvlName)) {
            lvAtrP = lvAtcC.getOPlyAtrP(); 
            // si el atributo coincide en cobertura y agravante
            if(lvAtrP.getOPlyAtrS().getCvrVal() == lvAgcP.getOPlyAgcS().getCvrVal() &&
               lvAtrP.getOPlyAtrS().getAgcVal() == lvAgcP.getOPlyAgcS().getAgcVal()){
                // se guarda el atributo en la lista
                lvAtrPT.add(lvAtrP);
            }
        }
    }
    //------------------------------------------------------------------------------
    // Obtener desglose conjunto partiendo del objeto cobertura completo del previo y los desgloses de entrada
    // se recorren los desgloses que se definen en el previo
    // en caso de que tengan importes modificables y esten informados como parametros de entrada se guarda entrada
    // en caso contrario se guarda lo que viene del previo
    //------------------------------------------------------------------------------
    public ArrayList<OPlyBrwP> getBrwPT(ArrayList<OPlyBrwP> pmPrrBrwPT,
                                   List<OPlyBrwP> pmInBrwPT,
                                   BigDecimal pmRskVal,
                                   BigDecimal pmCvrVal){
    	ArrayList<OPlyBrwP> rtBrwPT = new ArrayList <OPlyBrwP>();
        Boolean lvBrwMdf = false;
        Boolean lvInBrw = false;

        // se comprueba que el previo tenga desgloses para la cobertura  
        if (pmPrrBrwPT != null && !pmPrrBrwPT.isEmpty()) {
            // se comprueba que tengamos desgloses como parametro de entrada
            if(pmInBrwPT != null && !pmInBrwPT.isEmpty()){
                //----------------------------------------------------------------------------
                // se recorren los desgloses que se definen en el previo
                for(OPlyBrwP loopPrrBrwP : pmPrrBrwPT) {
                    // se recorre su fldT
                    lvBrwMdf = getBrwPTFirst(lvBrwMdf, loopPrrBrwP);
                    // si tiene importes modificables
                    lvInBrw = getBrwPTSecond(pmInBrwPT, pmRskVal, pmCvrVal, rtBrwPT, lvBrwMdf, lvInBrw, loopPrrBrwP);
                    // si no se ha añadido el desglose de entrada
                    if (!lvInBrw) {
                        // se añade el desglose del previo
                        rtBrwPT.add(loopPrrBrwP);
                    }
                    lvBrwMdf = false;
                    lvInBrw = false;
                }
                //----------------------------------------------------------------------------
            }else{
                // si no hay desgloses de entrada se asigna directamente lo que dice el previo
                rtBrwPT = pmPrrBrwPT;
            }
        }
    
        return rtBrwPT;
    }

    private Boolean getBrwPTSecond(List<OPlyBrwP> pmInBrwPT, BigDecimal pmRskVal, BigDecimal pmCvrVal,
            List<OPlyBrwP> rtBrwPT, Boolean lvBrwMdf, Boolean lvInBrw, OPlyBrwP loopPrrBrwP) {
        if(lvBrwMdf){
            //----------------------------------------------------------
            // se recorren los desgloses que recibimos por parametro
            for(OPlyBrwP loopInBrwP : pmInBrwPT) {
                // si el desglose corresponde con el riesgo, cobertura y desglose que queremos tratar
                if(loopInBrwP.getOPlyBrwS().getRskVal().compareTo(pmRskVal) == 0 &&
                   loopInBrwP.getOPlyBrwS().getCvrVal().compareTo(pmCvrVal) == 0 &&
                   loopInBrwP.getOPlyBrwS().getEcmBrwCncVal().compareTo(loopPrrBrwP.getOPlyBrwS().getEcmBrwCncVal()) == 0){
                    // se guarda el desglose en la lista
                    loopInBrwP.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_CRT);
                    rtBrwPT.add(loopInBrwP);
                    // se indica que ya se añadio el desglose de entrada
                    lvInBrw = true;
                    break;
                }
            }
            //----------------------------------------------------------
        }
        return lvInBrw;
    }

    private Boolean getBrwPTFirst(Boolean lvBrwMdf, OPlyBrwP loopPrrBrwP) {
        for(OTrnFldS loopOTrnFldS : loopPrrBrwP.getOTrnPrcS().getOTrnFldT()) {
            // se comprueba si el desglose tiene importes modificables
            if( (loopOTrnFldS.getFldNam().equals("ANL_AMN") || loopOTrnFldS.getFldNam().equals("ENR_AMN")) && 
               loopOTrnFldS.getMdf().equals(CIns.YES) ){
                lvBrwMdf = true;
                break;
            }
        }
        return lvBrwMdf;
    }
   
    
    private void getCvcCTSecond(List<OPlyAgcP> pmAgcPT, Hashtable<String, List<OPlyAtcC>> pmAtcCTHash,
            List<OPlyBrwP> pmBrwPT, BigDecimal pmRskVal, List<OPlyCvcPC> rtCvcCT, BigDecimal lvCvrVal,
            OPlyCvcPC loopPrrCvcPC, OPlyCvrP loopInCvrP) {
        OPlyCvcPC lvCvcPC;
        if (loopInCvrP.getOPlyCvrS().getRskVal().compareTo(pmRskVal) == 0 && 
                loopInCvrP.getOPlyCvrS().getCvrVal().compareTo(lvCvrVal) == 0) {
            //----------------------------------------------------------------------------------------
            //----------------------------------------------------------------------------------------
            // se copia el valor de "importe capital" a "capital texto" para su tratamiento en PL
            if(loopInCvrP.getOPlyCvrS().getCplAmn() != null) {
                loopInCvrP.getOPlyCvrS().setCplTxt(loopInCvrP.getOPlyCvrS().getCplAmn().toString());
            }
            loopInCvrP.setOTrnPrcS(loopPrrCvcPC.getOPlyCvcC().getOPlyCvrP().getOTrnPrcS());
            // se inicializa el objeto cobertura completo 
            lvCvcPC = new OPlyCvcPC();
            lvCvcPC.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_CRT);
            // se asigna el objeto cobertura --------------------------------------------------------
            loopInCvrP.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_CRT);
            lvCvcPC.getOPlyCvcC().setOPlyCvrP(loopInCvrP);
            // se asignan los agravantes --------------------------------------------------------
            lvCvcPC.getOPlyCvcC().setOPlyAvcCT(
                    getAvcCT(pmRskVal, lvCvrVal, pmAgcPT, pmAtcCTHash)
            );
            // oPlyAtc  CT ----------------------------------------------------------------------
            // se asignan los atributos y ocurrencias (nivel '3' Coberturas / subnivel '1' Comun)
            getCvcCTFirst(pmAtcCTHash, pmBrwPT, pmRskVal, rtCvcCT, lvCvcPC, lvCvrVal, loopPrrCvcPC);
            
            //----------------------------------------------------------------------------------------
            //----------------------------------------------------------------------------------------
        }
    }

    private void getCvcCTFirst(Hashtable<String, List<OPlyAtcC>> pmAtcCTHash, List<OPlyBrwP> pmBrwPT,
            BigDecimal pmRskVal, List<OPlyCvcPC> rtCvcCT, OPlyCvcPC lvCvcPC, BigDecimal lvCvrVal,
            OPlyCvcPC loopPrrCvcPC) {
        String atrLvlName;
        OPlyAtcPC lvAtcPC;
        lvCvcPC.getOPlyCvcC().setOPlyAtcCT(new ArrayList <OPlyAtcPC>());
        atrLvlName = getAtcId(CArd.LVL_TYP_CVR, CArd.SVL_TYP_CMN, pmRskVal);
        if (pmAtcCTHash.get(atrLvlName) != null && !pmAtcCTHash.get(atrLvlName).isEmpty()) {
            for(OPlyAtcC loopAtcC : pmAtcCTHash.get(atrLvlName)) {
            	// comprobamos que el atributo de nivel cobertura traiga el numero de cobertura al que pertenece
            	if (loopAtcC.getOPlyAtrP().getOPlyAtrS().getCvrVal() == null) {
                    String logerror = new StringBuilder()
                    	.append("Coverage attribute ")
                    	.append(loopAtcC.getOPlyAtrP().getOPlyAtrS().getFldNam())
                    	.append(" has no cvrVal.")
                    	.toString();
                    throw new NwtException(logerror);
				}
                // si el atributo coincide en cobertura y agravante
                if(loopAtcC.getOPlyAtrP().getOPlyAtrS().getCvrVal().compareTo(lvCvrVal) == 0){
                     // se guarda el atributo en la lista
                    lvAtcPC = new OPlyAtcPC();
                    lvAtcPC.setOPlyAtcC(loopAtcC);
                    lvCvcPC.getOPlyCvcC().getOPlyAtcCT().add(lvAtcPC);
                }
            }                   
        }
        // oPlyBrw  PT --------------------------------------------------------
        lvCvcPC.getOPlyCvcC().setOPlyBrwPT(
                getBrwPT(loopPrrCvcPC.getOPlyCvcC().getOPlyBrwPT(), pmBrwPT, pmRskVal, lvCvrVal)
        );
        
        rtCvcCT.add(lvCvcPC);
    }
    //------------------------------------------------------------------------------
    // Obtener anexos en formato String
    //------------------------------------------------------------------------------
    public String getRskAnxTxtVal(List<OPlyAnxP> pmAnxPT){
        StringBuilder rtAnxTxt = new StringBuilder().append("\n");
        // recorremos anexos
        if (pmAnxPT != null && !pmAnxPT.isEmpty()) {
            for (OPlyAnxP lvAnxP : pmAnxPT) {
                rtAnxTxt.append(lvAnxP.getOPlyAnxS().getTxtVal()).append("\n");
            }
        }
        return rtAnxTxt.toString();
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los elementos del array de coaseguro y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyCciP> getCciPT(List<OPlyCciS> pmCciT, String pmPlyVal){
        List<OPlyCciP> rtCciPT = new ArrayList<OPlyCciP>();
        OPlyCciP lvCciP;
        if (pmCciT != null && !pmCciT.isEmpty()) {
            for (OPlyCciS lvCciS : pmCciT) {
                lvCciP = new OPlyCciP();
                lvCciS.setPlyVal(pmPlyVal);
                lvCciS.setQtnVal(pmPlyVal);
                lvCciP.setOPlyCciS(lvCciS);
                rtCciPT.add(lvCciP);
            }
        }
        return rtCciPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los intervinientes y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyIneP> getInePT(List<OPlyIneS> pmIneT, String pmPlyVal){
        List<OPlyIneP> rtInePT = new ArrayList<OPlyIneP>();
        OPlyIneP lvIneP;
        if (pmIneT != null && !pmIneT.isEmpty()) {
            for (OPlyIneS lvIneS : pmIneT) {
                lvIneP = new OPlyIneP();
                lvIneS.setPlyVal(pmPlyVal);
                lvIneS.setQtnVal(pmPlyVal);
                lvIneP.setOPlyIneS(lvIneS);
                rtInePT.add(lvIneP);
            }
        }
        return rtInePT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los agentes y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyInaP> getInaPT(List<OPlyInaS> pmInaT, String pmPlyVal){
        List<OPlyInaP> rtInaPT = new ArrayList<OPlyInaP>();
        OPlyInaP lvInaP;
        if (pmInaT != null && !pmInaT.isEmpty()) {
            for (OPlyInaS lvInaS : pmInaT) {
                lvInaP = new OPlyInaP();
                lvInaS.setPlyVal(pmPlyVal);
                lvInaS.setQtnVal(pmPlyVal);
                lvInaP.setOPlyInaS(lvInaS);
                rtInaPT.add(lvInaP);
            }
        }
        return rtInaPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los riesgos y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyRskP> getRskPT(List<OPlyRskS> pmRskT, String pmPlyVal){
        List<OPlyRskP> rtRskPT = new ArrayList<OPlyRskP>();
        OPlyRskP lvRskP;
        if (pmRskT != null && !pmRskT.isEmpty()) {
            for (OPlyRskS lvRskS : pmRskT) {
                lvRskP = new OPlyRskP();
                lvRskS.setPlyVal(pmPlyVal);
                lvRskS.setQtnVal(pmPlyVal);
                lvRskP.setOPlyRskS(lvRskS);
                rtRskPT.add(lvRskP);
            }
        }
        return rtRskPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los accesorios y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyAcrP> getAcrPT(List<OPlyAcrS> pmAcrT, String pmPlyVal){
        List<OPlyAcrP> rtAcrPT = new ArrayList<OPlyAcrP>();
        OPlyAcrP lvAcrP;
        if (pmAcrT != null && !pmAcrT.isEmpty()) {
            for (OPlyAcrS lvAcrS : pmAcrT) {
                lvAcrP = new OPlyAcrP();
                lvAcrS.setPlyVal(pmPlyVal);
                lvAcrS.setQtnVal(pmPlyVal);
                lvAcrP.setOPlyAcrS(lvAcrS);
                rtAcrPT.add(lvAcrP);
            }
        }
        return rtAcrPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todas las coberturas y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyCvrP> getCvrPT(List<OPlyCvrS> pmCvrT, String pmPlyVal){
        List<OPlyCvrP> rtCvrPT = new ArrayList<OPlyCvrP>();
        OPlyCvrP lvCvrP;
        if (pmCvrT != null && !pmCvrT.isEmpty()) {
            for (OPlyCvrS lvCvrS : pmCvrT) {
                lvCvrP = new OPlyCvrP();
                lvCvrS.setPlyVal(pmPlyVal);
                lvCvrS.setQtnVal(pmPlyVal);
                lvCvrP.setOPlyCvrS(lvCvrS);
                rtCvrPT.add(lvCvrP);
            }
        }
        return rtCvrPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los agravantes y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyAgcP> getAgcPT(List<OPlyAgcS> pmAgcT, String pmPlyVal){
        List<OPlyAgcP> rtAgcPT = new ArrayList<OPlyAgcP>();
        OPlyAgcP lvAgcP;
        if (pmAgcT != null && !pmAgcT.isEmpty()) {
            for (OPlyAgcS lvAgcS : pmAgcT) {
                lvAgcP = new OPlyAgcP();
                lvAgcS.setPlyVal(pmPlyVal);
                lvAgcS.setQtnVal(pmPlyVal);
                lvAgcP.setOPlyAgcS(lvAgcS);
                rtAgcPT.add(lvAgcP);
            }
        }
        return rtAgcPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los desgloses y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyBrwP> getBrwPT(List<OPlyBrwS> pmBrwT, String pmPlyVal){
        List<OPlyBrwP> rtBrwPT = new ArrayList<OPlyBrwP>();
        OPlyBrwP lvBrwP;
        if (pmBrwT != null && !pmBrwT.isEmpty()) {
            for (OPlyBrwS lvBrwS : pmBrwT) {
                lvBrwP = new OPlyBrwP();
                lvBrwS.setPlyVal(pmPlyVal);
                lvBrwS.setQtnVal(pmPlyVal);
                lvBrwP.setOPlyBrwS(lvBrwS);
                rtBrwPT.add(lvBrwP);
            }
        }
        return rtBrwPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todos los anexos y devuelve un PT
    //------------------------------------------------------------------------------
    public List<OPlyAnxP> getAnxPT(List<OPlyAnxS> pmAnxT, String pmPlyVal){
        List<OPlyAnxP> rtAnxPT = new ArrayList<OPlyAnxP>();
        OPlyAnxP lvAnxP;
        if (pmAnxT != null && !pmAnxT.isEmpty()) {
            for (OPlyAnxS lvAnxS : pmAnxT) {
                lvAnxP = new OPlyAnxP();
                lvAnxS.setPlyVal(pmPlyVal);
                lvAnxS.setQtnVal(pmPlyVal);
                lvAnxP.setOPlyAnxS(lvAnxS);
                rtAnxPT.add(lvAnxP);
            }
        }
        return rtAnxPT;
    }
    //------------------------------------------------------------------------------
    // actualiza numero poliza para todas las clausulas
    //------------------------------------------------------------------------------
    public List<OPlyC1cPC> setC1cPlyVal(List<OPlyC1cC> pmC1cCT, String pmPlyVal){
        List<OPlyC1cPC> rtC1cCT= new ArrayList<OPlyC1cPC>();
        OPlyC1cPC lvC1cPC;
        if (pmC1cCT != null && !pmC1cCT.isEmpty()) {
            for (OPlyC1cC lvC1cC : pmC1cCT) {
                // se da valor a poliza y presupuesto
                lvC1cC.getOPlyClaP().getOPlyClaS().setPlyVal(pmPlyVal);
                lvC1cC.getOPlyClaP().getOPlyClaS().setQtnVal(pmPlyVal);
                // se recorre clausulas texto para asignar tambien poliza y presupuesto 
                for (OPlyCetP lvCetP : lvC1cC.getOPlyCetPT()) {
                    lvCetP.getOPlyCetS().setPlyVal(pmPlyVal);
                    lvCetP.getOPlyCetS().setQtnVal(pmPlyVal);
                }
                // se inicializa PC
                lvC1cPC = new OPlyC1cPC();
                // se asigna el objeto C
                lvC1cPC.setOPlyC1cC(lvC1cC);
                // se añade al listado
                rtC1cCT.add(lvC1cPC);
            }
        }
        return rtC1cCT;
    }
    //------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------
    


    
    //------------------------------------------------------------------------------
    // Obtener un objeto cobertura complejo
    // recibe como parametro cobertura completo que recupera el previo y los parametros que recibe el servicio de entrada
    // se crea el objeto de salida con la misma estructura que nos define el previo para respetar orden de coberturas y desgloses
    // de esta forma no valida ninguna cobertura que no este definida
    //------------------------------------------------------------------------------
    public List<OPlyCvcPC> getCvcCTISU(List<OPlyCvcPC> pmPrrCvcCT, 
                                    List<OPlyCvrP> pmInCvrPT, 
                                    List<OPlyAgcP> pmAgcPT, 
                                    Hashtable<String, List<OPlyAtcC>> pmAtcCTHash, 
                                    List<OPlyBrwP> pmBrwPT, 
                                    BigDecimal pmRskVal){
        // inicializamos objeto de retorno
        List<OPlyCvcPC> rtCvcCT = new ArrayList<OPlyCvcPC>();
        // creamos objeto para iteraciones del retorno
        BigDecimal lvCvrVal;
        
        // se comprueba que los arrays tengan elementos (previo y entrada) 
        if (pmPrrCvcCT != null && !pmPrrCvcCT.isEmpty()  && 
                pmInCvrPT != null && !pmInCvrPT.isEmpty()) {
            // recorremos las coberturas del previo
            for (OPlyCvcPC loopPrrCvcPC : pmPrrCvcCT) {
                lvCvrVal = loopPrrCvcPC.getOPlyCvcC().getOPlyCvrP().getOPlyCvrS().getCvrVal();
                // recorremos las coberturas que recibimos por parametro
                for (OPlyCvrP loopInCvrP : pmInCvrPT) {
                    // si la cobertura corresponde al riesgo y cobertura que queremos tratar
                    getCvcCTSecond(pmAgcPT, pmAtcCTHash, pmBrwPT, pmRskVal, rtCvcCT, lvCvrVal, loopPrrCvcPC,
                            loopInCvrP);
                }
            }
        } else {
            rtCvcCT = pmPrrCvcCT;
        }
        return rtCvcCT;
    }

    
    
    
    //------------------------------------------------------------------------------
    // Obtener un objeto cobertura complejo
    //------------------------------------------------------------------------------
    public List<OPlyCvcPC> getCvcCTTSY(List<OPlyCvrP> pmCvrPT, 
                                    List<OPlyAgcP> pmAgcPT, 
                                    Hashtable<String, List<OPlyAtcC>> pmAtcCTHash, 
                                    List<OPlyBrwP> pmBrwPT, 
                                    BigDecimal pmRskVal){
        String atrLvlName;
        // inicializamos objeto de retorno
        List<OPlyCvcPC> rtCvcCT = new ArrayList<OPlyCvcPC>();
        // creamos objeto para iteraciones del retorno
        OPlyCvcPC lvCvcPC;

        // recorremos coberturas
        for (OPlyCvrP lvCvrP : pmCvrPT) {
            // si la cobertura corresponde al riesgo que queremos tratar
            if (lvCvrP.getOPlyCvrS().getRskVal() == pmRskVal) {
                // se copia el valor de "importe capital" a "capital texto" para su tratamiento en PL
                if(lvCvrP.getOPlyCvrS().getCplAmn() != null) {
                    lvCvrP.getOPlyCvrS().setCplTxt(lvCvrP.getOPlyCvrS().getCplAmn().toString());
                }
                // se inicializa el objeto cobertura completo 
                lvCvcPC = new OPlyCvcPC();
                // se asigna el objeto cobertura --------------------------------------------------------
                lvCvcPC.getOPlyCvcC().setOPlyCvrP(lvCvrP);
                // se asignan los agravantes --------------------------------------------------------
                lvCvcPC.getOPlyCvcC().setOPlyAvcCT(getAvcCT(pmRskVal, lvCvrP.getOPlyCvrS().getCvrVal(), pmAgcPT, pmAtcCTHash));
                // oPlyAtc  CT --------------------------------------------------------
                // se asignan los atributos y ocurrencias (nivel '3' Coberturas / subnivel '1' Comun)
                lvCvcPC.getOPlyCvcC().setOPlyAtcCT(new ArrayList <OPlyAtcPC>());
                atrLvlName = getAtcId(CArd.LVL_TYP_CVR, CArd.SVL_TYP_CMN, pmRskVal);
                getCvcCTAuxA(pmAtcCTHash, atrLvlName, lvCvcPC, lvCvrP);
                // oPlyBrw  PT --------------------------------------------------------
                lvCvcPC.getOPlyCvcC().setOPlyBrwPT(new ArrayList <OPlyBrwP>());
                getCvcCTAuxB(pmBrwPT, pmRskVal, lvCvcPC, lvCvrP);
                rtCvcCT.add(lvCvcPC);
            }
        }
        return rtCvcCT;
    }

    private void getCvcCTAuxB(List<OPlyBrwP> pmBrwPT, BigDecimal pmRskVal, OPlyCvcPC lvCvcPC, OPlyCvrP lvCvrP) {
        if (pmBrwPT != null && !pmBrwPT.isEmpty()) {
            for(OPlyBrwP lvBrwP : pmBrwPT) {
                if(lvBrwP.getOPlyBrwS().getRskVal() == pmRskVal &&
                   lvBrwP.getOPlyBrwS().getCvrVal() == lvCvrP.getOPlyCvrS().getCvrVal()){
                    lvCvcPC.getOPlyCvcC().getOPlyBrwPT().add(lvBrwP);
                }
            }
        }
    }

    private void getCvcCTAuxA(Hashtable<String, List<OPlyAtcC>> pmAtcCTHash, String atrLvlName, OPlyCvcPC lvCvcPC,
            OPlyCvrP lvCvrP) {
        OPlyAtcPC lvAtcPC;
        if (pmAtcCTHash.get(atrLvlName) != null && !pmAtcCTHash.get(atrLvlName).isEmpty()) {
            for(OPlyAtcC lvAtcC : pmAtcCTHash.get(atrLvlName)) {
                // si el atributo coincide en cobertura y agravante
                if(lvAtcC.getOPlyAtrP().getOPlyAtrS().getCvrVal().compareTo(lvCvrP.getOPlyCvrS().getCvrVal()) == 0){
                    // se guarda el atributo en la lista
                    lvAtcPC = new OPlyAtcPC();
                    lvAtcPC.setOPlyAtcC(lvAtcC);
                    lvCvcPC.getOPlyCvcC().getOPlyAtcCT().add(lvAtcPC);
                }
            }                   
        }
    }
    
    public boolean checkDpnAtr(List<OTrnDpnS> pmDpnT) {
	for (OTrnDpnS lvDpnS : pmDpnT) {
	    if ("ATR".equals(lvDpnS.getDpnObjVal())) {
		return true;
	    }
	}
	return false;
    }
   
}
