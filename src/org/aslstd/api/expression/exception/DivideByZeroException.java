package org.aslstd.api.expression.exception;

/**
 * <p>DivideByZeroException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class DivideByZeroException extends ArithmeticException {
	/**
	 * <p>Constructor for DivideByZeroException.</p>
	 */
	public DivideByZeroException() {
		super("Division by zero");
	}
}