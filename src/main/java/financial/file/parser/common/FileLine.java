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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((lineContent == null) ? 0 : lineContent.hashCode());
	result = prime * result + lineNumber;
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	boolean areEqual = true;

	if (this == obj) {
	    areEqual = true;
	}

	if (obj == null) {
	    areEqual = false;
	} else {
	    if (getClass() != obj.getClass()) {
		areEqual = false;
	    }
	    FileLine other = (FileLine) obj;

	    if (lineContent == null) {
		if (other.lineContent != null) {
		    areEqual = false;
		}
	    } else if (!lineContent.equals(other.lineContent)) {
		areEqual = false;
	    }

	    if (lineNumber != other.lineNumber) {
		areEqual = false;
	    }
	}

	return areEqual;
    }

}
