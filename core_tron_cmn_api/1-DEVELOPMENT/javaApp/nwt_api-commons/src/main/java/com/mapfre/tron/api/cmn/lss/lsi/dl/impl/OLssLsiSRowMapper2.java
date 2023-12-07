package com.mapfre.tron.api.cmn.lss.lsi.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OLssLsiSRowMapper2 extends NewtronRowMapper<OLssLsiS> {

	@Override
	public OLssLsiS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    
		
	    	OLssLsiS oLssLsiS = new OLssLsiS();
						    	
	    	oLssLsiS.setCmpVal(rs.getBigDecimal("cod_cia"));
	    	oLssLsiS.setSecVal(rs.getBigDecimal("cod_sector"));
	    	oLssLsiS.setLobVal(rs.getBigDecimal("cod_ramo"));
			oLssLsiS.setMdtVal(rs.getBigDecimal("cod_modalidad"));
			oLssLsiS.setCrnVal(rs.getBigDecimal("cod_mon"));
			oLssLsiS.setGppVal(rs.getString("num_poliza_grupo"));
			oLssLsiS.setPlyVal(rs.getString("num_poliza"));
			oLssLsiS.setEnrSqn(rs.getBigDecimal("num_spto"));
			oLssLsiS.setAplVal(rs.getBigDecimal("num_apli"));
			oLssLsiS.setAplEnrSqn(rs.getBigDecimal("num_spto_apli"));
			oLssLsiS.setRskVal(rs.getBigDecimal("num_riesgo"));
			oLssLsiS.setPedVal(rs.getBigDecimal("num_periodo"));
			oLssLsiS.setLssVal(rs.getBigDecimal("num_sini"));
			oLssLsiS.setGrpLssVal(rs.getBigDecimal("num_sini_grupo"));
			oLssLsiS.setCinTypVal(rs.getString("tip_coaseguro"));
			oLssLsiS.setAgnVal(rs.getBigDecimal("cod_agt"));
			oLssLsiS.setFrsLvlVal(rs.getBigDecimal("cod_nivel1"));
			oLssLsiS.setScnLvlVal(rs.getBigDecimal("cod_nivel2"));
			oLssLsiS.setThrLvlVal(rs.getBigDecimal("cod_nivel3"));
			oLssLsiS.setInyDcmTypVal(rs.getString("tip_docum_aseg"));
			oLssLsiS.setInyDcmVal(rs.getString("cod_docum_aseg"));			
			oLssLsiS.setPlhDcmTypVal(rs.getString("tip_docum_tomador"));
			oLssLsiS.setPlhDcmVal(rs.getString("cod_docum_tomador"));
			oLssLsiS.setPrvMvm(rs.getString("mca_provisional"));
			oLssLsiS.setAtzDat(rs.getDate("fec_autorizacion"));
			oLssLsiS.setExvVal(rs.getString("mca_exclusivo"));
			oLssLsiS.setLssSpvVal(rs.getBigDecimal("cod_supervisor"));
			oLssLsiS.setCptFrsLvlVal(rs.getBigDecimal("cod_nivel1_captura"));
			oLssLsiS.setCptScnLvlVal(rs.getBigDecimal("cod_nivel2_captura"));
			oLssLsiS.setCptThrLvlVal(rs.getBigDecimal("cod_nivel3_captura"));
			oLssLsiS.setLssStsTypVal(rs.getString("tip_est_sini"));
			oLssLsiS.setLssTrmDat(rs.getDate("fec_term_sini"));
			oLssLsiS.setLssRpnDat(rs.getDate("fec_reap_sini"));
			oLssLsiS.setLssMdfDat(rs.getDate("fec_modi_sini"));
			oLssLsiS.setLssPrcDat(rs.getDate("fec_proc_sini"));
			oLssLsiS.setLssOcrDat(rs.getDate("fec_sini"));
			oLssLsiS.setLssOcrTme(rs.getString("hora_sini"));
			oLssLsiS.setLssCotDat(rs.getDate("fec_denu_sini"));
			oLssLsiS.setLssRsnVal(rs.getBigDecimal("cod_causa_sini"));
			oLssLsiS.setCtpEvnVal(rs.getBigDecimal("cod_evento"));
			oLssLsiS.setLssGlt(rs.getString("mca_culpable"));
			oLssLsiS.setRfrLssVal(rs.getString("num_sini_ref"));			
			oLssLsiS.setDelVal(rs.getBigDecimal("num_contrato"));
			oLssLsiS.setUsrVal(rs.getString("cod_usr"));
			oLssLsiS.setMdfDat(rs.getDate("fec_actu"));
			oLssLsiS.setOpgTypVal(rs.getString("tip_apertura"));
			oLssLsiS.setClpVal(rs.getString("num_poliza_cliente"));
			oLssLsiS.setLssPlyTypVal(rs.getString("tip_poliza_stro"));
			oLssLsiS.setInlLssVltAmn(rs.getBigDecimal("imp_val_ini_sini"));
			oLssLsiS.setRskEnrSqn(rs.getBigDecimal("num_spto_riesgo"));
			oLssLsiS.setExvUsrVal(rs.getString("cod_usr_exclusivo"));
			oLssLsiS.setLssCotTme(rs.getString("hora_denu_sini"));

			
		return oLssLsiS;
	}

}
