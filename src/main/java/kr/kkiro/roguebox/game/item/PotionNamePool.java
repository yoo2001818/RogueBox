package kr.kkiro.roguebox.game.item;

import java.util.ArrayList;
import java.util.List;

import kr.kkiro.roguebox.util.RandomProvider;
import static kr.kkiro.roguebox.util.I18n._;

public class PotionNamePool {
  
  public static List<String> list = new ArrayList<String>();
  
  public static void clear() {
    list.clear();
    list.add(_("redPotion"));
    list.add(_("greenPotion"));
    list.add(_("bluePotion"));
    list.add(_("purplePotion"));
    list.add(_("yellowPotion"));
  }
  
  public static String get() {
    return list.remove(RandomProvider.getRandom().nextInt(list.size()));
  }

}
