package ru.nsu.khamidullin.pizza;

import java.util.List;

public class PizzeriaConfiguration {
    private List<Integer> bakersCookingTime;
    private List<Integer> deliveriesCapacity;
    private int storageCapacity;


    public List<Integer> getBakersCookingTime() {
        return bakersCookingTime;
    }

    public void setBakersCookingTime(List<Integer> bakersCookingTime) {
        this.bakersCookingTime = bakersCookingTime;
    }

    public List<Integer> getDeliveriesCapacity() {
        return deliveriesCapacity;
    }

    public void setDeliveriesCapacity(List<Integer> deliveriesCapacity) {
        this.deliveriesCapacity = deliveriesCapacity;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }
}
