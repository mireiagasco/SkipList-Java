package interfícies;

import excepcions.ErrorEsborrar;
import excepcions.ErrorInserir;
import excepcions.NoTrobat;

public interface Llista {

    void Crear();               //Constructor que inicialitza la llista
    <T extends Comparable> void Inserir(T info) throws ErrorInserir;    //Insereix un element a la llista
                                                                        //Llença una excepció si no es pot realitzar l'operació
    int Longitud();                                                     //Retorna el nombre d'elements que té la llista
    <T extends Comparable> int Buscar(T info) throws NoTrobat;  //Comprova si un element està a la llista
                                                                //Retorna el cost de l'operació (nombre d'elements als que s'ha accedit)
                                                                //Llença una excepció si l'element no s'ha trobat (indicant el nombre d'elements accedits)
    <T extends Comparable> void Esborrar(T data) throws ErrorEsborrar, NoTrobat;
}
