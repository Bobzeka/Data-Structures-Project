/**
 * StringRadixTreeElement class extends AbstractRadixTreeElement for String
 *
 * @author Austin Leal
 * @version 1.0
 */
public class StringRadixTreeElement extends AbstractRadixTreeElement {
    private String data;

    /**
     * StringRadixTreeElement constructor
     *
     * @param  element     data String
     */
    public StringRadixTreeElement(String element) {
        data = element;
    }

    @Override
    public AbstractRadixTreeElement append(AbstractRadixTreeElement other) {
        if (other != null && other instanceof StringRadixTreeElement) {
            return new StringRadixTreeElement(data + other.getData());
        }
        return null;
    }

    @Override
    protected int comparePrefix(AbstractRadixTreeElement other) {
        int result = -1;
        if (other != null && other instanceof StringRadixTreeElement) {
            for (int i = 1; (i <= length()) && (i <= other.length()); i++) {
                if (subSet(0, i).equals(other.subSet(0, i))) {
                    result = i;
                } else {
                    i = length(); //additional terminating condition
                }
            }
        }
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o != null) {
            if (o instanceof StringRadixTreeElement) {
                return this.data.compareTo(((StringRadixTreeElement) o).data);
            } else {
                throw new ClassCastException();
            }
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof StringRadixTreeElement) {
            return data.equals(((StringRadixTreeElement) other).data);
        } else {
            return false;
        }
    }

    @Override
    protected String getData() {
        return data;
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
    public int length() {
        return data.length();
    }

    @Override
    public AbstractRadixTreeElement subSet(int start, int end) {
        if (data != null && start <= end) {
            return new StringRadixTreeElement(data.substring(start, end));
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return data;
    }
}