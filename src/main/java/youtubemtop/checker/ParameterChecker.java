package youtubemtop.checker;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;

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
	 */
	public static void checkString(final ParameterIdEnum paramName, final String paramValue) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(paramValue),
				"Parameter : " + paramName.getId() + " is missing.");
	}

}
