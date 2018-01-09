package youtubemtop.checker;

/**
 * Enum of parameter ids
 *
 * @author julien.orain@gmail.com
 *
 */
public enum ParameterIdEnum {
	USER_ID("userId"), MESSAGE_ID("messageId"), VIDEO_ID("videoId"), PLAYLIST_ID("playlistId");

	/** Parameter's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            parameter's id
	 */
	private ParameterIdEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get parameter's id
	 *
	 * @return parameter's id
	 */
	public String getId() {
		return id;
	}

}
