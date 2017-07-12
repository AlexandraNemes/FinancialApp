package financial.file.parser.common;

import java.util.ArrayList;
import java.util.List;

import financial.file.parser.common.processor.IFileProcessor;
import financial.file.parser.common.writer.IWriter;
import financial.file.parser.tx.processor.TXProcessor;
import financial.file.parser.tx.writer.TXDBWriter;
import financial.file.parser.tx.writer.TXSummaryFileWriter;

/**
 * Enum that contains the possible processors to be used.
 * 
 * @author Alexandra Nemes
 *
 */
public enum ProcessorsEnum {
    TX(TXProcessor.class, new ArrayList<Class<? extends IWriter>>() {{ add(TXSummaryFileWriter.class); add(TXDBWriter.class); }});

    private Class<? extends IFileProcessor> clazz;
    private List<Class<? extends IWriter>> writers;

    private ProcessorsEnum(Class<? extends IFileProcessor> clazz, List<Class<? extends IWriter>> writers) {
	this.clazz = clazz;
	this.writers = writers;
    }

    public Class<? extends IFileProcessor> getProcessorClass() {
	return clazz;
    }
    
    public List<Class<? extends IWriter>> getProcessorWritersClasses() {
	return writers;
    }
    


}