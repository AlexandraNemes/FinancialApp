package financial.file.parser.common;

/**
 * Class used to create objects containing a number(the line number) and a
 * text(the line content) for the lines read from the input file.
 * 
 * @author Alexandra Nemes
 *
 */
public class FileLine {

    private int lineNumber;
    private String lineContent;

    public FileLine(int number, String content) {
	super();
	this.lineNumber = number;
	this.lineContent = content;
    }

    public int getLineNumber() {
	return lineNumber;
    }

    public String getLineContent() {
	return lineContent;
    }

    @Override
    public String toString() {
	return "FileLine [lineNumber=" + lineNumber + ", lineContent=" + lineContent + "]";
    }
}

   