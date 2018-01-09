package youtubemtop.gmail;

/**
 * Enum of gmail labels
 *
 * @author julien.orain@gmail.com
 *
 */
public enum GmailLabelEnum {
	UNREAD("UNREAD");

	/** Gmail's label id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            gmail label's id
	 */
	private GmailLabelEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get gmail label's id
	 *
	 * @return gmail label's id
	 */
	public String getId() {
		return id;
	}

}
