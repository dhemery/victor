package com.dhemery.poller;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Repeatedly execute a command until either a condition is satisfied or a duration passes.</p>
 * 
 * <p>
 * Through constructors and other methods, you can specify:
 * </p>
 * <ul>
 * <li>A <strong>command</strong> to execute at the start of each polling cycle.</li>
 * <li>A <strong>condition</strong> that, when satisfied, will terminate the poll.</li>
 * <li>A <strong>duration</strong>, after which the poll will expire (and throw an exception) even if the condition is not satisfied.</li>
 * <li>A <strong>polling interval</strong> that indicates how long to pause between polling cycles.</li>
 * </ul>
 * 
 * <p>
 * Each polling cycle proceeds as follows:
 * </p>
 * <ol>
 * <li>If the duration has passed, terminate the poll (even if the command has not yet executed).</li>
 * <li>Execute the command.</li>
 * <li>Test whether the condition is satisfied. If it has, terminate the poll.</li>
 * <li>Pause for the polling interval.</li>
 * <li>Return to step 1.</li>
 * </ol>
 * 
 * @author Dale Emery
 *
 */
public class Poll {
	private static final int DEFAULT_POLLING_INTERVAL = 1000;
	private static final Command DEFAULT_COMMAND = new Waiting();
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Clock clock;
	private final Command command;
	private final long durationInMilliseconds;
	private final long pollingIntervalInMilliseconds;

	/**
	 * <p>Prepares to execute a command repeatedly.</p>
	 * 
	 * <p>
	 * You normally won't call this constructor.
	 * It is useful for supplying your own clock in place of the system clock, for testing purposes.
	 * </p>
	 * 
	 * @param clock the clock to used to determine the current time and to sleep between polls.
	 * @param command the command to execute repeatedly.
	 * @param durationInMilliseconds the poll duration, after which the poll will expire.
	 * @param pollingIntervalInMilliseconds how long to pause between polls.
	 */
	public Poll(Clock clock, Command command, long durationInMilliseconds, long pollingIntervalInMilliseconds) {
		this.clock = clock;
		this.command = command;
		this.durationInMilliseconds = durationInMilliseconds;
		this.pollingIntervalInMilliseconds = pollingIntervalInMilliseconds;
	}

	/**
	 * Prepares to execute a command repeatedly, using the system clock.
	 * 
	 * @param command the command to execute repeatedly.
	 * @param durationInMilliseconds the poll duration, after which the poll will expire.
	 * @param pollingIntervalInMilliseconds how long to pause between polls.
	 */
	public Poll(Command command, long durationInMilliseconds, long pollingIntervalInMilliseconds) {
		this(new SystemClock(), command, durationInMilliseconds, pollingIntervalInMilliseconds);
	}

	/**
	 * Prepares to execute a command repeatedly, using the system clock and the default polling interval.
	 * 
	 * @param command the command to execute repeatedly.
	 * @param durationInMilliseconds the poll duration, after which the poll will expire.
	 */
	public Poll(Command command, long durationInMilliseconds) {
		this(command, durationInMilliseconds, DEFAULT_POLLING_INTERVAL);
	}

	/**
	 * Prepares to poll a condition, using the system clock, and executing a no-op command on each poll.
	 * 
	 * @param durationInMilliseconds the poll duration, after which the poll will expire.
	 * @param pollingIntervalInMilliseconds how long to pause between polls.
	 */
	public Poll(long durationInMilliseconds, long pollingIntervalInMilliseconds) {
		this(DEFAULT_COMMAND, durationInMilliseconds, pollingIntervalInMilliseconds);
	}

	/**
	 * Prepares to poll a condition,
	 * using the system clock and the default polling interval,
	 * and taking no action on each poll.
	 * 
	 * @param durationInMilliseconds the poll duration, after which the poll will expire.
	 */
	public Poll(long durationInMilliseconds) {
		this(DEFAULT_COMMAND, durationInMilliseconds, DEFAULT_POLLING_INTERVAL);
	}
	
	/**
	 * Establishes a polling interval. Note that this method creates and returns a new poll.
	 * 
	 * @param pollingIntervalInMilliseconds how long to pause between polls.
	 * @return a new poll like the current one, but with the given polling interval.
	 */
	public Poll every(long pollingIntervalInMilliseconds) {
		return new Poll(clock, command, durationInMilliseconds, pollingIntervalInMilliseconds);
	}

	/**
	 * <p>Execute the poll's command until either the condition is satisfied or the poll duration expires.</p>
	 * 
	 * <p>
	 * Note that the command is executed before the condition is satisfied.
	 * Thus the command will execute at least once.
	 * </p>
	 * 
	 * @param condition a condition that, when satisfied, will terminate the poll.
	 * @throws PollTimeoutException if the duration expires before the condition is satisfied.
	 */
	public void until(Condition condition) throws PollTimeoutException {
		long end = clock.now() + durationInMilliseconds;
		int attemptNumber = 1;
		while (clock.now() < end) {
			log.debug(format("%s (%s) until %s", command.describe(), attemptNumber++, condition.describe()));
			command.execute();
			if (condition.isSatisfied()) {
				log.debug(format("Satisfied %s", condition.describe()));
				return;
			}
			clock.sleep(pollingIntervalInMilliseconds);
		}
		String message = String.format("Timed out %s until %s", command.describe(), condition.describe());
		throw new PollTimeoutException(message);
	}

	/**
	 * Execute the poll's command until the poll duration expires.
	 * Note that this method does not throw a {@link PollTimeoutException} when the duration expires.
	 */
	public void untilTimerExpires() {
		try {
			until(new PollTimerExpires(durationInMilliseconds));
		} catch (PollTimeoutException timeout) {
		}
	}
}
