package org.tp.bfm.time;

import java.util.concurrent.TimeUnit;

import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;

public class MomentOfTheDayImpl implements MomentOfTheDayService, PeriodicRunnable {

	/**
	 * The current moment of the day :
	 **/
	MomentOfTheDay currentMomentOfTheDay;

	int hour = 0;
	// Implementation of the MomentOfTheDayService ....

	public MomentOfTheDay getMomentOfTheDay() {
		return currentMomentOfTheDay;
	}

	// Implementation ot the PeriodicRunnable ...
	// The service will be periodically called every hour.

	@Override
	public long getPeriod() {
		return 1;
	}

	@Override
	public TimeUnit getUnit() {
		return TimeUnit.HOURS;
	}

	@Override
	public void run() {
		// The method run is called on a regular basis

		// TODO : do something to check the current time of the day and see if
		// it has changed
		hour += 1;
		currentMomentOfTheDay = currentMomentOfTheDay.getCorrespondingMoment(hour);
	}

	@Override
	public void register(MomentOfTheDayListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(MomentOfTheDayListener listener) {
		// TODO Auto-generated method stub

	}

	/** Component Lifecycle Method */
	public void stop() {
		// TODO: Add your implementation code here
	}

	/** Component Lifecycle Method */
	public void start() {
		// TODO: Add your implementation code here
	}

}
