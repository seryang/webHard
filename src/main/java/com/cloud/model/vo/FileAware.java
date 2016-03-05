package com.cloud.model.vo;

public interface FileAware {
    boolean isFile();
    String getName();
    String getPath();
    String getSize();
    String getDate();
    String getType();

}
