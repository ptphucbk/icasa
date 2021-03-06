package org.tp.bfm;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.light.DimmerLight;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tp.bfm.config.FollowMeConfig;

import fr.liglab.adele.icasa.device.light.Photometer;

public class BinaryFollowMe implements DeviceListener<GenericDevice>, FollowMeConfig {

	/** Field for presenceSensors dependency */
	private PresenceSensor[] presenceSensors;
	/** Field for binaryLights dependency */
	private BinaryLight[] binaryLights;
	/** Field for dimmerLights dependency */
	private DimmerLight[] dimmerLights;
	/** Field for photometers dependency */
	private Photometer[] photometers;
    
    /** Location constants */
	public static final String LOCATION_PROPERTY_NAME = "Location";
	public static final String LOCATION_UNKNOWN = "unknown";

	// The maximum number of lights to turn on when a user enters the room :
	private int maxLightsToTurnOnPerRoom = 2;
	// The targeted illuminance in each room
	private double targetedIlluminance = 8000d;

	// The energy consumed by a binary light in Watt
	private final double energyBinaryLight = 100.0d;

	// The maximum energy consumption allowed in a room in Watt:
	private double maximumEnergyConsumptionAllowedInARoom = 250.0d;

	// The maximum number of lights to turn on after considering the energy
	private int maxLightsOn = 0;

	/**
	 * Watt to lumens conversion factor It has been considered that: 1
	 * Watt=680.0 lumens at 555nm.
	 */
	public final static double ONE_WATT_TO_ONE_LUMEN = 680.0d;

