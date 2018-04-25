package test;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Facility> queue;
    private Facility facility;

    public Producer(BlockingQueue<Facility> queue, Facility facility) {
        this.queue = queue;
        this.facility = facility;
    }

    public Facility getFacility() {
        return facility;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(i);
                queue.put(facility);
                System.out.println(facility.getFacilityName() + " произвел " + facility.getPerformance() + " единиц " +facility.getProduct().productName);
                Thread.sleep(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }
}
