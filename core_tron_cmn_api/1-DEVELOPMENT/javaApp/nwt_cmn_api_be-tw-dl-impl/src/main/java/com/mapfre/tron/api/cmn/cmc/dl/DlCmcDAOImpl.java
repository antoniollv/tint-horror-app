package com.mapfre.tron.api.cmn.cmc.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.cmu.c.CCmu;
import com.mapfre.nwt.dsr.c.CDsr;
import com.mapfre.nwt.thp.c.CThp;

/**
 * The commission chart repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 15 Jul 2021 - 09:03:05
 *
 */
@Repository
public class DlCmcDAOImpl implements IDlCmcDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific commission chart code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param lobVal       -> Business line code
     * @param vldDat       -> Validation date
     * @param thpVal       -> Thirpard agent code
     * @param cmcVal       -> Commission chart code
     * @param frsDstHnlVal -> First distribution channel code
     * @param scnDstHnlVal -> Second distribution channel code
     * @param thrDstHnlVal -> Third distribution channel code
     * @param frsLvlVal    -> First level code
     * @param scnLvlVal    -> Second level code
     * @param thrLvlVal    -> Third level code
     * @return             -> Number of rows for a specific commission chart code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal, final Date vldDat,
            final BigDecimal thpVal, final BigDecimal cmcVal, final String frsDstHnlVal, final String scnDstHnlVal,
            final String thrDstHnlVal, final BigDecimal frsLvlVal, final BigDecimal scnLvlVal,
            final BigDecimal thrLvlVal) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.cod_cuadro_com) ");
        sql.append("FROM TRON2000.A1001750 t ");
        sql.append("WHERE t.cod_cia      = ? ");
        sql.append("AND t.cod_ramo       = ? ");
        sql.append("AND t.cod_cuadro_com = ? ");
        sql.append("AND t.cod_agt       IN (?,");
        sql.append(CThp.GNC_THP_VAL);
        sql.append(") ");
        sql.append("AND t.cod_canal1 IN (?,'");
        sql.append(CDsr.GNC_FRS_DST_HNL_VAL);
        sql.append("') ");
        sql.append("AND t.cod_canal2 IN (?,'");
        sql.append(CDsr.GNC_SCN_DST_HNL_VAL);
        sql.append("') ");
        sql.append("AND t.cod_canal3 IN (?,'");
        sql.append(CDsr.GNC_THR_DST_HNL_VAL);
        sql.append("') ");
        sql.append("AND t.cod_nivel1 IN (?,");
        sql.append(CCmu.GNC_FRS_LVL);
        sql.append(") ");
        sql.append("AND t.cod_nivel2 IN (?,");
        sql.append(CCmu.GNC_SCN_LVL);
        sql.append(") ");
        sql.append("AND t.cod_nivel3 IN (?,");
        sql.append(CCmu.GNC_THR_LVL);
        sql.append(") ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, lobVal, cmcVal, thpVal, frsDstHnlVal, scnDstHnlVal, thrDstHnlVal,
                    frsLvlVal, scnLvlVal, thrLvlVal, vldDat };
        } else {
            params = new Object[] { cmpVal, lobVal, cmcVal, thpVal, frsDstHnlVal, scnDstHnlVal, thrDstHnlVal,
                    frsLvlVal, scnLvlVal, thrLvlVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

}
