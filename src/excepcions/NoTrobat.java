package excepcions;

public class NoTrobat extends Exception{

    private int accessos;

    public NoTrobat(int num){
        super("S'han realitzat " + num + " acessos");
        accessos = num;
    }

    public int getAccessos() {
        return accessos;
    }
}
