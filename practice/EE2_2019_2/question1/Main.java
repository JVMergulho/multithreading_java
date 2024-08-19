/* Implementação do problema produtor-consumidor */

package practice.EE2_2019_2.question1;

import java.util.*;

class Buffer{
    // capacidade total
    private int capacity;
    // número de itens
    private int size;
    private int[] container;

    public Buffer(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.container = new int[capacity];
    }

    public void push(int item){
        synchronized(this){
            while(capacity == size){
                try {
                    System.out.println("Buffer cheio. Aguardando...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            container[size] = item;
            size++;
            this.notifyAll();
        }
    }

    public int pop(){
        synchronized(this){
            while(size == 0){
                try {
                    System.out.println("Buffer Vazio. Aguardando...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int item = container[size - 1];
            size--;
            this.notifyAll();
            return item;
        }
    }
}

class Producer extends Thread{

    Buffer buffer;

    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            produce();
        }
    }

    public void produce(){
        Random rand = new Random();
        int item = rand.nextInt(100);
        buffer.push(item);
        System.out.println("Item produzido: " + item);
    }
}

class Consumer extends Thread{

    Buffer buffer;

    public Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            consume();
        }
    }

    public void consume(){
        int item = buffer.pop();
        System.out.println("Item removido: " + item);
    }
}

public class Main {

    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);
        Thread producer = new Producer(buffer);
        Thread consumer = new Consumer(buffer);

        producer.start();
        consumer.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.interrupt();
            consumer.interrupt();
        }
    }
}
