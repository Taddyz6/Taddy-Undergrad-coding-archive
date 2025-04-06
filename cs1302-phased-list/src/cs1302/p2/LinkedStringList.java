package cs1302.p2;

import cs1302.adt.Node;
import cs1302.adt.StringList;

/**
 * This is LinkedStringList class.
 */
public class LinkedStringList extends BaseStringList {

    private Node front;
    private Node rear;

    /**
     * This is LinkedStringList method that inherit.
     */
    public LinkedStringList() {
        super();
        setFront(null);
        setRear(null);
    } // LinkedStringList

    /**
     * This is add method.
     *
     * @param arg0 for add things
     * @param arg1 the second to add
     */
    @Override
    public boolean add(int arg0, String arg1) {
        if (arg1 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg1 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        if (arg0 < 0 || arg0 > this.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (arg0 == 0) {
            this.prepend(arg1);
        } else {
            Node newNode = this.front;
            Node theNode = new Node(arg1);
            int index = 0;
            while (index < arg0 - 1) {
                newNode = newNode.getNext();
                index++;
            }
            Node nextNode = newNode.getNext();
            newNode.setNext(theNode);
            theNode.setNext(nextNode);
            this.setSize(this.getSize() + 1);
        }
        return true;

    } // add

    /**
     * This is clear method.
     */
    @Override
    public void clear() {
        this.setFront(null);
        this.setRear(null);
        this.setSize(0);
    } // clear

    /**
     * This is get method.
     *
     * @param arg0 this is for give exception
     */
    @Override
    public String get(int arg0) throws IndexOutOfBoundsException {
        if (arg0 > this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            Node newNode = this.front;
            int index = 0;
            while (index < arg0) {
                newNode = newNode.getNext();
                index++;
            }
            return newNode.getItem();
        }
    } // get

    /**
     * This is remove method.
     *
     * @param arg0 is integer in remove
     */
    @Override
    public String remove(int arg0) {
        if (arg0 > this.getSize() || arg0 < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else {
            if (arg0 == 0) {
                String result = this.front.getItem();
                this.front = this.front.getNext();
                this.setSize(this.getSize() - 1);
                return result;
            } else {
                Node newNode = this.front;
                int index = 0;
                while (index < arg0 - 1) {
                    newNode = newNode.getNext();
                    index++;
                }
                String result = newNode.getNext().getItem();
                newNode.setNext(newNode.getNext().getNext());

                this.setSize(this.getSize() - 1);
                return result;
            } // else
        } // else
    } // remove

    /**
     * This is indexInBound method.
     *
     * @param index to give the number
     * @return index
     */
    private boolean indexInBound(int index) {
        return (index >= 0 && index < this.getSize());
    }

    /**
     * This is slice method.
     *
     * @param arg0 give the number
     * @param arg1 give the number
     * @return resultList
     */
    @Override
    public StringList slice(int arg0, int arg1) {
        if (!indexInBound(arg0) || !indexInBound(arg1)) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else if (arg0 > arg1) {
            throw new IndexOutOfBoundsException("Slice range out of bound!");
        } else {
            LinkedStringList resultList = new LinkedStringList();
            Node newNode = this.front;
            int index = 0;
            while (index < arg0) {
                newNode = newNode.getNext();
                index++;
            }

            for (int i = 1; i <= arg1 - arg0 + 1; i++) {
                resultList.append(newNode.getItem());
                newNode = newNode.getNext();
            }
            resultList.setSize(arg1 - arg0 + 1);
            return resultList;
        }
    }

    /**
     * This is append method.
     *
     * @param arg0 give the number
     * @return true
     */
    @Override
    public boolean append(String arg0) {
        Node newNode = new Node(arg0, null);
        if (this.isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
            this.setSize(1);
        } else {
            this.rear.setNext(newNode);
            this.rear = this.rear.getNext();
            this.setSize(this.getSize() + 1);
        }

        return true;
    }

    /**
     * This is makeString method.
     *
     * @param arg0 give the number
     * @param arg1 give the number
     * @param arg2 give the number
     * @return resultStr
     */
    @Override
    public String makeString(String arg0, String arg1, String arg2) {
        if (this.isEmpty()) {
            return arg0 + arg2;
        }
        String resultStr = "" + arg0;
        Node newNode = this.front;
        resultStr += (newNode.getItem());
        while (newNode.hasNext()) {
            resultStr += (arg1 + newNode.getNext().getItem());
            newNode = newNode.getNext();
        }
        resultStr += arg2;
        return resultStr;
    }

    /**
     * This is prepend method.
     *
     * @param arg0 give the number
     * @return true
     */
    @Override
    public boolean prepend(String arg0) {
        if (this.isEmpty()) {
            Node newNode = new Node(arg0, null);
            this.front = newNode;
            this.rear = newNode;
            this.setSize(1);
        } else {
            Node newNode = new Node(arg0, this.front);
            this.front = newNode;
            this.setSize(this.getSize() + 1);
        }

        return true;
    }

    /**
     * This method is for get front.
     *
     * @return front
     */
    public Node getFront() {
        return front;
    }

    /**
     * This method for set front.
     *
     * @param front is faces forward
     */
    public void setFront(Node front) {
        this.front = front;
    }

    /**
     * This method for get rear.
     *
     * @return rear
     */
    public Node getRear() {
        return rear;
    }

    /**
     * This method for set rear.
     *
     * @param rear is for back
     */
    public void setRear(Node rear) {
        this.rear = rear;
    }

} // LinkedStringList
