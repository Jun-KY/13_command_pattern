package document;

public class Selection {
    private int start;
    private int end;

    public Selection(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }
    public int getLength() { return end - start; }

    public boolean isEmpty() {
        return start == end;
    }

    public void setSelection(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    @Override
    public String toString() {
        return String.format("Selection[%d-%d]", start, end);
    }
}