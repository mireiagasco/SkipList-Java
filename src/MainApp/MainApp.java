package MainApp;

import excepcions.ErrorInserir;
import SkipList.SkipList;

import java.util.Random;

public class MainApp {

    public static void main(String[] args){
        SkipList<Integer> skipList = new SkipList<Integer>();
        Random random = new Random();

        for (int i = 0; i < 30; i++){

            try {
                skipList.Inserir(random.nextInt(50));
            }
            catch (ErrorInserir e){
                System.out.println(e);
            }
        }

        System.out.println(skipList);
    }
}
