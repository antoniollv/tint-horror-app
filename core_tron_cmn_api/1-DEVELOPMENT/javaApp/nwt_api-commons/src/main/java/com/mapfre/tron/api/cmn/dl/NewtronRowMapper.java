package com.mapfre.tron.api.cmn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.bo.ObjS;

/**
 * RowMapper que extienden los mapper de los conceptos lógicos y que gestiona los datos comunes en caso de OK.
 * @param <T> clase del objeto P del concepto lógico que maneja el DAO.
 */
public abstract class NewtronRowMapper<T extends ObjS> implements RowMapper<T> {
	
    /**
     * Completa los datos del objeto P
     * @param p objeto P
     * @param s objeto S
     * @param rs resultset de la BBDD
     * @param rowNum numero de fila del resultset
     * @throws SQLException error jdbc
     */
	@Deprecated
	protected void complete(T p, ObjS s, ResultSet rs, int rowNum) throws SQLException {
//		p.setObj(s);
//		p.getOTrnPrcS().setTrmVal(CTrn.TRM_VAL_OK);
//      p.getOTrnPrcS().setObjTypVal(p.getClass().getSimpleName());
//		p.getOTrnPrcS().getOTrnRwdT().add(new OTrnRwdS());
//		p.getOTrnPrcS().getOTrnRwdT().get(0).setRwdVal(rs.getString("rowid"));
	}
	
	/**
	 * Recupera el Date como java.util.Date.
	 * @param rs  resultset de la BBDD
	 * @param col nombre de la columna
	 * @return Date como java.util.Date
	 * @throws SQLException error jdbc
	 */
	protected Date getDate(ResultSet rs, String col) throws SQLException {
	    java.sql.Date d = rs.getDate(col);
	    if (d != null) {
	        return new Date(d.getTime());
	    }
	    return d;
	}
}
