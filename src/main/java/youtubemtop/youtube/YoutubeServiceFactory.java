package youtubemtop.youtube;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

import youtubemtop.Application;
import youtubemtop.factory.GoogleServiceFactory;

/**
 * Factory for Youtube
 *
 * @author julien.orain@gmail.com
 *
 */
public class YoutubeServiceFactory extends GoogleServiceFactory {

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/youtube-mail-to-playlist-youtube");

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBEPARTNER);

	/**
	 * Constructor
	 */
	public YoutubeServiceFactory() {
		super(DATA_STORE_DIR, SCOPES);
	}

	/**
	 * Build and return an authorized API client service, such as a YouTube Data API
	 * client service.
	 *
	 * @return an authorized API client service
	 * @throws IOException
	 */
	@Override
	public AbstractGoogleJsonClient getService() throws IOException {
		final Credential credential = authorize();
		return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(Application.APPLICATION_NAME).build();
	}

}
