public class Main {
    public static void main(String[] args) throws InterruptedException {
        Calculator c = new Calculator();
        ImmutableValue im = new ImmutableValue(20);

        c.setValue(im);

        new Thread() {
            public void run() {
                c.addValue(20);
            }
        }.start();

        new Thread() {
            public void run() {
                c.addValue(20);
            }
        }.start();

        Thread.sleep(400);

        System.out.println(c.getValue().getValue());
    }
}

class ImmutableValue {
    int value;

    public ImmutableValue(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public ImmutableValue setValue(int value) {
        return new ImmutableValue(value + value);
    }
}

class Calculator {
    ImmutableValue im = null;

    public void setValue(ImmutableValue im) {
        this.im = im;
    }

    public void addValue(int value) {
        this.im = im.setValue(value);
    }

    public ImmutableValue getValue() {
        return im;
    }
}