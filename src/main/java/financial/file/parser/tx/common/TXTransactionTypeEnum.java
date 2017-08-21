package financial.file.parser.tx.common;

import financial.data.entities.TransactionDO.TransactionTypeEnum;

/**
 * Enum containing the possible types of the transactions.
 * 
 * @author Alexandra Nemes
 *
 */
public enum TXTransactionTypeEnum {

    CASH("0", TransactionTypeEnum.CASH), 
    CREDIT_CARD("1", TransactionTypeEnum.CREDIT_CARD), 
    CHEQUE("2", TransactionTypeEnum.CHEQUE),
    PHONE("3", TransactionTypeEnum.PHONE), 
    OTHER("4", TransactionTypeEnum.OTHER);

    private String transactionType;
    private  TransactionTypeEnum type;

    private TXTransactionTypeEnum(String transactionType, TransactionTypeEnum type) {
	this.transactionType = transactionType;
	this.type = type;
    }

    public String getTransactionType() {
	return transactionType;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    /**
     * Checks for a match between a String it gets and the Enum values.
     * 
     * @param value
     *            the String to verify
     * @return one of the Enum values if a match is found, or null if not
     */
    public static TXTransactionTypeEnum fromValue(String value) {
	TXTransactionTypeEnum output = null;

	if (value != null && !value.trim().isEmpty()) {
	    for (TXTransactionTypeEnum transactionTypeEnum : values()) {
		if (transactionTypeEnum.getTransactionType().equals(value)) {
		    output = transactionTypeEnum;
		}
	    }
	}
	return output;
    }
}
