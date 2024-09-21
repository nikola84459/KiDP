public class TasPrepravka extends Thread {

    public static final int brojIteracija = 1000;
    public static final int N = 30;
    public static volatile int counter;
    public int id;

    public volatile boolean[] ceka = new boolean[N];
    public static volatile boolean nemoze;
    public static volatile boolean zabranjeno = false;
    public static volatile boolean pomocna = false;


    public TasPrepravka(int id) {
        this.id = id;
    }

    public static boolean Tas() {
        TasPrepravka.pomocna = TasPrepravka.zabranjeno;
        TasPrepravka.zabranjeno = true;
        return pomocna;
    }

    public void lock() {
        ceka[id] = true;
        nemoze = true;

        while(nemoze && ceka[id]) {
            nemoze = TasPrepravka.Tas();
        }
        ceka[id] = false;
    }

    public void unlock() {
        int j = (id + 1) % N;

        while((id != j) && (!ceka[j])) {
            j = (j + 1) % N;
        }

        if(j == id) {
            TasPrepravka.zabranjeno = false;
        } else {
            ceka[j] = false;
        }
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
        TasPrepravka[] tp = new TasPrepravka[N];

        for(int i = 0; i < tp.length; i++) {
            tp[i] = new TasPrepravka(i);
            tp[i].start();
            tp[i].join();
        }

        System.out.println("Vrednost counter-a je " + TasPrepravka.counter);
    }
}