import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Unesite broj mašina: ");
        Scanner scanner = new Scanner(System.in);
        int brojMasina = scanner.nextInt();

        System.out.println("Unesite kapacitet skladišta: ");
        int kapacitetSkladista = scanner.nextInt();

        int brojRadnika = 2 * brojMasina;

        Masina m = new Masina(brojMasina);
        Skladiste s = new Skladiste(kapacitetSkladista);

        for(int i = 0; i < brojRadnika; i++) {
            Thread t = new Radnik(m, s);
            t.start();
        }
    }
}

class Masina {
    public int brojMaina;

    public Masina(int brojMasina) {
        this.brojMaina = brojMasina;
    }

    public synchronized void postaviRadnika() {
        while(brojMaina == 0) {
            System.out.println("Radnik čeka da dodje do mašine...");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        brojMaina--;
        System.out.println("Radnik pristupa mašini.");
    }

    public synchronized void oduzmiRadnika() {
        System.out.println("Radnik završio sa korišćenjem mašine.");
        brojMaina++;
        notify();
    }
}

class Skladiste {
    public int kapacitet;

    public Skladiste(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public synchronized void dodaj() {
        while(kapacitet == 0) {
            System.out.println("U skladištu nema mesta...");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        kapacitet--;
        System.out.println("Proizvod dodat u skladište.");
    }

    public synchronized void oduzmi() {
        kapacitet++;
        System.out.println("Proizvod je izašao iz skladišta.");
        notify();
    }
}

class Radnik extends Thread {
    Masina m = null;
    Skladiste s = null;

    public Radnik(Masina m, Skladiste s) {
        this.m = m;
        this.s = s;
    }

    public void run() {
        m.postaviRadnika();
        s.dodaj();
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        s.oduzmi();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        m.oduzmiRadnika();
    }
}