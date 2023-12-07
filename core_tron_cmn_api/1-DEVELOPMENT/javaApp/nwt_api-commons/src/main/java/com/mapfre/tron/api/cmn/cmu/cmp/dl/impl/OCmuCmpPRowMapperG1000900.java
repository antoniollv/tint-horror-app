package com.mapfre.tron.api.cmn.cmu.cmp.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpPK;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmuCmpPRowMapperG1000900 extends NewtronRowMapper<OCmuCmpS> {
	private OCmuCmpS s;
	private Map<OCmuCmpPK, OCmuCmpS> map;
	
	public OCmuCmpPRowMapperG1000900(OCmuCmpS s) {
		this.s = s;
	}
	
	public OCmuCmpPRowMapperG1000900(Map<OCmuCmpPK, OCmuCmpS> map) {
		this.map = map;
	}
	
	private OCmuCmpS getObj(ResultSet rs, int rowNum) throws SQLException {
		if (s != null) {
			return s;
		} else if (map != null) {
			OCmuCmpPK pk = OCmuCmpPK.builder().cmpVal(rs.getBigDecimal("COD_CIA")).build();
			if (map.containsKey(pk)) {
				return map.get(pk);
			}
		}
		OCmuCmpS s = new OCmuCmpS();
		s.setCmpVal(rs.getBigDecimal("COD_CIA"));
		return s;
	}

	@Override
	public OCmuCmpS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmuCmpS s = getObj(rs, rowNum);
		s.setThpRqrFrsSrn(rs.getString("mca_ape1_tercero_obg"));
		s.setThpRqrScnSrn(rs.getString("mca_ape2_tercero_obg"));
		s.setRqmAddPsc(rs.getString("mca_pos_secuencia_dir"));
		s.setLssPgmVal(rs.getString("cod_pgm_sini"));
		s.setFilPgmVal(rs.getString("cod_pgm_exp"));
		s.setLssTccThrLvlTypVal(rs.getString("tip_nivel3_ct_sini"));
		s.setTxtLntVal(rs.getBigDecimal("lng_texto"));
		s.setClsLntVal(rs.getBigDecimal("lng_clau"));
		s.setThpUsr(rs.getString("mca_usr_tercero"));
		s.setPtlInfQry(rs.getString("mca_inf_parcial"));
		s.setAgnEmp(rs.getString("mca_cod_emp_agt"));
		s.setCrrAcoFrmTypVal(rs.getString("tip_formato_cta_cte"));
		s.setTwoFldNam(rs.getString("mca_nom2_tercero"));
		s.setShwSfx(rs.getString("mca_sufijo_nombre"));
		s.setPscExt(rs.getString("mca_ext_cod_postal"));
		s.setShwPrx(rs.getString("mca_prefijo_nombre"));
		s.setInyOrgAcvVal(rs.getBigDecimal("cod_act_alt_asegurados"));
		s.setVsbScnSrn(rs.getString("mca_ape2_visible"));
		s.setThpAddValTaxTypCpt(rs.getString("mca_tip_tercero_iva"));
		s.setApcRgp(rs.getString("MCA_APLICA_RGPD"));
		s.setTolRgpTypVal(rs.getString("TIP_HERRAMIENTA_RGPD"));	
		return s;
	}

}
