package com.cloud.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectoryVO implements FileAware{

    private int id;

    private String directoryName;

    private String directoryPath;

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public String getName() {
        return getDirectoryName();
    }

    @Override
    public String getPath() {
        return getDirectoryPath();
    }

    @Override
    public String getSize() {
        return "";
    }

    @Override
    public String getDate() {
        return "";
    }

    @Override
    public String getType() {
        return "";
    }
}
