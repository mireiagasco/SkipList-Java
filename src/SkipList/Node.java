package SkipList;

/**
 * Classe Node de la Skiplist
 * @param <T> tipus de dada de la llista (ha de ser comparable)
 */
class Node<T extends Comparable>{
    private T info;
    private Node superior;
    private Node inferior;
    private Node seguent;
    private Node previ;

    //Constructor
    public Node(T info){
        this.info = info;
        this.superior = null;
        this.inferior = null;
        this.seguent = null;
        this.previ = null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Node getSuperior() {
        return superior;
    }

    public void setSuperior(Node superior) {
        this.superior = superior;
    }

    public Node getInferior() {
        return inferior;
    }

    public void setInferior(Node inferior) {
        this.inferior = inferior;
    }

    public Node getSeguent() {
        return seguent;
    }

    public void setSeguent(Node seguent) {
        this.seguent = seguent;
    }

    public Node getPrevi() {
        return previ;
    }

    public void setPrevi(Node previ) {
        this.previ = previ;
    }

    public String toString(){
        return ("Dada: " + this.info);
    }
}
