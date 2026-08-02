import java.util.ArrayList;

public class CustomPriorityQueue {

    private final ArrayList<Astar.Node> heap;

    public CustomPriorityQueue()
    {
        this.heap = new ArrayList<>();
    }

    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    public int getSize()
    {
        return heap.size();
    }

    /**
     * Inserts a new node into the priority queue and re-balances the heap
     * upwards to preserve the min-heap property.
     *
     * @param node The Astar.Node to be added.
     */
    public void add(Astar.Node node)
    {
        heap.add(node);
        siftUp(getSize() - 1);
    }

    /**
     * Removes and returns the highest priority node (the root element with
     * the lowest fCost). Re-balances the heap from the root downward.
     *
     * @return The Node with the smallest priority value, or null if empty.
     */
    public Astar.Node poll()
    {
        Astar.Node node;

        if (isEmpty())
        {
            node = null;
        }
        else
        {
            Astar.Node root = heap.getFirst();
            Astar.Node lastNode = heap.removeLast();

            if (!heap.isEmpty())
            {
                heap.set(0, lastNode);
                siftDown(0);
            }

            node = root;
        }

        return node;
    }

    /**
     * Returns the ArrayList of the heap nodes.
     *
     * @return The raw ArrayList containing the nodes.
     */
    public ArrayList<Astar.Node> getNodes()
    {
        return heap;
    }

    /**
     * Searches for and removes a specific target node from the heap.
     * Replaces the target with the last element and repairs the min-heap
     * structure by re-sifting in both directions.
     *
     * @param target The node to match and remove using equals().
     * @return true if the node was found and removed, false if otherwise.
     */
    public boolean remove(Astar.Node target)
    {
        int index = -1;
        boolean found = false;

        for (int i = 0; i < heap.size() && !found; i++) {
            if (heap.get(i).equals(target)) {
                index = i;
                found = true;
            }
        }

        if (found) {
            Astar.Node lastNode = heap.removeLast();
            if (index < heap.size()) {
                heap.set(index, lastNode);
                siftUp(index);
                siftDown(index);
            }
        }
        return found;
    }

    /**
     * Restores min-heap property by moving an element down the tree
     * until it is smaller than or equal to both of its children.
     *
     * @param index The array index of the node to sift down.
     */
    private void siftDown(int index)
    {
        boolean swapped = true;

        int size = heap.size();
        while (index * 2 + 1 < size && swapped) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = leftChild;

            if (rightChild < size && heap.get(rightChild).compareTo(heap.get(leftChild)) < 0) {
                smallest = rightChild;
            }

            if (heap.get(smallest).compareTo(heap.get(index)) < 0) {
                swap(index, smallest);
                index = smallest;
            } else {
                swapped = false;
            }
        }
    }

    /**
     * Restores the min-heap property by moving an element up the tree
     * until it is greater than or equal to its parent node.
     *
     * @param index The array index of the node to sift up.
     */
    private void siftUp(int index)
    {
        boolean swapped = true;

        while (index > 0 && swapped) {
            int parentIndex = (index - 1) / 2;

            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            }
            else {
                swapped = false;
            }
        }
    }

    /**
     * Swaps two nodes in the heap array.
     *
     * @param i The index of the first node.
     * @param j The index of the second node.
     */
    private void swap(int i, int j) {
        Astar.Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
