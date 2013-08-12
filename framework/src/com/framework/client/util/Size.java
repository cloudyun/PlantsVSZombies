package com.framework.client.util;

public class Size {
  public int width;

  public int height;

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public String toString() {
    return "height: " + height + ", width: " + width;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Size) {
      Size s = (Size) obj;
      if (width == s.width && height == s.height) {
        return true;
      }
      return false;
    }
    return super.equals(obj);
  }

}
