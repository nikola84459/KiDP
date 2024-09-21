import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Unesite broj parking mesta: ");
        Scanner scanner = new Scanner(System.in);
        int brojMesta = scanner.nextInt();

        int N = 3 * brojMesta;

        ParkingGaraza pg = new ParkingGaraza(brojMesta);

        for(int i = 0; i < N; i++) {
            Auto a = new Auto("Auto " + i, pg);
            a.start();
        }
    }
}

class ParkingGaraza {
    public int brojMesta;

    public ParkingGaraza(int brojMesta) {
        this.brojMesta = brojMesta;
    }

    public void parkiraj(String nazivAutomobila) {
        System.out.println(nazivAutomobila + " čeka da se parkira");
        while(brojMesta == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.brojMesta = brojMesta--;
        System.out.println(nazivAutomobila + " se uparkirao.");
    }

    public void isparkiraj(String nazivAutomobila) {
        this.brojMesta = brojMesta--;
        System.out.println(nazivAutomobila + " se isparkirao.");
        notify();
    }
}

class Auto extends Thread {
    String nazivAutomobila;
    ParkingGaraza pg;

    public Auto(String nazivAutomobila, ParkingGaraza pg) {
        super(nazivAutomobila);
        this.nazivAutomobila = nazivAutomobila;
        this.pg = pg;
    }

    public void run() {
        pg.parkiraj(getName());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pg.isparkiraj(getName());
    }
}