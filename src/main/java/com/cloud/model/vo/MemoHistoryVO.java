package com.cloud.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(schema = "cloud", name="memo_history")
public class MemoHistoryVO {

    @Id
    @GeneratedValue
    private int no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memoId")
    private MemoVO memoId;

    @Column(name="memo_content")
    private String memoContent;

    @Column(name="reg_date")
    private Date regDate;

    public MemoHistoryVO(){}

    public MemoHistoryVO(MemoVO memoId, String memoContent, Date regDate) {
        this.memoId = memoId;
        this.memoContent = memoContent;
        this.regDate = regDate;
    }
}