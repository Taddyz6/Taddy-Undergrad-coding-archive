package cs1302.p2;

import cs1302.adt.FancyStringList;
import cs1302.adt.Node;
import cs1302.adt.StringList;

/**
 * This is class LinkedStringList make link that use.
 */
public class LinkedStringList extends BaseStringList {

    private Node front;
    private Node rear;

    /**
     * This is Link String List.
     */
    public LinkedStringList() {
        super();
        setFront(null);
        setRear(null);
    }

    /**
     * This is Linked String List for String list other.
     *
     * @param other the Stringlist to add.
     */
    public LinkedStringList(StringList other) {
        super();
        setFront(null);
        setRear(null);
        if (other != null) {
            this.add(0, other);
        }
    }

    @Override
    public boolean add(int arg0, String arg1) {
        // TODO Auto-generated method stub
        if (arg1 == null) {
            throw new NullPointerException("Cannot add null to the list");
        }

        if (arg1 == "") {
            throw new IllegalArgumentException("Cannot add empty string to the list");
        }

        if (arg0 < 0 || arg0 > this.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (arg0 == 0) {
            if (this.isEmpty()) {
                Node newNode = new Node(arg1, null);
                this.front = newNode;
                this.rear = newNode;
                this.setSize(1);
            } else {
                Node newNode = new Node(arg1, this.front);
                this.front = newNode;
                this.setSize(this.getSize() + 1);
            }
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

    }

    @Override
    public void clear() {
        this.setFront(null);
        this.setRear(null);
        this.setSize(0);
    }

    @Override
    public String get(int arg0) throws IndexOutOfBoundsException {
        if (arg0 >= this.getSize() || arg0 < 0) {
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
    }

    @Override
    public String remove(int arg0) {
        if (arg0 >= this.getSize() || arg0 < 0) {
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
            }
        }
    }

    /**
     * This index in bound class for give the index.
     *
     * @param index to show if not in the bound
     * @return index larger than 0 and index smaller this.getSize()
     */
    private boolean indexInBound(int index) {
        return (index >= 0 && index < this.getSize());
    }

    @Override public StringList slice(int arg0, int arg1) {
        if (arg0 < 0 || arg1 > this.getSize()) {
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
            for (int i = 1; i < arg1 - arg0 + 1; i++) {
                Node newNode_t = new Node(newNode.getItem(), null);
                if (resultList.isEmpty()) {
                    resultList.front = newNode_t;
                    resultList.rear = newNode_t;
                    resultList.setSize(1);
                } else {
                    resultList.rear.setNext(newNode_t);
                    resultList.rear = this.rear.getNext();
                    resultList.setSize(resultList.getSize() + 1);
                }
                newNode = newNode.getNext();
            }
            resultList.setSize(arg1 - arg0);
            return resultList;
        }
    }


    /**
     * This is node get front method.
     *
     * @return front to get front
     */
    public Node getFront() {
        return front;
    }

    /**
     * This is set front.
     *
     * @param front for set front
     */
    public void setFront(Node front) {
        this.front = front;
    }

    /**
     * This is node get rear method.
     *
     * @return rear to get rear
     */
    public Node getRear() {
        return rear;
    }

    /**
     * This is set rear.
     *
     * @param rear to set rear
     */
    public void setRear(Node rear) {
        this.rear = rear;
    }



    @Override
    public FancyStringList reverse() {
        LinkedStringList result = new LinkedStringList();

        for (int i = this.size() - 1; i >= 0; i--) {
            Node newNode = new Node(this.get(i), null);
            if (result.isEmpty()) {
                result.front = newNode;
                result.rear = newNode;
                result.setSize(1);
            } else {
                result.rear.setNext(newNode);
                result.rear = this.rear.getNext();
                result.setSize(this.getSize() + 1);
            }
        }
        return result;
    }

    @Override public FancyStringList slice(int arg0, int arg1, int arg2) {
        if (arg0 < 0 || arg1 > this.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        } else if (arg0 > arg1 || arg2 < 1) {
            throw new IndexOutOfBoundsException("Slice range out of bound!");
        } else {
            LinkedStringList resultList = new LinkedStringList();
            for (int i = arg0; i < arg1; i += arg2) {
                Node newNode = new Node(this.get(i), null);
                if (resultList.isEmpty()) {
                    resultList.front = newNode;
                    resultList.rear = newNode;
                    resultList.setSize(1);
                } else {
                    resultList.rear.setNext(newNode);
                    resultList.rear = resultList.rear.getNext();
                    resultList.setSize(resultList.getSize() + 1);
                }
            }
            return resultList;
        }
    }

}
