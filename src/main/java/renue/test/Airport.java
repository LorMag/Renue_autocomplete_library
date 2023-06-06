public class Airport implements Comparable<Airport>{
    private String name;
    private long pointer;

    public Airport() {}

    public Airport(String name, long pointer) {
        this.name = name;
        this.pointer = pointer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPointer() {
        return pointer;
    }

    public void setPointer(long pointer) {
        this.pointer = pointer;
    }

    @Override
    public int compareTo(Airport o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.getName());
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", pointer=" + pointer +
                '}';
    }
}
