package financial.file.parser.tx.common;

/**
 * Enum containing the possible record types of the transactions.
 * 
 * @author Alexandra Nemes
 *
 */
public enum TXRecordTypeEnum {

    CREDIT_TRANSACTION("0"), DEBIT_TRANSACTION("1");

    private String recordType;

    private TXRecordTypeEnum(String recordType) {
	this.recordType = recordType;
    }

    public String getRecordType() {
	return recordType;
    }

    /**
     * Checks for a match between a String it gets and the Enum values.
     * 
     * @param value
     *            the String to verify
     * @return one of the Enum values if a match is found, or null if not
     */
    public static TXRecordTypeEnum fromValue(String value) {
	TXRecordTypeEnum output = null;

	if (value != null && !value.trim().isEmpty()) {
	    for (TXRecordTypeEnum recordTypeEnum : values()) {
		if (recordTypeEnum.getRecordType().equals(value)) {
		    output = recordTypeEnum;
		}
	    }
	}
	return output;
    }
}
