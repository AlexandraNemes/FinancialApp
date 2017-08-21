package financial.file.parser.tx.common;

import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;

/**
 * Class used to create the output to be written to the file for TX files,
 * containing a list of transactions, or two lists: one of transactions and one
 * of validation errors.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXFinalOutput extends AbstractProcessorOutput<TXTransactionDTO> {

    private List<TXValidationError> validationErrorList;

    public TXFinalOutput(List<TXTransactionDTO> transactionList) {
	super(transactionList);
    }

    public TXFinalOutput(List<TXValidationError> validationErrorList, List<TXTransactionDTO> transactionList) {
	super(transactionList);
	this.validationErrorList = validationErrorList;
    }

    public List<TXValidationError> getValidationErrorList() {
	return validationErrorList;
    }

}
