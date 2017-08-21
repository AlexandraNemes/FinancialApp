package financial.data.exception;

/**
 * Custom exception for database.
 * 
 * @author Alexandra Nemes
 *
 */
public class FinancialDBException extends Exception {

    private static final long serialVersionUID = -800118664898712667L;
    private String errorMessage;

    public FinancialDBException() {
	super();
    }

    public FinancialDBException(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    public FinancialDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public FinancialDBException(String message, Throwable cause) {
	super(message, cause);
    }

    public FinancialDBException(Throwable cause) {
	super(cause);
    }

    public String getErrorMessage() {
	return errorMessage;
    }

}
