package com.mapfre.tron.api.trn.nwo.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

import lombok.Data;

@Data
@Repository
public class SrTrnNwoQryDAOImpl implements ISrTrnNwoQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Query Newtron operation.
     *
     * @author Cristian Saball
     *
     * @param lngVal 	-> Language code
     * @return       	-> The newtron operations
     */
    @Override
    public List<OTrnNwoS> getNewtronOperationList(String lngVal) {
		
        final String query = new StringBuilder()
                .append("SELECT ")
                .append("a.opr_idn_val, ")
                .append("a.opr_idn_nam, ")
                .append("a.mol_val ")
                .append("FROM df_trn_nwt_xx_nod a ")
                .append("WHERE a.lng_val   = ? ")
                .toString();

        List<OTrnNwoS> lOTrnNwoSList = jdbcTemplate.query(query, new Object[] { lngVal },
                new OTrnNwoSRowMapper());
        
        return lOTrnNwoSList;

    }

}
