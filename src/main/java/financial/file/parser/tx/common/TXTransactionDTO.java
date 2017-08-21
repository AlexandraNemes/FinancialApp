package financial.file.parser.tx.common;

import java.math.BigDecimal;
import java.util.Date;

import financial.file.parser.common.ITransactionDTO;

/**
 * Class used to define TX transactions, with all their characteristics.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXTransactionDTO implements ITransactionDTO {

    private TXRecordTypeEnum recordType;
    private String customerNumber;
    private String customerName;
    private Date processingDate;
    private TXTransactionTypeEnum transactionType;
    private BigDecimal transactionAmount;

    public String toString() {
	return ("Record Type: " + this.getRecordType() + ", Customer Number: " + this.getCustomerNumber() + ", Customer Name: " + this.getCustomerName()
		+ ", Processing Date: " + this.getProcessingDate() + ", Transaction Type: " + this.getTransactionType() + ", Transaction Amount: "
		+ this.getTransactionAmount());
    }

    public String getCustomerNumber() {
	return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
	this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public TXRecordTypeEnum getRecordType() {
	return recordType;
    }

    public void setRecordType(TXRecordTypeEnum recordType) {
	this.recordType = recordType;
    }

    public Date getProcessingDate() {
	return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
	this.processingDate = processingDate;
    }

    public TXTransactionTypeEnum getTransactionType() {
	return transactionType;
    }

    public void setTransactionType(TXTransactionTypeEnum transactionType) {
	this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
	return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
	this.transactionAmount = transactionAmount;
    }
}
