/*
 * This is a custom written queue data structure class.
 *
 * Note on Generics: This class uses Java Generics (<T>) backed by an Object array.
 * Primitive types (like int, char) cannot be used. Use their respective
 * java.lang equivalents (like Integer, Character).
 *
 * Reprogrammed to use ArrayList instead of array
 *      Object[] queue --> ArrayList<Object> queue = new ArrayList<>();
 *
 * This version now has poll(). A dequeue() that returns instead of removing it outright.
 *
 * @author Johann Haree Tolentino & Edriel Maullon
 * @date 05/24/2026
 * @modified 07/28/2026
 * @param <T> The type of elements held in this stack
 */

import java.util.ArrayList;

public class CustomQueue<T> {

    private final ArrayList<T> queue = new ArrayList<>();
    private final int capacity;

    /**
     * This function creates an array to act as the queue data structure.
     * Serves as the CreateQueue function of this class.
     * In an event where the parameter maxSize is left null, or set to less than 0, the queue size defaults to 10.
     * @param maxSize specified size of the queue array data type.
     *                It defaults to 10 if it is left null, or set to less than 0
     */
    public CustomQueue(Integer maxSize)
    {
        if (maxSize == null || maxSize <= 0)
        {
            this.capacity = 10;
        }
        else
        {
            this.capacity = maxSize;
        }
    }

    public CustomQueue() {
        this.capacity = 10;
    }

    /**
     * This function inserts a specified object into the tail of the queue
     * @param item The object to be added at the tail of the queue
     */
    public void enQueue(T item){

        if (isFull()) {
            System.out.println("Queue is full");
        } else {
            queue.add(item);
        }
    }

    /**
     * This function removes and returns the item at the front of the queue.
     * Follows a First-In-First-Out (FIFO) methodology
     * @return T - The object at the head of the queue
     */
    public T poll()
    {
        T object = null;

        if (isEmpty())
        {
            System.out.println("Queue is empty");
        }
        else {
            object = queue.removeFirst();
        }

        return object;
    }

    /**
     * Advances the head pointer and removes the front element.
     */
    public void deQueue()
    {
        if (isEmpty())
        {
            System.out.println("Queue is empty");
        }
        else {
            queue.removeFirst();
        }
    }

    /**
     * This function returns the first active element of the CustomQueue
     * @return T - specified object type
     */
    public T head()
    {
        T result = null;

        if (isEmpty())
        {
            System.out.println("Queue is empty");
        }
        else
        {
            result = queue.getFirst();
        }

        return result;
    }

    /**
     * This function returns the last active element of the CustomQueue.
     * Returns null if queue is empty or does not exist
     * @return T - specified object type
     */
    public T tail()
    {
        T result = null;

        if (isEmpty())
        {
            System.out.println("Queue is empty");
        }
        else
        {
            result = queue.getLast();
        }

        return result;
    }

    /**
     * This function returns true if the current count of CustomQueue is empty
     * @return boolean - True if count is 0, False if otherwise
     */
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    /**
     * This function returns true if the current count of CustomQueue is full
     * @return boolean - True if count is equal to set capacity, False if otherwise
     */
    public boolean isFull()
    {
        return queue.size() >= capacity;
    }
}
