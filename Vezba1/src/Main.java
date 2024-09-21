public class Main {
    public static void main(String[] args) throws InterruptedException {
        PogodiBroj pb = new PogodiBroj();

        Thread t1 = new Nit1(pb);
        Thread t2 = new Nit2(pb);

        t1.start();
        t2.start();

        Thread.sleep(400);

        System.out.println("Broj 1 je " + pb.broj1 + " broj dva je " + pb.broj2);
    }
}

class PogodiBroj {
    int broj1 = 0;
    int broj2 = 0;
}

class Nit1 extends Thread {
    PogodiBroj pogodiBroj = null;

    public Nit1(PogodiBroj pogodiBroj) {
        this.pogodiBroj = pogodiBroj;
    }

    public void run() {
        do {
            pogodiBroj.broj1 = (int) (Math.random() * 10000 + 1);
        } while(pogodiBroj.broj1 != pogodiBroj.broj2);
    }
}

class Nit2 extends Thread {
    PogodiBroj pogodiBroj = null;

    public Nit2(PogodiBroj pogodiBroj) {
        this.pogodiBroj = pogodiBroj;
    }

    public void run() {
        do {
            pogodiBroj.broj2 = (int) (Math.random() * 10000 + 1);
        } while(pogodiBroj.broj2 != pogodiBroj.broj1);
    }
}