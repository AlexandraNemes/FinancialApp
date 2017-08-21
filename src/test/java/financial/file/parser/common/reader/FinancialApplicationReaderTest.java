package financial.file.parser.common.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import financial.file.parser.common.FileLine;
import financial.file.parser.common.exception.FileReaderException;

public class FinancialApplicationReaderTest {

    private static final FinancialApplicationReader READER = new FinancialApplicationReader();

    @Test
    public void testFileReader() throws Exception {
	File testFile = new File("src/test/resources/reader/TestFile.tx");
	List<FileLine> actualFileLineList = READER.read(testFile);
	FileLine[] fileLineArray = { new FileLine(1, "        11111111111   "), new FileLine(2, "     232323224234234"), new FileLine(3, "   afsdfsfdsdf   "),
		new FileLine(4, ""), new FileLine(5, " "), new FileLine(6, "   ###8879hkvjhvjvkh") };
	validateFileLine(actualFileLineList, fileLineArray);
    }

    @Test
    public void testReaderInputNull() {
	Exception e = null;
	String message = "input is null";
	String errorMessage = null;
	try {
	    READER.read(null);
	} catch (FileReaderException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}
	assertNotNull(e);
	assertEquals(message, errorMessage);
    }

    @Test
    public void testInputFileDoesNotExist() {
	Exception e = null;
	String message = "file does not exist";
	String errorMessage = null;
	try {
	    READER.read(new File("test/files/reader/TestFil.tx"));
	} catch (FileReaderException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}
	assertNotNull(e);
	assertEquals(message, errorMessage);
    }

    @Test
    public void testInputIsAFolder() {
	Exception e = null;
	String message = "file is a folder";
	String errorMessage = null;
	try {
	    READER.read(new File("src/test/resources/reader/"));
	} catch (FileReaderException ex) {
	    e = ex;
	    errorMessage = ex.getErrorMessage();
	}
	assertNotNull(e);
	assertEquals(message, errorMessage);
    }

    private void validateFileLine(List<FileLine> fileLineList, FileLine[] fileLineArray) {
	List<FileLine> expectedList = Arrays.asList(fileLineArray);
	assertTrue(expectedList.equals(fileLineList));
    }

}
