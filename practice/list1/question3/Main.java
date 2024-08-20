package practice.list1.question3;

import java.util.*;

class Buffer{
    // capacidade total
    private int capacity;
    // número de itens
    private Queue<Integer> odds;
    private Queue<Integer> evens;

    public Buffer(int capacity){
        this.capacity = capacity;
        this.odds = new LinkedList<Integer>();
        this.evens = new LinkedList<Integer>();
    }

    private void putEven(int item){
        synchronized(evens){
            while(evens.size() == capacity){
                try {
                    evens.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            evens.add(item);
            evens.notifyAll();
        }
    }

    private void putOdd(int item){
        synchronized(odds){
            while(odds.size() == capacity){
                try {
                    odds.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            odds.add(item);
            odds.notifyAll();
        }
    }

    public void put(int item){
        if(item % 2 == 0){
            putEven(item);
        } else {
            putOdd(item);
        }
    }

    public int takeEven(){
        synchronized(evens){
            while(evens.isEmpty()){
                try {
                    evens.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            evens.notifyAll();
            return evens.poll();
        }
    }

    public int takeOdd(){
        synchronized(odds){
            while(odds.isEmpty()){
                try {
                    odds.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            odds.notifyAll();
            return odds.poll();
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
        int item = rand.nextInt();
        buffer.put(item);
        System.out.println("Item produzido: " + item);
    }
}

class Consumer extends Thread{

    Buffer buffer;
    String parity;

    public Consumer(Buffer buffer, String parity){
        this.buffer = buffer;
        this.parity = parity; 
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            consume();
        }
    }

    public void consume(){
        int item;
        if(parity == "Odd"){
            item = buffer.takeOdd();
            System.out.println("Thread ímpar removeu: " + item);
        } else if(parity == "Even"){
            item = buffer.takeEven();
            System.out.println("Thread par removeu: " + item);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);
        Thread producer = new Producer(buffer);
        Thread consumerOdd = new Consumer(buffer, "Odd");
        Thread consumerEven = new Consumer(buffer, "Even");

        producer.start();
        consumerOdd.start();
        consumerEven.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.interrupt();
            consumerOdd.interrupt();
            consumerEven.interrupt();
        }
    }
}