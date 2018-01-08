package youtubemtop.gmail;

/**
 * Enum of gmail ids
 *
 * @author julien.orain@gmail.com
 *
 */
public enum GmailIdEnum {
	GMAIL_TIEGEZH_MUSIQUE("tiegezh.musique@gmail.com");

	/** Gmail account's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            gmail account's id
	 */
	private GmailIdEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get gmail account's id
	 *
	 * @return gmail account's id
	 */
	public String getId() {
		return id;
	}

}
