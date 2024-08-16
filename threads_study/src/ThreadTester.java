
public class ThreadTester {
    public static void main(String[] args) {

        Stack stack = new Stack(5);

        new Thread(() -> {
            int counter = 0;
            while(++ counter < 10)
                System.out.println("Pushed: " + stack.push(100));
        }).start();

        new Thread(() -> {
            int counter = 0;
            while(++ counter < 10)
                System.out.println("Popped: " + stack.pop());
        }).start();
    }
}
