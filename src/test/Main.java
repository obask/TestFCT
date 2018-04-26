package test;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * общие коменты:
 * обычно ./src/test используют для тестов, я бы поместил код в ./src/main/java/
 * коменты в коде на русском? seriously? на самом деле кому как удобнее
 */
public class Main {

    public static void main(String[] args) {

        BlockingQueue<Facility> queue = new ArrayBlockingQueue<>(1);
        // лучше читать в входные данные из файла ресурсов или написать тесты под разные случаи.
        // введите n не очень прикольно
        Scanner scan = new Scanner(System.in);
        // очень странно выглядит объявление переменных отдельно от использования, это не паскаль
        ArrayList<Product> productsList = new ArrayList<>(); //Список продуктов
        // продукты уже во множественном числе, не обязательно добавлять суффикс List
        ArrayList<Producer> facilitiesList = new ArrayList<>(); //Список заводов
        ArrayList<Thread> threadsList = new ArrayList<>(); //Список потоков
        double totalPerformance = 0; //Общая производительность

        //Получение входных данных
        System.out.print("Введите количество заводов: ");
        int facilitiesAmount = scan.nextInt();
        while(true){
            // условие if проще поместить в while
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
            // код лучше форматировать (cmd+alt+L), куча пробелов пропущена
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
        
        // не помешал бы комментарий почему эта система должна в конце концов остановиться

        //Проверка работы производства
        while (true){
            // что такое k?
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
