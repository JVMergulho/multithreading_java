package practice.EE2_2019_2.question2;

import java.util.*;

class Node{
    private Node next;
    private int value;

    public Node(int value){
        this.value = value;
        this.next = null;
    }

    public Node getNext(){
        return next;
    }

    public int getValue(){
        return value;
    }

    public void setNext(Node next){
        this.next = next;
    }

    public void setValue(int value){
        this.value = value;
    }
}

class BlockingQueue{
    private int capacity;
    private int size;
    private Node head;
    private Node tail;

    public BlockingQueue(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void enqueue(int item){
        Node node = new Node(item);
        synchronized(this){
            while(capacity == size){
                try{
                    this.wait();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            if(size == 0){
                head = node;
                tail = head;
            } else {
                tail.setNext(node);
                tail = node;
            }
            size++;

            this.notifyAll();
        }
    }

    public int dequeue(){
        synchronized(this){
            while(size == 0){
                try{
                    this.wait();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            int item = head.getValue();

            if(size == 1){
                head = null;
                tail = null;
            } else {
                head = head.getNext();
            }
            size--;

            this.notifyAll();
            return item;
        }
    }

}

class Producer extends Thread{

    BlockingQueue buffer;

    public Producer(BlockingQueue buffer){
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
        buffer.enqueue(item);
        System.out.println("Item produzido: " + item);
    }
}

class Consumer extends Thread{

    BlockingQueue buffer;

    public Consumer(BlockingQueue buffer){
        this.buffer = buffer;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            consume();
        }
    }

    public void consume(){
        int item = buffer.dequeue();
        System.out.println("Item removido: " + item);
    }
}

public class Main {

    public static void main(String[] args) {
        BlockingQueue buffer = new BlockingQueue(10);
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