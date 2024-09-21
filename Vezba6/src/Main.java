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

    boolean flag1 = false;
    boolean flag2 = false;

    int turn = 1;

    public void uvecajCounter(int counter) {
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
            c.flag1 = true;

            while(c.flag2) {
                if(c.turn == 2) {
                    c.flag1 = false;
                    while(c.turn == 2);

                    c.flag1 = true;
                }
            }
            c.uvecajCounter(i);
            c.flag1 = false;
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
            c.flag2 = true;

            while(c.flag1) {
                if(c.turn == 1) {
                    c.flag2 = false;
                    while(c.turn == 1);

                    c.flag2 = true;
                }
            }
            c.uvecajCounter(i);
            c.flag2 = false;
        }
    }
}