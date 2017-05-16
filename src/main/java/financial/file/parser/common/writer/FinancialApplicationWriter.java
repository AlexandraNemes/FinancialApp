package financial.file.parser.common.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import financial.file.parser.common.exception.FileWriterException;

/**
 * Class used to write data to a file.
 * 
 * @author Alexandra Nemes
 *
 */
public class FinancialApplicationWriter {

    private static final Logger LOG = Logger.getLogger(FinancialApplicationWriter.class);

    /**
     * Writes data from a List of Strings to a given file.
     * 
     * 
     * @param fileContent
     *            the content to write
     * @param file
     *            the file to write to
     */
    public void write(List<String> fileContent, File file) throws FileWriterException {

	if (file != null && fileContent != null) {
	    if (!file.isDirectory() && !file.getPath().endsWith(File.separator)) {
		if (!file.exists()) {
		    try {
			if (file.getParentFile() != null) {
			    new File(file.getParentFile().getPath()).mkdirs();
			}
			file.createNewFile();
		    } catch (IOException e) {
			LOG.error("File " + file.getName() + " could not be created.");
			throw new FileWriterException("could not create file", e);
		    }
		}

		if (LOG.isInfoEnabled()) {
		    LOG.info("Started writing data: " + fileContent.size() + " lines, in " + file.getPath());
		}

		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter(new FileWriter(file));
		    // write each line from fileContent to the given file
		    for (String line : fileContent) {
			if (line != null) {
			    writer.write(line + "\n");
			} else {
			    writer.write("\n");
			}
		    }
		} catch (IOException e) {
		    LOG.error("Exception occured.", e);
		} finally {
		    try {
			if (writer != null) {
			    writer.close();
			}
		    } catch (IOException e) {
			LOG.error("Exception occured.", e);
		    }
		}
		if (LOG.isInfoEnabled()) {
		    LOG.info("Finished writing data: " + fileContent.size() + " lines, in " + file.getPath() + "\n");
		}
	    } else {
		throw new FileWriterException("file is a folder.");
	    }
	} else {
	    throw new FileWriterException("missing mandatory input.");
	}
    }
}
