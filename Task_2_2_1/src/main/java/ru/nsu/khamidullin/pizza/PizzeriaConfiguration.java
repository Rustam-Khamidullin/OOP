package ru.nsu.khamidullin.pizza;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The {@code PizzeriaConfiguration} class represents the configuration parameters
 * for a simulated pizzeria.
 * It includes information such as bakers' cooking times, delivery capacities,
 * and storage capacity.
 */
public class PizzeriaConfiguration {
    private static final String PIZZERIA_CONFIGURATION = "pizzeria.json";
    private List<Integer> bakersCookingTime;
    private List<Integer> deliveriesCapacity;
    private int storageCapacity;

    public PizzeriaConfiguration() throws IOException, IllegalAccessException {
        setPizzeriaConfiguration();
    }

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

    /**
     * Reads and sets the pizzeria configuration from a configuration file.
     *
     * @throws IOException            If an I/O error occurs during the configuration
     *                                loading process.
     * @throws IllegalAccessException If the loaded configuration is invalid or incomplete.
     */
    private void setPizzeriaConfiguration() throws IOException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();

        PizzeriaConfiguration pizzeriaConfiguration;
        try (InputStream fileInputStream =
                     ClassLoader.getSystemResourceAsStream(PIZZERIA_CONFIGURATION)) {
            pizzeriaConfiguration =
                    objectMapper.readValue(fileInputStream, PizzeriaConfiguration.class);
        }

        List<Integer> bakersCookingTime = pizzeriaConfiguration.getBakersCookingTime();
        List<Integer> deliveriesCapacity = pizzeriaConfiguration.getDeliveriesCapacity();
        int storageCapacity = pizzeriaConfiguration.getStorageCapacity();

        if (deliveriesCapacity.isEmpty()
                || bakersCookingTime.isEmpty()
                || storageCapacity <= 0) {
            throw new IllegalAccessException();
        }
        for (var i : bakersCookingTime) {
            if (i <= 0) {
                throw new IllegalAccessException();
            }
        }
        for (var i : deliveriesCapacity) {
            if (i <= 0) {
                throw new IllegalAccessException();
            }
        }

        this.bakersCookingTime = bakersCookingTime;
        this.deliveriesCapacity = deliveriesCapacity;
        this.storageCapacity = storageCapacity;
    }
}
