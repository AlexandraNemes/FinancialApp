package financial.file.parser.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;

import financial.data.DBConnector;
import financial.data.exception.FinancialDBException;

/**
 * Class used to start the application.
 * 
 * @author Alexandra Nemes
 *
 */
public class MainHandler {

    public static final Properties PROPERTIES = new Properties();
    public static final Scanner SCANNER = new Scanner(System.in);
    public static File outputFolder = null;

    private static final Logger LOG = Logger.getLogger(MainHandler.class);

    private static String inputFolderPath = null;
    private static String outputFolderPath = null;
    private static File inputFolder = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;
    private static String driver = null;

    public static void main(String[] args) throws FinancialDBException {
	FileHandler fileHandler = new FileHandler();
	readPropertiesFile();
	createFolders();
	initializeDBConnector();

	if (LOG.isInfoEnabled()) {
	    LOG.info("Options:");
	    LOG.info("	1. Enter input folder");
	    LOG.info("	2. Start processing:");

	    LOG.info("-------------------------------------------------------------------");
	    LOG.info("- To choose the input folder type 'input'.:");
	    LOG.info("- To start the processing type 'start'.");
	    LOG.info("- To close the application type 'exit'.");

	}

	boolean done = false;
	
	// if the user doesn't specify an input folder the input folder from
	// properties file will be used
	File userInputFolder = inputFolder;

	while (!done) {
	    if (LOG.isInfoEnabled()) {
		LOG.info("Enter command:");
	    }
	    String message = SCANNER.nextLine();
	    PossibleCommandsEnum command = PossibleCommandsEnum.convert(message);

	    switch (command) {
	    case INPUT:
		File output = fileHandler.readUserFolder("Enter the input folder or type 'exit':");
		if (output != null) {
		    userInputFolder = output;
		}
		break;
	    case START:
		fileHandler.processFiles(userInputFolder);
		break;
	    case EXIT:
		done = true;
		break;
	    default:
		if (LOG.isInfoEnabled()) {
		    LOG.info("The command does not exist. Enter command or type 'exit'.");
		}
		break;
	    }
	}
	SCANNER.close();
    }

    /**
     * Reads the properties file.
     * 
     */
    private static void readPropertiesFile() {
	InputStream inputStream = null;
	try {
	    inputStream = MainHandler.class.getClassLoader().getResourceAsStream("properties/config.properties");

	    if (LOG.isInfoEnabled()) {
		LOG.info("Started loading properties file.");
	    }
	    // load a properties file
	    PROPERTIES.load(inputStream);

	    // get the property value
	    inputFolderPath = PROPERTIES.getProperty("financial.application.file.parsers.input_folder");
	    outputFolderPath = PROPERTIES.getProperty("financial.application.file.parsers.output_folder");

	    url = PROPERTIES.getProperty("financial_app_db.jdbc.url");
	    driver = PROPERTIES.getProperty("financial_app_db.jdbc.driver");
	    username = PROPERTIES.getProperty("financial_app_db.jdbc.username");
	    password = PROPERTIES.getProperty("financial_app_db.jdbc.password");

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

    /**
     * Initialize the connection to the database.
     * 
     * @param dbURL
     * @param dbUsername
     * @param dbPassword
     * @param dbDriver
     * @throws FinancialDBException
     */
    public static void initializeDBConnector() throws FinancialDBException {
	try {
	    DBConnector.initializeConnector(url, username, password, driver);
	} catch (FinancialDBException e) {
	    LOG.error("Exception occured while starting the application.", e);
	    throw e;
	}
    }

    /**
     * Creates the input folder and the output folder using the paths read form
     * the properties file.
     * 
     * @param inputFolderPath
     * @param outputFolderPath
     */
    public static void createFolders() {
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
    }
}
