package kr.kkiro.roguebox.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class FontUtil {
  
  private static List<String> fontlist;
  private static List<String> nonCJKfontlist;
  private static Dimension cachedSize;
  private static FontMetrics fontMetrics;
  
  public static List<String> getFontList() {
    if(fontlist == null) {
      List<String> nonCJKMonospaceFontFamilyNames = new ArrayList<String>();
      List<String> monospaceFontFamilyNames = new ArrayList<String>();
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      String[] fontFamilyNames = graphicsEnvironment.getAvailableFontFamilyNames();
      BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      Graphics graphics = bufferedImage.createGraphics();
      for (String fontFamilyName : fontFamilyNames) {
          boolean isMonospaced = true;
          int fontStyle = Font.PLAIN;
          int fontSize = 12;
          Font font = new Font(fontFamilyName, fontStyle, fontSize);
          FontMetrics fontMetrics = graphics.getFontMetrics(font);
          int firstCharacterWidth = 0;
          boolean hasFirstCharacterWidth = false;
          for (int codePoint = 0; codePoint < 128; codePoint++) { 
              if (Character.isValidCodePoint(codePoint) && (Character.isLetter(codePoint) || Character.isDigit(codePoint))) {
                  char character = (char) codePoint;
                  int characterWidth = fontMetrics.charWidth(character);
                  if (hasFirstCharacterWidth) {
                      if (characterWidth != firstCharacterWidth) {
                          isMonospaced = false;
                          break;
                      }
                  } else {
                      firstCharacterWidth = characterWidth;
                      hasFirstCharacterWidth = true;
                  }
              }
          }
          if (isMonospaced) {
            if(font.canDisplay('가')) {
              monospaceFontFamilyNames.add(fontFamilyName);
            } else {
              nonCJKMonospaceFontFamilyNames.add(fontFamilyName);
            }
          }
      }
      graphics.dispose();
      nonCJKfontlist = nonCJKMonospaceFontFamilyNames;
      fontlist = monospaceFontFamilyNames;
    }
    return fontlist;
  }
  
  public static Font getPreferredFont() {
    if(getFontList().contains("나눔고딕코딩")) return new Font("나눔고딕코딩", Font.PLAIN, 18);
    if(getFontList().contains("WenQuanYi Zen Hei Mono")) return new Font("WenQuanYi Zen Hei Mono", Font.PLAIN, 18);
    if(getFontList().contains("Monospaced")) return new Font("Monospaced", Font.PLAIN, 18);
    if(getFontList().size() >= 1) {
      return new Font(getFontList().iterator().next(), Font.PLAIN, 18);
    }
    JOptionPane.showMessageDialog(null, "한글을 표시할 수 있는 고정폭 글꼴이 없어 영어 고정폭 글꼴을 사용합니다.\n한글이 제대로 보이지 않을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
    return new Font(nonCJKfontlist.iterator().next(), Font.PLAIN, 18);
  }
  
  public static Dimension getFontSize() {
    if(cachedSize == null) { 
      BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      Graphics graphics = bufferedImage.createGraphics();
      fontMetrics = graphics.getFontMetrics(FontUtil.getPreferredFont());
      cachedSize = new Dimension(fontMetrics.charWidth('w'), fontMetrics.getHeight()+2);
    }
    return cachedSize;
  }
  
  public static Dimension getTermSize() {
    return new Dimension(getFontSize().width * 80, getFontSize().height * 24);
  }
  
  public static int getX(float x) {
    return (int)(getFontSize().width * x);
  }
  
  public static int getY(float y) {
    return (int)(getFontSize().height * y);
  }
  
  public static int getTY(float y) {
    return (int)(getFontSize().height * y) - fontMetrics.getDescent() - 1;
  }
}
