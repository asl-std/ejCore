package ru.asl.api.expression.parser;

/**
 * <p>StringSource class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class StringSource implements ExpressionSource {
	final String data;
	private int pos;
	private int variableNum = 0;
	/**
	 * <p>Constructor for StringSource.</p>
	 *
	 * @param data a {@link java.lang.String} object
	 */
	public StringSource(final String data) {
		this.data = data;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext() {
		return pos < data.length();
	}

	/** {@inheritDoc} */
	@Override
	public char next() {
		return data.charAt(pos++);
	}

	/** {@inheritDoc} */
	@Override
	public ExpressionException error(final String message) {
		return new ExpressionException(pos + ": " + message);
	}

	/** {@inheritDoc} */
	@Override
	public int getPos() {
		return pos;
	}

	/** {@inheritDoc} */
	@Override
	public int nextVariableNum() {
		return variableNum++;
	}
}
