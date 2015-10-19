/**
 * Abstract class for radix trees
 *
 * @author Austin Leal
 * @version 1.0
 */
public abstract class AbstractRadixTreeElement implements Comparable {

    /**
     * adds to end of current AbstractRadixTreeElement's data
     *
     * @param  other  AbstractRadixTreeElement to be appended
     *
     * @return boolean if successful
     */
    public abstract AbstractRadixTreeElement append(
            AbstractRadixTreeElement other);

    /**
     * compares prefixes of
     *
     * @param  otherElement       element to compare to
     *
     * @return int -1 if no equal prefix, positive number - end of equal prefix
     */
    protected abstract int comparePrefix(AbstractRadixTreeElement otherElement);

    @Override
    public abstract int compareTo(Object o);

    @Override
    public abstract boolean equals(Object other);

    /**
     * returns data
     *
     * @return Object data of element
     */
    protected abstract Object getData();

    @Override
    public abstract int hashCode();

    /**
     * method to find number of subSets
     *
     * @return number of subsets
     */
    public abstract int length();

    /**
     * returns a subset of this element
     * ensure that subSet(0,0) returns an empty element
     *
     * @param start        int starting position
     * @param end          int ending position
     *
     * @return new AbstractRadixTreeElement
     */
    public abstract AbstractRadixTreeElement subSet(int start, int end);

    @Override
    public abstract String toString();
}