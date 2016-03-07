package com.cloud.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderTreeVO {
    private String id;
    private String text;
    private List<FolderTreeVO> children;
    private State state = new State();

    public FolderTreeVO(String text, String id) {
        this.text = text;
        this.id = id;
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
