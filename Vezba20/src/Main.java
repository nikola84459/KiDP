import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Unesite kapacitet pumpe: ");
        int kapacitetPumpe = scanner.nextInt();

        int brojPumpi = 3 * kapacitetPumpe;

        Rezervoar r = new Rezervoar(kapacitetPumpe, 0);

        for(int i = 0; i < brojPumpi; i++) {
            Thread t1 = new Pumpa(r);
            t1.start();
        }

        for(int i = 0; i < 10; i++) {
            Thread t2 = new Potrosac(r);
            t2.start();
        }
    }
}

class Rezervoar {
    Semaphore semaphore = null;
    int kolicinaVode;
    int kapacitet;

    public Rezervoar(int kapacitet, int kolicinaVode) {
        this.kapacitet = kapacitet;
        this.kolicinaVode = kolicinaVode;
        this.semaphore = new Semaphore(kapacitet, true);
    }

    public void sipajVodu() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        kolicinaVode++;
        System.out.println("U rezervar je sipana voda i trenutna kolicina vode je " + kolicinaVode);
    }

    public void oduzmiVodu() {
        semaphore.release();
        System.out.println("Ispumpana voda.");
        kolicinaVode--;
    }
}


class Pumpa extends Thread {
    Rezervoar r = null;

    public Pumpa(Rezervoar r) {
        this.r = r;
    }

    public void run() {
        r.sipajVodu();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Potrosac extends Thread {
    Rezervoar r = null;

    public Potrosac(Rezervoar r) {
        this.r = r;
    }

    public void run() {
        r.oduzmiVodu();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}