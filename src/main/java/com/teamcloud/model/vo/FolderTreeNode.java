package com.teamcloud.model.vo;

import java.util.List;

/**
 * Created by Seryang on 2016. 2. 10..
 */
public class FolderTreeNode {
    private String id;
    private String text;
    private List<FolderTreeNode> children;
    private FolderTreeState state = new FolderTreeState();

    public FolderTreeNode(String text, String id) {
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
    public List<FolderTreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<FolderTreeNode> children) {
        this.children = children;
    }
    public void setSelected(boolean selected) {
        state.selected = selected;
    }
    public void setOpened(boolean opened) {
        state.opened = opened;
    }
    public FolderTreeState getState() {
        return state;
    }

    public void setState(FolderTreeState state) {
        this.state = state;
    }

    private class FolderTreeState {
        private boolean selected;
        private boolean opened;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }
    }
}
