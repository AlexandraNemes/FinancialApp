package financial.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import financial.data.DBConnector;
import financial.data.dao.ICustomerDAO;
import financial.data.entities.CustomerDO;
import financial.data.exception.FinancialDBException;

/**
 * Class used to create CustomerDAO objects.
 * 
 * @author Alexandra Nemes
 *
 */
public class CustomerDAO implements ICustomerDAO {

    private static final Logger LOG = Logger.getLogger(CustomerDAO.class);

    private static final String SQL_INSERT = "INSERT INTO customers (customer_number, customer_name) VALUES (?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM customers WHERE customer_number = ?";

    /**
     * This method executes the query to insert a customer to the database.
     * 
     * @param CustomerDO
     */
    @Override
    public void create(CustomerDO customer) throws FinancialDBException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	try {
	    connection = DBConnector.getInstance().getConnection();
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Connection established.");
	    }

	    preparedStatement = connection.prepareStatement(SQL_INSERT);
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Prepared statement created.");
	    }

	    preparedStatement.setString(1, customer.getCustomerNumber());
	    preparedStatement.setString(2, customer.getCustomerName());

	    // execute insert SQL statement
	    preparedStatement.executeUpdate();
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Inserted customer " + customer);
	    }

	} catch (SQLException | FinancialDBException e) {
	    LOG.error("Exception occured.", e);
	    throw new FinancialDBException("Exception occured while trying to create a customer.", e);
	} finally {
	    try {
		preparedStatement.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the preparedStatement.");
	    }
	    try {
		connection.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the connection.");
	    }
	}

    }

    /**
     * This method is used to select a customer from the database based on his
     * customer number.
     * 
     * @param customerNumber
     */
    @Override
    public CustomerDO getCustomerByNumber(String customerNumber) throws FinancialDBException {

	CustomerDO customerDO = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	try {
	    connection = DBConnector.getInstance().getConnection();
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Connection established.");
	    }

	    preparedStatement = connection.prepareStatement(SQL_SELECT);
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Prepared statement created.");
	    }

	    preparedStatement.setString(1, customerNumber);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
		customerDO = new CustomerDO(resultSet.getLong("id"), resultSet.getString("customer_number"), resultSet.getString("customer_name"));
		if (LOG.isDebugEnabled()) {
		    LOG.debug("Found customer with customer number " + customerNumber);
		}
	    } else {
		if (LOG.isDebugEnabled()) {
		    LOG.debug("The customer with customer number " + customerNumber + " was not found in the database.");
		}
	    }
	} catch (SQLException | FinancialDBException e) {
	    LOG.error("Exception occured.", e);
	    throw new FinancialDBException("Exception occured while trying to select a customer from the database.", e);
	} finally {
	    try {
		preparedStatement.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the preparedStatement.");
	    }
	    try {
		connection.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the connection.");
	    }
	}
	return customerDO;
    }

    /**
     * This method is used to select a customer id from the database based on
     * his customer number.
     * 
     * @param customerNumber
     */
    @Override
    public Long getCustomerId(String customerNumber) throws FinancialDBException {
	Long customerId = null;

	Connection connection = null;
	PreparedStatement preparedStatement = null;

	try {
	    connection = DBConnector.getInstance().getConnection();
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Connection established.");
	    }

	    preparedStatement = connection.prepareStatement(SQL_SELECT);
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Prepared statement created.");
	    }

	    preparedStatement.setString(1, customerNumber);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
		customerId = resultSet.getLong("id");
		if (LOG.isDebugEnabled()) {
		    LOG.debug("Found customer id for customer number " + customerNumber);
		}
	    } else {
		if (LOG.isDebugEnabled()) {
		    LOG.debug("The customer with customer number " + customerNumber + " was not found in the database.");
		}
	    }
	} catch (SQLException | FinancialDBException e) {
	    LOG.error("Exception occured.", e);
	    throw new FinancialDBException("Exception occured while trying to select a customer from the database.", e);
	} finally {
	    try {
		preparedStatement.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the preparedStatement.");
	    }
	    try {
		connection.close();
	    } catch (SQLException e) {
		LOG.error("Could not close the connection.");
	    }
	}
	return customerId;
    }

}