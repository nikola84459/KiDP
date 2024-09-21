public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Nit("Nit 1");
        Thread t2 = new Nit("Nit 2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        boolean thread1IsAlive = true;
        boolean thread2IsAlive = true;

        do {
            if(thread1IsAlive && !t1.isAlive()) {
                thread1IsAlive = false;
                System.out.println(t1.getName() + " nije više živa");
            }

            if(thread2IsAlive && !t2.isAlive()) {
                thread2IsAlive = false;
                System.out.println(t2.getName() + " nije više živa");
            }
        } while (thread1IsAlive || thread2IsAlive);
    }
}

class Nit extends Thread {
    static String message[] = {"Java", "je", "odlican", "najbolji,", "programski", "jezik."};

    public Nit(String id) {
        super(id);
    }

    public void randomWait() {
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        for(int i = 0; i < message.length; i++) {
            System.out.println("Nit " + getName() + " ispisuje " + message[i]);
            randomWait();
        }
    }
}