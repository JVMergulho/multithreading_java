import java.util.*;

public class BlockingQueue{
    private Queue<Integer> queue;

    private int capacity;

    public BlockingQueue(int capacity){
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public boolean add(int item){
        synchronized(queue){
            while(queue.size() == capacity) 
            {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            queue.add(item);
            // notifica todas as threads que estão esperando pelo objeto
            queue.notifyAll();
            return true;
        }
    }

    public int remove(){
        synchronized(queue){
            while(queue.size() == 0){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            int item = queue.poll();
            queue.notifyAll();
            return item; 
        }
    }
}