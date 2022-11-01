package org.aslstd.api.expression.operations;

import static org.aslstd.api.expression.DoubleOperations.add;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exceptions.OverflowException;

/**
 * <p>Add class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Add extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Add.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Add(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Add);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return add(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 1;
	}
}