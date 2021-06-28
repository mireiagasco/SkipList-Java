package Exceptions;

public class InsertionError extends Exception{

    public InsertionError(){
        super("No s'ha pogut inserir l'element");
    }
}
