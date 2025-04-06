
/************************************************************************************
 * @file LinHashMap.java
 *
 * @author  John Miller
 */

import java.io.*;
import java.lang.reflect.Array;
import static java.lang.System.out;
import java.util.*;

/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm.
 * A hash table is created that is an expandable array-list of buckets.
 */
public class LinHashMap <K, V>
       extends AbstractMap <K, V>
       implements Serializable, Cloneable, Map <K, V>
{
    /** The debug flag
     */
    private static final boolean DEBUG = true;

    /** The number of slots (for key-value pairs) per bucket.
     */
    private static final int SLOTS = 4;

    /** The threshold/upper bound on the load factor
     */
    private static final double THRESHOLD = 1.2;

    /** The class for type K.
     */
    private final Class <K> classK;

    /** The class for type V.
     */
    private final Class <V> classV;

    /********************************************************************************
     * This inner class defines buckets that are stored in the hash table.
     */
    class Bucket {
        int nKeys;
        K[] key;
        V[] value;
        Bucket next;
    
        @SuppressWarnings("unchecked")
        Bucket() {
            nKeys = 0;
            key = (K[]) Array.newInstance(classK, SLOTS);
            value = (V[]) Array.newInstance(classV, SLOTS);
            next = null;
        }
    
        V find(K k) {
            for (var j = 0; j < nKeys; j++) if (key[j].equals(k)) return value[j];
            return null;
        }
    
        void add(K k, V v) {
            if (nKeys < SLOTS) {
                key[nKeys] = k;
                value[nKeys] = v;
                nKeys++;
            } else {
                if (next == null) {
                    next = new Bucket(); 
                }
                next.add(k, v);
            }
        }
    
        void print() {
            out.print("[ ");
            for (var j = 0; j < nKeys; j++) out.print(key[j] + " . ");
            out.println("]");
        }
    }
    

    /** The list of buckets making up the hash table.
     */
    private final List <Bucket> hTable;

    /** The modulus for low resolution hashing
     */
    private int mod1;

    /** The modulus for high resolution hashing
     */
    private int mod2;

    /** The index of the next bucket to split.
     */
    private int isplit = 0;

    /** Counter for the number buckets accessed (for performance testing).
     */
    private int count = 0;

    /** The counter for the total number of keys in the LinHash Map
     */
    private int keyCount = 0;

    /********************************************************************************
     * Construct a hash table that uses Linear Hashing.
     * @param classK  the class for keys (K)
     * @param classV  the class for values (V)
     */
    public LinHashMap (Class <K> _classK, Class <V> _classV)
    {
        classK = _classK;
        classV = _classV;
        mod1   = 4;                                                          // initial size
        mod2   = 2 * mod1;
        hTable = new ArrayList <> ();
        for (var i = 0; i < mod1; i++) hTable.add (new Bucket ());
    } // constructor

    /********************************************************************************
     * Return a set containing all the entries as pairs of keys and values.
     * @return  the set view of the map
     */
    public Set <Map.Entry <K, V>> entrySet ()
    {
        var enSet = new HashSet <Map.Entry <K, V>> ();

        //  T O   B E   I M P L E M E N T E D
         // Iterate over each bucket in the hash table
        for (var bucket : hTable) {
            // Traverse each bucket in the linked list
            for (var b = bucket; b != null; b = b.next) {
                // Add each key-value pair in the current bucket to the entry set
                for (var j = 0; j < b.nKeys; j++) {
                    enSet.add(new AbstractMap.SimpleEntry<>(b.key[j], b.value[j]));
                }
            }
        }   
        return enSet;
    } // entrySet

    /********************************************************************************
     * Given the key, look up the value in the hash table.
     * @param key  the key used for look up
     * @return  the value associated with the key
     */
    @SuppressWarnings("unchecked")
    public V get (Object key)
    {
        var i = findRightBucket (key);
        return find ((K) key, hTable.get (i), true);
    } // get

    /********************************************************************************
     * Put the key-value pair in the hash table.  Split the 'isplit' bucket chain
     * when the load factor is exceeded.
     * @param key    the key to insert
     * @param value  the value to insert
     * @return  the old/previous value, null if none
     */
    public V put (K key, V value)
    {
    
        var i    = findRightBucket (key);                                    // hash to i-th bucket chain
        var bh   = hTable.get (i);                                           // start with home bucket
        var oldV = find (key, bh, false);                             // find old value associated with key
        out.println ("LinearHashMap.put: key = " + key + ", h() = " + i + ", value = " + value);
        
        if (oldV == null) {  // Only increment if this is a new key
            keyCount++;
            var lf = loadFactor ();                                  
            if (lf > THRESHOLD) split();
        }

        var b = bh;
        while (true)  {
            if (b.nKeys < SLOTS) { b.add (key, value); return oldV; }
            if (b.next != null) b = b.next; else break;
        } // while

        var bn = new Bucket ();
        bn.add (key, value);
        b.next = bn;                                                         // add new bucket at end of chain
        return oldV;
    } // put


    /********************************************************************************
     * Print the hash table.
     */
    public void print ()
    {
        out.println ("LinHashMap");
        out.println ("-------------------------------------------");

        for (var i = 0; i < hTable.size (); i++) {
            out.print ("Bucket [ " + i + " ] = ");
            var j = 0;
            for (var b = hTable.get (i); b != null; b = b.next) {
                if (j > 0) out.print (" \t\t --> ");
                b.print ();
                j++;
            } // for
        } // for

        out.println ("-------------------------------------------");
    } // print
 
    /********************************************************************************
     * Return the size (SLOTS * number of home buckets) of the hash table. 
     * @return  the size of the hash table
     */
    public int size ()
    {
        return SLOTS * (mod1 + isplit);
    } // size

    /********************************************************************************
     * Split bucket chain 'isplit' by creating a new bucket chain at the end of the
     * hash table and redistributing the keys according to the high resolution hash
     * function 'h2'.  Increment 'isplit'.  If current split phase is complete,
     * reset 'isplit' to zero, and update the hash functions.
     */
    private void split() {
        out.println("split: bucket chain " + isplit);
    
        // Add a new bucket at the end of the hash table for redistribution
        hTable.add(new Bucket());
        
        // Redistribute keys from the splitting bucket
        var oldBucket = hTable.get(isplit);
        var newBucket = new Bucket();
        Bucket currentBucket = oldBucket;
        Bucket previousBucket = null;
    
        while (currentBucket != null) {
            // Iterate over each key-value pair in the current bucket
            for (int i = 0; i < currentBucket.nKeys; i++) {
                K key = currentBucket.key[i];
                V value = currentBucket.value[i];
                
                // Determine the appropriate bucket based on the high-resolution hash function
                int newBucketIndex = h2(key);
                if (newBucketIndex == isplit) {
                    // Add to newBucket if it's destined to split bucket
                    if (newBucket.nKeys < SLOTS) {
                        newBucket.add(key, value);
                    } else {
                        // Chain a new bucket if `newBucket` is full
                        if (newBucket.next == null) newBucket.next = new Bucket();
                        newBucket.next.add(key, value);
                    }
                } else {
                    // Retain in original bucket if it stays in the same bucket chain
                    if (previousBucket == null) {
                        previousBucket = new Bucket();
                        hTable.set(isplit, previousBucket);
                    }
                    if (previousBucket.nKeys < SLOTS) {
                        previousBucket.add(key, value);
                    } else {
                        if (previousBucket.next == null) previousBucket.next = new Bucket();
                        previousBucket.next.add(key, value);
                    }
                }
            }
            currentBucket = currentBucket.next;
        }
        
        // Increase the `isplit` index to point to the next bucket to split.
        isplit++;
        if (isplit == mod1) {
            isplit = 0;
            mod1 = mod2;
            mod2 *= 2;
        }
    }
    

    /********************************************************************************
     * Return the load factor for the hash table.
     * @return  the load factor
     */
    private double loadFactor ()
    {
        return keyCount / (double) size ();
    } // loadFactor

    /********************************************************************************
     * Find the key in the bucket chain that starts with home bucket bh.
     * @param key     the key to find
     * @param bh      the given home bucket
     * @param by_get  whether 'find' is called from 'get' (performance monitored)
     * @return  the current value stored stored for the key
     */
    private V find (K key, Bucket bh, boolean by_get)
    {
        for (var b = bh; b != null; b = b.next) {
            if (by_get) count++;
            V result = b.find (key);
            if (result != null) return result;
        } // for
        return null;
    } // find

    private int findRightBucket(Object key){
        int bucket = h(key);
        if (bucket < isplit) {
            bucket = h2(key);
        }
        return bucket;
    }

    /********************************************************************************
     * Hash the key using the low resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h (Object key)
    {
        int ret = key.hashCode () % mod1;
        if(ret < 0) ret += mod1;
        return ret; 
    } // h

    /********************************************************************************
     * Hash the key using the high resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h2 (Object key)
    {
        int ret = key.hashCode () % mod2;
        if(ret < 0) ret += mod2;
        return ret; 
    } // h2

    /********************************************************************************
     * The main method used for testing.
     * @param  the command-line arguments (args [0] gives number of keys to insert)
     */
    public static void main (String [] args)
    {
        var totalKeys = 40;
        var RANDOMLY  = false;

        LinHashMap <Integer, Integer> ht = new LinHashMap <> (Integer.class, Integer.class);
        if (args.length == 1) totalKeys = Integer.valueOf (args [0]);

        if (RANDOMLY) {
            var rng = new Random ();
            for (var i = 1; i <= totalKeys; i += 2) ht.put (rng.nextInt (2 * totalKeys), i * i);
        } else {
            for (var i = 1; i <= totalKeys; i += 2) ht.put (i, i * i);
        } // if

        ht.print ();
        for (var i = 0; i <= totalKeys; i++) {
            out.println ("key = " + i + " value = " + ht.get (i));
        } // for
        out.println ("-------------------------------------------");
        out.println ("Average number of buckets accessed = " + ht.count / (double) totalKeys);
    } // main

} // LinHashMap class

