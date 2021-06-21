package excepcions;

public class ErrorInserir extends Exception{

    public ErrorInserir(){
        super("No s'ha pogut inserir l'element");
    }
}
