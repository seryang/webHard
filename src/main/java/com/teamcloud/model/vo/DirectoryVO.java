package com.teamcloud.model.vo;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ESTSoft on 2016-02-01.
 */
@Entity
@Table(name = "directory")
public class DirectoryVO{

    @Id
    @GeneratedValue //자동 숫자 증가
    @Column(name="directory_id", unique = true, nullable = false)
    private int id;

    @Column(length = 10000, nullable = false)
    private String directoryPath;

    @Column(length = 10000, nullable = true)
    private String directoryName;

    @Column(name="parentId", nullable = true)
    private int parentId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dir_Id")
    private Set<FileVO> file_Id = new HashSet<FileVO>(0);


    public DirectoryVO(){}

    public DirectoryVO(String fullPath, String childFolder, int parentId) {
        this.directoryPath = fullPath;
        this.directoryName = childFolder;
        this.parentId = parentId;
    }


    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Set<FileVO> getFile_Id() {
        return file_Id;
    }

    public void setFile_Id(Set<FileVO> file_Id) {
        this.file_Id = file_Id;
    }

    @Override
    public String toString() {
        return "DirectoryVO{" +
                "id=" + id +
                ", directoryPath='" + directoryPath + '\'' +
                ", directoryName='" + directoryName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
