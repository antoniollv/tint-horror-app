package com.mapfre.tron.api.trn.vrb.dl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * The TrnVrbQry repsository.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 may. 2021 - 11:56:55
 *
 */
@Repository
public class DlTrnVrbQryDAOImpl implements IDlTrnVrbQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Query variable definition by coverage.
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param lobVal    -> Line of Business
     * @param cvrVal    -> Coverage
     * @param vrbNamVal -> Variable name
     * @param mdtVal    -> Modality
     * @param vldDat    -> Validation Date
     * @param prnVrbVal -> Parent Variable Value
     * @return -> It will return the variable definition filtering by coverage
     */
    @Override
    public List<OTrnVrbS> getVariableDefinitionByCoverage(final String usrVal, final String lngVal,
            final Integer cmpVal, final Integer lobVal, final Integer cvrVal, final String vrbNamVal,
            final Integer mdtVal, final Long vldDat, final String prnVrbVal) {

        Date lvVldDat = null;
        if (vldDat != null) {
            lvVldDat = new Date(vldDat);
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT a.vrb_val,");
        query.append("  a.vrb_dsp_val,");
        query.append("  a.adt_val,");
        query.append("  a.dfl_val_pem,");
        query.append("  a.prn_vrb_val ");
        query.append("FROM df_trn_nwt_xx_vrb a ");
        query.append("INNER JOIN df_trn_nwt_xx_vrb_cvr b ");
        query.append("ON a.idn_key      = b.idn_key ");
        query.append("WHERE b.cmp_val   = ? ");                 // cmpVal del API
        query.append("AND a.lng_val     = ? ");                 // lngVal del API
        query.append("AND a.dsb_row     = 'N' ");
        query.append("AND b.vrb_nam_val = ? ");                 // vrbNamVal del API 'AS_COVERAGE_CLAIMTYPE'
        query.append("AND b.lob_val     = ? ");                 // loblVal del API 302
        query.append("AND b.mdt_val     = NVL(?, b.mdt_Val) "); // mdtVal para pruebas dejar a nulo
        query.append("AND b.cvr_val     = ? ");                 // cvrVal para pruebas 1021
        if (prnVrbVal != null) {
            query.append("AND a.prn_vrb_val = ? ");
        }
        query.append("AND a.vld_dat    IN ");
        query.append("(SELECT MAX(vld_dat) ");
        query.append("FROM df_trn_nwt_xx_vrb c ");
        query.append("WHERE c.idn_key = a.idn_key ");
        query.append("AND c.lng_val   = a.lng_val ");
        query.append("AND c.vrb_val   = a.vrb_val ");
        query.append("AND c.vld_dat  <= NVL(?, SYSDATE) "); // vldDat para pruebas dejar a nulo
        query.append(") ");
        query.append("ORDER BY a.sqn_val ");

        List<OTrnVrbS> lvOTrnVrbST = jdbcTemplate.query(
                query.toString(),
                prnVrbVal != null ? new Object[] { cmpVal, lngVal, vrbNamVal, lobVal, mdtVal, cvrVal, prnVrbVal, lvVldDat }
                        :  new Object[] { cmpVal, lngVal, vrbNamVal, lobVal, mdtVal, cvrVal, lvVldDat },
                new OTrnVrbSRowMapper());

        return lvOTrnVrbST;
    }

}
