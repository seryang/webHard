//package com.teamcloud.model;
//
//import com.teamcloud.model.vo.DirectoryVO;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Created by Seryang on 2016-02-01.
// */
//@Transactional
//@Repository
//public class DirectoryDao {
//
//    @Autowired
//    @Qualifier("sessionFactory")
//    private SessionFactory sessionFactory;
//
//    protected Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    // 폴더 생성
//    public void insertFolder(DirectoryVO dir) throws Exception {
//        Session session = getSession();
//        session.save(dir);
//    }
//
//
//    // 폴더 ID 구하기
//    public DirectoryVO selectId(String dirPath) throws Exception {
//        Session session = getSession();
//        return (DirectoryVO) session.createCriteria(DirectoryVO.class).add( Restrictions.eq("directoryPath", dirPath)).uniqueResult();
//    }
//
//    // 폴더 리스트
//    public List<DirectoryVO> selectFolderList(int parentId) throws Exception{
//        Session session = getSession();
//        List<DirectoryVO> folderList = session.createCriteria(DirectoryVO.class).add( Restrictions.eq("parentId", parentId)).list();
//        System.out.println("폴더 리스트 : " + folderList.toString());
//        return folderList;
//    }
//
//    // 폴더 삭제
//    public int deleteFolder(String path) throws Exception {
//        return 0;
//    }
//
//    // 폴더 이름 변경
//    public int updateFolder(String path, String newName) throws Exception {
//        return 0;
//    }
//}