package ca.ciccc.silverBullet.utils;

import ca.ciccc.silverBullet.SilverBulletApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LevelFileReadUtil {

  private static final String FILE_PATH = "levels/level%s.txt";

  private LevelFileReadUtil(){}

  public static char[][] getLevelMapAry(int levelSelected) {
    char[][] level = new char[9][9];
    try (InputStream is = SilverBulletApp.class.getResourceAsStream(String.format(FILE_PATH, levelSelected));
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
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
