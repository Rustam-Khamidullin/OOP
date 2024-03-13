package ru.nsu.khamidullin.pizza;

import java.util.List;

/**
 * The {@code PizzeriaConfiguration} class represents the configuration parameters for a simulated pizzeria.
 * It includes information such as bakers' cooking times, delivery capacities, and storage capacity.
 */
public class PizzeriaConfiguration {
    private List<Integer> bakersCookingTime;
    private List<Integer> deliveriesCapacity;
    private int storageCapacity;

    /**
     * Gets the list of cooking times for the bakers.
     *
     * @return The list of cooking times for the bakers.
     */
    public List<Integer> getBakersCookingTime() {
        return bakersCookingTime;
    }

    /**
     * Sets the list of cooking times for the bakers.
     *
     * @param bakersCookingTime The list of cooking times to set for the bakers.
     */
    public void setBakersCookingTime(List<Integer> bakersCookingTime) {
        this.bakersCookingTime = bakersCookingTime;
    }

    /**
     * Gets the list of delivery capacities for the deliverymen.
     *
     * @return The list of delivery capacities for the deliverymen.
     */
    public List<Integer> getDeliveriesCapacity() {
        return deliveriesCapacity;
    }

    /**
     * Sets the list of delivery capacities for the deliverymen.
     *
     * @param deliveriesCapacity The list of delivery capacities to set for the deliverymen.
     */
    public void setDeliveriesCapacity(List<Integer> deliveriesCapacity) {
        this.deliveriesCapacity = deliveriesCapacity;
    }

    /**
     * Gets the storage capacity for the pizzeria.
     *
     * @return The storage capacity for the pizzeria.
     */
    public int getStorageCapacity() {
        return storageCapacity;
    }

    /**
     * Sets the storage capacity for the pizzeria.
     *
     * @param storageCapacity The storage capacity to set for the pizzeria.
     */
    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }
}
