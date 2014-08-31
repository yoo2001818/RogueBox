package kr.kkiro.roguebox.util;

import java.util.Random;

public class RandomProvider {

  private static Random random;
  
  public static Random getRandom() {
    if(random == null) random = new Random();
    return random;
  }
  
}
