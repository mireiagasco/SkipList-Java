package SkipList;

import java.util.Iterator;

public class SkipListIterator<T> implements Iterator {

    private SkipList<T> llista;

    public SkipListIterator(SkipList<T> llista){
        this.llista = llista;
    }


    @Override
    public boolean hasNext() {
        boolean seguent = false;

        if (llista.getCurrent().getNext().getInfo() != null){
            seguent = true;
        }

        return seguent;
    }

    @Override
    public Object next() {
        return llista.next();
    }
}
