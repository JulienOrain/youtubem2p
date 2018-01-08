package youtubemtop.gmail.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MimeMessage parser util class
 *
 * @author julien.orain@gmail.com
 *
 */
public class MailParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailParser.class);

	/**
	 * Constructor
	 */
	public MailParser() {
		super();
	}

	/**
	 * Get youtube video's ids by parsing MimeMessage's content
	 *
	 * @param mimeMessage
	 *            MimeMessage
	 * @return youtube video's ids
	 * @throws MessagingException
	 *             MessagingException
	 * @throws IOException
	 *             IOException
	 */
	public List<Optional<String>> getVideoIds(final MimeMessage mimeMessage) throws MessagingException, IOException {
		final String content = getTextFromMessage(mimeMessage);
		return getVideoIds(content);

	}

	/**
	 * Get youtube video's ids by parsing String content
	 *
	 * @param content
	 *            string content
	 * @return youtube video's ids
	 */
	private List<Optional<String>> getVideoIds(final String content) {
		final List<Optional<String>> list = new ArrayList<>();
		final List<String> links = pullLinks(content);
		for (final String link : links) {
			list.add(getVideoId(link));
		}
		return list;
	}

	/**
	 * Get youtube video's id by parsing link
	 *
	 * @param link
	 *            link
	 * @return youtube video's id
	 */
	Optional<String> getVideoId(final String link) {
		String res = null;
		final String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?$]*";
		final Pattern compiledPattern = Pattern.compile(pattern);
		final Matcher matcher = compiledPattern.matcher(link);
		if (matcher.find()) {
			res = matcher.group();
		}
		return Optional.ofNullable(res);
	}

	/**
	 * Pull links from text
	 *
	 * @param text
	 *            text
	 * @return list of links
	 */
	private List<String> pullLinks(final String text) {
		final List<String> urls = new ArrayList<>();
		final String[] parts = text.split("\\s+");
		for (final String item : parts) {
			URL url;
			try {
				url = new URL(item);
				urls.add(url.toString());
			} catch (final MalformedURLException e) {
				LOGGER.debug("Bad format " + item);
			}

		}
		return urls;
	}

	/**
	 * Get content from MimeMessage
	 *
	 * @param mimeMessage
	 *            MimeMessage
	 * @return mimeMessage's content
	 * @throws MessagingException
	 *             MessagingException
	 * @throws IOException
	 *             IOException
	 */
	String getTextFromMessage(final MimeMessage mimeMessage) throws MessagingException, IOException {
		String result = "";
		if (mimeMessage.isMimeType("text/plain")) {
			result = mimeMessage.getContent().toString();
		} else if (mimeMessage.isMimeType("multipart/*")) {
			final MimeMultipart mimeMultipart = (MimeMultipart) mimeMessage.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	/**
	 * Get content from MimeMultipart
	 *
	 * @param mimeMultipart
	 *            MimeMultipart
	 * @return mimeMultipart's content
	 * @throws MessagingException
	 *             MessagingException
	 * @throws IOException
	 *             IOException
	 */
	private String getTextFromMimeMultipart(final MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		final int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				final String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}
