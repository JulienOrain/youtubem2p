package youtubemtop.job;

/**
 * Enum of trigger ids
 *
 * @author julien.orain@gmail.com
 *
 */
public enum TriggerEnum {

	YOUTUBE_M2P("trigger-youtubeM2P");

	/** trigger's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            trigger's id
	 */
	private TriggerEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get trigger's id
	 *
	 * @return trigger's id
	 */
	public String getId() {
		return id;
	}

}