package com.mapfre.tron.api.trn.ssg.dl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

/**
 * TODO General class comment.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 1 feb. 2022 - 17:54:52
 *
 */
@Repository
public class SrCmnSsgCrtDAOImpl implements ISrCmnSsgCrtDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Create Storage From Self-Service.
     *
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lngVal   -> Language code
     * @param oCmnSsgS -> Input data to new self service storage
     * @return         -> number or rows inserted
     */
    @Override
    public int postStorageFromSelfService(final Integer cmpVal, final String usrVal, final String lngVal,
            final OCmnSsgS oCmnSsgS) {

        String sql = new StringBuilder()
                .append("INSERT INTO rl_cmn_nwt_xx_ssg (")
                .append("    cmp_val,")
                .append("    sfr_idn_val,")
                .append("    sfr_sci_val,")
                .append("    sfr_sbs_val,")
                .append("    sfr_sqn_val,")
                .append("    vrb_dsp_val,")
                .append("    vrb_val,")
                .append("    vld_dat,")
                .append("    dsb_row,")
                .append("    usr_val,")
                .append("    mdf_dat")
                .append(") VALUES (")
                .append("    ?,")                                                                 // otrnsfrs.cmpval
                .append("    ?,")                                                                 // otrnsfrs.sfridnval
                .append("    ?,")                                                                 // otrnsfrs.sfrscival
                .append("    ?,")                                                                 // otrnsfrs.sfrsbsval
                .append("    ?,")                                                                 // otrnsfrs.sfrsqnval
                .append("    ?,")                                                                 // otrnsfrs.vrbdspval
                .append("    ?,")                                                                 // otrnsfrs.vrbval
                .append("    sysdate,")                                                   // otrnsfrs.vlddat
                .append("    'N',")
                .append("    ?,")                                                                 // usrval
                .append("    sysdate ")
                .append(")")
                .toString();

        return jdbcTemplate.update(sql.toString(),
                oCmnSsgS.getCmpVal(),
                oCmnSsgS.getSfrIdnVal(),
                oCmnSsgS.getSfrSciVal(),
                oCmnSsgS.getSfrSbsVal(),
                oCmnSsgS.getSfrSqnVal(),
                oCmnSsgS.getVrbDspVal(),
                oCmnSsgS.getVrbVal(),
                usrVal);

    }

}
