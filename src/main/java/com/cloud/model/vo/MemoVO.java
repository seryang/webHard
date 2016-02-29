package com.cloud.model.vo;

import javax.persistence.*;

@Entity
@Table(schema = "cloud", name="memo")
public class MemoVO {
    @Id
    @GeneratedValue // autoincrement
    @Column(unique = true, nullable = false)
    private int id;

    @Column
    private String path;

    @Column
    private String uid;

    public MemoVO(){}

    public MemoVO(String path, String uid) {
        this.uid = uid;
        this.path = path;
    }

    public MemoVO(int id, String path, String uid) {
        this.id = id;
        this.uid = uid;
        this.path = path;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
