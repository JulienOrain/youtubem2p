package youtubemtop.checker;

import com.google.api.client.util.Strings;

import youtubemtop.exception.MissingParameterException;

/**
 * Checker for missing parameters
 *
 * @author julien.orain@gmail.com
 *
 */
public class ParameterChecker {

	/**
	 * Constructor
	 */
	private ParameterChecker() {
		super();
	}

	/**
	 * Check if the string parameter is not null or empty
	 *
	 * @param paramName
	 *            parameter's name
	 * @param paramValue
	 *            parameter's value
	 * @throws MissingParameterException
	 *             MissingParameterException
	 */
	public static void checkString(final ParameterIdEnum paramName, final String paramValue)
			throws MissingParameterException {
		if (Strings.isNullOrEmpty(paramValue)) {
			throw new MissingParameterException(paramName);
		}
	}

}
