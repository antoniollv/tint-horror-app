package com.mapfre.tron.api.cmn.mvr.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

/**
 * @author Javier Sangil
 * @version 04/06/2021
 *
 */
public class OCmnMvsSRowMapper implements RowMapper<OCmnMvrS> {

    @Override
    public OCmnMvrS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnMvrS oCmnMvrS = new OCmnMvrS();
        oCmnMvrS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
        oCmnMvrS.setRskIdn(rs.getString("RSK_IDN"));
        oCmnMvrS.setSqnVal(new BigDecimal(rs.getString("SQN_VAL")));
        oCmnMvrS.setMvmIdn(rs.getString("MVM_IDN"));
        oCmnMvrS.setMvmDat(rs.getDate("MVM_DAT"));
        oCmnMvrS.setMvmTme(rs.getString("MVM_TME"));
        oCmnMvrS.setThrDstHnlVal(rs.getString("THR_DST_HNL_VAL"));
        oCmnMvrS.setMvmSttTypVal(rs.getString("MVM_STT_TYP_VAL"));
        oCmnMvrS.setMvmSttDat((rs.getDate("MVM_STT_DAT")));
        oCmnMvrS.setMvmPbl(rs.getString("MVM_PBL"));
        oCmnMvrS.setQtnVal(rs.getString("QTN_VAL"));
        oCmnMvrS.setPlyVal(rs.getString("PLY_VAL"));
        oCmnMvrS.setRcpVal(rs.getBigDecimal("RCP_VAL"));
        oCmnMvrS.setLssVal(rs.getBigDecimal("LSS_VAL"));
        oCmnMvrS.setPymOrdVal(rs.getBigDecimal("PYM_ORD_VAL"));
        oCmnMvrS.setMvmPrcIdn(rs.getString("MVM_PRC_IDN"));
        oCmnMvrS.setMvmPrcNam(rs.getString("MVM_PRC_NAM"));
        oCmnMvrS.setMvmSucIdn(rs.getString("MVM_SUC_IDN"));
        oCmnMvrS.setMvmSucNam(rs.getString("MVM_SUC_NAM"));
        oCmnMvrS.setMvmRsnIdn(rs.getString("MVM_RSN_IDN"));
        oCmnMvrS.setMvmRsnNam(rs.getString("MVM_RSN_NAM"));
        oCmnMvrS.setTitVal(rs.getString("TIT_VAL"));
        oCmnMvrS.setDspVal(rs.getString("DSP_VAL"));
        oCmnMvrS.setRskVal(rs.getBigDecimal("RSK_VAL"));
        oCmnMvrS.setStsTypVal(rs.getString("STS_TYP_VAL"));
        oCmnMvrS.setErrTxtVal(rs.getString("ERR_TXT_VAL"));
        oCmnMvrS.setPrcTypVal(rs.getString("PRC_TYP_VAL"));
        oCmnMvrS.setEtcVal(rs.getString("ETC_VAL"));
        oCmnMvrS.setAgrValPrn(rs.getString("AGR_VAL_PRN"));

        return oCmnMvrS;
    }

}
