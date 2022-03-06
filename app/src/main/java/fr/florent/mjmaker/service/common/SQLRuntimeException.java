package fr.florent.mjmaker.service.common;

import java.sql.SQLException;

/**
 * Runtime wrapper for {@link SQLException}
 */
public class SQLRuntimeException extends RuntimeException{
    public SQLRuntimeException(SQLException ex) {
        super(ex);
    }
}
