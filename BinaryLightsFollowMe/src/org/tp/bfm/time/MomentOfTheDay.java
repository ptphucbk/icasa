package org.tp.bfm.time;

public enum MomentOfTheDay {
    MORNING(6), AFTERNOON(12), EVENING(18), NIGHT(24);
 
    /**
     * Gets the moment of the day corresponding to the hour.
     * 
     * @param hour
     *            the given hour
     * @return the corresponding moment of the day
     */
    MomentOfTheDay getCorrespondingMoment(int hour) {
        assert ((0 <= startHour) && (startHour <= 24));
        // TODO : if (hour < //..
        MomentOfTheDay momentOfTheDay;
        if (hour >=0 && hour < 6){
        	momentOfTheDay = MomentOfTheDay.NIGHT;
        } else if (hour >= 6 && hour < 12){
        	momentOfTheDay = MomentOfTheDay.MORNING;
        } else if (hour >= 12 && hour < 18){
        	momentOfTheDay = MomentOfTheDay.AFTERNOON;
        } else {
        	momentOfTheDay = MomentOfTheDay.EVENING;
        }
        return momentOfTheDay;
    }
 
    /**
     * The hour when the moment start.
     */
    private final int startHour;
 
    /**
     * Build a new moment of the day :
     * 
     * @param startHour
     *            when the moment start.
     */
    MomentOfTheDay(int startHour) {
        assert ((0 <= startHour) && (startHour <= 24));
        this.startHour = startHour;
    }
}
