package financial.data.entities;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDO {

    private Long id;
    private RecordTypeEnum recordType;
    private Date processingDate;
    private TransactionTypeEnum transactionType;
    private BigDecimal transactionAmount;
    private CustomerDO customer;

    public enum RecordTypeEnum {
	CREDIT, DEBIT
    }

    public enum TransactionTypeEnum {
	CASH, CREDIT_CARD, CHEQUE, PHONE, OTHER
    }

    public TransactionDO(RecordTypeEnum recordType, Date processingDate, TransactionTypeEnum transactionType, BigDecimal transactionAmount,
	    CustomerDO customer) {
	this.recordType = recordType;
	this.processingDate = processingDate;
	this.transactionType = transactionType;
	this.transactionAmount = transactionAmount;
	this.customer = customer;
    }

    public TransactionDO(Long id, RecordTypeEnum recordType, Date processingDate, TransactionTypeEnum transactionType, BigDecimal transactionAmount,
	    CustomerDO customer) {
	this.id = id;
	this.recordType = recordType;
	this.processingDate = processingDate;
	this.transactionType = transactionType;
	this.transactionAmount = transactionAmount;
	this.customer = customer;
    }

    public Long getId() {
	return id;
    }

    public RecordTypeEnum getRecordType() {
	return recordType;
    }

    public Date getProcessingDate() {
	return processingDate;
    }

    public TransactionTypeEnum getTransactionType() {
	return transactionType;
    }

    public BigDecimal getTransactionAmount() {
	return transactionAmount;
    }

    public CustomerDO getCustomer() {
	return customer;
    }

}