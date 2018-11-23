package ca.ciccc.silverBullet.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {

  File file = null;
  FileReader fr = null;
  BufferedReader br = null;

  public char[][] getLevel(int levelSelected) {
    char[][] level = new char[9][9];

    try {
      if (levelSelected == 1) {
        file = new File("src/main/resources/levels/level1.txt");
      } else if (levelSelected == 2) {
        file = new File("src/main/resources/levels/level2.txt");
      } else {
        file = new File("src/main/resources/levels/level3.txt");
      }

      fr = new FileReader(file);
      br = new BufferedReader(fr);

      // Read the txt of the levels
      // and get if it is:
      // W -> Wall
      // S -> Space
      // E -> Water

      // Read the next line of the file
      String line = "";
      //int cont = 0;

      for (int i = 0; i < 9; i++) {
        line = br.readLine();
        for (int j = 0; j < 9; j++) {
          level[i][j] = line.charAt(j);
        }
      }
      /*while (line != null) {
        String[] values = line.split("");
        for (int i = 0; i < level.length; i++) {
          // Get the first position of the array
          char[cont][i] = values[i].charAt(0);
        }
        cont++;
        line = br.readLine();
        */
    } catch (FileNotFoundException filenotfound) {
      filenotfound.printStackTrace();
    } catch (IOException ioexception) {
      ioexception.printStackTrace();
    } finally {
      try {
        if (null != fr) {
          br.close();
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    return level;
  }
}
