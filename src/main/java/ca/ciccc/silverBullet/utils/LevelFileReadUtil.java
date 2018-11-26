package ca.ciccc.silverBullet.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelFileReadUtil {

  private static final String FILE_PATH = "src/main/resources/levels/level%s.txt";

  private LevelFileReadUtil(){}

  public static char[][] getLevelMapAry(int levelSelected) {
    char[][] level = new char[9][9];

    File file = new File(String.format(FILE_PATH, levelSelected));
    try (FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr)) {
      // Read the txt of the levels
      // and get if it is:
      // W -> Wall
      // S -> Space
      // E -> Water
      // H -> Hole

      // Read the next line of the file
      String line;
      for (int i = 0; i < 9; i++) {
        line = br.readLine();
        for (int j = 0; j < 9; j++) {
          level[i][j] = line.charAt(j);
        }
      }
    } catch (IOException ioE) {
      ioE.printStackTrace();
      throw new RuntimeException(ioE);
    }
    return level;
  }
}
