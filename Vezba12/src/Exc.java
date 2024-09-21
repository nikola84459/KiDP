public class Exc extends Thread {
    public int id;
    public static final int brojIteracija = 100;
    public static final int N = 50;
    public static volatile int counter;

    public static volatile boolean nemoze = true;
    public static volatile boolean zabranjeno = false;
    public static volatile boolean pomocna = false;


    public Exc(int id) {
        this.id = id;
    }

    public static void Exchange() {
        Exc.pomocna = Exc.zabranjeno;
        Exc.zabranjeno = Exc.nemoze;
        Exc.nemoze = Exc.pomocna;
    }

    public void lock() {
        Exc.nemoze = true;
        while(Exc.nemoze) {
            Exc.Exchange();
        }
    }

    public void unlock() {
        Exc.zabranjeno = false;
    }

    public void cs() {
        counter++;
    }

    public void run() {
        for(int i = 0; i < brojIteracija; i++) {
            lock();
            cs();
            unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Exc[] e = new Exc[N];

        for(int i = 0; i < e.length; i++) {
            e[i] = new Exc(i);
            e[i].start();
            e[i].join();
        }

        System.out.println("Vrednost counter-a je " + Exc.counter);
    }
}