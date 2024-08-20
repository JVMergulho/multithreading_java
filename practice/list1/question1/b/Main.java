// versão sequencial

package practice.list1.question1.b;

import java.util.*;

class BSTree{
    private Node root = null;
    private int size = 0;

    public synchronized void insert(int value){
        size++;
        
        if(root == null){
            root = new Node(value);
            return;
        }

        Node curr = root;
        Node next = curr;

        while(next != null){
            curr = next;
            if(value < curr.value){
                next = curr.left;
            } else{
                next = curr.right;
            }
        }

        if(value < curr.value){
            curr.left = new Node(value);
        } else{
            curr.right = new Node(value);
        }
    }

    public int getSize(){
        return size;
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

public class Main{
    public static void main(String[] args){
        // Medição de tempo
        long startTime = System.currentTimeMillis();

        BSTree tree = new BSTree();
        Random rand = new Random();
        int value;

        for(int i = 0; i < 100_000; i ++){
            value = rand.nextInt();
            tree.insert(value);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Número de nós inseridos: " + tree.getSize());
        System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
    }
}