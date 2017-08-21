package financial.file.parser.common.writer;

import java.io.File;
import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;
import financial.file.parser.common.ITransactionDTO;

/**
 * Interface for defining a common behavior for the file writers of different types
 * of files.
 * 
 * @author Alexandra Nemes
 *
 */
public interface ISummaryFileWriter<T extends ITransactionDTO> extends IWriter<ITransactionDTO> {

    public List<String> write(AbstractProcessorOutput<T> output, File file);
}
