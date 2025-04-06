package cs1302.p2;

import cs1302.adt.FancyStringList;
import cs1302.adt.StringList;

/**
 * This is ArrayStringList.
 */
public class ArrayStringList extends BaseStringList {

    private String[] items;
    private int capacity;

    /**
     * This is ArrayStringList method.
     */
    public ArrayStringList() {
        super();
        setCapacity(100);
        setData(new String[this.getCapacity()]);
    }

    /**
     * This is ArrayStringList to super parent.
     *
     * @param other StringList
     */
    public ArrayStringList(StringList other) {
        super();
        if (other == null) {
            setCapacity(100);
            setData(new String[this.getCapacity()]);
        } else {
            setCapacity(100);
            setData(new String[this.getCapacity()]);
            this.add(0, other);
        }
    }

    @Override
    public boolean add(int arg0, String arg1) throws NullPointerException {
        if (arg1 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg1 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        if (arg0 < 0 || arg0 > this.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (this.getSize() < this.getCapacity()) {
            for (int i = this.getSize(); i > arg0; i--) {
                this.items[i] = this.items[i - 1];
            }
            this.items[arg0] = arg1;
            this.setSize(this.getSize() + 1);
        } else {
            String [] newData = new String[this.getCapacity() * 2];
            for (int i = 0; i < arg0; i++) {
                newData[i] = this.items[i];
            }
            newData[arg0] = arg1;
            for (int i = arg0 + 1; i < this.getCapacity() + 1; i++) {
                newData[i] = this.items[i - 1];
            }
            this.setData(newData);
            this.capacity *= 2;
            this.setSize(this.getSize() + 1);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.getSize(); i++) {
            this.items[i] = null;
        }
        this.setSize(0);
    }

    @Override
    public String get(int arg0) throws IndexOutOfBoundsException {
        if (arg0 >= this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            return this.getData()[arg0];
        }
    }

    @Override
    public String remove(int arg0) throws IndexOutOfBoundsException {
        if (arg0 >= this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            String result = this.getData()[arg0];
            for (int i = arg0; i < this.getSize(); i++) {
                this.items[i] = this.items[i + 1];
            }
            this.setSize(this.getSize() - 1);
            return result;
        }
    }

    /**
     * This is boolean indexInbound method.
     *
     * @param index to show index
     * @return index larger than zero and smaller than getSize
     */
    private boolean indexInBound(int index) {
        return (index >= 0 && index < this.getSize());
    }

    @Override public StringList slice(int arg0, int arg1)
        throws IndexOutOfBoundsException {
        if (arg0 < 0 || arg1 > this.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else if (arg0 > arg1) {
            throw new IndexOutOfBoundsException("Slice range out of bound!");
        } else {
            ArrayStringList resultList = new ArrayStringList();
            for (int i = arg0; i < arg1; i++) {
                resultList.getData()[i - arg0] = this.items[i];
            }
            resultList.setSize(arg1 - arg0);
            return resultList;
        }
    }

    /**
     * This is getData method.
     *
     * @return items in the data
     */
    public String[] getData() {
        return items;
    }

    /**
     * This is set data method.
     *
     * @param data String[]
     */
    public void setData(String[] data) {
        this.items = data;
    }

    /**
     * This is get capacity.
     *
     * @return capacity to get it
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This is set capacity method.
     *
     * @param capacity to give new capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public FancyStringList reverse() {
        ArrayStringList result = new ArrayStringList();

        for (int i = this.size() - 1; i >= 0; i--) {
            if (result.getSize() < result.getCapacity()) {
                result.items[result.getSize()] = this.get(i);
                result.setSize(result.getSize() + 1);
            } else {
                String[] newData = new String[result.getCapacity() * 2];
                for (int j = 0; j < result.getCapacity(); j++) {
                    newData[j] = result.items[j];
                }
                result.setData(newData);
                result.capacity *= 2;
                result.items[result.getSize()] = this.get(i);
                result.setSize(result.getSize() + 1);
            }
        }
        return result;
    } // reverse



    @Override
    public FancyStringList slice(int arg0, int arg1, int arg2) {
        if (!indexInBound(arg0) || !indexInBound(arg1)) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else if (arg0 > arg1 || arg2 < 0) {
            throw new IndexOutOfBoundsException("Slice range out of bound!");
        } else {
            ArrayStringList result = new ArrayStringList();
            for (int i = arg0; i <= arg1; i += arg2) {
                if (result.getSize() < result.getCapacity()) {
                    result.items[result.getSize()] = this.get(i);
                    result.setSize(result.getSize() + 1);
                } else {
                    String[] newData = new String[result.getCapacity() * 2];
                    for (int j = 0; j < result.getCapacity(); j++) {
                        newData[j] = result.items[j];
                    }
                    result.setData(newData);
                    result.capacity *= 2;
                    result.items[result.getSize()] = this.get(i);
                    result.setSize(result.getSize() + 1);
                }
            }
            return result;
        }
    }
}
