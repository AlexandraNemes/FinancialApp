package financial.file.parser.common.writer;

import java.io.File;
import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;

/**
 * Interface for defining a common behavior for the writers of different types
 * of files.
 * 
 * @author Alexandra Nemes
 *
 */
public interface ISummaryFileWriter extends IWriter {

    public List<String> write(AbstractProcessorOutput output, File file);
}
