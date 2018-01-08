package youtubemtop.job;

/**
 * Enum of group ids
 *
 * @author julien.orain@gmail.com
 *
 */
public enum GroupEnum {

	YOUTUBE_M2P("group-youtubeM2P");

	/** group's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            group's id
	 */
	private GroupEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get group's id
	 *
	 * @return group's id
	 */
	public String getId() {
		return id;
	}

}