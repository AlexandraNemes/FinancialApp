package financial.file.parser.common;

import java.util.List;

/**
 * Abstract class used to define common behavior for the result after processing
 * different types of files.
 * 
 * @author Alexandra Nemes
 *
 */
public abstract class AbstractProcessorOutput<T extends ITransactionDTO> {

    public List<T> transactionList;
    
    public AbstractProcessorOutput(List<T> transactionList) {
	this.transactionList = transactionList;
    }
    public List<T> getTransactionList() {
        return transactionList;
    }
}
