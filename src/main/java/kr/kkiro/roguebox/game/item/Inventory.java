package kr.kkiro.roguebox.game.item;

import java.util.ArrayList;
import java.util.List;

import kr.kkiro.roguebox.game.entity.Character;

public class Inventory {
  
  protected List<ItemType> contents;
  protected ItemBank bank;
  protected Character character;
  
  public Inventory(ItemBank bank) {
    this.bank = bank;
    contents = new ArrayList<ItemType>();
  }
  
  public ItemBank getBank() {
    return bank;
  } 
  
  public Character getCharacter() {
    return character;
  }
  
  public void setCharacter(Character character) {
    this.character = character;
  }

}
