package org.tp.fmm.impl;

import org.tp.fmm.EnergyGoal;
import org.tp.fmm.FollowMeAdministration;
import org.tp.fmm.IlluminanceGoal;
import org.tp.bfm.config.FollowMeConfig;

import fr.liglab.adele.icasa.service.location.PersonLocationService;
import fr.liglab.adele.icasa.service.preferences.Preferences;

public class FollowMeManagerImpl implements FollowMeAdministration {

	/** Field for configService dependency */
	private FollowMeConfig configService;
	/** Preferences services */
	private Preferences preferencesService;
	/** Location services */
    private PersonLocationService personLocationService; //...
	
    @Override
    public void setUserPreferences(){
    	preferencesService.setUserPropertyValue("Alice", USER_PROP_ILLUMINANCE, USER_PROP_ILLUMINANCE_VALUE_SOFT);
    	preferencesService.setUserPropertyValue("Paul", USER_PROP_ILLUMINANCE, USER_PROP_ILLUMINANCE_VALUE_MEDIUM);
    	preferencesService.setUserPropertyValue("Mary", USER_PROP_ILLUMINANCE, USER_PROP_ILLUMINANCE_VALUE_FULL);
    }
    
	@Override
	public void setIlluminancePreference(String person, IlluminanceGoal illuminanceGoal) {
		// TODO Auto-generated method stub
		configService.setMaximumNumberOfLightsToTurnOn(illuminanceGoal.getNumberOfLightsToTurnOn());
		configService.setTargetedIlluminance(illuminanceGoal.getIlluminanceValue());
		// System.out.println("setIlluminancePreference");
	}

	@Override
	public IlluminanceGoal getIlluminancePreference() {
		IlluminanceGoal illuminanceGoal;
		if (configService.getMaximumNumberOfLightsToTurnOn() == 1) {
			illuminanceGoal = IlluminanceGoal.SOFT;
		} else if (configService.getMaximumNumberOfLightsToTurnOn() == 2) {
			illuminanceGoal = IlluminanceGoal.MEDIUM;
		} else if (configService.getMaximumNumberOfLightsToTurnOn() == 3) {
			illuminanceGoal = IlluminanceGoal.FULL;
		} else {
			illuminanceGoal = null;
		}
		// System.out.println("getIlluminancePreference");
		return illuminanceGoal;
	}

	/** Component Lifecycle Method */
	public void stop() {
		// TODO: Add your implementation code here
	}

	/** Component Lifecycle Method */
	public void start() {
		// TODO: Add your implementation code here
	}

	@Override
	public void setEnergySavingGoal(EnergyGoal energyGoal) {
		// TODO Auto-generated method stub
		configService.setMaximumAllowedEnergyInRoom(energyGoal.getMaximumEnergyInRoom());
	}

	@Override
	public EnergyGoal getEnergyGoal() {
		// TODO Auto-generated method stub
		EnergyGoal energyGoal;
		if (configService.getMaximumAllowedEnergyInRoom() == 100d) {
			energyGoal = EnergyGoal.LOW;
		} else if (configService.getMaximumAllowedEnergyInRoom() == 250d) {
			energyGoal = EnergyGoal.MEDIUM;
		} else if (configService.getMaximumAllowedEnergyInRoom() == 1000d) {
			energyGoal = EnergyGoal.HIGH;
		} else {
			energyGoal = null;
		}
		// System.out.println("getIlluminancePreference");
		return energyGoal;
	}

}
