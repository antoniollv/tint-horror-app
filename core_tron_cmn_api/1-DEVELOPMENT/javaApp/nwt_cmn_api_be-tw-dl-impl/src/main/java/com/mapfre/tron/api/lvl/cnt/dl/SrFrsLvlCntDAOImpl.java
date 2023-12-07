package com.mapfre.tron.api.lvl.cnt.dl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

import lombok.Data;

/**
 * SrFrsLvlCntDAO comment of the class.
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 11:08:33
 *
 */
@Data
@Repository
public class SrFrsLvlCntDAOImpl implements ISrFrsLvlCntDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Query Contact From First Level
     *
     * @author magarafr
     * @purpose Query the first level contact information. It will return the
     *          contact of the first level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param frsLvlVal -> First Level
     * @return -> List<OThpCntP>
     */
    @Override
    public OThpCntS frsLvlCnt(final Integer cmpVal, final Integer frsLvlVal) {
        final String query = new StringBuilder()
                .append("SELECT a.rowid, ")
                .append("a.cod_cia, ")
                .append("a.tlf_pais, ")
                .append("a.tlf_zona, ")
                .append("a.tlf_numero, ")
                .append("a.fax_numero, ")
                .append("a.email, ")
                .append("a.nom_resp, ")
                .append("a.ape_resp ")
                .append("FROM a1000700 a ")
                .append("WHERE a.cod_cia = ? ")
                .append("AND a.cod_nivel1 = ? ").toString();

        OThpCntS lvOThpCntS = jdbcTemplate.queryForObject(query, new Object[] { cmpVal, frsLvlVal },
                new OFrsLvlRowMapper());
        
        return lvOThpCntS;

    }
    
    
    
    
    
    @Override
    public OThpAdrS get(final Integer cmpVal, final String lngVal, final String usrVal, final Integer frsLvlVal) {
        final String query = new StringBuilder()
        		.append(" SELECT")
        		.append(" a.cod_cia,")
        		.append(" a.tip_domicilio,")
        		.append(" t.nom_valor,")
        		.append(" a.nom_domicilio1,")
        		.append(" a.nom_domicilio2,")
        		.append(" a.nom_domicilio3,")
        		.append(" e.cod_pais,")
        		.append(" f.nom_pais,")
        		.append(" a.cod_estado,")
        		.append(" c.nom_estado,")
        		.append(" a.cod_prov,")
        		.append(" d.nom_prov,")
        		.append(" a.cod_localidad,")
        		.append(" b.nom_localidad,")
        		.append(" a.cod_postal,")
        		.append(" a.num_apartado")
        		.append(" FROM a1000702 a")
        		.append(" INNER JOIN a1000900 e ON a.cod_cia=e.cod_cia")
        		.append(" INNER JOIN a1000102 b ON a.cod_localidad=b.cod_localidad AND e.cod_pais=b.cod_pais AND a.cod_prov=b.cod_prov")
        		.append(" INNER JOIN a1000100 d ON d.cod_pais=e.cod_pais AND a.cod_prov=d.cod_prov")
        		.append(" INNER JOIN a1000104 c ON c.cod_pais=e.cod_pais AND a.cod_estado=c.cod_estado")
        		.append(" INNER JOIN a1000101 f ON f.cod_pais=e.cod_pais")
        		.append(" INNER JOIN g1010031 t ON t.cod_campo = 'TIP_DOMICILIO' AND t.cod_valor = a.tip_domicilio AND t.cod_idioma = ? AND t.cod_ramo = 999 AND t.cod_cia = ? ")
        		.append(" WHERE a.cod_cia = ?")
        		.append(" AND a.cod_nivel3 = ?")
                
        		.toString();

        OThpAdrS lvOThpCntS = jdbcTemplate.queryForObject(query, new Object[] { lngVal, cmpVal,cmpVal, frsLvlVal },
                new OThpAdrSRowMapper());
        
        return lvOThpCntS;

    }

}
