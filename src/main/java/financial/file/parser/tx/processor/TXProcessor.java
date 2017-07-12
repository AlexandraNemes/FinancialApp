package financial.file.parser.tx.processor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import financial.file.parser.common.FileLine;
import financial.file.parser.common.processor.IFileProcessor;
import financial.file.parser.tx.common.TXFinalOutput;
import financial.file.parser.tx.common.TXRecordTypeEnum;
import financial.file.parser.tx.common.TXTransactionDTO;
import financial.file.parser.tx.common.TXTransactionTypeEnum;
import financial.file.parser.tx.common.TXValidationError;

/**
 * Class used to process TX files.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXProcessor implements IFileProcessor {

    private static final Logger LOG = Logger.getLogger(TXProcessor.class);

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * Checks the list to get the lines that shouldn't be ignored. It process
     * those lines and adds the ones with issues in a List of ValidationErrors
     * and the other ones in a List of Transactions.
     * 
     * @param fileLineList
     *            the List that contains every line read from the file
     * @return an object that contains the lists of validation errors and
     *         completed transactions
     */
    public TXFinalOutput process(List<FileLine> fileLineList) {

	List<TXTransactionDTO> transactionList = new ArrayList<TXTransactionDTO>();
	List<TXValidationError> validationErrorList = new ArrayList<TXValidationError>();
	TXFinalOutput finalOutput = null;

	// will contain all the Strings from each line, separated by ","
	String[] values = null;

	boolean headerFound = false;
	boolean headerProcessed = false;

	// If lines shouldn't be ignored we create a Transaction object and a
	// ValidationError object.
	// We check each component of the String[] values to match the
	// conditions for being part of a Transaction object. If they match,
	// we set that value to the Transaction object, else we add a
	// ValidationError object to say where the error appeared and what the
	// error is.
	// In the end, if the line we read had no validation errors, we add the
	// Transaction object to a List of Transactions, else we add the
	// ValidationError object to a List of validation Errors

	if (LOG.isInfoEnabled()) {
	    LOG.info("Started processing " + fileLineList.size() + " elements");
	}

	for (FileLine line : fileLineList) {
	    if (!shouldIgnoreLine(line)) {
		if (!headerFound) {
		    headerFound = isHeader(line);
		}
		if (headerFound && headerProcessed) {

		    TXTransactionDTO transaction = new TXTransactionDTO();
		    TXValidationError validationError = new TXValidationError(line.getLineNumber());

		    if (LOG.isDebugEnabled()) {
			LOG.debug("Processing line: " + line.getLineNumber());
		    }

		    values = line.getLineContent().split(",");
		    for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		    }

		    // index is used to get the values from the String[] values
		    int index = 0;

		    // set the Record Type of the Transaction if value is
		    // correct, or the validation error if not
		    TXRecordTypeEnum recordType = TXRecordTypeEnum.fromValue(values[index]);
		    if (recordType == null) {
			String message = "Record Type " + values[index] + " is not valid";
			validationError.addErrorMessage(message);
		    } else {
			transaction.setRecordType(TXRecordTypeEnum.fromValue(values[index]));
		    }
		    index++;

		    // Set the Customer Number of the Transaction if value is
		    // correct, or the validation error if not
		    String customerNumber = values[index];
		    if (customerNumber.length() < 6 || customerNumber.length() > 10) {
			String message = "Customer number must contain from 6 to 10 characters: " + "this customer number contains " + customerNumber.length()
				+ " characters";
			validationError.addErrorMessage(message);
		    } else {
			transaction.setCustomerNumber(values[index]);
		    }
		    index++;

		    // Set the Customer Name of the Transaction if value is
		    // correct, or the validation error if not
		    String customerName = values[index];
		    if (customerName.length() > 50) {
			String message = "Customer name can not contain more than 50 characters: " + "this customer name contains " + customerName.length()
				+ " characters";
			validationError.addErrorMessage(message);
		    } else {
			transaction.setCustomerName(values[index]);
		    }
		    index++;

		    // set the Date of the Transaction if value is correct, or
		    // the validation error if not
		    String date = values[index];
		    try {
			Date transactionDate = format.parse(date);
			transaction.setProcessingDate(transactionDate);
		    } catch (ParseException e) {
			String message = "Date format is not valid: date format must be YYYY-MM-DD hh:mm:ss";
			validationError.addErrorMessage(message);
		    }
		    index++;

		    // set the Transaction Type of the Transaction if value is
		    // correct, or the validation error if not
		    TXTransactionTypeEnum transactionType = TXTransactionTypeEnum.fromValue(values[index]);
		    if (transactionType == null) {
			String message = "Transaction Type " + values[index] + " is not valid";
			validationError.addErrorMessage(message);
		    } else {
			transaction.setTransactionType(transactionType);
		    }
		    index++;

		    // set the Transaction Amount of the Transaction if value is
		    // correct, or the validation error if not.
		    // If the Record Type of the Transaction is Credit, the
		    // amount will be deducted, else the amount will be added
		    try {
			if (transaction.getRecordType() == TXRecordTypeEnum.CREDIT_TRANSACTION) {
			    transaction.setTransactionAmount(new BigDecimal(values[index]).abs().negate());
			} else if (transaction.getRecordType() == TXRecordTypeEnum.DEBIT_TRANSACTION) {
			    transaction.setTransactionAmount(new BigDecimal(values[index]).abs());
			}
		    } catch (NumberFormatException e) {
			String message = "Number format of the amount is not valid";
			validationError.addErrorMessage(message);
		    }

		    // add to the Transaction List or the Validation Error List,
		    // depending on the case
		    if (validationError.hasErrors()) {
			validationErrorList.add(validationError);
		    } else {
			transactionList.add(transaction);
		    }

		    // create the object that will represent the output of the
		    // method
		    finalOutput = new TXFinalOutput(validationErrorList, transactionList);

		} else {
		    headerProcessed = headerFound;
		}
	    }
	}

	if (LOG.isInfoEnabled()) {
	    LOG.info("Finished processing " + (validationErrorList.size() + transactionList.size()) + " elements. (only the lines that shouldn't be ignored.)");
	}
	return finalOutput;
    }

    private boolean shouldIgnoreLine(FileLine fileLine) {
	return (fileLine.getLineContent().trim().isEmpty() || fileLine.getLineContent().trim().startsWith("#"));
    }

    private boolean isHeader(FileLine fileLine) {
	return (fileLine.getLineContent().trim().startsWith("Record Type"));
    }    
}
