package youtubemtop.job;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.gmail.model.Message;

import youtubemtop.gmail.GmailIdEnum;
import youtubemtop.gmail.GmailServiceFacade;
import youtubemtop.youtube.YoutubePlaylistIdEnum;
import youtubemtop.youtube.YoutubeServiceFacade;

public class JobM2P implements org.quartz.Job {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(JobM2P.class);

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		try {
			// Utilisateur gmail : tiegezh musique
			final String userId = GmailIdEnum.GMAIL_TIEGEZH_MUSIQUE.getId();

			// Création des facades
			final GmailServiceFacade gmailServiceFacade = GmailServiceFacade.getInstance();
			final YoutubeServiceFacade youtubeServiceFacade = YoutubeServiceFacade.getInstance();

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
		} catch (final MessagingException | IOException e) {
			LOGGER.error("Error", e);
			System.exit(1);
		}
	}

}
