package financial.file.parser.handlers;

import static financial.file.parser.handlers.MainHandler.SCANNER;
import static financial.file.parser.handlers.MainHandler.outputFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import financial.data.exception.FinancialDBException;
import financial.file.parser.common.AbstractProcessorOutput;
import financial.file.parser.common.FileLine;
import financial.file.parser.common.FileProcessorsFactory;
import financial.file.parser.common.ITransactionDTO;
import financial.file.parser.common.exception.FileReaderException;
import financial.file.parser.common.processor.IFileProcessor;
import financial.file.parser.common.reader.FinancialApplicationReader;
import financial.file.parser.common.writer.IDBWriter;
import financial.file.parser.common.writer.ISummaryFileWriter;
import financial.file.parser.common.writer.IWriter;

/**
 * Class used to define methods for reading and processing the files.
 * 
 * @author Alexandra Nemes
 *
 */
public class FileHandler {

    private static final Logger LOG = Logger.getLogger(FileHandler.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss_");

    private static FinancialApplicationReader reader = new FinancialApplicationReader();
    private IWriter<ITransactionDTO> writer = null;

    /**
     * Takes a command from the user, a path to create an input or output
     * folder. If the path can be used to create the folder, the method returns
     * that folder.
     * 
     * @param message
     *            the command from the user
     * @return a folder created from the path given by the user
     */
    public File readUserFolder(String message) {
	File output = null;
	boolean tryAgain = true;
	String userInput = null;
	do {
	    System.out.println(message);
	    userInput = SCANNER.nextLine();
	    File userDefinedFile = new File(userInput);

	    if ((userDefinedFile.exists() && userDefinedFile.isDirectory()) || userInput.trim().equalsIgnoreCase("exit")) {
		output = userDefinedFile;
		tryAgain = false;
	    } else {
		tryAgain = true;
		System.out.println("Specified path does not exist or is not a directory.");
	    }
	} while (tryAgain);

	return output;
    }

    /**
     * Checks all the files from the input folder. If it can find a
     * corresponding processor it will process the files from that folder and
     * write the result where the user chooses. It also creates the folder
     * structure for the output if the user chooses to write to file.
     * 
     * @param inputFolder
     */
    public void processFiles(final File inputFolder) {
	for (File folder : inputFolder.listFiles()) {
	    // check the input folder and get the names of its folders
	    if (folder.isDirectory()) {
		String folderName = folder.getName();
		if (LOG.isInfoEnabled()) {
		    LOG.info("Searching folders in input folder " + inputFolder.getName());
		    LOG.info("Found folder: " + folderName);
		}

		// get a processor to use, according to the folder name
		IFileProcessor<ITransactionDTO> fileProcessorInstance = FileProcessorsFactory.getInstance().getFileProcessor(folderName);

		if (fileProcessorInstance != null) {
		    Map<String, Class<? extends IWriter<ITransactionDTO>>> writersMap = FileProcessorsFactory.getInstance().getWriters(folderName);

		    if (LOG.isDebugEnabled()) {
			LOG.debug("The processor to use is: " + fileProcessorInstance);
		    }

		    // for each folder that can be processed from the input
		    // folder all the files will be checked and read
		    for (File file : folder.listFiles()) {
			if (file.isFile()) {
			    if (LOG.isInfoEnabled()) {
				LOG.info("Found file:" + file.getName());
			    }

			    try {
				List<FileLine> fileLineList = reader.read(file);
				AbstractProcessorOutput<ITransactionDTO> finalOutput = fileProcessorInstance.process(fileLineList);

				if (finalOutput != null) {
				    // this folder will contain the result files
				    String baseOutputFolderPath = new StringBuilder
					    (outputFolder.getPath()).append(File.separator)
					    .append(folderName).append(File.separator)
					    .append("ProcessedFiles").append(File.separator).toString();
				    
				    // this folder will contain the original files,
				    // moved from the input folder
				    String originalFilesPath = new StringBuilder
					    (baseOutputFolderPath).append(File.separator)
					    .append("OriginalFiles").toString();
				    
				    File baseOutputFolder = new File(baseOutputFolderPath);
				    File originalFiles = new File(originalFilesPath);
				    
				    baseOutputFolder.mkdirs();
				    originalFiles.mkdirs();
				    
				    if (LOG.isInfoEnabled()) {
					LOG.info("Available writers: ");
				    }
				    
				    for (String command : writersMap.keySet()) {
					Class<? extends IWriter<ITransactionDTO>> writerClass = writersMap.get(command);
					if (LOG.isInfoEnabled()) {
					    LOG.info("type " + "'" + command + "'" + " to use class " + writerClass.getSimpleName());
					}
				    }
				    
				    boolean done = false;
				    while (!done) {
					String message = SCANNER.nextLine();
					
					Class<? extends IWriter<ITransactionDTO>> writerClassToUse = writersMap.get(message);
					
					if (writerClassToUse != null) {
					    done = true;
					    
					    try {
						writer = writerClassToUse.newInstance();
					    } catch (InstantiationException | IllegalAccessException e) {
						LOG.error("Error occured " + e.getMessage());
					    }
					    
					    String outputFileName = DATE_FORMAT.format(new Date()) + file.getName();
					    
					    moveFile(file, originalFiles, outputFileName);
					    if (LOG.isDebugEnabled()) {
						LOG.debug("Moved file: " + file.getName() + " (renamed as " + outputFileName + ") to folder: " + originalFiles);
					    }
					    
					    // write to file
					    if (writer instanceof ISummaryFileWriter) {
						File outputFile = new File(baseOutputFolderPath + outputFileName);
						((ISummaryFileWriter<ITransactionDTO>) writer).write(finalOutput, outputFile);
					    }
					    
					    // write to database
					    if (writer instanceof IDBWriter) {
						try {
						    List<ITransactionDTO> transactionList = finalOutput.getTransactionList();
						    ((IDBWriter<ITransactionDTO>) writer).writeToDB(transactionList);
						} catch (FinancialDBException e) {
						    LOG.error("Could not write to database. Error: " + e.getErrorMessage());
						}
					    }
					} else {
					    if (message != null && !message.trim().isEmpty() && message.trim().equalsIgnoreCase("exit")) {
						done = true;
						break;
					    }
					    if (LOG.isInfoEnabled()) {
						LOG.info("No writer can be created with the command: " + message);
						LOG.info("Enter command or type 'exit' to exit.");
					    }
					}
				    }
				} else {
				    if (LOG.isInfoEnabled()) {
					LOG.info("The file " + file.getName() + " is not processable.");
				    }
				}
			    } catch (FileReaderException e) {
				LOG.error("The file " + file.getName() + " can not be read because " + e.getErrorMessage());
			    }

			} else {
			    if (LOG.isInfoEnabled()) {
				LOG.info("The folder " + folder.getName() + " does not contain any files.");
			    }
			}
		    }
		    if (LOG.isInfoEnabled()) {
			LOG.info("Finished with the folder: " + folder.getName());
		    }
		} else {
		    if (LOG.isInfoEnabled()) {
			LOG.info("The folder: " + folder.getName() + " is not processable.");
		    }
		}
	    } else {
		if (LOG.isInfoEnabled()) {
		    LOG.info("The file: " + folder.getName() + " is not a folder.");
		}
	    }
	}
    }

    /**
     * Moves a file to a specified folder.
     * 
     * 
     * @param file
     *            the file to move
     * @param outputFolder
     *            the folder where the file should be moved
     * @param fileName
     *            the name of the file to move
     */
    private void moveFile(File file, File outputFolder, String fileName) {
	Path filePath = null;
	Path outputFolderPath = null;

	if (fileName == null) {
	    fileName = file.getName();
	}

	// setting the file path and the output folder path
	if (file.isFile() && outputFolder.isDirectory()) {
	    filePath = FileSystems.getDefault().getPath(file.getPath());
	    outputFolderPath = FileSystems.getDefault().getPath(outputFolder.getPath() + File.separator + fileName);
	} else if (file.isDirectory() && outputFolder.isFile()) {
	    filePath = FileSystems.getDefault().getPath(outputFolder.getPath());
	    outputFolderPath = FileSystems.getDefault().getPath(file.getPath() + File.separator + fileName);
	} else {
	    LOG.error("Can not set the path for the file " + file.getName() + " and the output folder " + outputFolder.getName());
	}

	// the paths were set so the file can be moved
	if (filePath != null && outputFolderPath != null) {
	    try {
		Files.move(filePath, outputFolderPath, StandardCopyOption.ATOMIC_MOVE);
	    } catch (IOException e) {
		LOG.error("Can not move file " + outputFolder.getName() + " to folder " + fileName);
	    }
	}
    }
}
