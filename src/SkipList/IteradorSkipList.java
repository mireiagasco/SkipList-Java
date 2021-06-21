package SkipList;

import java.util.Iterator;

public class IteradorSkipList<T> implements Iterator {

    private SkipList<T> llista;

    public IteradorSkipList(SkipList<T> llista){
        this.llista = llista;
    }


    @Override
    public boolean hasNext() {
        boolean seguent = false;

        if (llista.getActual().getSeguent().getInfo() != null){
            seguent = true;
        }

        return seguent;
    }

    @Override
    public Object next() {
        return llista.seguent();
    }
}
