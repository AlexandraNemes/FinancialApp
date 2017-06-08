package financial.file.parser.tx.common;

/**
 * Enum containing the possible types of the transactions.
 * 
 * @author Alexandra Nemes
 *
 */
public enum TXTransactionTypeEnum {

    CASH("0"), CREDIT_CARD("1"), CHEQUE("2"), PHONE("3"), OTHER("4");

    private String transactionType;

    private TXTransactionTypeEnum(String transactionType) {
	this.transactionType = transactionType;
    }

    public String getTransactionType() {
	return transactionType;
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
