package ru.asl.api.expression.exceptions;

/**
 * <p>IllegalVariableNameException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class IllegalVariableNameException extends ParsingException {
	/**
	 * <p>Constructor for IllegalVariableNameException.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 */
	public IllegalVariableNameException(final String name) {
		super("Invalid variable name: " + name);
	}
	/**
	 * <p>Constructor for IllegalVariableNameException.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param pos a int
	 */
	public IllegalVariableNameException(final String name, final int pos) {
		super("Invalid variable name: " + name, pos);
	}
}
