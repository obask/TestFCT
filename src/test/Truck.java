package test;

import java.util.ArrayList;

public class Truck {
    String name; //название грузовика
    int capacity = 0; //вместимость грузовика
    private ArrayList<Product> truckProductsSequence = new ArrayList<>(); //грузы, перевезенные грузовиком

    public Truck(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    //погрузка продукта в грузовик
    public void loadProduct(Product product){
        truckProductsSequence.add(product);
    }

    public ArrayList<Product> getTruckProductsSequence() {
        return truckProductsSequence;
    }

    //подсчет перевезенных продуктов
    public void getProductsCount() {
        ArrayList<Product> uniqueProducts = new ArrayList<>();
        int[] productsCount = new int[truckProductsSequence.size()];
        for(Product product : truckProductsSequence) {
            if(uniqueProducts.contains(product)){
                productsCount[uniqueProducts.indexOf(product)]++;
            }
            else{
                uniqueProducts.add(product);
                productsCount[uniqueProducts.indexOf(product)]++;
            }
        }
        System.out.println(name + " перевез:");
        for(int i = 0; i < uniqueProducts.size(); i++){
            System.out.println(uniqueProducts.get(i).productName + " " + productsCount[i]);
        }
    }
}
