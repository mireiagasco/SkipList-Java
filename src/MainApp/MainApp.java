package MainApp;

import Exceptions.ElementNotFound;
import Exceptions.InsertionError;
import SkipList.SkipList;

import java.util.Random;

public class MainApp {

    public static void main(String[] args){
        SkipList<Integer> skipList = new SkipList<Integer>();
        Random random = new Random();


        System.out.println("\n>>>Inserting 20 random numbers to the list...");
        for (int i = 0; i < 20; i++){

            try {
                skipList.insert(random.nextInt(50));
            }
            catch (InsertionError | ElementNotFound e){
                System.out.println(e);
            }
        }
        System.out.println("Done.  List generated: ");

        System.out.println(skipList);
        System.out.println("Length of the list: " + skipList.size());

        System.out.println("\n>>>Inserting numbers from 0 to 5 to the list...");
        for (int i = 0; i < 6; i++){

            try {
                skipList.insert(i);
            }
            catch (InsertionError | ElementNotFound e){
                System.out.println(e);
            }
        }
        System.out.println("Done.  List generated: ");

        System.out.println(skipList);

        System.out.println("\n>>>Searching...");
        System.out.println("2: " + skipList.search(2));
        System.out.println("5: " + skipList.search(5));
        System.out.println("105: " + skipList.search(105));
        System.out.println("87: " + skipList.search(87));
        System.out.println("\nDone.");

        System.out.println("\n>>>Deleting numbers from 0 to 5 to the list...");
        for (int i = 0; i < 6; i++){

            try {
                skipList.delete(i);
            }
            catch (ElementNotFound e){
                System.out.println(e);
            }
        }

        System.out.println(skipList);
    }
}
