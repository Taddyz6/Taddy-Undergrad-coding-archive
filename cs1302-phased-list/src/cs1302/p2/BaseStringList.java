package cs1302.p2;

import cs1302.adt.StringList;

/**
 * This is {@code BaseStringList} abstract class.
 */
public abstract class BaseStringList implements StringList {

    private int size;

    /**
     * This is empty to determine size is zero.
     * @return false
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * This is append and return false.
     *
     * @return false
     * @param item is add item
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean append(String item) {
        return false;

    }

    /**
     * Make the base string list size is zero.
     */
    public BaseStringList() {
        size = 0;
    }

    /**
     * make a string and return empty.
     *
     * @return empty
     * @param start is begin
     * @param sep is for make seperate
     * @param end is for in the end
     * <p>
     * {@inheritDoc}
     */
    @Override
    public String makeString(String start, String sep, String end) {
        // "[item sep item sep item sep item]"
        return "";
    }

    /**
     * This is prepend return false.
     *
     * @return false
     * @param item is for prepend item
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean prepend(String item) {
        return false;
    }

    /**
     * This is size method.
     * @return size value
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * This is make the structure of String.
     * @return makeString
     * <p>
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        return makeString("[", ", ", "]");
    }

    /**
     * This method is for get size.
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * This method is for set size.
     *
     * @param size for set size
     */
    public void setSize(int size) {
        this.size = size;
    }
} // BaseStringList
