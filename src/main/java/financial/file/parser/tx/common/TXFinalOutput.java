package financial.file.parser.tx.common;

import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;

/**
 * Class used to create the output to be written to the file, containing a list
 * of transactions and a list of validation errors.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXFinalOutput extends AbstractProcessorOutput {

    private List<TXTransaction> transactionList;
    private List<TXValidationError> validationErrorList;

    public TXFinalOutput(List<TXValidationError> validationErrorList, List<TXTransaction> transactionList) {
	this.validationErrorList = validationErrorList;
	this.transactionList = transactionList;
    }

    public List<TXTransaction> getTransactionList() {
	return transactionList;
    }

    public List<TXValidationError> getValidationErrorList() {
	return validationErrorList;
    }

}
