package financial.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import financial.data.DBConnector;
import financial.data.dao.ICustomerDAO;
import financial.data.entities.CustomerDO;
import financial.data.exception.FinancialDBException;

public class CustomerDAO implements ICustomerDAO {

    private static final Logger LOG = Logger.getLogger(CustomerDAO.class);

    private static final String SQL_INSERT = "INSERT INTO customers (customer_number, customer_name) VALUES (?, ?)";

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

}