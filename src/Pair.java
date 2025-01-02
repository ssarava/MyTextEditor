import java.util.HashSet;

public class Pair<T extends Comparable<T>> {
    
    private T start, end;

    public static void main(String[] args) {
        Pair<Integer> p1 = new Pair<>(3,8);
        Pair<Integer> p2 = new Pair<>(5, 10);

        HashSet<Pair<Integer>> set = new HashSet<>();
        set.add(p1); set.add(p2);
    }
    
    public Pair(T v1, T v2) {
        this.start = v1;
        this.end = v2;
    }

    public T getStart() {
        return this.start;
    }

    public T getEnd() {
        return this.end;
    }

    public void setStart(T startIn) {
        this.start = startIn;
    }

    public void setEnd(T endIn) {
        this.end = endIn;
    }

    public boolean valueInRange(T value) {
        return this.start.compareTo(value) <= 0 && value.compareTo(this.end) <= 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Pair)) {
            return false;
        }
        Pair<T> pair = (Pair<T>) other;
        return this.start.equals(pair.start) && this.end.equals(pair.end);
    }

    @Override
    public String toString() {
        return "(" + this.start + ", " + this.end + ")";
    }
}
