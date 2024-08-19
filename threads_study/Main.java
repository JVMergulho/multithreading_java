
class Thread1 extends Thread{
    private String name;

    public Thread1(String name) {
        this.name = name;
    }

    public void run(){
        for(int i = 0; i < 1000; i ++){
            System.out.println( this.name + " " + i);
        }
    }
}

class Thread2 implements Runnable{
    private String name;

    public Thread2(String name) {
        this.name = name;
    }

    public void run(){
        for(int i = 0; i < 1000; i ++){
            System.out.println( this.name + " " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Main is starting");

        // criando thread
        Thread thread1 = new Thread1("thread 1");

        // criando thread e passando como parametro um objeto que implementa Runnable
        Thread thread2 = new Thread(new Thread2("thread 2"));

        // iniciando as threads
        thread1.start();
        thread2.start();

        System.out.println("Main is exiting");
    }
}
