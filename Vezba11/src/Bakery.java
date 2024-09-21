public class Bakery extends Thread {
    public int thread_id;
    public static final int brojNiti = 5;
    public static final int brojIteracija = 1000;
    public volatile boolean[] choosing = new boolean[brojNiti];
    public volatile int[] ticket = new int[brojNiti];
    public static volatile int counter;

    public Bakery(int thread_id) {
        this.thread_id = thread_id;
    }

    public int findMax() {
        int max = ticket[0];

        for(int i = 0; i < ticket.length; i++) {
            if(ticket[i] > max) {
                max = ticket[i];
            }
        }

        return max;
    }

    public void lock() {
        choosing[thread_id] = true;

        ticket[thread_id] = findMax() + 1;

        choosing[thread_id] = false;

        for(int j = 0; j < brojNiti; j++) {
            if(j == thread_id) {
                continue;
            }

            while(choosing[j]);

            while(ticket[j] != 0 && (ticket[thread_id] > ticket[j]) || (ticket[thread_id] == ticket[j] && thread_id > j));

        }
    }

    public void unlock() {
        ticket[thread_id] = 0;
    }

    public void run() {
        for(int i = 0; i < brojIteracija; i++) {
            lock();
            counter++;
            unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Bakery[] b = new Bakery[brojNiti];

        for(int i = 0; i < b.length; i++) {
            b[i] = new Bakery(i);
            b[i].start();
            b[i].join();
        }

        System.out.println("Vrednost counter-a je " + Bakery.counter);
    }
}