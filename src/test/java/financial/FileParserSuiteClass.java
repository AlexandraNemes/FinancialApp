package financial;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import financial.file.parser.common.reader.FinancialApplicationReaderTest;
import financial.file.parser.common.writer.FinancialApplicationWriterTest;
import financial.file.parser.handlers.FileHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({ 
    FinancialApplicationReaderTest.class,
    FinancialApplicationWriterTest.class,
    FileHandlerTest.class
})
public class FileParserSuiteClass {

}
