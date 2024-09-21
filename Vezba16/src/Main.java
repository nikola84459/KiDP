public class Main {
    public static void main(String[] args) throws InterruptedException {
        PingPong pp = new PingPong();

        Thread t1 = new Nit("Ping", pp);
        Thread t2 = new Nit("Pong", pp);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class PingPong {
    public volatile String state = "";


    public synchronized void ping() {
        this.state = "Ping";
        System.out.println(this.state);
        notify();

        try {
            while(this.state.equals("Ping")) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void pong() {
        this.state = "Pong";
        System.out.println(this.state);
        notify();

        try {
            while(this.state.equals("Pong")) {
                wait();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Nit extends Thread {
    String naziv = "";
    PingPong pp = null;

    public Nit(String naziv, PingPong pp) {
        super(naziv);
        this.naziv = naziv;
        this.pp = pp;
    }

    public void run() {
        if(getName().equals("Ping")) {
            while(true) {
                pp.ping();
            }
        }

        if(getName().equals("Pong")) {
            while(true) {
                pp.pong();
            }
        }
    }
}