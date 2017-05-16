package financial.file.parser.common.exception;

/**
 * Custom exception for file parsers.
 * 
 * @author Alexandra Nemes
 *
 */
public class FileWriterException extends Exception {

    private static final long serialVersionUID = 6973305449697021928L;

    private String errorMessage;

    public FileWriterException(String errorMessage) {
	super(errorMessage);
	this.errorMessage = errorMessage;
    }

    public FileWriterException(String errorMessage, Throwable exception) {
	super(errorMessage, exception);
	this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

}
