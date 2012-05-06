package cz.cuni.mff.dpp.api;

/**
 * Represents lower and upper bounds of how many time an option or common argument can occur.<br>
 * Immutable.
 * 
 * @author Tom
 * 
 */
public class RequiredOccurrenceCountInterval {

    public static final int MAX_BOUND = Integer.MAX_VALUE;
    public static final int MIN_BOUND = 0;

    private final int min;
    private final int max;

    /**
     * 
     * @param min
     *            - lower bound
     * @param max
     *            - upper bound
     */
    public RequiredOccurrenceCountInterval(final int min, final int max) {
        super();
        isValid(min, max);
        this.min = min;
        this.max = max;
    }

    /**
     * Returns lower bound
     * 
     * @return lower bound
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns upper bound
     * 
     * @return upper bound
     */
    public int getMax() {
        return max;
    }

    /**
     * Returns {@code} if the count is inside interval (inclusive)
     * 
     * @param count
     * @return
     */
    public boolean isInside(final int count) {
        return min <= count && max >= count;
    }

    /**
     * Validates bounds
     * 
     * @param min
     * @param max
     * @return
     */
    public static boolean isValid(final int min, final int max) {
        return min >= MIN_BOUND && max <= MAX_BOUND && min <= max;
    }

    /**
     * Checks if the bound are valid and if not, throw an IllegalArgumentException
     * 
     * @param min
     * @param max
     */
    public static void check(final int min, final int max) {

        if (!isValid(min, max)) {
            throw new IllegalArgumentException(String.format("Invalid parameters %d, %d.", min, max));
        }

    }

}
