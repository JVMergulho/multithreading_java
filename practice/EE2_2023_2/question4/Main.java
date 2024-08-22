package EE2_2023_2.question4;

import java.util.*;

class BlockingQueue{
    private Queue<Integer> queue;

    private int capacity;

    public BlockingQueue(int capacity){
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public synchronized int getSize(){
        return queue.size();
    }

    public boolean addTen(int item){
        synchronized(this){
            while(queue.size() + 10 > capacity) 
            {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < 10; i++){
                queue.add(item);
            }

            this.notifyAll();
            return true;
        }
    }

    public void removeTen(){
        synchronized(this){
            while(queue.size() == 0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < 10; i++){
                queue.poll();
            }

            this.notifyAll();
        }
    }
}

class Producer extends Thread{
    private BlockingQueue queue;

    public Producer(BlockingQueue queue){
        this.queue = queue;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){

            if(queue.getSize() == 0){
                for(int i = 0; i < 5; i++){
                    // delay para os reabastecer os pães
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    queue.addTen(100);
                    System.out.println("10 pães foram para o forno: há " + queue.getSize() + " pães no forno.");
                }
            }
        }
    }
}

class Consumer extends Thread{
    private BlockingQueue queue;

    public Consumer(BlockingQueue queue){
        this.queue = queue;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){

            // delay para os pães assarem
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            queue.removeTen();

            System.out.println("10 pães saíram do forno");
        }
    }
}

public class Main {
    public static void main(String[] args){
        BlockingQueue forno = new BlockingQueue(50);
        Producer p = new Producer(forno);
        Consumer c = new Consumer(forno);
    
        p.start();
        c.start();
    }
}
