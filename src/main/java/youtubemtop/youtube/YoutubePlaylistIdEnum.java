package youtubemtop.youtube;

/**
 * Enum of playlist ids
 * 
 * @author julien.orain@gmail.com
 *
 */
public enum YoutubePlaylistIdEnum {

	PLAYLIST_JULIEN("PLcxHc0l3AXLNEFHl4rZGtiG0yx5nYme58");

	/** Youtube playlist's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            youtube playlist's id
	 */
	private YoutubePlaylistIdEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get youtube playlist's id
	 *
	 * @return youtube playlist's id
	 */
	public String getId() {
		return id;
	}

}
