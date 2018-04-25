package test;

public class Facility extends Thread{
    private String facilityName;
    private Product product;
    private double performance;

    public Facility(String facilityName, Product product, double performance) {
        this.facilityName = facilityName;
        this.product = product;
        this.performance = performance;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public Product getProduct() {
        return product;
    }

    public double getPerformance() {
        return performance;
    }
}
