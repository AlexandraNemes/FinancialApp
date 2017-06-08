package financial.file.parser.common.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import financial.file.parser.common.FileLine;
import financial.file.parser.common.exception.FileWriterException;
import financial.file.parser.common.reader.FinancialApplicationReader;
import financial.file.parser.common.writer.impl.FinancialApplicationFileWriter;

public class FinancialApplicationWriterTest {
    private static final FinancialApplicationFileWriter WRITER = new FinancialApplicationFileWriter();
    private static final FinancialApplicationReader READER = new FinancialApplicationReader();

    /**
     * test if the content is written correctly. when the list to be written
     * contains null values we expect to write an empty line instead
     * 
     * @throws Exception
     */
    @Test
    public void testFileWriter() throws Exception {
	File outputFile = new File("src/test/resources/writer/testFile.txt");
	outputFile.delete();
	List<String> mockData = getMockData();

	WRITER.writeToFile(mockData, outputFile);

	assertTrue(outputFile.exists());

	List<FileLine> fileLineList = READER.read(outputFile);

	List<String> fileContentList = fileLineToString(fileLineList, outputFile);
	assertTrue(areEqual(mockData, fileContentList));

	outputFile.delete();
    }

    /**
     * test when output file does not exist
     */
    @Test
    public void testIncorrectFile() {
	Exception e = null;
	String message = "could not create file";
	String errorMessage = null;

	File outputFile = new File("");
	List<String> mockData = getMockData();

	outputFile.delete();

	try {
	    WRITER.writeToFile(mockData, outputFile);
	} catch (FileWriterException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}

	assertNotNull(e);
	assertEquals(message, errorMessage);

	outputFile.delete();
    }

    /**
     * test when outputFile is null
     */
    @Test
    public void testNullInput_1() {
	Exception e = null;
	String message = "missing mandatory input.";
	String errorMessage = null;

	List<String> mockData = getMockData();

	try {
	    WRITER.writeToFile(mockData, null);
	} catch (FileWriterException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}

	assertNotNull(e);
	assertEquals(message, errorMessage);
    }

    /**
     * test when outputFile and the content list are null
     */
    @Test
    public void testNullInput_2() {
	Exception e = null;
	String message = "missing mandatory input.";
	String errorMessage = null;

	try {
	    WRITER.writeToFile(null, null);
	} catch (FileWriterException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}

	assertNotNull(e);
	assertEquals(message, errorMessage);
    }

    /**
     * test when the content list is null
     */
    @Test
    public void testFileContent_1() {
	Exception e = null;
	String message = "missing mandatory input.";
	String errorMessage = null;

	File outputFile = new File("src/test/resources/writer/testFile.txt");
	outputFile.delete();

	try {
	    WRITER.writeToFile(null, outputFile);
	} catch (FileWriterException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}

	assertNotNull(e);
	assertEquals(message, errorMessage);

	outputFile.delete();
    }

    /**
     * test when the content of the list is empty
     * 
     * @throws Exception
     */
    @Test
    public void testFileContent_2() throws Exception {
	File outputFile = new File("src/test/resources/writer/testFile.txt");
	outputFile.delete();
	List<String> mockData = new ArrayList<String>();

	WRITER.writeToFile(mockData, outputFile);

	assertTrue(outputFile.exists());

	List<FileLine> fileLineList = READER.read(outputFile);

	List<String> fileContentList = null;
	fileContentList = fileLineToString(fileLineList, outputFile);

	assertEquals(mockData, fileContentList);
	outputFile.delete();
    }

    private List<String> getMockData() {
	List<String> mockData = new ArrayList<String>();
	mockData.add("afaga");
	mockData.add("111  afaga");
	mockData.add("");
	mockData.add("  afa555ga ");
	mockData.add(null);
	mockData.add("25bafaga");
	mockData.add(" ");

	return mockData;
    }

    private List<String> fileLineToString(List<FileLine> fileLineList, File outputFile) {
	List<String> fileContentList = new ArrayList<String>();
	for (FileLine line : fileLineList) {
	    fileContentList.add(line.getLineContent());
	}
	return fileContentList;
    }

    private boolean areEqual(List<String> expectedStringsList, List<String> actualStringsList) {
	boolean result = false;

	assertTrue(expectedStringsList.size() == actualStringsList.size());

	for (int i = 0; i < expectedStringsList.size(); i++) {
	    String expected = expectedStringsList.get(i);
	    String actual = actualStringsList.get(i);

	    if ((expected == null && actual == null) || (expected == null && actual != null && actual.trim().isEmpty())
		    || (actual == null && expected != null && expected.trim().isEmpty()) || (expected != null && actual != null && expected.equals(actual))) {
		result = true;
	    }
	}

	return result;
    }
}
