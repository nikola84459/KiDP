import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite broj knjiga: ");
        int brojKnjiga = scanner.nextInt();

        int brojOsoba = brojKnjiga * 3;

        Biblioteka b = new Biblioteka(brojKnjiga);

        for(int i = 0; i < brojOsoba; i++) {
            Thread t = new Osoba(b);
            t.start();
            t.join();
        }
    }
}

class Biblioteka {
    int brojKnjiga;

    public Biblioteka(int brojKnjiga) {
        this.brojKnjiga = brojKnjiga;
    }

    public synchronized void uzmiKnjigu() {
        System.out.println("Korisnik čeka na red da uzme knjigu...");
        while(brojKnjiga == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        brojKnjiga--;
        System.out.println("Korisnik je uzeo knjigu.");
    }

    public synchronized void vratiKnjigu() {
        brojKnjiga++;
        notify();
        System.out.println("Korisnik je vratio knjigu");
    }
}

class Osoba extends Thread {
    Biblioteka b = null;

    public Osoba(Biblioteka b) {
        this.b = b;
    }

    public void run() {
        b.uzmiKnjigu();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        b.vratiKnjigu();
    }
}