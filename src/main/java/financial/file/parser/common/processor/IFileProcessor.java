package financial.file.parser.common.processor;

import java.util.List;

import financial.file.parser.common.AbstractProcessorOutput;
import financial.file.parser.common.FileLine;

/**
 * Interface for defining a common behavior for the file parsers.
 * 
 * @author Alexandra Nemes
 *
 */
public interface IFileProcessor {

    public AbstractProcessorOutput process(List<FileLine> fileLineList);
}
