package cs1302.p2;

import cs1302.adt.StringList;

/**
 * This is ArrayStringList class.
 */
public class ArrayStringList extends BaseStringList {

    private String[] items;
    private int capacity;

    /**
     * This is method ArrayStringList to inherit base string list.
     */
    public ArrayStringList() {
        super();
        setCapacity(100);
        setData(new String[this.getCapacity()]);
    }

    /**
     * This is add method in the arraystringlist class.
     *
     * @return true
     * @param arg0 give the number
     * @param arg1 give the number
     * @throws NullPointerException canot add null to the list
     */
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
    } // add

    /**
     * This is clear method.
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.getSize(); i++) {
            this.items[i] = null;
        }
        this.setSize(0);
    }

    /**
     * This is get method.
     *
     * @param arg0 give the number
     * @throws IndexOutOfBoundsException index out of bound
     */
    @Override
    public String get(int arg0) throws IndexOutOfBoundsException {
        if (arg0 > this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            return this.getData()[arg0];
        }
    } // get

    /**
     * This is remove method.
     *
     * @param arg0 give the number
     * @throws IndexOutOfBoundsException index out of bound
     */
    @Override
    public String remove(int arg0) throws IndexOutOfBoundsException {
        if (arg0 > this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            String result = this.getData()[arg0];
            for (int i = arg0; i < this.getSize(); i++) {
                this.items[i] = this.items[i + 1];
            }
            this.setSize(this.getSize() - 1);
            return result;
        }
    } // remove

    /**
     * This is indexInBound method.
     *
     * @param index to show that in bound
     * @return index
     */
    private boolean indexInBound(int index) {
        return (index >= 0 && index < this.getSize());
    } // indexInBound

    /**
     * This method is for slice.
     *
     * @param arg0 give the number
     * @param arg1 give the number
     * @throws IndexOutOfBoundsException index out of bound
     */
    @Override
    public StringList slice(int arg0, int arg1) throws IndexOutOfBoundsException {
        if (!indexInBound(arg0) || !indexInBound(arg1)) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else if (arg0 > arg1) {
            throw new IndexOutOfBoundsException("Slice range out of bound!");
        } else {
            ArrayStringList resultList = new ArrayStringList();

            for (int i = arg0; i <= arg1; i++) {
                resultList.getData()[i - arg0] = this.items[i];
            }
            resultList.setSize(arg1 - arg0 + 1);
            return resultList;
        }

    } // slice

    /**
     * This method is for append.
     *
     * @param arg0 give the number
     * @throws NullPointerException cannot add null to the list
     */
    @Override
    public boolean append(String arg0) {
        if (arg0 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg0 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        if (this.getSize() < this.getCapacity()) {
            this.items[this.getSize()] = arg0;
            this.setSize(this.getSize() + 1);
        } else {
            String [] newData = new String[this.getCapacity() * 2];
            for (int i = 0; i < this.getCapacity(); i++) {
                newData[i] = this.items[i];
            }
            this.setData(newData);
            this.capacity *= 2;
            this.items[this.getSize()] = arg0;
            this.setSize(this.getSize() + 1);
        }
        return true;
    } // append

    /**
     * This method is for makeString.
     *
     * @param arg0 give the number
     * @param arg1 give the number
     * @param arg2 give the number
     */
    @Override
    public String makeString(String arg0, String arg1, String arg2) {
        String resultStr = "" + arg0;
        for (int i = 0; i < this.getSize() - 1; i++) {
            resultStr += (this.items[i] + arg1);
        }
        resultStr += (this.items[this.getSize() - 1] + arg2);
        return resultStr;
    } // makeString

    /**
     * This method is for prepend.
     *
     * @param arg0 give the number
     */
    @Override
    public boolean prepend(String arg0) {
        if (this.getSize() < this.getCapacity()) {
            for (int i = this.getSize(); i >= 1; i--) {
                this.items[i] = this.items[i - 1];
            }
            this.items[0] = arg0;
            this.setSize(this.getSize() + 1);
        } else {
            String [] newData = new String[this.getCapacity() * 2];
            for (int i = 0; i < this.getCapacity(); i++) {
                newData[i + 1] = this.items[i];
            }
            newData[0] = arg0;
            this.setData(newData);
            this.capacity *= 2;
            this.setSize(this.getSize() + 1);
        }
        return true;
    } // prepend

    /**
     * This is array to get data.
     *
     * @return items
     */
    public String[] getData() {
        return items;
    } // String[] getData

    /**
     * This is to set the array data.
     *
     * @param data so reset the data
     */
    public void setData(String[] data) {
        this.items = data;
    } // setData

    /**
     * This is to get capacity.
     *
     * @return capacity to get capacity
     */
    public int getCapacity() {
        return capacity;
    } // getCapacity

    /**
     * This is to set the new Capacity.
     *
     * @param capacity to reset capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
