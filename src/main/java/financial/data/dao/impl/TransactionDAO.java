package financial.data.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import financial.data.DBConnector;
import financial.data.dao.ITransactionDAO;
import financial.data.entities.TransactionDO;
import financial.data.exception.FinancialDBException;

/**
 * Class used to create TransactionDAO objects.
 * 
 * @author Alexandra Nemes
 *
 */
public class TransactionDAO implements ITransactionDAO {

    private static final Logger LOG = Logger.getLogger(TransactionDAO.class);

    private static final String SQL_INSERT = new StringBuilder(
	    "INSERT INTO transactions (record_type, processing_date, transaction_type, transaction_amount, customer_id) ").append("VALUES (?, ?, ?, ?, ?)")
		    .toString();

    /**
     * This method executes the query to insert a transaction to the database.
     * 
     * @param TransactionDO
     */
    @Override
    public void create(TransactionDO transaction) throws FinancialDBException {
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

	    preparedStatement.setString(1, transaction.getRecordType().name());
	    preparedStatement.setDate(2, new Date(transaction.getProcessingDate().getTime()));
	    preparedStatement.setString(3, transaction.getTransactionType().name());
	    preparedStatement.setBigDecimal(4, transaction.getTransactionAmount());
	    preparedStatement.setLong(5, transaction.getCustomer().getId());

	    // execute insert SQL statement
	    preparedStatement.executeUpdate();
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Inserted transaction " + transaction);
	    }

	} catch (SQLException | FinancialDBException e) {
	    LOG.error("Exception occured.", e);
	    throw new FinancialDBException("Exception occured while trying to create a transaction.", e);

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