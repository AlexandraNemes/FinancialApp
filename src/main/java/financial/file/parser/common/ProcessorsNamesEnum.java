package financial.file.parser.common;

import financial.file.parser.common.processor.IFileProcessor;
import financial.file.parser.tx.processor.TXProcessor;

/**
 * Enum that contains the possible processors to be used.
 * 
 * @author Alexandra Nemes
 *
 */
public enum ProcessorsNamesEnum {
    TX(TXProcessor.class);

    private Class<? extends IFileProcessor> clazz;

    private ProcessorsNamesEnum(Class<? extends IFileProcessor> clazz) {
	this.clazz = clazz;
    }

    public Class<? extends IFileProcessor> getProcessorClass() {
	return clazz;
    }
}
