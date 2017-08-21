package financial.file.parser.common.processor;

import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;
import financial.file.parser.common.FileLine;
import financial.file.parser.common.ITransactionDTO;

/**
 * Interface for defining a common behavior for the file parsers.
 * 
 * @author Alexandra Nemes
 *
 */
public interface IFileProcessor<T extends ITransactionDTO> {
    
    public AbstractProcessorOutput<T> process(List<FileLine> fileLineList);
   
}
