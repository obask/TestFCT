package test;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Storage implements Runnable{

    private BlockingQueue<Facility> queue;
    private double capacity;  //вместимость склада
    private double currentCount; //количество единиц продукции на складе
    private ArrayList<Product> productsSequence = new ArrayList<>(); //список продуктов на складе
    private ArrayList<Truck> truckList = new ArrayList<>(); //список грузовиков

    public Storage(BlockingQueue<Facility> queue, double capacity, int truckCount) {
        this.queue = queue;
        this.capacity = capacity;
        this.currentCount = 0;
        for(int i = 0; i < truckCount; i++){
            truckList.add(new Truck("Грузовик"+(i+1),(i+1)));
        }
    }

    public ArrayList<Truck> getTruckList() {
        return truckList;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Facility tempFacility = queue.take();
                if (currentCount + tempFacility.getPerformance() < capacity) {
                    Thread.sleep(10);
                    currentCount += tempFacility.getPerformance();
                    for (int i = 0; i < tempFacility.getPerformance(); i++) {
                        productsSequence.add(tempFacility.getProduct());
                    }
                    System.out.println("На склад поступило " + tempFacility.getPerformance() + " единиц " + tempFacility.getProduct().productName + " (всего " + currentCount + ")");
                } else {
                    while (currentCount + tempFacility.getPerformance() > capacity) {
                        for (int i = 0; i < truckList.size(); i++) {
                            if (currentCount + tempFacility.getPerformance() > capacity) {
                                for (int j = 0; j < truckList.get(i).capacity; j++) {
                                    currentCount--;
                                    truckList.get(i).loadProduct(productsSequence.get(productsSequence.size() - 1));
                                    //System.out.println(truckList.get(i).name + " отгружает " + productsSequence.get(productsSequence.size() - 1).productName+" (на складе осталось " + currentCount+")");
                                    productsSequence.remove(productsSequence.size() - 1);
                                }
                            } else break;
                        }
                    }
                    currentCount += tempFacility.getPerformance();
                    for (int i = 0; i < tempFacility.getPerformance(); i++) {
                        productsSequence.add(tempFacility.getProduct());
                    }
                    System.out.println("На склад поступило " + tempFacility.getPerformance() + " единиц " + tempFacility.getProduct().productName + " (всего " + currentCount + ")");
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
