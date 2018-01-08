package youtubemtop.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import youtubemtop.Application;

public abstract class GoogleServiceFactory {

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the HTTP transport. */
	protected static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the JSON factory. */
	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private List<String> scopes;

	/**
	 * Constructor
	 *
	 * @param dataStorDir
	 *            dataStorDir
	 * @param scopes
	 *            scopes
	 */
	public GoogleServiceFactory(final File dataStorDir, final List<String> scopes) {
		super();
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(dataStorDir);
			this.scopes = scopes;
		} catch (final Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Create an authorized Credential object.
	 *
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		final InputStream in = Application.class.getResourceAsStream("/client_secret.json");
		final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, scopes).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		final Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
				.authorize("user");
		return credential;
	}

	public abstract AbstractGoogleJsonClient getService() throws IOException;
}
