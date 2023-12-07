package com.mapfre.tron.api.trn.syp.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.syp.bo.OTrnSypS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTrnSypDAOImpl implements IDlTrnSypDAO {

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
    public OTrnSypS get(BigDecimal cmpVal, String stuVal, String usrLngVal) {

	final String query = "SELECT t.COD_EST, t.NOM_EST, t.COD_PGM, t.NOM_PRG, t.TIP_CTO_LOGICO, t.NOM_PRG_CARGA_EST_NWT, t.URL_TRATAR, t.NOM_PRG_ELIMINAR, t.NOM_PRG_TRASPASAR, t.NOM_PRG_MOSTRAR, t.NOM_PRG_NWT, t.TIP_OPR_FUNCIONAL "
		+ "FROM G9990003 t "
		+ "WHERE t.cod_cia = ? AND t.cod_est = ? ";

	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
		    		cmpVal,
		    		stuVal,
	    },
		    new OTrnSypRowMapper());


	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SYP", "STU_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

}
