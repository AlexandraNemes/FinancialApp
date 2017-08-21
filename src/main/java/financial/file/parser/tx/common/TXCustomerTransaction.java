package financial.file.parser.tx.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to define transactions for each customer.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXCustomerTransaction {

    private int totalTrxCount;
    private int totalCreditTrxCount;
    private int totalDebitTrxCount;
    private BigDecimal totalTrxAmount = BigDecimal.ZERO;
    private BigDecimal totalCreditTrxAmount = BigDecimal.ZERO;
    private BigDecimal totalDebitTrxAmount = BigDecimal.ZERO;

    public TXCustomerTransaction(TXTransactionDTO transaction) {
	super();
	this.addTransaction(transaction);
    }

    // Map used to group transactions by their type
    private Map<TXTransactionTypeEnum, BigDecimal> transactionTypeMap = new HashMap<TXTransactionTypeEnum, BigDecimal>();

    private List<TXTransactionDTO> transactionList = new ArrayList<TXTransactionDTO>();

    /**
     * Adds transactions to the Transaction List. It also calculates the count
     * and the amount of the transactions and groups them by type.
     * 
     * @param transaction
     *            the Transaction object to be processed
     */
    public void addTransaction(TXTransactionDTO transaction) {

	transactionList.add(transaction);

	// increase the count of the transactions and add the amount to the
	// previous
	totalTrxCount++;
	totalTrxAmount = totalTrxAmount.add(transaction.getTransactionAmount());

	// calculate credit or debit amount and count depending on the Record Type
	if (transaction.getRecordType() == TXRecordTypeEnum.CREDIT_TRANSACTION) {
	    totalCreditTrxCount++;
	    totalCreditTrxAmount = totalCreditTrxAmount.add(transaction.getTransactionAmount());
	} else if (transaction.getRecordType() == TXRecordTypeEnum.DEBIT_TRANSACTION) {
	    totalDebitTrxCount++;
	    totalDebitTrxAmount = totalDebitTrxAmount.add(transaction.getTransactionAmount());
	}

	// add elements to the Map
	BigDecimal value = transactionTypeMap.get(transaction.getTransactionType());

	if (value == null) {
	    transactionTypeMap.put(transaction.getTransactionType(), transaction.getTransactionAmount());
	} else {
	    value = value.add(transaction.getTransactionAmount());
	    transactionTypeMap.put(transaction.getTransactionType(), value);
	}
    }

    public List<TXTransactionDTO> getTransactionList() {
	return transactionList;
    }

    public Map<TXTransactionTypeEnum, BigDecimal> getTransactionTypeMap() {
	return transactionTypeMap;
    }

    public int getTotalTrxCount() {
	return totalTrxCount;
    }

    public int getTotalCreditTrxCount() {
	return totalCreditTrxCount;
    }

    public int getTotalDebitTrxCount() {
	return totalDebitTrxCount;
    }

    public BigDecimal getTotalTrxAmount() {
	return totalTrxAmount;
    }

    public BigDecimal getTotalCreditTrxAmount() {
	return totalCreditTrxAmount;
    }

    public BigDecimal getTotalDebitTrxAmount() {
	return totalDebitTrxAmount;
    }

    public String toString() {
	return ("Total transaction count: " + this.totalTrxCount + ", Total Debit transaction count: " + this.totalDebitTrxCount
		+ ", Total Credit transaction type: " + this.totalCreditTrxCount + ", Total transaction Amount: " + this.totalTrxAmount
		+ ", Total Debit transaction amount: " + this.totalDebitTrxAmount + ", Total Credit transaction amount: " + this.totalCreditTrxAmount + ".");
    }

}
