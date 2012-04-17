package de.lemo.dms.processing.parameter;

/**
 * A parameter whose argument must be contained in an interval.
 * 
 * @author Leonard Kappe
 * 
 * @param <T>
 *            Type of the argument. Must be comparable to {@link T}.
 */
public class Interval<T extends Comparable<T>> extends Parameter<T> {

    private T min;
    private T max;

    public Interval(String id, String name, String description, T min, T max) {
        this(id, name, description, min, max, null);
    }

    public Interval(String id, String name, String description, T min, T max, T defaultValue) {
        super(id, name, description, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean validate(T argument) {
        return argument.compareTo(min) >= 0 && argument.compareTo(max) <= 0;
    }
}