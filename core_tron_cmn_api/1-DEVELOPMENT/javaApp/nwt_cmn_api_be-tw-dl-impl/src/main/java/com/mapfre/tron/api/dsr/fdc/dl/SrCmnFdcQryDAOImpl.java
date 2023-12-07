package com.mapfre.tron.api.dsr.fdc.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.dsr.fdc.bo.ODsrFdcS;

import lombok.Data;


/**
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
@Data
@Repository
public class SrCmnFdcQryDAOImpl implements ISrDsrFdcQryDAO{

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Query First Level Distribution Channel by company
     *
     * @author Javier S.
     * 
     * @purpose Query First Level Distribution Channel by company
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<ODsrFdcS>
     * 
     */
    @Override
    public List<ODsrFdcS> tblByCmp(Integer cmpVal, String usrVal, String lngVal) {
        final String query = new StringBuilder()
                .append(" SELECT cod_cia, cod_canal1, des_canal1, abr_canal1, mca_inh ")
                .append(" from A1000721 ")
                .append(" where cod_cia = ? ")
                .append(" and mca_inh = 'N'  ")
                .toString();

        List<ODsrFdcS> oPlyAtrSList = jdbcTemplate.query(query, new Object[] { cmpVal }, new OCmnFdcSRowMapper());
        return oPlyAtrSList;
    }

    /**
     * First Level Distribution Channel by Company.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language value
     * @param usrVal       -> User value
     * @param frsDstHnlVal -> First level distribution channel
     * @return             -> The first level distribution channel by company
     */
    @Override
    public ODsrFdcS tblByCmpById(final Integer cmpVal, final String usrVal, final String lngVal,
            final String frsDstHnlVal) {

        final String query = new StringBuilder()
                .append("SELECT cod_cia,")
                .append("  cod_canal1,")
                .append("  des_canal1,")
                .append("  abr_canal1,")
                .append("  mca_inh ")
                .append("FROM A1000721 ")
                .append("WHERE cod_cia = ? ")
                .append("AND mca_inh   = 'N' ")
                .append("AND COD_CANAL1 = ? ")
                .toString();

        ODsrFdcS lvODsrFdcS = jdbcTemplate.queryForObject(
                query,
                new Object[] { cmpVal, frsDstHnlVal },
                new OCmnFdcSRowMapper());

        return lvODsrFdcS;
    }

}
