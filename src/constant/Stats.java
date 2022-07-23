package constant;

/*
 * user defined class to store player info and score
 * */
public class Stats implements Comparable<Stats>{
    private String name;
    private int time;

    public Stats (String name, int time) {
        super();
        this.name = name;
        this.time = time;
    }

    @Override
    public int compareTo(Stats s) {
        return this.time - s.time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public int getTime() {
        return this.time;
    }
}
