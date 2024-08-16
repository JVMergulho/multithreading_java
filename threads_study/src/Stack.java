class Stack {
    private int[] container;
    private int stackTop;
    Object lock = new Object();

    public Stack(int capacity){
        container = new int[capacity];
        stackTop = -1;
    }

    public boolean push(int elem){
        synchronized(lock) {
            if(isFull()) return false;
            ++ stackTop;

            try { Thread.sleep(1000); } catch (Exception e) { }
            
            container[stackTop] = elem;
            return true;
        }
    }

    public int pop(){
        synchronized(lock) {
            if(isEmpty()) return Integer.MIN_VALUE;
            int elem = container[stackTop];

            try { Thread.sleep(1000); } catch (Exception e) { }
            
            stackTop --;
            return elem;
        }
    }

    public boolean isEmpty(){
        return stackTop < 0;
    }

    public boolean isFull(){
        return stackTop >= container.length - 1;
    }
}
