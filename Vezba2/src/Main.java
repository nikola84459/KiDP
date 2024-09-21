public class Main {
    public static void main(String[] args) throws InterruptedException {
       Skladiste s = new Skladiste();
       Kamion k = new Kamion();

       Thread t1 = new Radnik(s, k);
       Thread t2 = new Radnik(s, k);

       t1.start();
       t2.start();

       Thread.sleep(400);

        System.out.println("Broj cigli u skladištu je " + s.brojCigli + " broj cigli u kamionu je " + k.brojCigli);
    }
}

class Skladiste  {
    int brojCigli = 1000;

    public void oduzmiCigle() {
        brojCigli--;
    }
}

class Kamion {
    int brojCigli = 0;

    public void dodajCigle() {
        brojCigli++;
    }
}

class Radnik extends Thread {
    Skladiste s = null;
    Kamion k = null;

    public Radnik(Skladiste s, Kamion k) {
        this.s = s;
        this.k = k;
    }

    public void run() {
        do {
            s.oduzmiCigle();
            k.dodajCigle();
        } while (s.brojCigli > 1 && k.brojCigli < 1000);
    }
}