public class Peterson extends Thread {
    public int id;
    public volatile FlagAndTurn fat;
    static volatile int counter;

    public int vratiId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Peterson(int id, FlagAndTurn fat) {
        this.fat = fat;
        this.id = id;
    }

    public void run() {
        while(counter <= 98) {
            fat.flag[id] = true;

            int procesId = (this.id == 0 ? 1 : 0);

            this.fat.turn = procesId;

            while(fat.flag[procesId] == true && fat.turn == procesId);

            counter++;

            fat.flag[this.id] = false;

        }
    }

    public static void main(String[] args) throws InterruptedException {
        FlagAndTurn fat = new FlagAndTurn();

        Thread t1 = new Peterson(0, fat);
        Thread t2 = new Peterson(1, fat);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(Peterson.counter);
    }
}

class FlagAndTurn {
    boolean[] flag = new boolean[2];

    int turn = 1;

    public FlagAndTurn() {
        flag[0] = false;
        flag[1] = false;
    }
}