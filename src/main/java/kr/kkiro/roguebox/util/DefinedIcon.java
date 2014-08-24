package kr.kkiro.roguebox.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class DefinedIcon {

  protected static HashMap<Character, String> map = new HashMap<Character, String>();
  
  public static void clear() {
    map.clear();
  }

  public static int size() {
    return map.size();
  }

  public static String get(Object key) {
    return map.get(key);
  }

  public static boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  public static String put(char key, String value) {
    return map.put(key, value);
  }

  public static String remove(Object key) {
    return map.remove(key);
  }

  public static boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  public static Set<Character> keySet() {
    return map.keySet();
  }

  public static Set<Entry<Character, String>> entrySet() {
    return map.entrySet();
  }
  
  

}
