package com.cloud.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
}
