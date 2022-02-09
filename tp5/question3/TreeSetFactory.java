package question3;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetFactory<T> implements Factory<Set>{
    public TreeSet<T> create(){
        return new TreeSet<T>();
    }
}