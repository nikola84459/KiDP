public class Main {
    public static void main(String[] args) throws InterruptedException {
       final int N = 800;
       int brojIteracija = 30;

       Semaphore s = new Semaphore(1);

       Nit[] n = new Nit[N];

       for(int i = 0; i < n.length; i++) {
           n[i] = new Nit(i + "", brojIteracija, s);
           n[i].start();
           n[i].join();
       }

        System.out.println("Vrednost counter-a je " + Nit.counter);
    }
}


class Nit extends Thread {
    public String naziv;
    public static int brojIteracija;
    Semaphore s;
    public static volatile int counter;

    public Nit(String naziv, int brojIteracija, Semaphore s) {
        super(naziv);
        this.naziv = naziv;
        this.s = s;
        Nit.brojIteracija = brojIteracija;
    }

    public void run() {
        for(int i = 0; i < brojIteracija; i++) {
            s.waitS();
            counter++;
            System.out.println(getName() + " nit je u kritičnoj sekciji.");
            s.signalS();

        }
    }
}

class Semaphore {
    int p;

    public Semaphore(int p) {
        this.p = p;
    }

    public synchronized void waitS() {
        while(p <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        p = p + 1;
    }

    public synchronized void signalS() {
        p = p - 1;
        notify();
    }
}