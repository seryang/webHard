package com.cloud.model.vo;

import java.util.List;

public class FolderTreeVO {
    private String id;
    private String text;
    private List<FolderTreeVO> children;
    private State state = new State();

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

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setOpened(boolean opened) {
        this.state.opened = opened;
    }

    public class State {
        private boolean opened;
        public boolean isOpened() {
            return opened;
        }
    }
}
