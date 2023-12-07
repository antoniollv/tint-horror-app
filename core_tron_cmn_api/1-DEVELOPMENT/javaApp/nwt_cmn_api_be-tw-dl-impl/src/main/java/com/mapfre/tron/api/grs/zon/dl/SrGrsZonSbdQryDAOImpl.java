package com.mapfre.tron.api.grs.zon.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

/**
 * The GrsZonSbdQry repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 12:51:33
 *
 */
@Repository
public class SrGrsZonSbdQryDAOImpl implements ISrGrsZonSbdQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Query Zone Four Subcode.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param ditVal    -> District Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the zone four subcode (District)
     */
    @Override
    public OGrsZnfS getZoneFourSubcode(final Integer cmpVal, final String usrVal, final String lngVal,
            final String cnyVal, final Integer sttVal, final Integer pvcVal, final Integer twnVal, final String ditVal,
            final String reaGrsDit) {

        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.cny_val,")
                .append("    t.stt_val,")
                .append("    t.pvc_val,")
                .append("    t.twn_val,")
                .append("    t.dit_val,")
                .append("    t.lng_val,")
                .append("    t.dit_nam,")
                .append("    t.rea_grs_dit,")
                .append("    t.vld_dat,")
                .append("    t.dsb_row,")
                .append("    t.usr_val,")
                .append("    t.mdf_dat ")
                .append("FROM")
                .append("    df_grs_nwt_xx_znf_sbd t ")
                .append("WHERE")
                .append("        t.cny_val = ?")                                                           // cnyVal
                .append("    AND t.stt_val = ?")                                                           // sttVal
                .append("    AND t.pvc_val = ?")                                                           // pvcVal
                .append("    AND t.twn_val = ?")                                                           // twnVal
                .append("    AND t.dit_val = ?")                                                           // ditVal
                .append("    AND t.rea_grs_dit = nvl(?, t.rea_grs_dit)")                                   // reaGrsDit
                .append("    AND vld_dat = (SELECT MAX(b.vld_dat)")
                .append("                     FROM df_grs_nwt_xx_znf_sbd b")
                .append("                    WHERE b.cny_val = t.cny_val")
                .append("                      AND b.stt_val = t.stt_val")
                .append("                      AND b.pvc_val = t.pvc_val")
                .append("                      AND b.twn_val = t.twn_val")
                .append("                      AND b.lng_val = t.lng_val")
                .append("                      AND b.dit_val = t.dit_val")
                .append("                      AND b.rea_grs_dit = t.rea_grs_dit) ")
                .toString();

        return jdbcTemplate.queryForObject(query,
                new Object[] { cnyVal,
                        sttVal,
                        pvcVal,
                        twnVal,
                        ditVal,
                        reaGrsDit},
                new OGrsZnfSRowMapper());

    }

    /**
     * Query Zone Four Subcode List.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the list of zone four subcode list (Districts)
     */
    @Override
    public List<OGrsZnfS> getZoneFourSubcodeList(final Integer cmpVal, final String usrVal, final String lngVal,
            final String cnyVal, final String sttVal, final Integer pvcVal, final Integer twnVal,
            final String reaGrsDit) {

        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.cny_val,")
                .append("    t.stt_val,")
                .append("    t.pvc_val,")
                .append("    t.twn_val,")
                .append("    t.dit_val,")
                .append("    t.lng_val,")
                .append("    t.dit_nam,")
                .append("    t.rea_grs_dit,")
                .append("    t.vld_dat,")
                .append("    t.dsb_row,")
                .append("    t.usr_val,")
                .append("    t.mdf_dat ")
                .append("FROM")
                .append("    df_grs_nwt_xx_znf_sbd t ")
                .append("WHERE")
                .append("        t.cny_val = ?")                                                           // cnyVal
                .append("    AND t.stt_val = ?")                                                           // sttVal
                .append("    AND t.pvc_val = ?")                                                           // pvcVal
                .append("    AND t.twn_val = ?")                                                           // twnVal
                .append("    AND t.rea_grs_dit = nvl(?, t.rea_grs_dit) ")                                  // reaGrsDit
                .append("    AND vld_dat = (SELECT MAX(b.vld_dat)")
                .append("                     FROM df_grs_nwt_xx_znf_sbd b")
                .append("                    WHERE b.cny_val = t.cny_val")
                .append("                      AND b.stt_val = t.stt_val")
                .append("                      AND b.pvc_val = t.pvc_val")
                .append("                      AND b.twn_val = t.twn_val")
                .append("                      AND b.lng_val = t.lng_val")
                .append("                      AND b.dit_val = t.dit_val")
                .append("                      AND b.rea_grs_dit = t.rea_grs_dit) ")
                .append("ORDER BY")
                .append("    t.dit_val")
                .toString();

        return jdbcTemplate.query(query,
                new Object[] { cnyVal,
                        sttVal,
                        pvcVal,
                        twnVal,
                        reaGrsDit},
                new OGrsZnfSRowMapper());

    }

}
