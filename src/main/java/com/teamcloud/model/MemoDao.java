package com.teamcloud.model;

import com.teamcloud.model.vo.MemoHistoryVO;
import com.teamcloud.model.vo.MemoVO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class MemoDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // [Memo Table] - Path에 맞는 Memo ID 구하기
    public MemoVO selectMemoId(String path, String uid) throws Exception{
        System.out.println("path : " + path + " / uid : " + uid);
        Session session = getSession();
        return (MemoVO) session.createCriteria(MemoVO.class).add( Restrictions.eq("uid", uid)).add(Restrictions.eq("path", path)).uniqueResult();
    }

    // [MemoHistory Table] - Memo ID를 참조하는 MemoHistory 리스트 가져오기
    public List<MemoHistoryVO> selectMemoHistoryList(MemoVO id) throws Exception {
        Session session = getSession();
        System.out.println("id : " + id);
        List<MemoHistoryVO> list = session.createCriteria(MemoHistoryVO.class).add(Restrictions.eq("memoId", id)).list();
        return list;
    }

    // [Memo Table]  - 데이터 삽입
    public void insertMemo(MemoVO mvo) throws Exception {
        Session session = getSession();
        session.save(mvo);
    }

    // [MemoHistory Table] - 데이터 삽입
    public void insertMemoHistory(MemoHistoryVO hvo) throws Exception  {
        Session session = getSession();
        session.save(hvo);
    }

    // [MemoHistory Table] - 메모 삭제
    public void deleteMemo(int no) throws Exception {
        Session session = getSession();
        MemoHistoryVO hvo = selectMemoHistoryId(no);
        session.delete(hvo);
    }

    // [MemoHistory Table] - 메모 내용 수정
    public void updateMemoHistory(MemoHistoryVO hvo) throws Exception {
        Session session = getSession();
        session.update(hvo);
    }

    // [MemoHistory Table] - MemoHistory ID로 MemoHistory 객체 select
    public MemoHistoryVO selectMemoHistoryId(int no) throws Exception {
        Session session = getSession();
        MemoHistoryVO hvo = (MemoHistoryVO) session.createCriteria(MemoHistoryVO.class).add(Restrictions.eq("no", no)).uniqueResult();
        return hvo;
    }
}