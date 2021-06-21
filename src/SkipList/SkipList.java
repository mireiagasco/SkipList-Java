package SkipList;


import excepcions.ErrorEsborrar;
import excepcions.ErrorInserir;
import excepcions.NoTrobat;
import interfícies.Llista;

import java.util.Iterator;
import java.util.Random;

/**
 * Classe que implementa una Skiplist
 * @param <T> tipus de dades que desarà la skiplist
 */
public class SkipList<T> implements Llista, Iterable{

    @Override
    public Iterator iterator() {
        return new IteradorSkipList(this);
    }

    /**
     * Classe que guarda un enter i un node (per retornar el nombre d'accessos i el node trobat en la cerca)
     */
    private static class EnterNode<T extends Comparable>{
        private int enter;
        private Node node;

        //Constructor
        public EnterNode(int enter, Node node){
            this.enter = enter;
            this.node = node;
        }
    }

    //Atributs
    private Node cap;   //extrem superior esquerre de la llista
    private Node cua;   //extrem superior dret de la llista
    private int numElem;    //llargada de la llista

    private int alcadaLlista;   //alçada de la llista
    public Random random = new Random();

    private Node primer;    //primer node de la planta baixa
    private Node actual;    //punter que ens marca la posició de la llista en la que ens trobem (per iterar)

    //Constructor
    public SkipList(){
        Crear();
    }

    //Getters

    public Node getCap() {
        return cap;
    }

    public Node getPrimer() {
        return primer;
    }

    public Node getActual() {
        return actual;
    }

    //Mètodes

    public Node seguent(){
        if (actual.getSeguent().getInfo() != null){
            actual = actual.getSeguent();
            return actual;
        }
        else {
            actual = primer;
            return null;
        }
    }

    @Override
    public void Crear() {
        cap = new Node(null);
        cua = new Node(null);
        actual = cap;
        primer = actual;
        cap.setSeguent(cua);
        cua.setPrevi(cap);
        numElem = 0;
        alcadaLlista = 0;
    }

    @Override
    public <T extends Comparable> void Inserir(T info) throws ErrorInserir {
        EnterNode aux = cerca(info); //obtenim la posició prèvia a la que ha d'ocupar l'element
        Node posicio = aux.node;     //obtenim la posició prèvia a la que ha d'ocupar l'element
        int maxNivells = (int)(Math.log(numElem+1)/Math.log(2));   //obtenim el màxim de nivells que pot tenir la nostra llista segons la seva llargada
        int nivell = 0; //nivell en el que toca col·locar l'element
        Node nodeInferior = null; //node inferior al que volem col·locar

        do {
            //comprovem que el nivell en el que volem afegir el node existeix
            if (alcadaLlista < nivell){
                crearNivellBuit();  //si no existeix, el creem
            }
            nodeInferior = colocarElement(info, posicio, nivell, nodeInferior); //col·loquem el node en la posició que toca
            nivell++;   //pugem un nivell
        } while (alcadaLlista < maxNivells && random.nextBoolean());    //mentre no ens passem del màxim de nivells i el booleà aleatori sigui cert

        numElem++;
    }

    /**
     * Mètode que afegeix un nivell buit a la llista
     */
    private void crearNivellBuit(){
        Node nouCap = new Node(null);   //cap del nou nivell i nou cap de la llista
        Node novaCua = new Node(null);  //cua del nou nivell i nova cua de la llista

        //actualitzem tots els punters
        cap.setSuperior(nouCap);
        cua.setSuperior(novaCua);
        nouCap.setSeguent(novaCua);
        novaCua.setPrevi(nouCap);
        nouCap.setInferior(cap);
        novaCua.setInferior(cua);

        //actualitzem la posició del cap i la cua
        cap = nouCap;
        cua = novaCua;

        alcadaLlista++;
    }

    /**
     * Mètode que col·loca un nou element a la llista en la posició indicada
     * @param info informació que ha de contenir el nou element
     * @param posicio posició prèvia a la que li correspon al nou element
     * @param nivell nivell de la llista on es vol col·locar el nou element
     * @param nodeInferior node inferior al que es vol col·locar (en el nivell zero serà null)
     * @param <T> tipus de la informació que contindrà el node
     * @return retorna el nou node
     */
    private <T extends Comparable> Node colocarElement(T info, Node posicio, int nivell, Node nodeInferior){
        Node n = posicio;   //n ens indica el node previ en el nivell que toqui

        //ens col·loquem en el node previ del nivell que toqui
        for (int i = 0; i < nivell; i++){
            while (n != null && n.getSuperior() == null){
                n = n.getPrevi();
            }
            if (n != null) n = n.getSuperior();
        }

        //creem el nou node
        Node nouNode = new Node(info);

        //col·loquem els punters
        nouNode.setPrevi(n);
        nouNode.setSeguent(n.getSeguent());
        n.getSeguent().setPrevi(nouNode);
        n.setSeguent(nouNode);

        //si estem en un nivell que no sigui el zero
        if (nodeInferior != null){
            nodeInferior.setSuperior(nouNode);
            nouNode.setInferior(nodeInferior);
        }

        return nouNode;
    }


