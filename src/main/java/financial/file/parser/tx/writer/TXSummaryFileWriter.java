package financial.file.parser.tx.writer;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import financial.file.parser.common.AbstractProcessorOutput;
import financial.file.parser.common.exception.FileWriterException;
import financial.file.parser.common.writer.ISummaryFileWriter;
import financial.file.parser.common.writer.impl.FinancialApplicationFileWriter;
import financial.file.parser.tx.common.TXCustomerTransaction;
import financial.file.parser.tx.common.TXFinalOutput;
import financial.file.parser.tx.common.TXTransactionDTO;
import financial.file.parser.tx.common.TXTransactionTypeEnum;
import financial.file.parser.tx.common.TXValidationError;

/**
 * Class used to create the final output for the TX files.
 * 
 * @author Alexandra Nemes
 *
 */

public class TXSummaryFileWriter implements ISummaryFileWriter<TXTransactionDTO> {

    private static final Logger LOG = Logger.getLogger(TXSummaryFileWriter.class);

    /**
     * Handles TXFinalOutput objects in order to write the result to a file. The
     * output file will contain: 1. Validation issues 2. Processing date of the
     * file 3. For each customer number a list of the count and the amount of
     * it's transactions.
     *
     * @param output
     *            an AbstractProcessorOutput object containing the list of
     *            validation errors and the list of completed transactions
     * @param outputFile
     *            the output file in which the summary will be written
     * @return a String List representing the content to be written
     * @see TXFinalOutput
     */
    @Override
    public List<String> write(AbstractProcessorOutput<TXTransactionDTO> output, File outputFile) {
	
	final TXFinalOutput txFinalOutput = (TXFinalOutput) output;

	if (LOG.isInfoEnabled()) {
	    LOG.info("Started creating the content to be written to the output file.");
	}
	// the content of the output file
	List<String> fileContent = new ArrayList<String>();

	if (LOG.isDebugEnabled()) {
	    LOG.debug("Found " + txFinalOutput.getValidationErrorList().size() + " validation issues.");
	}

	if (txFinalOutput.getValidationErrorList().isEmpty()) {
	    fileContent.add("File has been successfully processed.");
	} else {
	    fileContent.add("Validation issues: \n");
	}

	// adding the validation issues to the file content
	for (TXValidationError error : txFinalOutput.getValidationErrorList()) {
	    fileContent.add("Validation error on line " + error.getLineNumber() + ". ");

	    List<String> errorMessage = error.getErrorList();
	    for (String message : errorMessage) {
		fileContent.add(message);
	    }
	    fileContent.add("");
	}
	fileContent.add("");

	// Map used to group transactions by Customer Number
	Map<String, TXCustomerTransaction> customerNumberMap = new HashMap<String, TXCustomerTransaction>();

	// add elements to the Map
	for (TXTransactionDTO transaction : txFinalOutput.getTransactionList()) {

	    TXCustomerTransaction customerTransaction = customerNumberMap.get(transaction.getCustomerNumber());

	    if (customerTransaction == null) {
		customerNumberMap.put(transaction.getCustomerNumber(), new TXCustomerTransaction(transaction));
	    } else {
		customerTransaction.addTransaction(transaction);
	    }
	}

	if (LOG.isDebugEnabled()) {
	    LOG.debug("Found " + customerNumberMap.size() + " customers with valid transactions.");
	}

	final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	fileContent.add("Processing date: " + format.format(new Date()));
	fileContent.add("");

	// adding the transactions details to the file content
	for (String key : customerNumberMap.keySet()) {
	    TXCustomerTransaction value = customerNumberMap.get(key);

	    fileContent.add("Customer number: " + key);
	    fileContent.add("Total count of the transactions: " + value.getTotalTrxCount());
	    fileContent.add("Total count of debit transactions: " + value.getTotalDebitTrxCount());
	    fileContent.add("Total count of credit transactions: " + value.getTotalCreditTrxCount());
	    fileContent.add("Total amount of the transactions: " + value.getTotalTrxAmount());
	    fileContent.add("Total amount of debit transactions: " + value.getTotalDebitTrxAmount());
	    fileContent.add("Total amount of credit transactions: " + value.getTotalCreditTrxAmount());

	    Map<TXTransactionTypeEnum, BigDecimal> transactionTypeMap = value.getTransactionTypeMap();
	    Set<TXTransactionTypeEnum> transactionTypeSet = transactionTypeMap.keySet();

	    ArrayList<TXTransactionTypeEnum> transactionTypeList = new ArrayList<TXTransactionTypeEnum>(transactionTypeSet);
	    Collections.sort(transactionTypeList);

	    for (TXTransactionTypeEnum transactionTypeKey : transactionTypeList) {
		BigDecimal txValue = value.getTransactionTypeMap().get(transactionTypeKey);
		fileContent.add("Total amount of " + transactionTypeKey + " transactions: " + txValue);
	    }
	    fileContent.add("");
	}

	if (LOG.isInfoEnabled()) {
	    LOG.info("Finished creating the content for the output file.");
	}

	FinancialApplicationFileWriter fileWriter = new FinancialApplicationFileWriter();
	try {
	    fileWriter.writeToFile(fileContent, outputFile);
	} catch (FileWriterException e) {

	}
	
	return fileContent;
    }
}
