package cz.muni.fi.helpers;

import org.springframework.dao.DataAccessException;

/**
 * Exception is thrown when there is a problem with accessing the data on the lower (DAO) layer.
 *
 * @author Martin Hořelka (469003)
 */
public class ServiceDataAccessException extends DataAccessException {

    public ServiceDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
