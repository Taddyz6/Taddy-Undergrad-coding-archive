package cs1302.p2;

import cs1302.adt.FancyStringList;
import cs1302.adt.StringList;

/**
 * This is the abstract class Base Sring List.
 */
public abstract class BaseStringList implements FancyStringList {

    private int size;

    /**
     * This is boolean to is empty or not method.
     *
     * @return size equal to zero
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * This is boolean append to Stirng item.
     *
     * @param arg0 to determind
     * @return false as well
     */
    public boolean append(String arg0) {
        if (arg0 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg0 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        this.add(this.size(), arg0);

        return true;
    }

    /**
     * This is BaseStringList method.
     */
    public BaseStringList() {
        size = 0;
    }

    /**
     * This is makeString method.
     *
     * @param arg0 show start string
     * @param arg1 make sep string
     * @param arg2 show end string
     * @return empty string
     */
    public String makeString(String arg0, String arg1, String arg2) {
        String start, sep, end;
        if (arg0 == null) {
            start = "null";
        } else {
            start = arg0;
        }
        if (arg1 == null) {
            sep = "null";
        } else {
            sep = arg1;
        }
        if (arg2 == null) {
            end = "null";
        } else {
            end = arg2;
        }
        String resultStr = "" + start;
        for (int i = 0; i < this.getSize(); i++) {
            if (i == this.getSize() - 1) {
                resultStr += this.get(i);
            } else {
                resultStr += (this.get(i) + sep);
            }
        }
        resultStr += end;
        return resultStr;

    }

    /**
     * This is boolean prepend method.
     *
     * @param arg0 to prepend
     * @return false prepend
     */
    public boolean prepend(String arg0) {
        if (arg0 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg0 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        this.add(0, arg0);

        return true;
    }

    /**
     * This is size method.
     *
     * @return size when size method use
     */
    public int size() {
        return size;
    }

    /**
     * This is to string method.
     *
     * @return makeString to make a string
     */
    public String toString() {
        return makeString("[", ", ", "]");
    }

    /**
     * This is get size method.
     *
     * @return size when user want to know size
     */
    public int getSize() {
        return size;
    }

    /**
     * This is set size method.
     *
     * @param size to set the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean append(StringList arg0) {
        if (arg0 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }
        if (!arg0.isEmpty()) {
            for (int i = 0; i <= arg0.size() - 1; i++) {
                this.append(arg0.get(i));
            }
        }
        return true;
    }

    @Override
    public boolean prepend(StringList arg0) {
        if (arg0 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }
        if (!arg0.isEmpty()) {
            for (int i = arg0.size() - 1; i >= 0; i--) {
                this.prepend(arg0.get(i));
            }
        }
        return true;
    }

    @Override
    public boolean add(int arg0, StringList arg1) {
        if (arg1 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg0 < 0 || arg0 > this.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        for (int i = 0; i < arg1.size(); i++) {
            this.add(arg0 + i, arg1.get(i));
        }

        return true;
    }

    @Override
    public boolean contains(int arg0, String arg1) {
        if (arg0 < 0 || arg0 >= this.size() || arg1 == null) {
            return false;
        }

        for (int i = arg0; i < this.size(); i++) {
            if (this.get(i).equals(arg1)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int indexOf(int arg0, String arg1) {
        if (arg0 < 0 || arg0 >= this.size() || arg1 == null) {
            return -1;
        }

        for (int i = arg0; i < this.size(); i++) {
            if (this.get(i).equals(arg1)) {
                return i;
            }
        }

        return -1;
    }
}
