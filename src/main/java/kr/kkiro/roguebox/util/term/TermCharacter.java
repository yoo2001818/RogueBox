package kr.kkiro.roguebox.util.term;

import kr.kkiro.roguebox.util.ANSIColor;

public class TermCharacter implements Cloneable {
  public char c;
  public ANSIColor back;
  public ANSIColor text;
  
  @Override
  public TermCharacter clone() {
    try {
      return (TermCharacter)(super.clone());
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((back == null) ? 0 : back.hashCode());
    result = prime * result + c;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TermCharacter other = (TermCharacter) obj;
    if (back != other.back) return false;
    if (c != other.c) return false;
    if (text != other.text) return false;
    return true;
  }
}
