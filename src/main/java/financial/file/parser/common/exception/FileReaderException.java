package financial.file.parser.common.exception;

/**
 * Custom exception for file parsers.
 * 
 * @author Alexandra Nemes
 *
 */
public class FileReaderException extends Exception {

    private static final long serialVersionUID = 2320450733154473740L;

    private String errorMessage;

    public FileReaderException(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

}
