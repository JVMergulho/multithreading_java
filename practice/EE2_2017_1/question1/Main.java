/*
 * Uma conta bancária é compartilhada por seis pessoas. Cada uma pode depositar ou retirar dinheiro desde que o saldo não se torne negativo. 
 * Implemente em Java uma solução para conta bancária, utilizando bloco sincronizado. Considere que saques e depósitos são de valores aleatórios.
 */

package practice.EE2_2017_1.question1;

import java.util.*;

class BankAcount{
    int amount;

    public BankAcount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }

    public void add(int value){
        synchronized(this){
            amount += value;
        }
    }

    public void take(int value) throws SIexception{
        synchronized(this){
            if(amount - value >= 0){
                amount -= value;
            } else {
                throw new SIexception(); 
            }
        }
    }
}

// exceção de Saldo Insuficiente
class SIexception extends Exception{
    public SIexception(){
        super("Saldo Insuficiente");
    }
}

class User extends Thread{
    String name;
    BankAcount acount;

    public User(String name, BankAcount acount){
        this.name = name;
        this.acount = acount;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            Random rand = new Random();
            int value = rand.nextInt(200);
            acount.add(value);
            System.out.println(name + " depositou " + value + " reais");

            value = rand.nextInt(200);
            try {
                acount.take(value);
                System.out.println(name + " sacou " + value + " reais");
            } catch (SIexception e) {
                System.out.print(e.getMessage() + " : apenas " + acount.getAmount() + " na conta");
            }
        } 
    }
}

public class Main {
    public static void main(String[] args) {
        BankAcount acount = new BankAcount(0);
        String[] nomes = {"A","B","C","D","E","F"};
        List<User> users = new ArrayList<User>();

        for(int i = 0; i < nomes.length; i++){
            users.add(new User(nomes[i], acount));
            users.get(i).start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for(int i = 0; i < nomes.length; i++){
                users.get(i).interrupt();
            }
        }
    }
}
