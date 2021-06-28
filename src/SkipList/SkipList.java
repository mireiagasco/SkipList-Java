package SkipList;

import Exceptions.ElementNotFound;

import java.util.Iterator;
import java.util.Random;

/**
 * Skip-List Implementation with generics
 * @author Mireia Gasco Agorreta
 * @version 2.0
 *
 * @param <T> type of objects that will be stored
 */
public class SkipList<T> implements Iterable{

    @Override
    public Iterator iterator() {
        return new SkipListIterator(this);
    }


    /**
     * Class for the SkipList nodes
     * @param <T> type of objects (must be comparable)
     */
    public class Node<T extends Comparable>{

        //Attributes

        private T info;         //node data
        private Node above;     //reference to the node above
        private Node below;     //reference to the node below
        private Node next;      //reference to the node on the right
        private Node previous;  //reference to the node on the left

        //Constructor
        public Node(T info){
            this.info = info;
            this.above = null;
            this.below = null;
            this.next = null;
            this.previous = null;
        }

        //Getters
        public T getInfo() {
            return info;
        }

        public Node getAbove() {
            return above;
        }

        public Node getBelow() {
            return below;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return previous;
        }
    }

    //Attributes
    private Node head;      //top left element of the list
    private Node tail;      //top right element of the list
    private int numElem;    //length of the list
    private int listHeight; //height of the list

    private Random random = new Random();    //random generator to toss the coin

    private Node first;    //first node of the bottom list
    private Node current;  //reference to the current node (when iterating)

    //Constructor
    public SkipList(){
        head = new Node(null);
        tail = new Node(null);
        current = head;
        first = current;
        head.next = tail;
        tail.previous = head;
        numElem = 0;
        listHeight = 0;
    }

    //Getters

    public Node getCurrent() {
        return current;
    }

    public int size() {
        return numElem;
    }

    //Methods

    public Node next(){
        if (current.next.info != null){
            current = current.next;
            return current;
        }
        else {
            current = first;
            return null;
        }
    }

    /**
     * Insertion method.  Adds the new element to its position.
     * @param info element to be added to the list.
     */
    public <T extends Comparable> void insert(T info) throws ElementNotFound {

        Node position = lookFor(info);    //position in which the element must be placed

        int maxLevel = (int)(Math.log(numElem+1)/Math.log(2));   //maximum number of levels that the list can have according to its length

        int level = 0;                                           //level in which the element must be positioned
        Node nodeBelow = null;                                //node above which the element must be positioned

        do {
            if (listHeight < level){
                addEmptyLevel();  //create a level in case it doesn't exist
            }
            nodeBelow = placeElement(info, position, level, nodeBelow); //col·loquem el node en la posició que toca
            level++;    //go to the next level
        } while (listHeight < maxLevel && random.nextBoolean());    //toss the coin while we can still increase the level

        numElem++;
    }

    /**
     * Method that adds a new empty level to the skiplist
     */
    private void addEmptyLevel(){
        Node newHead = new Node(null);   //head of the new level and new head of the list
        Node newTail = new Node(null);  //tail of the new level and new tail of the list

        //update all references
        head.above = newHead;
        tail.above = newTail;
        newHead.next = newTail;
        newTail.previous = newHead;
        newHead.below = head;
        newTail.below = tail;

        //update head and tail positions
        head = newHead;
        tail = newTail;

        listHeight++;
    }

    /**
     * Method that places the element in its position
     * @param info data of the new element
     * @param position previous node to the position in which the new node must be placed
     * @param level level of the list in which the element must be placed
     * @param nodeBelow node above which the element must be placed
     * @return the new node
     */
    private <T extends Comparable> Node placeElement(T info, Node position, int level, Node nodeBelow){
        Node n = position;   //n wil indicate the previous node to the position in which the element must be placed

        //get n
        for (int i = 0; i < level; i++){
            while (n != null && n.getAbove() == null){
                n = n.previous;
            }
            if (n != null) n = n.getAbove();
        }

        //create the new node
        Node newNode = new Node(info);

        //position the references
        newNode.previous = n;
        newNode.next = n.next;
        n.next.previous = newNode;
        n.next = newNode;

        //if we are not in the base level
        if (nodeBelow != null){
            nodeBelow.above = newNode;
            newNode.below = nodeBelow;
        }

        return newNode;
    }

    /**
     * Search method.
     * @param data data to be searched
     * @return true if the element is in the list, false if it is not.
     * @throws ElementNotFound if the element is not found
     */
    public <T extends Comparable> boolean search(T data){

        boolean found = false;

        Node position = lookFor(data);    //la cerca retorna un enter (nombre d'accessos) i un node (node trobat)

        if ((position.next != null) && (position.next.info != null)){    //if the position found is not the node we were looking for
            if (position.next.info.compareTo(data) == 0){
                found = true;
            }
        }

        return found;
    }

    /**
     * Method that looks for an element on the list.
     * @param data element to be searched for
     * @return the object found
     */
    private <T extends Comparable> Node lookFor(T data){
        Node position = head;
        boolean found = false;

        while (!found){
            while (position.next.info != null && position.next.info.compareTo(data) < 0){  //while we don't get passed the element
                position = position.next;
            }
            if (position.getBelow() != null){  //go to the level below if it is possible
                position = position.getBelow();
            }
            else found = true; //if we cannot go right nor below, we have finished our search
        }

        return position;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkiplist:");

        Node start = head;

        Node higherLevel = start;
        int level = listHeight;

        while (higherLevel != null) {
            sb.append("\n\nLevel: " + level + "\n");

            while (start != null) {
                sb.append(start.info);

                if (start.next != null) {
                    sb.append(" : ");
                }

                start = start.next;
            }

            higherLevel = higherLevel.below;
            start = higherLevel;
            level--;
        }

        return sb.toString();
    }

    /**
     * Delete method.  Deletes the element indicated if it is on the list.
     * @param info data to be deleted.
     * @throws ElementNotFound if the element is not in the list.
     */
    public <T extends Comparable>void delete(T info) throws ElementNotFound{

        if (search(info)){
            Node aux = lookFor(info); //position of the element
            deleteNode(aux.next);
            numElem--;
        }
        else throw new ElementNotFound();

    }

    /**
     * Method that deletes the node in the specified position
     * @param nodeToBeDeleted node to be deleted
     */
    private void deleteNode(Node nodeToBeDeleted){

        //delete all the node in all levels
        while (nodeToBeDeleted != null){

            //eliminem les referències al node
            nodeToBeDeleted.previous.next = nodeToBeDeleted.next;
            nodeToBeDeleted.next.previous = nodeToBeDeleted.next;
            nodeToBeDeleted.below = null;
            nodeToBeDeleted = nodeToBeDeleted.above;

            if (nodeToBeDeleted != null){
                nodeToBeDeleted.below.above = null;
            }
        }

        //if the top level is empty, delete it
        if (head.next == tail && head.getBelow() != null){
            head = head.below;
            head.above = null;
            tail = tail.below;
            tail.above = null;

            listHeight--;
        }
    }
}

