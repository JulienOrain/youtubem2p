package youtubem2p;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import youtubem2p.job.JobM2P;

/**
 * Main Application
 *
 * @author julien.orain@gmail.com
 *
 */
public class Application {

	/** Application name. */
	public static final String APPLICATION_NAME = "Youtube Mail To Playlist";

	/**
	 * Main method
	 */
	public static void main(final String[] args) throws SchedulerException {

		final SchedulerFactory sf = new StdSchedulerFactory();
		final Scheduler sched = sf.getScheduler();

		final JobDetail job = newJob(JobM2P.class).withIdentity("job-youtubeM2P", "group-youtubeM2P").build();

		// Job #1 is scheduled to run every 20 seconds
		final CronTrigger trigger = newTrigger().withIdentity("trigger-youtubeM2P", "group-youtubeM2P")
				.withSchedule(cronSchedule("0/20 * * * * ?")).build();

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
		sched.start();
	}

}