	/** Bind Method for presenceSensors dependency */
	public synchronized void bindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("bind presence sensor " + presenceSensor.getSerialNumber());
		presenceSensor.addListener(this);
	}

	/** Unbind Method for presenceSensors dependency */
	public synchronized void unbindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("Unbind presence sensor " + presenceSensor.getSerialNumber());
		presenceSensor.removeListener(this);
	}

	/** Bind Method for binaryLights dependency */
	public synchronized void bindBinaryLight(BinaryLight binaryLight, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("bind binary light " + binaryLight.getSerialNumber());
		binaryLight.addListener(this);
	}

	/** Unbind Method for binaryLights dependency */
	public synchronized void unbindBinaryLight(BinaryLight binaryLight, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("unbind binary light " + binaryLight.getSerialNumber());
		binaryLight.removeListener(this);
	}

	/** Bind Method for dimmerLights dependency */
	public synchronized void bindDimmerLight(DimmerLight dimmerLight, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("bind dimmer light " + dimmerLight.getSerialNumber());
		dimmerLight.addListener(this);
	}

	/** Unbind Method for dimmerLights dependency */
	public synchronized void unbindDimmerLight(DimmerLight dimmerLight, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("unbind dimmer light " + dimmerLight.getSerialNumber());
		dimmerLight.removeListener(this);
	}

	/** Bind Method for photometer dependency */
	public void bindPhotometer(Photometer photometer, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("bind dimmer light " + photometer.getSerialNumber());
		photometer.addListener(this);
	}

	/** Unbind Method for photometer dependency */
	public void unbindPhotometer(Photometer photometer, Map properties) {
		// TODO: Add your implementation code here
		System.out.println("unbind dimmer light " + photometer.getSerialNumber());
		photometer.removeListener(this);
	}

	/** Component Lifecycle Method */
	public synchronized void stop() {
		// TODO: Add your implementation code here
		System.out.println("Component is stopping...");
		for (PresenceSensor sensor : presenceSensors) {
			sensor.removeListener(this);
		}
		for (BinaryLight binLight : binaryLights) {
			binLight.removeListener(this);
		}
	}

	/** Component Lifecycle Method */
	public void start() {
		// TODO: Add your implementation code here
		System.out.println("Component is starting...");
	}

	/**
	 * Methods get list of divices
	 */
	private synchronized List<PresenceSensor> getPresenceSensorFromLocation(String location) {
		List<PresenceSensor> presenceSensorsLocation = new ArrayList<PresenceSensor>();
		for (PresenceSensor presenceSensor : presenceSensors) {
			if (presenceSensor.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				presenceSensorsLocation.add(presenceSensor);
			}
		}
		return presenceSensorsLocation;
	}

	private synchronized List<BinaryLight> getBinaryLightFromLocation(String location) {
		List<BinaryLight> binaryLightsLocation = new ArrayList<BinaryLight>();
		for (BinaryLight binLight : binaryLights) {
			if (binLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				binaryLightsLocation.add(binLight);
			}
		}
		return binaryLightsLocation;
	}

	private synchronized List<DimmerLight> getDimmerLightFromLocation(String location) {
		List<DimmerLight> dimmerLightsLocation = new ArrayList<DimmerLight>();
		for (DimmerLight dimLight : dimmerLights) {
			if (dimLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				dimmerLightsLocation.add(dimLight);
			}
		}
		return dimmerLightsLocation;
	}

	private synchronized List<Photometer> getPhotometerFromLocation(String location) {
		List<Photometer> photometersLocation = new ArrayList<Photometer>();
		for (Photometer photometer : photometers) {
			if (photometer.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				photometersLocation.add(photometer);
			}
		}
		return photometersLocation;
	}

	/**
	 * Method turn lightOn number of binary lights on
	 */

	public int SwitchBinaryLights(List<BinaryLight> sameLocationLigths, int lightOn) {
		int light = 0;
		for (BinaryLight binaryLight : sameLocationLigths) {
			if (lightOn > 0) {
				if (light < lightOn) {
					binaryLight.turnOn();
					light += 1;
				}
			} else {
				binaryLight.turnOff();
			}
		}
		return light;
	}

	@Override
	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue, Object newValue) {

		System.out.println(propertyName);
		int lightOn = 0; // Number of lights are turned on in each room
		double energyConsumed = 0; // Energy actually consumed
		if (maxLightsToTurnOnPerRoom < (int) maximumEnergyConsumptionAllowedInARoom / energyBinaryLight) {
			maxLightsOn = maxLightsToTurnOnPerRoom;
		} else {
			maxLightsOn = (int) (maximumEnergyConsumptionAllowedInARoom / energyBinaryLight);
		}

		// If the modified device property is of PresenceSensor
		if (device instanceof PresenceSensor) {
			PresenceSensor changingSensor = (PresenceSensor) device;
			// check the change is related to presence sensing
			if (propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
				// get the location of the changing sensor: when a person moves,
				// there are two events occur
				// in two presence sensors
				String detectorLocation = (String) changingSensor.getPropertyValue(LOCATION_PROPERTY_NAME);
				if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
					// Get the binary lights in the location the event occurs
					List<BinaryLight> sameLocationLigths = getBinaryLightFromLocation(detectorLocation);
					// Get the dimmer lights in the location the event occurs
					List<DimmerLight> sameLocationDimmerLigths = getDimmerLightFromLocation(detectorLocation);
					// Get the photometers in the location the event occurs
					List<Photometer> sameLocationPhotometers = getPhotometerFromLocation(detectorLocation);
					if (changingSensor.getSensedPresence()) {
						double dimmerLightPower = 0.0;
						for (BinaryLight binaryLight : sameLocationLigths) {
							if (sameLocationPhotometers.get(0).getIlluminance() < targetedIlluminance
									&& lightOn < maxLightsOn) {
								binaryLight.turnOn();
								lightOn += 1;
							}
						}
						if (sameLocationPhotometers.get(0).getIlluminance() >= targetedIlluminance) {
							sameLocationLigths.get(0).turnOff();
							lightOn -= 1;
						}

						// Energy consumed by binary lights
						energyConsumed = lightOn * energyBinaryLight;
						// Power dimmer lights to reach the

						while (energyConsumed < maximumEnergyConsumptionAllowedInARoom
								&& sameLocationPhotometers.get(0).getIlluminance() < targetedIlluminance
								&& dimmerLightPower <= 0.99) {
							dimmerLightPower += 0.01;
							energyConsumed += 0.01 * energyBinaryLight;
							sameLocationDimmerLigths.get(0).setPowerLevel(dimmerLightPower);
						}
						System.out.println("Illuminance value " + sameLocationPhotometers.get(0).getIlluminance());
						System.out.println("Dimmer value " + sameLocationDimmerLigths.get(0).getPowerLevel());
						System.out.println("Energy consumed " + energyConsumed);
					} else {
						SwitchBinaryLights(sameLocationLigths, 0);
						for (DimmerLight dimmerLight : sameLocationDimmerLigths) {
							dimmerLight.setPowerLevel(0.0);
						}
					}
				}
			}
		}
		// If the modified device property is of PresenceSensor
		else if (device instanceof BinaryLight) {

			BinaryLight changingLight = (BinaryLight) device;

			// If the light is changed in location
			if (propertyName.equals("Location")) {
				// get the location of the changing sensor:
				String detectorLocation = (String) changingLight.getPropertyValue(LOCATION_PROPERTY_NAME);
				if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
					// get the related presence sensors
					List<PresenceSensor> sameLocationPresenceSensors = getPresenceSensorFromLocation(detectorLocation);
					for (PresenceSensor presenceSensor : sameLocationPresenceSensors) {
						// and switch them on/off depending on the sensed
						// presence
						List<BinaryLight> sameLocationLigths = getBinaryLightFromLocation(detectorLocation);
						List<DimmerLight> sameLocationDimmerLigths = getDimmerLightFromLocation(detectorLocation);
						List<Photometer> sameLocationPhotometers = getPhotometerFromLocation(detectorLocation);
						if (presenceSensor.getSensedPresence()) {
							// Count the binary lights are off
							for (BinaryLight binaryLight : sameLocationLigths) {
								if (binaryLight.getPowerStatus()) {
									lightOn += 1;
								}
							}
							double dimmerLightPower = sameLocationDimmerLigths.get(0).getPowerLevel();
							// Energy consumed by binary lights
							energyConsumed = lightOn * energyBinaryLight + dimmerLightPower * energyBinaryLight;

							if (energyConsumed >= maximumEnergyConsumptionAllowedInARoom || lightOn >= maxLightsOn
									|| sameLocationPhotometers.get(0).getIlluminance() >= targetedIlluminance) {
								changingLight.turnOff();
							} else {
								changingLight.turnOn();
								energyConsumed += energyBinaryLight;
								while (energyConsumed >= maximumEnergyConsumptionAllowedInARoom
										|| sameLocationPhotometers.get(0).getIlluminance() >= targetedIlluminance
										|| dimmerLightPower > 0.01) {
									dimmerLightPower -= 0.01;
									sameLocationDimmerLigths.get(0).setPowerLevel(dimmerLightPower);
									energyConsumed -= 0.01 * energyBinaryLight;
								}
							}

							System.out.println("New location of binary light");
							System.out.println("Illuminance value " + sameLocationPhotometers.get(0).getIlluminance());
							System.out.println("Dimmer value " + sameLocationDimmerLigths.get(0).getPowerLevel());
							System.out.println("Energy consumed " + energyConsumed);

						} else {
							changingLight.turnOff();
						}
					}
				}
				String oldLocation = oldValue.toString();
				// System.out.println(oldLocation);
				if (!oldLocation.equals(LOCATION_UNKNOWN)) {
					int lightOff = 0;
					// get the related presence sensors
					List<PresenceSensor> oldLocationPresenceSensors = getPresenceSensorFromLocation(oldLocation);
					for (PresenceSensor presenceSensor : oldLocationPresenceSensors) {
						List<BinaryLight> oldLocationBinaryLigths = getBinaryLightFromLocation(oldLocation);
						List<DimmerLight> oldLocationDimmerLigths = getDimmerLightFromLocation(oldLocation);
						List<Photometer> oldLocationPhotometers = getPhotometerFromLocation(oldLocation);
						if (presenceSensor.getSensedPresence()) {
							// Count the binary lights are off
							for (BinaryLight binaryLight : oldLocationBinaryLigths) {
								if (binaryLight.getPowerStatus()) {
									lightOn += 1;
								} else {
									lightOff += 1;
								}
							}
							double dimmerLightPower = oldLocationDimmerLigths.get(0).getPowerLevel();
							// Energy consumed by binary lights
							energyConsumed = lightOn * energyBinaryLight + dimmerLightPower * energyBinaryLight;

							if (lightOff > 0) {
								if (energyConsumed < maximumEnergyConsumptionAllowedInARoom && lightOn < maxLightsOn
										&& oldLocationPhotometers.get(0).getIlluminance() < targetedIlluminance) {
									for (BinaryLight binaryLight : oldLocationBinaryLigths) {
										if (!binaryLight.getPowerStatus()) {
											binaryLight.turnOn();
											lightOn += 1;
											break;
										}
									}
								}
							} else {
								while (energyConsumed < maximumEnergyConsumptionAllowedInARoom
										&& oldLocationPhotometers.get(0).getIlluminance() < targetedIlluminance
										&& dimmerLightPower < 0.99) {
									dimmerLightPower += 0.01;
									oldLocationDimmerLigths.get(0).setPowerLevel(dimmerLightPower);
									energyConsumed += 0.01 * energyBinaryLight;
								}
							}

							System.out.println("Old location of binary light");
							System.out.println("Illuminance value " + oldLocationPhotometers.get(0).getIlluminance());
							System.out.println("Dimmer value " + oldLocationDimmerLigths.get(0).getPowerLevel());
							System.out.println("Energy consumed " + energyConsumed);
						}
					}
				}
			}
		} else if (device instanceof DimmerLight) {

		}

	}

	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaximumNumberOfLightsToTurnOn() {
		// TODO Auto-generated method stub
		// System.out.println("Get maximum number of lights to turn on");
		return maxLightsToTurnOnPerRoom;
	}

	@Override
	public void setMaximumNumberOfLightsToTurnOn(int maximumNumberOfLightsToTurnOn) {
		// TODO Auto-generated method stub
		System.out.println("Set maximum number of lights to turn on to " + maximumNumberOfLightsToTurnOn);
		maxLightsToTurnOnPerRoom = maximumNumberOfLightsToTurnOn;
	}

	@Override
	public double getMaximumAllowedEnergyInRoom() {
		// TODO Auto-generated method stub
		return maximumEnergyConsumptionAllowedInARoom;
	}

	@Override
	public void setMaximumAllowedEnergyInRoom(double maximumEnergy) {
		// TODO Auto-generated method stub
		System.out.println("Set maximum energy " + maximumEnergy);
		maximumEnergyConsumptionAllowedInARoom = maximumEnergy;
	}

	@Override
	public double getTargetedIlluminance() {
		// TODO Auto-generated method stub
		return targetedIlluminance;
	}

	@Override
	public void setTargetedIlluminance(double illuminance) {
		// TODO Auto-generated method stub
		targetedIlluminance = illuminance;
	}
}
