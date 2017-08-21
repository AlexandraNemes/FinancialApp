package financial.file.parser.tx.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	// map used to group transactions by customer
	Map<CustomerDO, List <TransactionDO>> transactionMap = new HashMap<CustomerDO, List<TransactionDO>>();
	
	for (TXTransactionDTO txTransactionDTO : transactionList) {
	    
	    CustomerDO customer = new CustomerDO(txTransactionDTO.getCustomerNumber(), txTransactionDTO.getCustomerName());
	    TransactionDO transaction = new TransactionDO(txTransactionDTO.getRecordType().getType(), txTransactionDTO.getProcessingDate(),
		    txTransactionDTO.getTransactionType().getType(), txTransactionDTO.getTransactionAmount(), customer);

	    if(transactionMap.containsKey(customer)) {
		List<TransactionDO> transactionDOList = transactionMap.get(customer);
		transactionDOList.add(transaction);
	    } else {
		List<TransactionDO> transactionDOList = new ArrayList<TransactionDO>();
		transactionDOList.add(transaction);
		transactionMap.put(customer, transactionDOList);
	    }
	}
	
	// set the right customer for each transaction in the map
	for (CustomerDO parentCustomerDO : transactionMap.keySet()) {
	    for (TransactionDO transactionDO : transactionMap.get(parentCustomerDO)) {
		transactionDO.setCustomer(parentCustomerDO);
	    }
	}

	ICustomerDAO customerDAO = new CustomerDAO();
	ITransactionDAO transactionDAO = new TransactionDAO();

	for (CustomerDO customerDO : transactionMap.keySet()) {
	    try {
		CustomerDO dbCustomer = customerDAO.getCustomerByNumber(customerDO.getCustomerNumber());
		if (dbCustomer == null) {
		    customerDAO.create(customerDO);
		} else {
		    customerDO.setId(dbCustomer.getId());
		}
	    } catch (FinancialDBException e) {
		LOG.error("Exception occured while trying to insert customer " + customerDO + " to the database.");
		throw e;
	    }

	    List<TransactionDO> transactionDOList = transactionMap.get(customerDO);
	    for (TransactionDO transactionDO : transactionDOList) {
		try {
		    transactionDAO.create(transactionDO);
		} catch (FinancialDBException e) {
		    LOG.error("Exception occured while trying to insert transaction " + transactionDO + " to the database.");
		    throw e;
		}
	    }
	}
    }
}
