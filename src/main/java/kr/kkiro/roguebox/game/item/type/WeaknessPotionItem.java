package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionItem;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.StatusEffect;
import kr.kkiro.roguebox.game.status.WeaknessEffect;

public class WeaknessPotionItem extends PotionItem {

  public WeaknessPotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public StatusEffect getEffect() {
    return new WeaknessEffect(15);
  }


}
