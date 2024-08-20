
package practice.list1.question2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class BSTree{
    private Node root = null;
    private final AtomicInteger size = new AtomicInteger(0);

    public void insert(int value){
        if (root == null) {
            synchronized(this) {
                if (root == null) { 
                    root = new Node(value);
                    size.incrementAndGet();
                    return;
                }
            }
        }

        Node curr = root;
        Node next;

        while(true){
            synchronized(curr){
                if(value < curr.value){
                next = curr.left;
                if(curr.left == null){
                    curr.left = new Node(value);
                    size.incrementAndGet();
                    break;
                } 
                } else{
                    next = curr.right;
                    if(curr.right == null){
                        curr.right = new Node(value);
                        size.incrementAndGet();
                        break;
                    } 
                }
            }
            curr = next;
        }
    }

    public int getSize(){
        return size.get();
    }
        
}

class Node{
    public Node right;
    public Node left;
    public int value;

    public Node(int value){
        this.value = value;
        this.right = null;
        this.left = null;
    }
}

class Producer extends Thread{

    BSTree tree;
    int id;

    Producer(BSTree tree, int id){
        this.tree = tree;
        this.id = id;
    }

    public void run(){
        Random rand = new Random();
        int value;
        int count = 0;

        while(count < 2000){
            value = rand.nextInt();
            tree.insert(value);
            //System.out.println("Thread " + id + " inseriu " + value);
            count++;
        }
    }
}

public class Main{
    public static void main(String[] args){
        // Medição de tempo
        long startTime = System.currentTimeMillis();

        BSTree tree = new BSTree();
        List<Producer> producers = new ArrayList<Producer>();

        for(int i = 0; i < 50; i ++){
            producers.add(new Producer(tree, i));
            producers.get(i).start();
        }

        for(Producer p: producers){
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Número de nós inseridos: " + tree.getSize());
        System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
    }
}