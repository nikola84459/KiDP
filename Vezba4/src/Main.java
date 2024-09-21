public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Thread t1 = new Nit(c);
        Thread t2 = new Nit(c);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Vrednost counter-a je " + c.counter);
    }
}

class Counter {
    int counter = 0;
    boolean moze = true;

    public void add(int counter) {
        this.counter += counter;
    }
}

class Nit extends Thread {
    Counter c = null;

    public Nit(Counter c) {
        this.c = c;
    }

    public void run() {
        for(int i = 0; i < 100; i++) {
            if(c.moze) {
                c.moze = false;
                c.add(i);
            }
            c.moze = true;
        }

        c.moze = true;
    }
}