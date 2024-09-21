public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Thread t1 = new Nit1(c);
        Thread t2 = new Nit2(c);

        t1.start();
        t2.start();

        Thread.sleep(400);

        System.out.println("Vrednost counter-a je " + c.counter);
    }
}

class Counter {
    int counter = 0;

    boolean moze1 = true;
    boolean moze2 = true;

    boolean blokiraj1 = false;
    boolean blokiraj2 = false;

    public void add(int counter) {
        this.counter += counter;
    }
}

class Nit1 extends Thread {
    Counter c = null;

    public Nit1(Counter c) {
        this.c = c;
    }

    public void run() {
        for(int i = 0; i < 100; i++) {
            c.blokiraj2 = true;

            if(c.moze1 && !c.blokiraj1) {
                c.moze2 = false;
                c.add(i);
                c.moze2 = true;
            }

            c.blokiraj2 = false;
        }
    }
}

class Nit2 extends Thread {
    Counter c = null;

    public Nit2(Counter c) {
        this.c = c;
    }

    public void run() {
        for(int i = 0; i < 100; i++) {
            c.blokiraj1 = true;

            if(c.moze2 && !c.blokiraj2) {
                c.moze1 = false;
                c.add(i);
                c.moze1 = true;
            }

            c.blokiraj1 = false;
        }
    }
}