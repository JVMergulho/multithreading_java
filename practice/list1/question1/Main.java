// Defina uma classe ArvoreBusca que implementa uma  ́arvore de busca onde  ́e poss ́ıvel realizar insercoes 
// de elementos. Essa estrutura de dados deve funcionar com v ́arias threads. Fa ̧ca o que  ́e pedido:
// (a) Implemente um metodo main() que cria 50 threads onde cada uma insere 2000 n umeros aleat ́orios
// nessa arvore. Seu programa deve informar a quantidade de n ́os total da  arvore ap os todas as
// insercoes
// (b) Meca o tempo de execucao do seu programa, comparando-o com o de uma execucao puramente
// sequencial.

package practice.list1.question1;

import java.util.*;

class BSTree{
    Node root;
    
    public BSTree(){
        root = null;
    }

    public void insert(int value){
        synchronized(this){
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
            value = rand.nextInt(100);
            tree.insert(value);
            System.out.println("Thread " + id + " inseriu " + value);
            count++;
        }
    }
}

public class Main{
    public static void main(String[] args){
        BSTree tree = new BSTree();
        List<Producer> producers = new ArrayList<Producer>();

        for(int i = 0; i < 50; i ++){
            new Producer(tree, i).start();
        }
    }
}