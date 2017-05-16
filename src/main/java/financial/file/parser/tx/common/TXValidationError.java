package financial.file.parser.tx.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create a list of validation errors.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXValidationError {

    private int lineNumber;
    private List<String> errorList;

    public TXValidationError(int lineNumber) {
	this.lineNumber = lineNumber;
	errorList = new ArrayList<String>();
    }

    public TXValidationError(int number, String error) {
	this.lineNumber = number;
	errorList = new ArrayList<String>();
	errorList.add(error);
    }

    public TXValidationError(int number, List<String> errorList) {
	this.lineNumber = number;
	this.errorList = errorList;
    }

    public boolean hasErrors() {
	return !errorList.isEmpty();
    }

    public void addErrorMessage(String errorMessage) {
	errorList.add(errorMessage);
    }

    public int getLineNumber() {
	return lineNumber;
    }

    public List<String> getErrorList() {
	return errorList;
    }
}
