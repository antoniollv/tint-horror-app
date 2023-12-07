package com.mapfre.tron.api.cmn.dl;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * SqlParameterSource para consultas sin parámetros no contenmpladas en la actual versión de Spring.
 * Copiado de https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/EmptySqlParameterSource.html
 */
public class NewtronEmptySqlParameterSource implements SqlParameterSource {
    
    public static final NewtronEmptySqlParameterSource I = new NewtronEmptySqlParameterSource();

    @Override
    public int getSqlType(String arg0) {return SqlParameterSource.TYPE_UNKNOWN;}

    @Override
    public String getTypeName(String arg0) { return null; }

    @Override
    public Object getValue(String arg0) throws IllegalArgumentException {
        throw new IllegalArgumentException("Empty SqlParameterSource");
    }

    @Override
    public boolean hasValue(String arg0) { return false; }

}
