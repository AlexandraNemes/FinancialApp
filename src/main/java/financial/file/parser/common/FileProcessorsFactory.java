package financial.file.parser.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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

    /**
     * This class is used to create processor objects that contain the class of
     * the processor and also the list of it's available writers.
     * 
     * @author Alexandra Nemes
     *
     */
    private class ProcessorDetails {
	private Class<? extends IFileProcessor<?>> processorType;
	private Map<String, Class<? extends IWriter<ITransactionDTO>>> writersMap;

	private ProcessorDetails(Class<? extends IFileProcessor<?>> processorType, Map<String, Class<? extends IWriter<ITransactionDTO>>> writersList) {
	    this.processorType = processorType;
	    this.writersMap = writersList;
	}

	public Class<? extends IFileProcessor<?>> getProcessorType() {
	    return processorType;
	}

	public Map<String, Class<? extends IWriter<ITransactionDTO>>> getWritersList() {
	    return writersMap;
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
    @SuppressWarnings("unchecked")
    public IFileProcessor<ITransactionDTO> getFileProcessor(String folderName) {
	IFileProcessor<ITransactionDTO> instance = null;

	// check if the input folder name matches one of the values
	// from our Map. If it does, search for a constructor without parameters
	// in that class and create an instance of it.
	Class<? extends IFileProcessor<?>> clazz = PROCESSORS_MAP.get(folderName).getProcessorType();
	if (clazz != null) {
	    Constructor<?> foundConstructor = null;
	    for (Constructor<?> constructor : clazz.getConstructors()) {
		if (constructor.getParameterCount() == 0) {
		    foundConstructor = constructor;
		}
	    }

	    if (foundConstructor != null) {
		try {
		    instance = (IFileProcessor<ITransactionDTO>) foundConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		    LOG.error("Exception occured.", e);
		}
	    } else {
		LOG.error("Can not create instance of " + clazz.getName() + " because it has no constructor without parameters.");
	    }
	}
	return instance;
    }

    /**
     * This method is used to return the writers available for a specific file type
     * depending on the name of the folder that contains that file.
     * 
     * @param folderName
     * @return writersMap
     */
    public Map<String, Class<? extends IWriter<ITransactionDTO>>> getWriters(String folderName) {
	Map<String, Class<? extends IWriter<ITransactionDTO>>> writersMap = null;
	writersMap = PROCESSORS_MAP.get(folderName).getWritersList();

	return writersMap;
    }
}
