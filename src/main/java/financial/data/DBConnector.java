package financial.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import financial.data.exception.FinancialDBException;

/**
 * Class used to create a connection to the database.
 * 
 * @author Alexandra Nemes
 *
 */
public class DBConnector {

    private static final Logger LOG = Logger.getLogger(DBConnector.class);

    private static DBConnector INSTANCE = null;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;

    public DBConnector(String dbURL, String dbUsername, String dbPassword) {
	super();
	this.dbURL = dbURL;
	this.dbUsername = dbUsername;
	this.dbPassword = dbPassword;
    }

    /**
     * This method is used to initialize the connector for the database.
     * 
     * @param url
     * @param username
     * @param password
     * @param driverClass
     * @throws FinancialDBException
     */
    public static void initializeConnector(String url, String username, String password, String driverClass) throws FinancialDBException {
	if (INSTANCE == null) {
	    if (driverClass == null || driverClass.trim().isEmpty()) {
		throw new FinancialDBException("Driver class is mandatory.");
	    } else {
		try {
		    Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
		    throw new FinancialDBException("Driver class '" + driverClass + "' is missing in classpath.");
		}
		INSTANCE = new DBConnector(url, username, password);
	    }
	}
    }

    public static DBConnector getInstance() {
	return INSTANCE;
    }

    /**
     * This method is used to get a connection to the database.
     * 
     * @return Connection
     * @throws FinancialDBException
     */
    public Connection getConnection() throws FinancialDBException {
	if (INSTANCE == null) {
	    throw new FinancialDBException("DBConnector instance is null");
	}
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
	} catch (SQLException e) {
	    LOG.error("Could not establish database connection.");
	    throw new FinancialDBException(e.getMessage(), e);
	}
	return connection;
    }

}
