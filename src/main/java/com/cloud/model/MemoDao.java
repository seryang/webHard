package com.cloud.model;

import com.cloud.model.vo.MemoHistoryVO;
import com.cloud.model.vo.MemoVO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class MemoDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    // [MemoHistory Table] - Memo ID를 참조하는 MemoHistory 리스트 가져오기
    public List selectMemoHistoryList(MemoVO id) throws Exception {
        return getSession().createCriteria(MemoHistoryVO.class).createAlias("memoId", "memoId", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("memoId", id)).list();
    }

    // [MemoHistory Table] - 데이터 삽입
    public void insertMemoHistory(MemoHistoryVO hvo) throws Exception  {
        getSession().save(hvo);
    }

    // [MemoHistory Table] - 메모 삭제
    public void deleteMemo(MemoHistoryVO hvo) throws Exception {
        getSession().delete(hvo);
    }

    // [MemoHistory Table] - 메모 내용 수정
    public void updateMemoHistory(MemoHistoryVO hvo) throws Exception {
        getSession().update(hvo);
    }

    // [MemoHistory Table] - MemoHistory ID로 MemoHistory 객체 select
    public MemoHistoryVO selectMemoHistoryId(int no) throws Exception {
        return (MemoHistoryVO) getSession().createCriteria(MemoHistoryVO.class)
        .createAlias("memoId", "memoId", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("no", no)).uniqueResult();
    }

    // [Memo Table] - Path에 맞는 Memo ID 구하기
    public MemoVO selectMemoId(String path, String uid) throws Exception{
        return (MemoVO) getSession().createCriteria(MemoVO.class).add( Restrictions.eq("uid", uid)).add(Restrictions.eq("path", path)).uniqueResult();
    }

    // [Memo Table]  - 데이터 삽입
    public void insertMemo(MemoVO mvo) throws Exception {
        getSession().save(mvo);
    }
}