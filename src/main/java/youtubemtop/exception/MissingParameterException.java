package youtubemtop.exception;

import youtubemtop.checker.ParameterIdEnum;

/**
 * Exception for missing parameters
 *
 * @author julien.orain@gmail.com
 *
 */
public class MissingParameterException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 5137676559449867088L;

	/**
	 * Constructor
	 *
	 * @param parameterName
	 *            parameter's name
	 */
	public MissingParameterException(final ParameterIdEnum parameterName) {
		super(buildMessage(parameterName));

	}

	/**
	 * Build message telling the parameter's name is missing
	 *
	 * @param parameterName
	 *            parameter's name
	 * @return exception's message
	 */
	private static String buildMessage(final ParameterIdEnum parameterName) {
		final StringBuilder builder = new StringBuilder();
		builder.append("Parameter : ").append(parameterName.getId()).append(" is missing.");
		return builder.toString();
	}
}
