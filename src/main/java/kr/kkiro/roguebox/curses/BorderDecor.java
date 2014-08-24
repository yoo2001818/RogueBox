package kr.kkiro.roguebox.curses;


public class BorderDecor {
  public static void drawBox(TextGraphics g, int x, int y, int w, int h) {
    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append('┌');
    for(int i = x+1; i < x+w-1; ++i) textBuilder.append('─');
    textBuilder.append('┐');
    g.drawString(x, y,textBuilder.toString());
    for(int i = y+1; i < y+h-1; ++i) {
      g.drawChar(x, i, '│');
      g.drawChar(x+w-1, i, '│');
    }
    textBuilder.replace(0, 1, "└");
    textBuilder.replace(textBuilder.length()-1, textBuilder.length(), "┘");
    g.drawString(x, y+h-1,textBuilder.toString());
  }
}
