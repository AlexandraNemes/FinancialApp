package financial.file.parser.handlers;

/**
 * Enum containing the possible commands that the user can give when he runs the
 * application.
 * 
 * @author Alexandra Nemes
 *
 */
public enum PossibleCommandsEnum {

    UNKNOWN(""), 
    INPUT("input"), 
    START("start"), 
    EXIT("exit");

    private String command;

    private PossibleCommandsEnum(String command) {
	this.command = command;
    }

    public String getCommand() {
	return command;
    }

    /**
     * Converts the input user into a command.
     * 
     * @param message
     *            the input from the user
     * @return one of the possible Enum values
     */
    public static PossibleCommandsEnum convert(String message) {
	PossibleCommandsEnum output = UNKNOWN;
	if (message != null && !message.trim().isEmpty()) {
	    for (PossibleCommandsEnum command : PossibleCommandsEnum.values()) {
		if (message.trim().equalsIgnoreCase(command.getCommand())) {
		    output = command;
		    break;
		}
	    }
	}
	return output;
    }
}
