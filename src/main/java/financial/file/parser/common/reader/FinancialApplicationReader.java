package financial.file.parser.common.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import financial.file.parser.common.FileLine;
import financial.file.parser.common.exception.FileReaderException;

/**
 * Class used to read data from files.
 * 
 * @author Alexandra Nemes
 *
 */
public class FinancialApplicationReader {

    private static final Logger LOG = Logger.getLogger(FinancialApplicationReader.class);

    /**
     * Reads data from a file line by line and returns a FileLine List as an
     * output.
     * 
     * @param file
     *            the file to read
     * @return a FileLine List with Objects that store the number of the line
     *         and also it's content
     * @throws FileReaderException
     */
    public List<FileLine> read(File file) throws FileReaderException {
	if (file == null) {
	    throw new FileReaderException("input is null");
	}

	List<FileLine> fileLineList = null;

	if (LOG.isInfoEnabled()) {
	    LOG.info("Started reading file " + file.getName());
	}

	if (file.exists()) {
	    if (file.isFile()) {
		// create an ArrayList to store the lines read from the file
		fileLineList = new ArrayList<FileLine>();

		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));

		    String currentLine;
		    int index = 1;

		    // add each line to the ArrayList
		    while ((currentLine = reader.readLine()) != null) {
			fileLineList.add(new FileLine(index++, currentLine));
		    }

		} catch (IOException e) {
		    LOG.error("Exception occured.", e);
		} finally {
		    try {
			if (reader != null) {
			    reader.close();
			}
		    } catch (IOException e) {
			LOG.error("Exception occured.", e);

		    }
		}
	    } else {
		LOG.warn("The file is a folder. Will not process the folder.");
		throw new FileReaderException("file is a folder");
	    }
	} else {
	    LOG.warn("File does not exist.");
	    throw new FileReaderException("file does not exist");
	}

	if (LOG.isInfoEnabled()) {
	    LOG.info("Finished reading file " + file.getName());
	}
	return fileLineList;
    }
}
