package bstefanov.printinghouse.data.paper;

public enum PaperSize {
    A1(4),
    A2(3),
    A3(2),
    A4(1),
    A5(0);

    private final int value;

    PaperSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
