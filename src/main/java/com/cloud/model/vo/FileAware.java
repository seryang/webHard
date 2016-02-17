package com.cloud.model.vo;

/**
 * Created by Seryang on 2016. 2. 13..
 */
public interface FileAware {
    boolean isFile();
    String getName();
    String getPath();
    String getSize();
    String getDate();
    String getType();

}
