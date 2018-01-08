package youtubemtop.gmail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import youtubemtop.Application;
import youtubemtop.factory.GoogleServiceFactory;

/**
 * Factory for Gmail
 *
 * @author julien.orain@gmail.com
 *
 */
public class GmailServiceFactory extends GoogleServiceFactory {

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/youtube-mail-to-playlist-gmail");

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/gmail-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_MODIFY);

	/**
	 * Constructor
	 */
	public GmailServiceFactory() {
		super(DATA_STORE_DIR, SCOPES);
	}

	/**
	 * Build and return an authorized Gmail client service.
	 *
	 * @return an authorized Gmail client service
	 * @throws IOException
	 */
	@Override
	public AbstractGoogleJsonClient getService() throws IOException {
		final Credential credential = authorize();
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(Application.APPLICATION_NAME).build();
	}
}
