package com.mapfre.tron.api.cmn.mvd.dl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;

/**
 * The CmnMvd repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 28 Mar 2023 - 09:36:33
 *
 */
@Repository
public class DlCmnMvdDAOImpl implements IDlCmnMvdDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Query movement definition.
     *
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal -> Company code
     * @param mvmIdn -> Movement Identification
     * @param vldDat -> Valid date
     * @return       -> It will return the defintion of movements.
     */
    @Override
    public List<OCmnMvdS> getMovementDefinition(final String lngVal, final String usrVal, final Integer cmpVal,
            final List<String> mvmIdn, final Long vldDat) {

        final String inMvmIdnSql = String.join(",", Collections.nCopies(mvmIdn.size(), "?"));
        final String sql = new StringBuilder()
                .append("SELECT")
                .append("    a.cmp_val,")
                .append("    a.mvm_idn,")
                .append("    a.mvm_trn,")
                .append("    a.gnr_cmq,")
                .append("    a.cmi_typ_val,")
                .append("    a.alr_cmi_hnl,")
                .append("    a.mvm_pbl,")
                .append("    a.mvm_typ_val,")
                .append("    a.dsb_row,")
                .append("    a.usr_val,")
                .append("    a.mdf_dat,")
                .append("    a.vld_dat,")
                .append("    a.nte_val,")
                .append("    a.nte_clf_val ")
                .append("FROM")
                .append("    df_cmn_nwt_xx_mvd a ")
                .append("WHERE")
                .append("        a.cmp_val = ?")                                                              // cmpVal
                .append(String.format("AND a.mvm_idn IN (%s)", inMvmIdnSql))                                  // mvmIdn
                .append("    AND a.vld_dat = (")
                .append("        SELECT")
                .append("            MAX(b.vld_dat)")
                .append("        FROM")
                .append("            df_cmn_nwt_xx_mvd b")
                .append("        WHERE")
                .append("                b.cmp_val = a.cmp_val")
                .append("            AND b.mvm_idn = a.mvm_idn")
                .append("            AND b.vld_dat <= nvl(?, sysdate)")                                       // vldDat
                .append("    ) ")
                .append("            AND a.dsb_row = 'N'")
                .append("ORDER BY")
                .append("    a.mvm_idn")
                .toString();

        List<Object> objects = new ArrayList<>(); 
        objects.add(cmpVal);                                                                                  // cmpVal
        mvmIdn.stream().forEach(idn -> objects.add(idn));                                                     // mvmIdn
        Date vldDate = null;
        if (vldDat != null) {
            vldDate = new Date(vldDat);
        }
        objects.add(vldDate);                                                                                 // vldDat

        return jdbcTemplate.query(sql, objects.toArray(), new OCmnMvdSRowMapper()); 
    }

}
