public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        for(int i = 0; i < 10; i++) {
            new Nit(c).start();
        }

        Thread.sleep(2000);

        System.out.println("Counter ima vrednost " + c.counter);
    }
}

class TAS {
    public static volatile boolean nemoze = false;


    static synchronized boolean Tas(boolean nemoze) {
        boolean pomocna = nemoze;
        TAS.nemoze = true;

        return pomocna;
    }
}

class Counter {
    int counter = 0;

    public void count() {
        counter++;
    }
}

class Nit extends Thread {
    Counter c = null;

    public Nit(Counter c) {
        this.c = c;
    }

    public void run() {
        for(int i = 0; i < 100; i++) {
            while(TAS.Tas(TAS.nemoze));

            c.count();

            TAS.nemoze = false;
        }
    }
}