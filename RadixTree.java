import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * class for Radix Trees of generic type that extends AbstractRadixTreeElement
 *
 * @param <V> generic that is instance of AbstractRadixTreeElement
 * @author Austin Leal
 * @version 1.0
 */
public class RadixTree<V extends AbstractRadixTreeElement>
        extends AbstractCollection<V> {
    private final RadixTreeNode<V> root;
    private int size;

    /**
     * emtry RadixTree constructor
     */
    public RadixTree() {
        root = new RadixTreeNode(null);
        size = 0;
    }

    @Override
    public Iterator<V> iterator() {
        return new RadixTreeIterator<V>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null && o instanceof AbstractRadixTreeElement) {
            return find((V) o) >= 0;
        }
        return false;
    }


    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        if (!isEmpty()) {
            toArrayHelper(result, (V) root.children.peek().data.subSet(0, 0),
                    root);
        }
        return result;
    }

    /**
     * Returns array of elements in order of occurance.
    */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a != null) {
            if (a.length < size) {
                int i = 0;
                for (Object element : toArray()) {
                    a[i] = (T) element;
                    i++;
                }
                if (a.length > size) {
                    for (int j = size; j < a.length; j++) {
                        a[j] = null;
                    }
                }
            } else {
                a = (T[]) toArray();
            }
            return a;
        }
        return null;
    }

    @Override
    public boolean add(V newElement) {
        boolean added = false;
        if (newElement != null) {
            if (size == 0) {
                RadixTreeNode newNode = new RadixTreeNode(newElement);
                root.children.add(newNode);
                newNode.locations.add(++size);
                return true;
            } else {
                RadixTreeNode parent = root;
                while (!added) {
                    boolean found = false;
                    for (RadixTreeNode current
                            : (LinkedList<RadixTreeNode>) parent.children) {
                        int comparison = current.data.comparePrefix(newElement);
                        if (comparison > 0) {
                            found = true;
                            if ((comparison) == current.data.length()) {
                                if (current.data.length()
                                        == newElement.length()) {
                                    //CASE1: WHOLE NEWELEMENT FOUND
                                    if (current.locations.isEmpty()
                                            && current.children.size() == 2) {
                                        for (RadixTreeNode node
                                                : (LinkedList<RadixTreeNode>)
                                                current.children) {
                                            if (node.data.equals(
                                                    node.data.subSet(0, 0))) {
                                                //CASE1.2 CURRENT IS EMPTY
                                                //SUBSET NODE
                                                //this occurs when one element
                                                //has only one addional child,
                                                //but is also an end, thus it is
                                                //not an end but instead
                                                //contains an empty string child
                                                //in accordance with the no only
                                                //child rule
                                                current = node;
                                            }
                                        }
                                    }
                                    current.locations.add(++size);
                                    return true;
                                } else {
                                    //CASE2: ALL OF CURRENT IN NEWELEMENT
                                    newElement = (V) newElement.subSet(
                                            comparison, newElement.length());
                                    parent = current;
                                    break;
                                }
                            } else {
                                //CASE3: PART OF CURRENT IN NEWELEMENT
                                RadixTreeNode newNode = new RadixTreeNode(
                                        newElement.subSet(comparison,
                                        newElement.length()));
                                RadixTreeNode newChild = new RadixTreeNode(
                                        current.data.subSet(
                                        comparison, current.data.length()));
                                current.data = current.data.subSet(0,
                                        comparison);
                                newChild.mergeChildren(current.children);
                                current.children.clear();
                                current.children.add(newChild);
                                current.children.add(newNode);
                                newChild.mergeLocations(current.locations);
                                current.locations.clear();
                                newNode.locations.add(++size);
                                return true;
                            }
                        }
                    }
                    if (!found) {
                        //CASE4: NO MATCH STILL
                        RadixTreeNode newNode = new RadixTreeNode(
                                newElement);
                        if (parent.children.isEmpty()) {
                            //CASE4.1: NO CHILDREN, parent must be end
                            //creates empty subset node
                            newEmpty(parent);
                        } else if (parent.locations.isEmpty()
                                && parent.children.size() == 2) {
                            for (RadixTreeNode node
                                    : (LinkedList<RadixTreeNode>)
                                    parent.children) {
                                if (node.data.equals(node.data.subSet(0, 0))) {
                                    //CASE4.2 EMPTY SUBSET NODE PRESENT
                                    //this occurs when one element has only one
                                    //addional child, but is also an end, thus
                                    //it is not an end but instead contains an
                                    //empty string child in accordance with the
                                    //no only child -rule- policy
                                    parent.children.remove(node);
                                    parent.mergeLocations(node.locations);
                                }
                            }
                        }
                        parent.children.add(newNode);
                        newNode.locations.add(++size);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * removes last occurance of element in tree
     */
    @Override
    public boolean remove(Object o) {
        if (o != null && o instanceof AbstractRadixTreeElement && !isEmpty()) {
            RadixTreeNode[] nodes = findHelper((V) o);
            if (nodes != null && !nodes[1].locations.isEmpty()) {
                shiftElements((Integer) nodes[1].locations.removeLast());
                size--;
                if (nodes[1].locations.size() == 0
                        && nodes[1].children.isEmpty()) { //node must be removed
                    nodes[0].children.remove(nodes[1]);
                    if (nodes[0] != root && nodes[0].children.size() == 1) {
                        if (!nodes[0].locations.isEmpty()) {
                            newEmpty(nodes[0]); //new empty subset node
                        } else { //append child node
                            RadixTreeNode oldNode =
                                    (RadixTreeNode)
                                    nodes[0].children.removeFirst();
                            nodes[0].mergeLocations(oldNode.locations);
                            nodes[0].data = nodes[0].data.append(oldNode.data);
                            nodes[0].mergeChildren(oldNode.children);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        boolean result = false;
        if (c != null) {
            for (V newElement : c) {
                if (!result) {
                    result = add(newElement);
                } else {
                    add(newElement);
                }
            }
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        if (c != null) {
            for (Object element : c) {
                if (!result) {
                    result = remove(element);
                } else {
                    remove(element);
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c != null) {
            for (V element : this) {
                if (!c.contains(element)) {
                    remove(element);
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    @Override
    public String toString() {
        String result = "";
        for (Object element : toArray()) {
            result += "[" + String.valueOf(element) + "], ";
        }
        return result.substring(0, result.length() - 2) + "\n";
    }





    /**
     * finds first location of element in tree
     *
     * @param element element to be found in tree
     * @return int location of first occurance of element in tree
     */
    public int find(V element) {
        RadixTreeNode[] nodes = findHelper(element);
        if (nodes != null && !nodes[1].locations.isEmpty()) {
            return (int) nodes[1].locations.getFirst();
        } else {
            return -1;
        }
    }

    /**
     * Finds all occurances of element in tree with locations starting at 1.
     *
     * @param element element to be found in tree
     * @return set of int locations of element in tree or null if not in tree
     */
    public List<Integer> findAll(V element) {
        RadixTreeNode[] nodes = findHelper(element);
        if (nodes != null && !nodes[1].locations.isEmpty()) {
            return nodes[1].locations;
        } else {
            return null;
        }
    }

    /**
     * Helper for find method
     *
     * @param newElement element to find
     */
    private RadixTreeNode[] findHelper(V newElement) {
        if (newElement != null && !isEmpty()) {
            boolean keepLooking = true;
            RadixTreeNode parent = root;
            while (keepLooking) {
                keepLooking = false;
                for (RadixTreeNode current
                        : (LinkedList<RadixTreeNode>) parent.children) {
                    int comparison = current.data.comparePrefix(newElement);
                    if (comparison > 0) {
                        if (comparison <= current.data.length()) {
                            if (current.data.length() == newElement.length()) {
                                //CASE1: WHOLE ELEMENT FOUND
                                if (!current.locations.isEmpty()) {
                                    //CASE1.1: CURRENT IS END
                                    RadixTreeNode[] result = {parent, current};
                                    return result;
                                } else if (current.children.size() == 2) {
                                    for (RadixTreeNode node
                                            : (LinkedList<RadixTreeNode>)
                                            current.children) {
                                        if (node.data.equals(
                                                node.data.subSet(0, 0))) {
                                            //CASE1.2 CURRENT HAS EMPTY SUBSET
                                            //this occurs when one element has
                                            //only one addional child, but is
                                            //also an end, thus it is not an end
                                            //but instead contains an empty
                                            //string child in accordance with
                                            //the no only child rule
                                            RadixTreeNode[] result = {current,
                                                node};
                                            return result;
                                        }
                                    }
                                }
                            } else {
                                //CASE2: ALL OF PARENT IN CHILD
                                keepLooking = true;
                                newElement = (V) newElement.subSet(comparison,
                                        newElement.length());
                                parent = current;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }

    /**
     * shifts all elements in tree after start by minus one
     *
     * @param  start         first location to start
     */
    private void shiftElements(int start) {
        if (start < size) {
            shiftElementsHelper(start, root);
        }
    }

    /**
     * Helpert to shiftElements method
     *
     * @param start                     location to start
     * @param node                     current start node
     */
    private void shiftElementsHelper(int start, RadixTreeNode node) {
        for (RadixTreeNode current : (LinkedList<RadixTreeNode>) node.children)
        {
            shiftElementsHelper(start, current);
        }
        if (!node.locations.isEmpty()) {
            int first = (Integer) node.locations.removeFirst();
            if (first > start) {
                first--;
            }
            node.locations.add(first);
            while (first != (Integer) node.locations.getFirst()) {
                int current = (Integer) node.locations.removeFirst();
                if (current > start) {
                    current--;
                }
                node.locations.add(current);
            }
        }
    }

    /**
     * Helper for toArray
     *
     * @param array               array to populate
     * @param element             current element to append data
     * @param node               current start node
     */
    private void toArrayHelper(Object[] array, V element, RadixTreeNode node) {
        for (RadixTreeNode current : (LinkedList<RadixTreeNode>) node.children)
        {
            toArrayHelper(array, (V) element.append(current.data), current);
        }
        if (!node.locations.isEmpty()) {
            for (Integer index : (LinkedList<Integer>) node.locations) {
                array[index - 1] = element;
            }
        }
    }

    /**
     * Provides new empty node for add method
     *
     * @param  parent   parent to add empty node to
     */
    private void newEmpty(RadixTreeNode parent) {
        RadixTreeNode newEmpty = new RadixTreeNode(parent.data.subSet(0, 0));
        newEmpty.mergeLocations(parent.locations);
        parent.locations.clear();
        parent.children.add(newEmpty);
    }









    /**
     * inner node class for RadixTree with generic of AbstractRadixTreeElement
     */
    private class RadixTreeNode<V extends AbstractRadixTreeElement> implements
            Comparable {
        private V data;
        private LinkedList<RadixTreeNode> children;
        private LinkedList<Integer> locations;

        /**
         * RadixTreeNode constructor
         *
         * @param element data for node
         */
        private RadixTreeNode(V element) {
            data = element;
            children = new LinkedList<>();
            locations = new LinkedList<>();
        }

        @Override
        public int hashCode() {
            if (data != null) {
                return data.hashCode();
            } else {
                return -1;
            }
        }

        @Override
        public boolean equals(Object other) {
            return other != null && other instanceof RadixTreeNode
                    && data.equals(((RadixTreeNode) other).data);
        }

        @Override
        public int compareTo(Object o) {
            if (o != null) {
                if (o instanceof RadixTreeNode) {
                    return this.data.compareTo(((RadixTreeNode) o).data);
                } else {
                    throw new ClassCastException();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * adds new locations to locations LinkedList
         *
         * @param  newLocations locations to be added
         */
        private void mergeLocations(LinkedList<Integer> newLocations) {
            for (Integer newLocation : newLocations) {
                locations.add(newLocation);
            }
        }

        /**
         * adds new Children to children LinkedList
         *
         * @param  newChildren Children to be added
         */
        private void mergeChildren(LinkedList<RadixTreeNode> newChildren) {
            for (RadixTreeNode newChild : newChildren) {
                children.add(newChild);
            }
        }
    }

    /**
     * Inner node class for Iterator
     */
    private class RadixTreeIterator<V extends AbstractRadixTreeElement>
            implements Iterator<V> {
        private int i;
        private Object[] array;

        /**
         * RadixTreeIterator constructor
         */
        public RadixTreeIterator() {
            i = 0;
            array = toArray();
        }

        /**
         * If has next
         *
         * @return hasNext() boolean
         */
        public boolean hasNext() {
            if (array != null && i < array.length) {
                return true;
            } else {
                i = 0;
                return false;
            }
        }

        /**
         * Provides next element if available
         *
         * @return next element
         */
        public V next() {
            if (hasNext()) {
                return (V) array[i++];
            }
            return null;
        }
    }
}