    @Override
    public int Longitud() {
        return numElem;
    }

    @Override
    public <T extends Comparable> int Buscar(T info) throws NoTrobat{

        EnterNode aux = cerca(info);    //la cerca retorna un enter (nombre d'accessos) i un node (node trobat)

        if (aux.node.getSeguent() != null && aux.node.getSeguent().getInfo() != null &&aux.node.getSeguent().getInfo().compareTo(info) != 0){    //si el node trobat no és el que buscàvem, vol dir que no existeix
            throw new NoTrobat(aux.enter);
        }

        return aux.enter;
    }


    /**
     * Mètode que busca un element a la llista
     * @param data informació que es vol cercar
     * @param <T> tipus de la informació que es vol cercar
     * @return retorna un objecte EnterNode amb el node previ al que es buscava i el nombre d'accessos que han fet falta per trobar-lo
     */
    public <T extends Comparable> EnterNode cerca(T data) {
        Node posicio = cap;
        boolean trobat = false;
        int numAccessos = 0;

        while (!trobat){
            while (posicio.getSeguent().getInfo() != null && posicio.getSeguent().getInfo().compareTo(data) < 0){  //mentre la posició següent tingui un valor més petit que el que busquem
                posicio = posicio.getSeguent();
                numAccessos++;
            }
            if (posicio.getInferior() != null){  //si no podem avançar més, baixem un nivell
                posicio = posicio.getInferior();
                numAccessos++;
            }
            else trobat = true; //si no podem avançar ni baixar, hem acabat la cerca
        }

        EnterNode aux = new EnterNode(numAccessos, posicio);
        return aux;
    }

    /**
     * toString de la classe Skiplist
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkiplist:");

        Node inici = cap;

        Node nivellMesAlt = inici;
        int nivell = alcadaLlista;

        while (nivellMesAlt != null) {
            sb.append("\n\nNivell: " + nivell + "\n");

            while (inici != null) {
                sb.append(inici.getInfo());

                if (inici.getSeguent() != null) {
                    sb.append(" : ");
                }

                inici = inici.getSeguent();
            }

            nivellMesAlt = nivellMesAlt.getInferior();
            inici = nivellMesAlt;
            nivell--;
        }

        return sb.toString();
    }

    /**
     * Mètode que esborra l'element demanat (si el troba)
     * @param info dada que es vol eliminar
     */
    public <T extends Comparable>void Esborrar(T info) throws ErrorEsborrar{
        //busquem el node en la llista
        EnterNode aux = cerca(info);

        //si el node es troba en la llista
        if (aux.node.getSeguent() != null && aux.node.getSeguent().getInfo() != null && aux.node.getSeguent().getInfo().compareTo(info) == 0){
            eliminarNode(aux.node.getSeguent());
            numElem--;
        }
        else throw new ErrorEsborrar("L'element " + info + " no es troba en la llista i no s'ha pogut eliminar");
    }

    /**
     * Mètode que elimina un node de la llista
     * @param nodeAEliminar referència al node que es vol eliminar
     */
    private void eliminarNode(Node nodeAEliminar){

        //eliminem el node
        while (nodeAEliminar != null){

            //eliminem les referències al node
            nodeAEliminar.getPrevi().setSeguent(nodeAEliminar.getSeguent());
            nodeAEliminar.getSeguent().setPrevi(nodeAEliminar.getPrevi());
            nodeAEliminar.setInferior(null);
            nodeAEliminar = nodeAEliminar.getSuperior();

            if (nodeAEliminar != null){
                nodeAEliminar.getInferior().setSuperior(null);
            }
        }

        //comprovem si el nivell s'ha quedat buit
        if (cap.getSeguent() == cua && cap.getInferior() != null){
            cap = cap.getInferior();
            cap.setSuperior(null);
            cua = cua.getInferior();
            cua.setSuperior(null);

            alcadaLlista--;
        }
    }
}

