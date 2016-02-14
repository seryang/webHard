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

    // 해당 Path의 memoId 구하기
    public MemoVO selectMemoId(String path, String uid) throws Exception{
        System.out.println("path : " + path + " / uid : " + uid);
        Session session = getSession();
        return (MemoVO) session.createCriteria(MemoVO.class).add( Restrictions.eq("uid", uid)).add(Restrictions.eq("path", path)).uniqueResult();
    }

    public List<MemoHistoryVO> selectMemoHistoryList(MemoVO id) throws Exception {
        Session session = getSession();
        System.out.println("id : " + id);
        List<MemoHistoryVO> list = session.createCriteria(MemoHistoryVO.class).add(Restrictions.eq("memoId", id)).list();
        return list;
    }
}
