package youtubemtop.job;

/**
 * Enum of job ids
 *
 * @author julien.orain@gmail.com
 *
 */
public enum JobEnum {

	YOUTUBE_M2P("job-youtubeM2P");

	/** Job's id */
	private String id;

	/**
	 * Constructor
	 *
	 * @param id
	 *            job's id
	 */
	private JobEnum(final String id) {
		this.id = id;
	}

	/**
	 * Get job's id
	 *
	 * @return job's id
	 */
	public String getId() {
		return id;
	}

}
