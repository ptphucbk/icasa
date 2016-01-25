package org.tp.fmm.command;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.tp.fmm.EnergyGoal;
import org.tp.fmm.FollowMeAdministration;
import org.tp.fmm.IlluminanceGoal;

import fr.liglab.adele.icasa.command.handler.Command;
import fr.liglab.adele.icasa.command.handler.CommandProvider;
 
//Define this class as an implementation of a component :
@Component
//Create an instance of the component
@Instantiate(name = "fmm.command")
//Use the handler command and declare the command as a command provider. The
//namespace is used to prevent name collision.
@CommandProvider(namespace = "followme")
public class FollowMeCommandImpl {
 
    // Declare a dependency to a FollowMeAdministration service
    @Requires
    private FollowMeAdministration m_administrationService;
  
    /**
     * Felix shell command implementation to sets the illuminance preference.
     *
     * @param goal the new illuminance preference ("SOFT", "MEDIUM", "FULL")
     */
 
    // Each command should start with a @Command annotation
    @Command
    public void setIlluminancePreference(String person, String goal) {
        // The targeted goal
        IlluminanceGoal illuminanceGoal;
 
        // TODO : Here you have to convert the goal string into an illuminance
        // goal and fail if the entry is not "SOFT", "MEDIUM" or "HIGH"
        if (goal.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_SOFT)){
        	illuminanceGoal = IlluminanceGoal.SOFT;
        } else if (goal.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_MEDIUM)){
        	illuminanceGoal = IlluminanceGoal.MEDIUM;
        } else if (goal.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_FULL)){
        	illuminanceGoal = IlluminanceGoal.FULL;
        } else {
        	illuminanceGoal = null;
        }
        //call the administration service to configure it :
        m_administrationService.setIlluminancePreference(person, illuminanceGoal);
    }
 
    @Command
    public void getIlluminancePreference(){
        //TODO : implement the command that print the current value of the goal
    	IlluminanceGoal illuminanceGoal;
    	illuminanceGoal = m_administrationService.getIlluminancePreference();
    	if (illuminanceGoal.getNumberOfLightsToTurnOn() == 1){
    		System.out.println("The illuminance goal is SOFT " + illuminanceGoal.getIlluminanceValue());
    	} else if (illuminanceGoal.getNumberOfLightsToTurnOn() == 2){
    		System.out.println("The illuminance goal is MEDIUM " + illuminanceGoal.getIlluminanceValue());
    	} else if (illuminanceGoal.getNumberOfLightsToTurnOn() == 3){
    		System.out.println("The illuminance goal is FULL " + illuminanceGoal.getIlluminanceValue());
    	} else {
    		System.out.println("The illuminance goal is out of range [1 - 3]");
    	}
    }
    @Command
    public void setEnergySavingGoal(String goal) {
        // The targeted goal
        EnergyGoal energyGoal;
 
        // TODO : Here you have to convert the goal string into an illuminance
        // goal and fail if the entry is not "SOFT", "MEDIUM" or "HIGH"
        if (goal.equals("LOW")){
        	energyGoal = EnergyGoal.LOW;
        } else if (goal.equals("MEDIUM")){
        	energyGoal = EnergyGoal.MEDIUM;
        } else if (goal.equals("HIGH")){
        	energyGoal = EnergyGoal.HIGH;
        } else {
        	energyGoal = null;
        }
        //call the administration service to configure it :
        m_administrationService.setEnergySavingGoal(energyGoal);
    }
 
    @Command
    public void getEnergyGoal(){
        //TODO : implement the command that print the current value of the goal
    	EnergyGoal energyGoal;
    	energyGoal = m_administrationService.getEnergyGoal();
    	if (energyGoal.getMaximumEnergyInRoom() == 100){
    		System.out.println("The illuminance goal is LOW");
    	} else if (energyGoal.getMaximumEnergyInRoom() == 250){
    		System.out.println("The illuminance goal is MEDIUM");
    	} else if (energyGoal.getMaximumEnergyInRoom() == 1000){
    		System.out.println("The illuminance goal is HIGH");
    	} else {
    		System.out.println("The illuminance goal is out of range");
    	}
    }
 
}