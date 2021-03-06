package org.tp.fmm;

/**
 * The Interface FollowMeAdministration allows the administrator to configure
 * its preference regarding the management of the Follow Me application.
 */
public interface FollowMeAdministration {
 

    /** User preferences for illuminance */
    public static final String USER_PROP_ILLUMINANCE = "illuminance";
    public static final String USER_PROP_ILLUMINANCE_VALUE_SOFT = "SOFT";
    public static final String USER_PROP_ILLUMINANCE_VALUE_MEDIUM = "MEDIUM";
    public static final String USER_PROP_ILLUMINANCE_VALUE_FULL = "FULL";
    
    /**
     * Sets the illuminance preference. The manager will try to adjust the
     * illuminance in accordance with this goal.
     * 
     * @param illuminanceGoal
     *            the new illuminance preference
     */
    public void setIlluminancePreference(String person, String goal);
 
    /**
     * Get the current illuminance preference.
     * 
     * @return the new illuminance preference
     */
    public void getIlluminancePreference();
    /**
     * Configure the energy saving goal.
     * @param energyGoal : the targeted energy goal.
     */
    public void setEnergySavingGoal(EnergyGoal energyGoal);
 
    /**
     * Gets the current energy goal.
     * 
     * @return the current energy goal.
     */
    public EnergyGoal getEnergyGoal();

	void getMoment();
 
}
