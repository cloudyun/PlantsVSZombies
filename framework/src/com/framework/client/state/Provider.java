package com.framework.client.state;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.framework.client.js.JsonConverter;


/**
 * Abstract base class for state provider implementations. This class provides
 * methods for encoding and decoding objects.
 */
public abstract class Provider {


  /**
   * Clears a value.
   * 
   * @param name the key name
   */
  public void clear(String name) {
    clearKey(name);
  }

  /**
   * Returns the current value for a key.
   * 
   * @param name the key name
   * @return the value
   */
  public Object get(String name) {
    String val = getValue(name);
    if (val == null) return null;
    Object obj = JsonConverter.decode(val).get("state");
    return obj;
  }

  /**
   * Returns the current value for a key.
   * 
   * @param name the key name
   * @return the value
   */
  public boolean getBoolean(String name) {
    String val = getValue(name);
    if (val == null) return false;
    Boolean bVal = (Boolean) JsonConverter.decode(val).get("state");
    return bVal.booleanValue();
  }

  /**
   * Returns the current value for a key.
   * 
   * @param name the key name
   * @return the value
   */
  public Date getDate(String name) {
    String val = getValue(name);
    if (val == null) return null;
    Date date = (Date) JsonConverter.decode(val).get("state");
    return date;
  }

  /**
   * Returns the current value for a key.
   * 
   * @param name the key name
   * @return the value or -1
   */
  public int getInteger(String name) {
    String val = getValue(name);
    Integer iVal = (Integer) JsonConverter.decode(val).get("state");
    if (iVal == null) {
      return -1;
    }
    return iVal.intValue();
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> getMap(String name) {
    String val = getValue(name);
    if (val == null) return null;
    Map<String, Object> map = (Map) JsonConverter.decode(val).get("state");
    return map;
  }

  /**
   * Returns the current value for a key.
   * 
   * @param name the key name
   * @return the value
   */
  public String getString(String name) {
    String val = getValue(name);
    if (val == null) return null;
    String obj = (String) JsonConverter.decode(val).get("state");
    return obj;
  }

  /**
   * Sets a key.
   * 
   * @param name the key name
   * @param value the value
   */
  public void set(String name, Object value) {
    Map<String, Object> map = new HashMap<String,Object>();
    map.put("state", value);
    setValue(name, JsonConverter.encode(map).toString());
  }

  protected abstract void clearKey(String name);

  protected abstract String getValue(String name);

  protected abstract void setValue(String name, String value);

}
