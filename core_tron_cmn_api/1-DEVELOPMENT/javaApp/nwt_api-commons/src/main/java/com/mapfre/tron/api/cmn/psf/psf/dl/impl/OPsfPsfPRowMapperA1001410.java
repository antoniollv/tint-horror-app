package com.mapfre.tron.api.cmn.psf.psf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfPK;

public class OPsfPsfPRowMapperA1001410 extends NewtronRowMapper<OPsfPsfS> {
	private OPsfPsfS s;
	private Map<OPsfPsfPK, OPsfPsfS> map;
	
	public OPsfPsfPRowMapperA1001410(OPsfPsfS s) {
		this.s = s;
	}
	
	public OPsfPsfPRowMapperA1001410(Map<OPsfPsfPK, OPsfPsfS> map) {
		this.map = map;
	}
	
	private OPsfPsfS getObj(ResultSet rs, int rowNum) throws SQLException {
		if (s != null) {
			return s;
		} else if (map != null) {
			OPsfPsfPK pk = OPsfPsfPK.builder().cmpVal(rs.getBigDecimal("COD_CIA")).pmsVal(rs.getBigDecimal("cod_plan_pago")).build();
			if (map.containsKey(pk)) {
				return map.get(pk);
			}
		}
		OPsfPsfS s = new OPsfPsfS();
		s.setCmpVal(rs.getBigDecimal("COD_CIA"));
		s.setPmsVal(rs.getBigDecimal("cod_plan_pago"));
		return s;
	}

	@Override
	public OPsfPsfS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OPsfPsfS s = getObj(rs, rowNum);
		s.setPlnInmVal(rs.getBigDecimal("num_cuotas"));
		s.setInmDstVal(rs.getString("tip_dst_cuotas"));
		s.setInmGnrInlDatTypVal(rs.getString("tip_fec_ini"));
		s.setInmGnrInlDatPrdNam(rs.getString("nom_prg_fec_ini"));
		s.setInmGnrFnlDatTypVal(rs.getString("tip_fec_fin"));
		s.setInmGnrEndDatPrdNam(rs.getString("nom_prg_fec_fin"));
		s.setPtlCanInmDstVal(rs.getString("tip_dst_ap"));
		s.setPmsEfcDat(rs.getString("mca_fec_efec_nueva_as"));
		s.setInmDstTypVal(rs.getString("tip_dst_vcto"));
		s.setInmFrcDstPrdNam(rs.getString("nom_prg_tip_dst_vcto"));
		s.setMnmInmTypVal(rs.getString("tip_cuota_minima"));
		s.setItrCllPrdNam(rs.getString("nom_prg_interes"));
		s.setTotCanItrCllPrdNam(rs.getString("nom_prg_interes_at"));
		s.setInmMdfPrdNam(rs.getString("nom_prg_altera_cuota"));
		s.setDsbRow(rs.getString("mca_inh"));
		s.setPtaPly(rs.getString("mca_prorrata"));
		s.setDclLasInm(rs.getString("mca_decimales_ultima_cuota"));
		s.setPerMdfPrdNam(rs.getString("nom_prg_altera_pct"));
		return s;
	}

}
