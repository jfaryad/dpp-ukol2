package cz.cuni.mff.dpp.api;

public class RequiredCountInterval {

    public static final int MAX_BOUND = Integer.MAX_VALUE;
    public static final int MIN_BOUND = 0;

    private final int min;
    private final int max;

    public RequiredCountInterval(int min, int max) {
        super();
        isValid(min, max);
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isInside(int count) {
        return min <= count && max >= count;
    }

    public static boolean isValid(int min, int max) {
        return min >= MIN_BOUND && max <= MAX_BOUND && min <= max;
    }

    public static void check(int min, int max) {

        if (!isValid(min, max)) {
            throw new IllegalArgumentException(String.format("Invalid parameters %d, %d.", min, max));
        }

    }

}
