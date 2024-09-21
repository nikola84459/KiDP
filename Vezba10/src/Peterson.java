public class Peterson extends Thread {
    private static final int n = 2;
    public static volatile int turn;
    public static volatile boolean[] flag = new boolean[n];
    public int brojIteracija = 10;
    public static int counter;
    public int thread_id;

    public Peterson(int thread_id) {
        this.thread_id = thread_id;
    }

    public void lock() {
        this.flag[thread_id] = true;

        this.turn = (thread_id + 1) % n;

        while(this.flag[(thread_id + 1) % n] == true && this.turn == (thread_id + 1) % n);
    }

    public void unlock() {
        this.flag[thread_id] = false;
    }

    public void cs() {
        counter++;
    }

    public void run() {
        for(int i = 0; i < brojIteracija; i++) {
            lock();
            cs();
            System.out.println("Nit " + thread_id + " je u kritičnoj sekciji.");
            unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Peterson[] p = new Peterson[n];

        for(int i = 0; i < p.length; i++) {
            p[i] = new Peterson(i);
            p[i].start();
            p[i].join();
        }

        System.out.println("Vrednost counter-a je " + counter);
    }
}