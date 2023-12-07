package com.mapfre.nwt.commons.dl;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * SqlParameterSource para consultas sin parámetros no contenmpladas en la actual versión de Spring.
 * Copiado de https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/EmptySqlParameterSource.html
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 6 abr. 2021 - 8:06:47
 *
 */
public class NewtronEmptySqlParameterSource implements SqlParameterSource {

    public static final NewtronEmptySqlParameterSource I = new NewtronEmptySqlParameterSource();

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSource#hasValue(java.lang.String)
     */
    @Override
    public boolean hasValue(String paramName) {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSource#getValue(java.lang.String)
     */
    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        throw new IllegalArgumentException("Empty SqlParameterSource");
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSource#getSqlType(java.lang.String)
     */
    @Override
    public int getSqlType(String paramName) {
        return SqlParameterSource.TYPE_UNKNOWN;
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSource#getTypeName(java.lang.String)
     */
    @Override
    public String getTypeName(String paramName) {
        return null;
    }

}
