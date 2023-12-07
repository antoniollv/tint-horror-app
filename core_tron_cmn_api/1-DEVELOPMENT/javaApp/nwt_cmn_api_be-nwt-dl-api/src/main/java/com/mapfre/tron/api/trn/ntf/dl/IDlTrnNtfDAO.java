package com.mapfre.tron.api.trn.ntf.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public interface IDlTrnNtfDAO {

    OTrnNtfS get_Ntf_001(BigDecimal cmpVal, String dcnVal, Date vldDat, String usrLngVal);
    
    OTrnNtfS get_Ntf_002(BigDecimal cmpVal, String dcnVal, String ntfTypVal, Date vldDat, String usrLngVal);
    
    OTrnNtfS get_Ntf_003(BigDecimal cmpVal, String dcnVal, Date vldDat, String usrLngVal);
    
    OTrnNtfS get_Nod_002(BigDecimal cmpVal, String oprIdnVal, String usrLngVal); 
    
    OTrnNtfS get_thpAcvVal(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal);
    
    OTrnNtfS get_MnrTypVal(BigDecimal cmpVal, String mnrTypVal, String usrLngVal);
    
    OTrnNtfS get_lvlVal(BigDecimal cmpVal, String lvlVal, String usrLngVal);
    
    OTrnNtfS get_encVal(BigDecimal cmpVal, String encVal,String usrLngVal);
    
    OTrnNtfS get_pcsVal(BigDecimal cmpVal, String pcsVal, String usrLngVal);
    
    OTrnNtfS get_thrLvl(BigDecimal cmpVal, BigDecimal frsLvlVal, BigDecimal scnLvlVal, BigDecimal thrLvlVal, String usrLngVal);
    
    OTrnNtfS get_delVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal lobVal, String usrLngVal);
    
    OTrnNtfS get_sblVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal sblVal, BigDecimal lobVal, String usrLngVal);
    
    OTrnNtfS get_hpnVal(BigDecimal cmpVal, String hpnVal, BigDecimal lobVal, String filTypVal, String usrLngVal);
    
    OTrnNtfS get_filTypVal(BigDecimal cmpVal, String filTypVal, String usrLngVal);

    OTrnNtfS get_gppVal_002(BigDecimal cmpVal, String gppVal, BigDecimal lobVal, String usrLngVal);
    
    OTrnNtfS get_enrVal(BigDecimal cmpVal, BigDecimal enrVal, String enrTypVal, BigDecimal enrSbdVal, String usrLngVal) ;
    
    OTrnNtfS get_thrDstHnlNam(BigDecimal cmpVal, String thrDstHnlVal,String usrLngVal);
    
    OTrnNtfS get_tplLytVal(BigDecimal cmpVal, String tplLytVal, String usrLngVal);
    
    OTrnNtfS get_enrSbdVal(BigDecimal cmpVal, BigDecimal enrVal, String enrTypVal, BigDecimal enrSbdVal, String usrLngVal);
    
    OTrnNtfS get_NtfVal(BigDecimal cmpVal, String dcnVal, String ntfTypVal, Date vldDat, String usrLngVal);
}
