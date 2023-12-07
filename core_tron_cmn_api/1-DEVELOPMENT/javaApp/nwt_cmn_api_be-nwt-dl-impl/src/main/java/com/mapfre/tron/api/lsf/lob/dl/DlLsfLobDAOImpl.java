package com.mapfre.tron.api.lsf.lob.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.OTrnPrcRowMapper;

import lombok.Data;

@Data
@Repository
public class DlLsfLobDAOImpl implements IDlLsfLobDAO {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
	final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
	jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    @Override
    public List<OTrnPrcS> getNomPrg(String nomPrg, String usrLngVal, BigDecimal cmpVal, String name) {
	final String query ="SELECT 0 as PRD_NAM "+
		"FROM dba_procedures a "+
		"WHERE ((SUBSTR(A.OBJECT_NAME,0,LENGTH(A.OBJECT_NAME)-4) = upper(substr(?,0, INSTR(?,'.') -1 )) "+
		"AND A.PROCEDURE_NAME = upper(substr(?,INSTR(?,'.')+1 , LENGTH(?)))) "+
		"OR (A.OBJECT_NAME = upper(substr(?,0, INSTR(?,'.') -1 )) "+
		"AND A.PROCEDURE_NAME = upper(substr(?,INSTR(?,'.')+1 , LENGTH(?))))) "+
		"OR ((SUBSTR(A.OBJECT_NAME,0,LENGTH(A.OBJECT_NAME)-4) = (upper(?)) "+
		"OR A.OBJECT_NAME = (upper(?))) "+
		"AND OBJECT_TYPE IN ('PROCEDURE', 'FUNCTION'))";

		List<OTrnPrcS> lvOTrnPrc = jdbcTemplate.query(query,
		    new Object[]{
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
			    nomPrg,
            	    },
		    new OTrnPrcRowMapper());

		if (lvOTrnPrc.isEmpty()){
			BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, name, cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
		}

		return lvOTrnPrc;	
        
    }
}
