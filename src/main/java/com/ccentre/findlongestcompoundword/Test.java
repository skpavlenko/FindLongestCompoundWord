package com.ccentre.findlongestcompoundword;

import java.io.File;
import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {

        // file with compound words
        //File file = new File("src/test.txt");
        File file = new File("src/words.txt");
        FindLongestCompoundWord.find(file);

    }
}
