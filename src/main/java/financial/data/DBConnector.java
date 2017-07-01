package financial.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import financial.data.exception.FinancialDBException;

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
