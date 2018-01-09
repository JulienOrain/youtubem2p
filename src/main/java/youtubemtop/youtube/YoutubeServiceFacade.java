package youtubemtop.youtube;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;

import youtubemtop.checker.ParameterChecker;
import youtubemtop.exception.MissingParameterException;

/**
 * Facade for Youtube
 *
 * @author julien.orain@gmail.com
 *
 */
public class YoutubeServiceFacade {

	/** Service Youtube */
	private final YouTube youtube;

	/**
	 * Constructor
	 *
	 * @throws IOException
	 *             IOException
	 */
	public YoutubeServiceFacade() throws IOException {
		super();
		youtube = (YouTube) new YoutubeServiceFactory().getService();
	}

	/**
	 * Insert video in playlist
	 *
	 * @param videoId
	 *            video's id
	 * @param playlistId
	 *            playlist's id
	 * @throws MissingParameterException
	 *             MissingParameterException
	 * @throws IOException
	 *             IOException
	 */
	public void insertVideoIntoPlaylist(final String videoId, final String playlistId)
			throws MissingParameterException, IOException {
		ParameterChecker.checkString("videoId", videoId);
		ParameterChecker.checkString("playlistId", playlistId);

		// Appel au service d'insertion
		youtube.playlistItems().insert("snippet", buildPlaylistItem(videoId, playlistId)).execute();

	}

	/**
	 * Build playlist item
	 *
	 * @param videoId
	 *            video's id
	 * @param playlistId
	 *            playlist's id
	 * @return PlaylistItem
	 */
	private PlaylistItem buildPlaylistItem(final String videoId, final String playlistId) {
		final PlaylistItem playlistItem = new PlaylistItem();
		final PlaylistItemSnippet snippet = new PlaylistItemSnippet();
		snippet.set("playlistId", playlistId);
		final ResourceId resourceId = new ResourceId();
		resourceId.set("kind", "youtube#video");
		resourceId.set("videoId", videoId);
		snippet.setResourceId(resourceId);
		playlistItem.setSnippet(snippet);
		return playlistItem;
	}
}
