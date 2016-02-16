package com.teamcloud.model.vo;

import javax.persistence.*;

@Entity
@Table(name="memo")
public class MemoVO {
    @Id
    @GeneratedValue // autoincrement
    @Column(unique = true, nullable = false)
    private int id;

    @Column
    private String uid;

    @Column
    private String path;

    public MemoVO(){}

    public MemoVO(String path, String uid) {
        this.uid = uid;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemoVO{");
        sb.append("id=").append(id);
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
