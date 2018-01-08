package youtubemtop.youtube;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;

/**
 * Facade for Youtube
 *
 * @author julien.orain@gmail.com
 *
 */
public class YoutubeServiceFacade {
	private static YouTube youtube;

	private static YoutubeServiceFacade instance = null;

	/**
	 * Get Instance of {@link YoutubeServiceFacade} singleton
	 *
	 * @return instance of {@link YoutubeServiceFacade}
	 * @throws IOException
	 *             IOException
	 */
	public static YoutubeServiceFacade getInstance() throws IOException {
		if (instance == null) {
			instance = new YoutubeServiceFacade();
		}
		return instance;
	}

	/**
	 * Constructor
	 *
	 * @throws IOException
	 *             IOException
	 */
	public YoutubeServiceFacade() throws IOException {
		super();
		youtube = YoutubeServiceFactory.getYouTubeService();
	}

	/**
	 * Insert video in playlist
	 *
	 * @param videoId
	 *            video's id
	 * @param playlistId
	 *            playlist's id
	 * @throws IOException
	 *             IOException
	 */
	public void insertVideoIntoPlaylist(final String videoId, final String playlistId) throws IOException {

		// Création de l'item à ajouter à la playlist
		final PlaylistItem playlistItem = buildPlaylistItem(videoId, playlistId);

		// Appel au service d'insertion
		final YouTube.PlaylistItems.Insert playlistItemsInsertRequest = youtube.playlistItems().insert("snippet",
				playlistItem);

		// Execution de la requete
		playlistItemsInsertRequest.execute();

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
