package financial.file.parser.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import financial.file.parser.common.processor.IFileProcessor;
import financial.file.parser.common.writer.IWriter;

/**
 * Class used to define the processor to use depending on the file name.
 * 
 * @author Alexandra Nemes
 *
 */
public class FileProcessorsFactory {
    
    private static final Logger LOG = Logger.getLogger(FileProcessorsFactory.class);

    // a Map used to group classes by their name in order to
    // establish which file processor to use depending on the
    // name of the input folder
    private Map<String, ProcessorDetails> PROCESSORS_MAP = new HashMap<String, ProcessorDetails>();

    private static final FileProcessorsFactory INSTANCE = new FileProcessorsFactory();

    private FileProcessorsFactory() {
	for (ProcessorsEnum value : ProcessorsEnum.values()) {
	    PROCESSORS_MAP.put(value.name(), new ProcessorDetails(value.getProcessorClass(), value.getProcessorWritersClasses()));
	}
    }

    public static FileProcessorsFactory getInstance() {
	return INSTANCE;
    }
    
    private class ProcessorDetails {
	private Class<? extends IFileProcessor> processorType;
	private List<Class<? extends IWriter>> writersList;

	private ProcessorDetails(Class<? extends IFileProcessor> processorType, List<Class<? extends IWriter>> writersList) {
	    this.processorType = processorType;
	    this.writersList = writersList;
	}

	public Class<? extends IFileProcessor> getProcessorType() {
	    return processorType;
	}

	public List<Class<? extends IWriter>> getWritersList() {
	    return writersList;
	}

    }

    /**
     * This method is used to create a new instance of a class that extends
     * IFileProcessor depending on the name of the input folder. The possible
     * classes are in a Map that has as keys the values from
     * ProcessorsNamesEnum.
     * 
     * 
     * @param folderName
     *            the input folder where the files that have to be read are
     * @return an instance of one of the possible classes, depending on the
     *         input folder name
     * 
     * @see ProcessorsEnum
     */
    public IFileProcessor getFileProcessor(String folderName) {
	IFileProcessor instance = null;

	// check if the input folder name matches one of the values
	// from our Map. If it does, search for a constructor without parameters
	// in that class and create an instance of it.
	Class<? extends IFileProcessor> clazz = PROCESSORS_MAP.get(folderName).getProcessorType();
	if (clazz != null) {
	    Constructor<?> foundConstructor = null;
	    for (Constructor<?> constructor : clazz.getConstructors()) {
		if (constructor.getParameterCount() == 0) {
		    foundConstructor = constructor;
		}
	    }

	    if (foundConstructor != null) {
		try {
		    instance = (IFileProcessor) foundConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		    LOG.error("Exception occured.", e);
		}
	    } else {
		LOG.error("Can not create instance of " + clazz.getName() + " because it has no constructor without parameters.");
	    }
	}
	return instance;
    }
    
    public List<Class<? extends IWriter>> getWriters(String folderName) {
	List<Class<? extends IWriter>> writersList = null;
	writersList = PROCESSORS_MAP.get(folderName).getWritersList();
	
	return writersList;
	
    }

}
