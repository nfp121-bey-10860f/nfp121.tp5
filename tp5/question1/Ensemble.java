package question1;

import java.util.*;

public class Ensemble<T> extends AbstractSet<T> {

    protected java.util.Vector<T> table = new java.util.Vector<T>();

    public int size() {
        return table.size();
    }

    public Iterator<T> iterator() {
        return table.iterator();
    }

    public boolean add(T t) {
        table.add(t);
        return table.contains(t);
    }

    public Ensemble<T> union(Ensemble<? extends T> e) {
        Ensemble<T> resultat = new Ensemble<T>(); 
        resultat.addAll(table);
        resultat.addAll(e);
        
        Ensemble<T> tab1 = new Ensemble<T>();
        tab1.addAll(table);
        tab1.retainAll(e);
        
        resultat.removeAll(tab1);
        return resultat;
    }

    public Ensemble<T> inter(Ensemble<? extends T> e) {
        Ensemble<T> resultat = new Ensemble<T>();
        resultat.addAll(table);
        resultat.retainAll(e);
        return resultat;
    }

    public Ensemble<T> diff(Ensemble<? extends T> e) {
        Ensemble<T> resultat = new Ensemble<T>();
        resultat.addAll(table);
        resultat.removeAll(e);
        return resultat;
    }

    Ensemble<T> diffSym(Ensemble<? extends T> e) {
        Ensemble<T> resultat = new Ensemble<T>();
        resultat.addAll(union(e));
        resultat.removeAll(inter(e));
        return resultat;
    }
}