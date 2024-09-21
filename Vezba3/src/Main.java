public class Main {
    public static void main(String[] args) throws InterruptedException {
        KamionSkladiste ks = new KamionSkladiste();

        Thread t1 = new Radnik(ks);
        Thread t2 = new Radnik(ks);

        t1.start();
        t2.start();

        Thread.sleep(400);

        System.out.println("Broj cigli u skladištu je " + ks.brojCigliSkladiste + " broj cigli u kamionu je " + ks.brojCigliKamion);
    }
}

class KamionSkladiste {
    int brojCigliSkladiste = 1000;
    int brojCigliKamion = 0;

    public void promeniBrojeve() {
        synchronized (this) {
            brojCigliSkladiste--;
            brojCigliKamion++;
        }
    }
}

class Radnik extends Thread {
    KamionSkladiste ks = null;

    public Radnik (KamionSkladiste ks) {
        this.ks = ks;
    }

    public void run() {
        do {
            ks.promeniBrojeve();
        } while(ks.brojCigliSkladiste > 1 && ks.brojCigliKamion < 1000);
    }
}