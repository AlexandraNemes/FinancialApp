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
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FileLine other = (FileLine) obj;
	if (lineContent == null) {
	    if (other.lineContent != null)
		return false;
	} else if (!lineContent.equals(other.lineContent))
	    return false;
	if (lineNumber != other.lineNumber)
	    return false;
	return true;
    }

}
