package com.teamcloud.model.vo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="memo_history")
public class MemoHistoryVO {

    @Id
    @GeneratedValue
    @Column(name="no")
    private int no;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memoId")
    private MemoVO memoId;

    @Column(name="memo_content")
    private String memoContent;

    @Column(name="reg_date")
    private Date regDate;

    public MemoHistoryVO(){}

    public MemoHistoryVO(String memoContent, Date regDate) {
        this.memoContent = memoContent;
        this.regDate = regDate;
    }

    public MemoHistoryVO(MemoVO memoId, String memoContent, Date regDate) {
        this.memoId = memoId;
        this.memoContent = memoContent;
        this.regDate = regDate;
    }

    public MemoHistoryVO(int no, MemoVO memoId, String memoContent, Date regDate) {
        this.no = no;
        this.memoId = memoId;
        this.memoContent = memoContent;
        this.regDate = regDate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public MemoVO getMemoId() {
        return memoId;
    }

    public void setMemoId(MemoVO memoId) {
        this.memoId = memoId;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemoHistoryVO{");
        sb.append("no=").append(no);
        sb.append(", memoContent='").append(memoContent).append('\'');
        sb.append(", regDate=").append(regDate);
        sb.append('}');
        return sb.toString();
    }
}