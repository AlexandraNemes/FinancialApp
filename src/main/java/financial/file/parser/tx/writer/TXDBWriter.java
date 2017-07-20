package financial.file.parser.tx.writer;

import java.util.List;

import org.apache.log4j.Logger;

import financial.data.dao.ICustomerDAO;
import financial.data.dao.ITransactionDAO;
import financial.data.dao.impl.CustomerDAO;
import financial.data.dao.impl.TransactionDAO;
import financial.data.entities.CustomerDO;
import financial.data.entities.TransactionDO;
import financial.data.exception.FinancialDBException;
import financial.file.parser.common.writer.IDBWriter;
import financial.file.parser.tx.common.TXTransactionDTO;

/**
 * Class used to create the output to be written in the database for TX files.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXDBWriter implements IDBWriter<TXTransactionDTO> {

    private static final Logger LOG = Logger.getLogger(TXDBWriter.class);

    /**
     * Method used to write customers and their transactions to the database.
     * 
     * @param transactionList
     *            the list of transactions that resulted from reading the file
     * 
     */
    @Override
    public void writeToDB(List<TXTransactionDTO> transactionList) throws FinancialDBException {

	ICustomerDAO customerDAO = new CustomerDAO();
	ITransactionDAO transactionDAO = new TransactionDAO();

	for (TXTransactionDTO txTransactionDTO : transactionList) {

	    CustomerDO customerDO = null;

	    try {
		customerDO = customerDAO.getCustomerByNumber(txTransactionDTO.getCustomerNumber());
		if (customerDO == null) {
		    customerDO = new CustomerDO(txTransactionDTO.getCustomerNumber(), txTransactionDTO.getCustomerName());
		    customerDAO.create(customerDO);
		}
	    } catch (FinancialDBException e) {
		LOG.error("Exception occured while trying to insert customer " + customerDO + " to the database.");
		throw e;
	    }

	    TransactionDO transactionDO = null;
	    try {
		customerDO.setId(customerDAO.getCustomerId(customerDO.getCustomerNumber()));
		transactionDO = new TransactionDO(txTransactionDTO.getRecordType().getType(), txTransactionDTO.getProcessingDate(),
			txTransactionDTO.getTransactionType().getType(), txTransactionDTO.getTransactionAmount(), customerDO);
		
		transactionDAO.create(transactionDO);
	    } catch (FinancialDBException e) {
		LOG.error("Exception occured while trying to insert transaction " + transactionDO + " to the database.");
		throw e;
	    }
	}
    }
}
