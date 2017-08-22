package financial.file.parser.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import financial.file.parser.common.FileLine;
import financial.file.parser.common.reader.FinancialApplicationReader;

public class FileHandlerTest {

    private static final FileHandler FILE_HANDLER = MainHandler.FILE_HANDLER;

    private static final FinancialApplicationReader READER = new FinancialApplicationReader();

    private static final String BACKUP_TX_TEST_FILE = "src/test/resources/inputFolder/TX/SampleFile.tx";
    private static final String INPUT_TEST_FOLDER = "target/test/inputFolder";
    private static final String TX_INPUT_TEST_FOLDER = INPUT_TEST_FOLDER + "/TX";
    private static final String OUTPUT_TEST_FOLDER = "target/test/outputFolder";
    private static final String TX_OUTPUT_TEST_FOLDER = OUTPUT_TEST_FOLDER + "/TX";
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String CURRENT_DATE = FORMAT.format(new Date());

    @Before
    public void beforeEveryTest() {
	File testInputFolder = new File(TX_INPUT_TEST_FOLDER);
	File testOutputFolder = new File(OUTPUT_TEST_FOLDER);
	MainHandler.outputFolder = testOutputFolder;

	this.deleteDir(testInputFolder);
	this.deleteDir(testOutputFolder);

	testInputFolder.mkdirs();
	testOutputFolder.mkdirs();
    }
    
    
    @Test
    @Ignore 
    // this test is no longer suited for the method processFiles in FileHandler class
    // because the method was modified and needs data from the user in order to run
    // TODO rewrite in order to be able to test the functionality of the method
    public void testProcessFiles() throws Exception {

	// copy the test file from the original folder to the test folder
	copyBackUpFile();

	// process the files
	FILE_HANDLER.processFiles(new File(INPUT_TEST_FOLDER));
	

	File testOutputFolderTX = new File(TX_OUTPUT_TEST_FOLDER);
	File processedFiles = new File(testOutputFolderTX + "/ProcessedFiles");
	File originalFiles = new File(processedFiles + "/OriginalFiles");

	assertTrue(testOutputFolderTX.exists() && testOutputFolderTX.isDirectory());
	assertTrue(processedFiles.exists() && processedFiles.isDirectory());
	assertTrue(originalFiles.exists() && originalFiles.isDirectory());

	// read the processed SampleFile
	List<FileLine> fileContent = new ArrayList<FileLine>();
	for (File file : processedFiles.listFiles()) {
	    if (file.getName().contains("SampleFile.tx")) {
		fileContent = READER.read(file);
	    }
	}

	// retrieve the content of each line from the processed SampleFile
	List<String> actualFileContent = new ArrayList<String>();
	for (FileLine line : fileContent) {
	    actualFileContent.add(line.getLineContent());
	}

	// get the expected file content
	List<String> expectedFileContent = loadExpectedFileContent();

	assertEquals(expectedFileContent, actualFileContent);

	// we know that we processed only a single file, check that the output
	// folder contains only one file (the one that has been processed)
	assertTrue(originalFiles.listFiles().length == 1);

	// get the hash code of the original SampleFile
	String backUpFileHashCode = getFileHashCode(BACKUP_TX_TEST_FILE);
	String testFileHashCode = getFileHashCode(originalFiles.listFiles()[0].getPath());

	assertEquals(backUpFileHashCode, testFileHashCode);
    }

    private String getFileHashCode(String file) throws NoSuchAlgorithmException, IOException {
	MessageDigest md = MessageDigest.getInstance("SHA1");
	FileInputStream fis = new FileInputStream(file);
	byte[] dataBytes = new byte[1024];

	int nread = 0;

	while ((nread = fis.read(dataBytes)) != -1) {
	    md.update(dataBytes, 0, nread);
	}

	byte[] mdbytes = md.digest();

	// convert the byte to hex format
	StringBuffer sb = new StringBuffer("");
	for (int i = 0; i < mdbytes.length; i++) {
	    sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	}

	fis.close();
	return sb.toString();
    }

    private void deleteDir(File folder) {
	File[] contents = folder.listFiles();
	if (contents != null) {
	    for (File crntFolder : contents) {
		deleteDir(crntFolder);
		crntFolder.delete();
	    }
	}
    }

    private List<String> loadExpectedFileContent() {
	List<String> fileContent = new ArrayList<String>();

	fileContent.add("File has been successfully processed.");
	fileContent.add("");
	fileContent.add("Processing date: " + CURRENT_DATE);
	fileContent.add("");
	fileContent.add("Customer number: 2211111111");
	fileContent.add("Total count of the transactions: 2");
	fileContent.add("Total count of debit transactions: 1");
	fileContent.add("Total count of credit transactions: 1");
	fileContent.add("Total amount of the transactions: 0.00");
	fileContent.add("Total amount of debit transactions: 120.75");
	fileContent.add("Total amount of credit transactions: -120.75");
	fileContent.add("Total amount of CASH transactions: -120.75");
	fileContent.add("Total amount of CREDIT_CARD transactions: 120.75");
	fileContent.add("");
	fileContent.add("Customer number: 1111111111");
	fileContent.add("Total count of the transactions: 6");
	fileContent.add("Total count of debit transactions: 3");
	fileContent.add("Total count of credit transactions: 3");
	fileContent.add("Total amount of the transactions: 71.00");
	fileContent.add("Total amount of debit transactions: 331.65");
	fileContent.add("Total amount of credit transactions: -260.65");
	fileContent.add("Total amount of CASH transactions: -126.55");
	fileContent.add("Total amount of CREDIT_CARD transactions: -11.55");
	fileContent.add("Total amount of CHEQUE transactions: 0.00");
	fileContent.add("Total amount of PHONE transactions: 122.55");
	fileContent.add("Total amount of OTHER transactions: 86.55");
	fileContent.add("");
	fileContent.add("Customer number: 2111111111");
	fileContent.add("Total count of the transactions: 9");
	fileContent.add("Total count of debit transactions: 8");
	fileContent.add("Total count of credit transactions: 1");
	fileContent.add("Total amount of the transactions: -300.00");
	fileContent.add("Total amount of debit transactions: 800.00");
	fileContent.add("Total amount of credit transactions: -1100.00");
	fileContent.add("Total amount of CASH transactions: 300.00");
	fileContent.add("Total amount of CREDIT_CARD transactions: -1000.00");
	fileContent.add("Total amount of CHEQUE transactions: 200.00");
	fileContent.add("Total amount of PHONE transactions: 100.00");
	fileContent.add("Total amount of OTHER transactions: 100.00");
	fileContent.add("");

	return fileContent;
    }

    private void copyBackUpFile() {
	File backupTestFile = new File(BACKUP_TX_TEST_FILE);
	File testFile = new File(TX_INPUT_TEST_FOLDER + File.separator + backupTestFile.getName());

	try {
	    Files.copy(backupTestFile.toPath(), testFile.toPath());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
