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
	// TODO Auto-generated constructor stub
    }

    public FinancialDBException(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    public FinancialDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

    public FinancialDBException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public FinancialDBException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    public String getErrorMessage() {
	return errorMessage;
    }

}
