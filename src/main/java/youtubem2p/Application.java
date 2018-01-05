package youtubem2p;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.google.api.services.gmail.model.Message;

import youtubem2p.gmail.GmailIdEnum;
import youtubem2p.gmail.GmailServiceFacade;
import youtubem2p.youtube.YoutubePlaylistIdEnum;
import youtubem2p.youtube.YoutubeServiceFacade;

/**
 * Main Application
 *
 * @author julien.orain@gmail.com
 *
 */
public class Application {

	/** Application name. */
	public static final String APPLICATION_NAME = "Youtube Mail To Playlist";

	/** Main method */
	public static void main(final String[] args) throws IOException, MessagingException {

		// Utilisateur gmail : tiegezh musique
		final String userId = GmailIdEnum.GMAIL_TIEGEZH_MUSIQUE.getId();

		// Création des facades
		final GmailServiceFacade gmailServiceFacade = new GmailServiceFacade();
		final YoutubeServiceFacade youtubeServiceFacade = new YoutubeServiceFacade();

		// Récupération des messages
		final List<Message> messages = gmailServiceFacade.listMessages(userId);
		for (final Message message : messages) {
			// Récupération de l'id du message
			final String messageId = message.getId();

			// Récupération de l'id de la vidéo
			final List<Optional<String>> optVideoIds = gmailServiceFacade.getVideoIds(userId, messageId);

			for (final Optional<String> optVideoId : optVideoIds) {
				if (optVideoId.isPresent()) {
					// Ajout de la vidéo à la playlist
					youtubeServiceFacade.insertVideoIntoPlaylist(optVideoId.get(),
							YoutubePlaylistIdEnum.PLAYLIST_JULIEN.getId());
					// Marque le mail comme lu
					gmailServiceFacade.markAsRead(userId, messageId);
				}
			}

		}
	}

}
