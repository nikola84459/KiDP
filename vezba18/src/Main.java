import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Unesite broj stolova: ");
        Scanner scanner = new Scanner(System.in);
        int brojStolova = scanner.nextInt();

        int brojGostiju = 2 * brojStolova;

        Restoran r = new Restoran(brojStolova);

        for(int i = 0; i < brojGostiju; i++) {
            Thread t = new Gost(r);
            t.start();
        }
    }
}

class Restoran {
    private int brojStolova;

    public Restoran(int brojStolova) {
        this.brojStolova = brojStolova;
    }

    // Sinhronizovana metoda za zauzimanje stola
    public synchronized void sedi() {
        while(brojStolova == 0) {
            try {
                System.out.println("Nema slobodnih stolova, gost čeka...");
                wait(); // Čekaj dok se sto ne oslobodi
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        brojStolova--; // Smanji broj stolova kad gost sedne
        System.out.println("Gost je seo za sto. Preostalo slobodnih stolova: " + brojStolova);
    }

    // Sinhronizovana metoda za napuštanje stola
    public synchronized void ustani() {
        brojStolova++; // Povećaj broj stolova kad gost ustane
        System.out.println("Gost je napustio restoran. Preostalo slobodnih stolova: " + brojStolova);
        notify(); // Obavesti ostale goste koji čekaju da mogu da zauzmu sto
    }
}

class Gost extends Thread {
    private Restoran restoran;

    public Gost(Restoran r) {
        this.restoran = r;
    }

    public void run() {
        restoran.sedi();
        try {
            Thread.sleep(200); // Simuliraj da gost koristi sto neko vreme
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        restoran.ustani();
    }
}