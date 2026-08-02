/*
 * This is a custom written stack data structure class.
 *
 * Note on Generics: This class uses Java Generics (<T>) backed by an Object array.
 * Primitive types (like int, char) cannot be used. Use their respective
 * java.lang equivalents (like Integer, Character).
 *
 * Reprogrammed to use ArrayList instead of array
 *      Object[] stack --> ArrayList<Object> stack = new ArrayList<>();
 *
 * @author Johann Haree Tolentino && Edriel Maullon
 * @date 05/24/2026
 * @modified 06/16/2026
 * @param <T> The type of elements held in this stack
 */

import java.util.ArrayList;

public class CustomStack<T> {
    private final ArrayList<T> stack = new ArrayList<>();
    private final int capacity;

    /**
     * This function creates an array to act as the stack data structure.
     * Serves as the CreateStack function of this class.
     * In an event where the parameter maxSize is left null, or set to less than 0, the queue size defaults to 10.
     * @param maxSize specified size of the stack array data type.
     *                It defaults to 10 if it is left null, or set to less than 0
     */
    public CustomStack(Integer maxSize)
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

    public CustomStack()
    {
        this.capacity = 10;
    }

    /**
     * This function returns the last active element of the stack.
     * Returns null if stack is empty or does not exist
     * @return topmost element of type object
     */
    public T top()
    {
        T result = null;

        if (isEmpty())
        {
            System.out.println("Stack is empty");
        }
        else
        {
            result = stack.getLast();
        }

        return result;
    }

    /**
     * This function returns and removes the last active element of the stack.
     * Returns null if stack is empty
     * @return topmost element of type object
     */
    public T popItem()
    {
        T result = null;

        if (isEmpty())
        {
            System.out.println("Stack is empty");
        }
        else
        {
            result = stack.removeLast();
        }

        return result;
    }

    /**
     * This function removes the last active element of the stack.
     */
    public void pop()
    {
        if (isEmpty())
        {
            System.out.println("Stack is empty");
        }
        else stack.removeLast();
    }

    /**
     * This function adds a specified object type onto the top of the custom stack
     * @param item object type
     */
    public void push(T item)
    {
        stack.add(item);
    }

    /**
     * This function returns true if the current count of custom stack is empty, false if otherwise
     * @return boolean - True if count is 0, False if otherwise
     */
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    /**
     * This function returns true if the current count of custom stack is full, false if otherwise
     * @return boolean - True if count is equal to set capacity, False if otherwise
     */
    public boolean isFull() {
        return stack.size() == capacity;
    }

}
