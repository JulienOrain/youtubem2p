package youtubem2p.gmail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;

import youtubem2p.gmail.util.MailParser;

/**
 * Facade for Gmail
 *
 * @author julien.orain@gmail.com
 *
 */
public class GmailServiceFacade {

	/** Search Query for emails */
	private static final String QUERY = "ziq is:unread";

	/** Label unread */
	private static final String UNREAD = "UNREAD";

	/** Service Gmail */
	private static Gmail gmail;

	/** Mail Reader */
	private static MailParser mailReader;

	/**
	 * Constructor
	 *
	 * @throws IOException
	 *             IOException
	 */
	public GmailServiceFacade() throws IOException {
		super();
		gmail = GmailServiceFactory.getGmailService();
		mailReader = new MailParser();
	}

	/**
	 * List user's messages according to {@link GmailServiceFacade#QUERY} query
	 *
	 * @param userId
	 *            user's id
	 * @return user's messages
	 * @throws IOException
	 *             IOException
	 */
	public List<Message> listMessages(final String userId) throws IOException {
		ListMessagesResponse response = gmail.users().messages().list(userId).setQ(QUERY).execute();

		final List<Message> messages = new ArrayList<>();
		while (response.getMessages() != null) {
			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null) {
				final String pageToken = response.getNextPageToken();
				response = gmail.users().messages().list(userId).setQ(QUERY).setPageToken(pageToken).execute();
			} else {
				break;
			}
		}

		return messages;
	}

	/**
	 * Get a Message and use it to create a MimeMessage.
	 *
	 * @param userId
	 *            user's id
	 * @param messageId
	 *            message's id
	 * @return MimeMessage MimeMessage populated from retrieved Message
	 * @throws IOException
	 *             IOException
	 * @throws MessagingException
	 *             MessagingException
	 */
	@SuppressWarnings("static-access")
	public MimeMessage getMimeMessage(final String userId, final String messageId)
			throws IOException, MessagingException {
		final Message message = gmail.users().messages().get(userId, messageId).setFormat("raw").execute();

		final Base64 base64Url = new Base64(true);
		final byte[] emailBytes = base64Url.decodeBase64(message.getRaw());

		final Properties props = new Properties();
		final Session session = Session.getDefaultInstance(props, null);

		final MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));

		return email;
	}

	/**
	 * Mark a Message as read by removing the unread label
	 *
	 * @param userId
	 *            user's id
	 * @param messageId
	 *            message's id
	 * @throws IOException
	 *             IOException
	 */

	public void markAsRead(final String userId, final String messageId) throws IOException {
		final List<String> removeLabelIds = new ArrayList<>();
		removeLabelIds.add(UNREAD);
		final ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(removeLabelIds);
		gmail.users().messages().modify(userId, messageId, mods).execute();
	}

	/**
	 * Get video's ids from a user's id and message's id
	 *
	 * @param userId
	 *            user's id
	 * @param messageId
	 *            message's id
	 * @return video'ids
	 * @throws MessagingException
	 *             MessagingException
	 * @throws IOException
	 *             IOException
	 */
	public List<Optional<String>> getVideoIds(final String userId, final String messageId)
			throws MessagingException, IOException {
		return mailReader.getVideoIds(getMimeMessage(userId, messageId));
	}

}
