package kr.kkiro.roguebox.util;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 언어 파일을 로드하는 클래스입니다.
 * Java의 {@link ResourceBundle}을 사용해서 언어 파일을 로드하게 됩니다.
 */
public class I18n {

  /**
   * 한번 생성한 객체를 전역적으로 접근하기 위해 두는 변수입니다.
   */
  private static I18n instance;
  
  /**
   * 언어 설정 파일의 이름입니다.
   */
  public static final String LANGUAGE = "language";
  
  /**
   * 기본으로 사용할 언어를 나타냅니다. (보통 영어)
   */
  private final Locale defaultLocale = Locale.getDefault();
  
  /**
   * 현재 사용하고 있는 언어를 담는 변수입니다.
   */
  private Locale currentLocale = defaultLocale;
  
  /**
   * 언어 설정 파일을 담는 변수입니다.
   */
  private ResourceBundle localeBundle;
  
  /**
   * 기본 설정 파일을 담는 변수입니다.
   */
  private final ResourceBundle defaultBundle;
  
  /**
   * 메시지를 포맷해주는 객체들을 캐시하는 변수입니다..
   */
  private final Map<String, MessageFormat> messageFormatCache = new HashMap<String, MessageFormat>();
 
  public I18n() {
    instance = this;
    // 기본 설정 파일을 불러옵니다.
    defaultBundle = ResourceBundle.getBundle(LANGUAGE, defaultLocale);
    // 나머지 설정 파일은 refreshLanguageFile() 함수에서 불러옵니다.
    refreshLanguageFile();
  }
  
  /**
   * 언어 설정 파일과 커스텀 설정 파일을 불러오는 함수입니다.
   */
  private void refreshLanguageFile() {
    // 언어 설정 파일도 불러옵니다.
    localeBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale);
  }
  
  /**
   * 현재 사용하고 있는 언어를 출력합니다.
   * @return 현재 사용중인 언어
   */
  public Locale getCurrentLocale() {
    return currentLocale;
  }
  
  /**
   * 언어 파일에서 해당 키의 값을 불러옵니다.
   * @param key 값을 불러올 키
   * @return 값
   */
  public String translate(String key) {
    try {
      return localeBundle.getString(key);
    } catch (MissingResourceException e) {
      return defaultBundle.getString(key);
    }
  }
  
  /**
   * 언어 파일에서 해당 키의 값을 불러와 매개변수들로 포맷을 시킵니다.
   * @param key 값을 불러올 키
   * @param params 매개변수들
   * @return 포맷된 문자열
   */
  public String format(String key, Object... params) {
    // 포맷을 수행할 원본 문자열입니다.
    String format = translate(key);
    // 일단, 포맷 캐시에서 해당 포맷이 있는지 먼저 불러와 봅니다.
    MessageFormat messageFormat = messageFormatCache.get(format);
    if (messageFormat == null) {
      // 없다면 만듭니다.
      try {
        messageFormat = new MessageFormat(format);
      } catch (IllegalArgumentException e) {
        return format + ":" + Arrays.toString(params);
      }
      // 포맷 캐시에 포맷을 집어넣습니다.
      messageFormatCache.put(format, messageFormat);
    }
    // 포맷한 문자열을 출력합니다.
    return messageFormat.format(params);
  }
  
  /**
   * 현재 언어를 해당 언어 코드에 해당하는 언어로 바꿉니다.
   * @param locale 언어 코드
   */
  public void setCurrentLocale(String locale) {
    // 언어 코드가 비어있으면 처리하지 않습니다.
    if(locale == null || locale.isEmpty()) return;
    // 언어 코드를 \ 혹은 . 혹은 _ 단위로 쪼갭니다.
    // ko.KR , ko_KR, ko\KR
    String[] parts = locale.split("[\\._]");
    // 언어 코드의 길이대로 객체를 생성합니다.
    if (parts.length == 1)
      currentLocale = new Locale(parts[0]);
    if (parts.length == 2)
      currentLocale = new Locale(parts[0], parts[1]);
    if (parts.length == 3)
      currentLocale = new Locale(parts[0], parts[2]);
    // 캐시들을 전부 지웁니다.
    ResourceBundle.clearCache();
    messageFormatCache.clear();
    // 언어를 로드합니다.
    refreshLanguageFile();
  }
  
  /**
   * 전역 함수로서, I18n 객체를 불러와 포맷된 문자열을 리턴합니다.
   * <p>
   * <code>import static kr.kkiro.utils.I18n._</code> 따위로 임포트하면
   * <code>_("key")</code>를 바로 사용할 수 있어 편리합니다.
   * @param key 키
   * @param params 매개변수들
   * @return 포맷된 문자열
   */
  public static String _(String key, Object... params) {
    if (instance == null) {
      new I18n();
    }
    String message = "";
    if (params.length == 0) {
      // 포맷할 필요가 없으므로, 문자열을 그대로 처리합니다.
      message = instance.translate(key);
    } else {
      // 문자열을 포맷합니다.
      message = instance.format(key, params);
    }
    // ChatColor과 \n 처리를 한 후 리턴합니다.
    return message.replace("\\n", "\n");
  }
}
