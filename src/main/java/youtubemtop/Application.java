package youtubemtop;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import youtubemtop.job.GroupEnum;
import youtubemtop.job.JobEnum;
import youtubemtop.job.JobM2P;
import youtubemtop.job.TriggerEnum;

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

		final JobDetail job = newJob(JobM2P.class)
				.withIdentity(JobEnum.YOUTUBE_M2P.getId(), GroupEnum.YOUTUBE_M2P.getId()).build();

		// Job #1 is scheduled to run every 20 seconds
		final CronTrigger trigger = newTrigger()
				.withIdentity(TriggerEnum.YOUTUBE_M2P.getId(), GroupEnum.YOUTUBE_M2P.getId())
				.withSchedule(cronSchedule("0/20 * * * * ?")).build();

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
		sched.start();
	}

}
