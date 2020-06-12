package model;

public class Renames {

    String contains;
    String renameTo;

    public Renames(String contains, String renameTo) {
        this.contains = contains;
        this.renameTo = renameTo;
    }

    public String getContains() {
        return contains;
    }

    public String getRenameTo() {
        return renameTo;
    }
}
