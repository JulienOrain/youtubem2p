package youtubemtop.checker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParameterCheckerTest {

	/**
	 * Test checkString with paramValue is null
	 */
	@Test
	public void testParamValueNull() {
		// Inits
		final ParameterIdEnum paramName = ParameterIdEnum.USER_ID;
		final String paramValue = null;
		// Test
		try {
			ParameterChecker.checkString(paramName, paramValue);
			// Fail
			fail("Exception not thrown");
		} catch (final IllegalArgumentException e) {
			// Asserts
			assertEquals("Parameter : " + paramName.getId() + " is missing.", e.getMessage());
		}
	}

	/**
	 * Test checkString
	 */
	@Test
	public void test() {
		// Inits
		final ParameterIdEnum paramName = ParameterIdEnum.USER_ID;
		final String paramValue = "paramValue";
		// Test
		try {
			ParameterChecker.checkString(paramName, paramValue);
		} catch (final IllegalArgumentException e) {
			// Fail
			fail("Exception should not have been thrown");
		}
	}

}
