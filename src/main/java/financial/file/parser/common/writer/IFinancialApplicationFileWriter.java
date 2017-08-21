package financial.file.parser.common.writer;

import java.io.File;
import java.util.List;

import financial.file.parser.common.exception.FileWriterException;

/**
 * Interface used to define common behavior for file writers.
 * 
 * @author Alexandra Nemes
 *
 */
public interface IFinancialApplicationFileWriter {

    public void writeToFile(List<String> fileContent, File file) throws FileWriterException;
}
