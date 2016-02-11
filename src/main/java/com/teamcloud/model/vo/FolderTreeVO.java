package com.teamcloud.model.vo;

import java.util.List;

/**
 * Created by Seryang on 2016. 2. 10..
 */
public class FolderTreeVO {
    private String id;
    private String text;
    private List<FolderTreeVO> children;

    public FolderTreeVO(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public List<FolderTreeVO> getChildren() {
        return children;
    }
    public void setChildren(List<FolderTreeVO> children) {
        this.children = children;
    }
}
