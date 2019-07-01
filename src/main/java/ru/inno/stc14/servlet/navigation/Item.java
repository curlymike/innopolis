package ru.inno.stc14.servlet.navigation;

/**
 * Этот класс представляет собо элемент навигационного меню
 */

public class Item {
  private String label;
  private String path;
  private String aliasPath;
  private boolean active;

  public Item(String label, String path) {
    this(label, path, false);
  }

  public Item(String label, String path, boolean active) {
    this.label = label;
    this.path = path;
    this.active = active;
  }

  public String getLabel() {
    return label;
  }

  public String getPath() {
    return path;
  }

  public String getAlias() {
    return aliasPath;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean value) {
    active = value;
  }

  public Item withAlias(String alias) {
    aliasPath = alias;
    return this;
  }

}
