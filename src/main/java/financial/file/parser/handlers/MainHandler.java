package financial.file.parser.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * Class used to start the application.
 * 
 * @author Alexandra Nemes
 *
 */
public class MainHandler {

    private static final Logger LOG = Logger.getLogger(MainHandler.class);
    private static File inputFolder = null;
    private static File outputFolder = null;
    
    public static void main(String[] args) {
	FileHandler fileHandler = new FileHandler();
	readPropertiesFile();

	Scanner scan = new Scanner(System.in);
	if (LOG.isInfoEnabled()) {
	    LOG.info("Options:");
	    LOG.info("1. Continue with the predefined settings");
	    LOG.info("2. Choose input folder");
	    LOG.info("3. Choose output folder");
	    LOG.info("4. Start processing:");
	    
	    LOG.info("-------------------------------------------------------------------");
	    LOG.info("- To continue with the predefined settings type 'continue'.");
	    LOG.info("- To choose the input folder type 'input'.:");
	    LOG.info("- To choose the output folder type 'output'.");
	    LOG.info("- To start the processing type 'start'.");
	    LOG.info("- To close the application type 'exit'."); 
	    
	    LOG.info("Enter command:");
	}

	boolean done = false;
	File userInputFolder = inputFolder;
	File userOutputFolder = outputFolder;
	
	while (!done) {
	    String message = scan.nextLine();
	    PossibleCommandsEnum command = PossibleCommandsEnum.convert(message);

	    switch (command) {
	    case CONTINUE:
		fileHandler.processFiles(inputFolder, outputFolder);
		break;
	    case INPUT:
		File output = fileHandler.doReadFile(scan, "Enter the input folder or type 'exit':");
		if (output != null) {
		    userInputFolder = output;
		}
		break;
	    case OUTPUT:
		output = fileHandler.doReadFile(scan, "Enter the output folder or type 'exit': ");
		if (output != null) {
		    userOutputFolder = output;
		}
		break;
	    case START:
		fileHandler.processFiles(userInputFolder, userOutputFolder);
		break;
	    case EXIT:
		done = true;
		break;
	    default:
		if (LOG.isInfoEnabled()) {
		    LOG.info("The command does not exist.");
		}
		break;
	    }
	}
	scan.close();
    }
    

    /**
     * Reads the properties file and creates the input folder and the output
     * folder.
     */
    private static void readPropertiesFile() {
	Properties prop = new Properties();
	InputStream inputStream = null;
	try {
	    inputStream = MainHandler.class.getClassLoader().getResourceAsStream("properties/config.properties");

	    if (LOG.isInfoEnabled()) {
		LOG.info("Started loading properties file.");
	    }
	    // load a properties file
	    prop.load(inputStream);

	    // get the property value
	    String inputFolderPath = prop.getProperty("financial.application.file.parsers.input_folder");
	    String outputFolderPath = prop.getProperty("financial.application.file.parsers.output_folder");

	    // the input folder path must be specified in the properties
	    // file. If it is not, the application will not run

	    if (inputFolderPath != null && !inputFolderPath.trim().isEmpty()) {
		if (LOG.isInfoEnabled()) {
		    LOG.info("Input folder path was found: " + inputFolderPath);
		}
		inputFolder = new File(inputFolderPath);
	    } else {
		LOG.error("The input folder path must be specified!");
	    }

	    // if the output folder path is found in the properties file,
	    // then that will be used to create a folder for each file type that
	    // it is able to process and in that folder it will store the result
	    // files and also a folder containing the original files. if the
	    // output folder path is not specified in the properties file the
	    // input folder path will be used to create the output folder there.

	    if (outputFolderPath == null || outputFolderPath.trim().isEmpty()) {
		if (LOG.isInfoEnabled()) {
		    LOG.info("The path for the output folder was not set in the properties file so the input folder path will be used");
		}
		outputFolderPath = inputFolderPath;
	    } else {
		if (LOG.isInfoEnabled()) {
		    LOG.info("Output folder path was found: " + outputFolderPath);
		}
	    }
	    outputFolder = new File(outputFolderPath);

	    if (LOG.isInfoEnabled()) {
		LOG.info("Finished loading properties file.");
	    }
	} catch (IOException e) {
	    LOG.error("Exception occured.", e);
	} finally {
	    if (inputStream != null) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    LOG.error("Exception occured.", e);
		}
	    }
	}
    }
}
