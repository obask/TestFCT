package test;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<Facility> queue = new ArrayBlockingQueue<>(1);
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> productsList = new ArrayList<>(); //Список продуктов
        ArrayList<Producer> facilitiesList = new ArrayList<>(); //Список заводов
        ArrayList<Thread> threadsList = new ArrayList<>(); //Список потоков
        double totalPerformance = 0; //Общая производительность

        //Получение входных данных
        System.out.print("Введите количество заводов: ");
        int facilitiesAmount = scan.nextInt();
        while(true){
            if (facilitiesAmount < 3){
                System.out.print("Заводов должно быть не менее 3, введите количество заводов повторно: ");
                facilitiesAmount = scan.nextInt();
            }
            else break;
        }
        System.out.print("Введите n: ");
        double n = scan.nextDouble();
        while(true){
            if (n < 50){
                System.out.print("n должно быть не менее 50, введите n повторно: ");
                n = scan.nextInt();
            }
            else break;
        }
        System.out.print("Введите M: ");
        int M = scan.nextInt();
        while(true){
            if (M < 100){
                System.out.print("M должно быть не менее 100, введите m повторно: ");
                M = scan.nextInt();
            }
            else break;
        }

        //Cоздание продуктов и заводов
        for(int i = 0; i < facilitiesAmount; i++){
            productsList.add(new Product("Продукт"+(i+1),1, "Тип"+(i+1)));
            facilitiesList.add(new Producer(queue, new Facility("Завод"+(i+1), productsList.get(i), n * (1 + 0.1 * i))));
            threadsList.add(new Thread(facilitiesList.get(i)));
            threadsList.get(i).start();
            totalPerformance+=facilitiesList.get(i).getFacility().getPerformance();
        }

        //Создание склада
        Storage storage = new Storage(queue, M * totalPerformance * 0.95,2);
        Thread storageThread = new Thread(storage);
        storageThread.start();

        //Проверка работы производства
        while (true){
            int k = 0;
            for(Thread thread : threadsList){
                if(!thread.isAlive()){
                    k++;
                }
            }
            if(k == threadsList.size()){
                System.out.println("=========================================================");
                for(int i = 0; i < storage.getTruckList().size(); i++){
                    storage.getTruckList().get(i).getProductsCount();
                }
                storageThread.interrupt();
                break;
            }
        }
    }
}