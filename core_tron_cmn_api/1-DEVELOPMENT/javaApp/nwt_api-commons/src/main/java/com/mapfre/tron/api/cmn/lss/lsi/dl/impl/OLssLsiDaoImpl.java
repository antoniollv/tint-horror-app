package com.mapfre.tron.api.cmn.lss.lsi.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiDao;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiPK;

@Repository
public class OLssLsiDaoImpl implements OLssLsiDao {
	
	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OLssLsiSRowMapper MAPPER = new OLssLsiSRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM g7000200 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.tip_causa = 1 AND a.cod_causa = :lssRsnVal";

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Override
	public List<OLssLsiS> getLossQuery(BigDecimal numSini, BigDecimal codCia) {
		
		String sql = new StringBuilder()
				.append(" SELECT cod_cia, ")
				.append(" cod_sector, ")
				.append(" cod_ramo, ")
				.append(" cod_modalidad, ")
				.append(" cod_mon, ")
				.append(" num_poliza_grupo, ")
				.append(" num_poliza, ")
				.append(" num_spto, ")
				.append(" num_apli, ")
				.append(" num_spto_apli, ")
				.append(" num_riesgo, ")
				.append(" num_periodo, ")
				.append(" num_sini, ")
				.append(" num_sini_grupo, ")
				.append(" tip_coaseguro, ")
				.append(" cod_agt, ")
				.append(" cod_nivel1, ")
				.append(" cod_nivel2, ")
				.append(" cod_nivel3, ")
				.append(" tip_docum_aseg, ")
				.append(" cod_docum_aseg, ")
				.append(" tip_docum_tomador, ")
				.append(" cod_docum_tomador, ")
				.append(" mca_provisional, ")
				.append(" fec_autorizacion, ")
				.append(" mca_exclusivo, ")
				.append(" cod_supervisor, ")
				.append(" cod_nivel1_captura, ")
				.append(" cod_nivel2_captura, ")
				.append(" cod_nivel3_captura, ")
				.append(" tip_est_sini, ")
				.append(" fec_term_sini, ")
				.append(" fec_reap_sini, ")
				.append(" fec_modi_sini, ")
				.append(" fec_proc_sini, ")
				.append(" fec_sini, ")
				.append(" hora_sini, ")
				.append(" fec_denu_sini, ")
				.append(" cod_causa_sini, ")
				.append(" cod_evento, ")
				.append(" nom_contacto, ")
				.append(" ape_contacto, ")
				.append(" tel_pais_contacto, ")
				.append(" tel_zona_contacto, ")
				.append(" tel_numero_contacto, ")
				.append(" cod_usr, ")
				.append(" fec_actu, ")
				.append(" num_spto_riesgo, ")
				.append(" mca_culpable, ")
				.append(" num_sini_ref, ")
				.append(" tip_apertura, ")
				.append(" tip_docum_contacto, ")
				.append(" cod_docum_contacto, ")
				.append(" tip_relacion, ")
				.append(" email_contacto, ")
				.append(" num_poliza_cliente, ")
				.append(" num_contrato, ")
				.append(" hora_denu_sini, ")
				.append(" cod_usr_exclusivo, ")
				.append(" tip_poliza_stro, ")
				.append(" imp_val_ini_sini, ")
				.append(" mca_aper_nwt, ")
				.append(" txt_cnh_clr, ")
				.append(" txt_cnh_tlp, ")
				.append(" txt_cnh_eml ")
				.append(" FROM A7000900 ")
				.append(" WHERE num_sini = ? ")
				.append(" AND cod_cia = ? ")
				.toString();
		
   
		
		
		return  jdbcTemplate.query(sql,
				new Object[] { numSini, codCia},
				new OLssLsiSRowMapper2());
		
		
	}

	@Override 
	@Cacheable("PoC-OLssLsiS")
	public OLssLsiS get(OLssLsiPK pk) {
		try {
			return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override 
	@Cacheable("PoC-OLssLsiSList")
	public List<OLssLsiS> getAll() {
		return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	}

	@Override
	public String getDescription(OLssLsiS o) {
		if (o != null ) {
			return StringUtils.defaultString(o.getThrLvlNam());
		}
		return "";
	}

	@Override
	public String getAbr(OLssLsiS o) {
	    throw new UnsupportedOperationException();
	}

    @Override
    public OLssLsiS save(OLssLsiS o) {

        return null;
    }

    @Override
    public int delete(OLssLsiPK o) {

        return 0;
    }

    @Override
    public OLssLsiS update(OLssLsiS o) {

        return null;
    }

	
}

