package financial.file.parser.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import financial.file.parser.common.processor.IFileProcessor;

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
    private Map<String, Class<? extends IFileProcessor>> PROCESSORS_MAP = new HashMap<String, Class<? extends IFileProcessor>>();

    private static final FileProcessorsFactory INSTANCE = new FileProcessorsFactory();

    private FileProcessorsFactory() {
	for (ProcessorsNamesEnum value : ProcessorsNamesEnum.values()) {
	    PROCESSORS_MAP.put(value.name(), value.getProcessorClass());
	}
    }

    public static FileProcessorsFactory getInstance() {
	return INSTANCE;
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
     * @see ProcessorsNamesEnum
     */
    public IFileProcessor getFileProcessor(String folderName) {
	IFileProcessor instance = null;

	// check if the input folder name matches one of the values
	// from our Map. If it does, search for a constructor without parameters
	// in that class and create an instance of it.
	Class<? extends IFileProcessor> clazz = PROCESSORS_MAP.get(folderName);
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
}
