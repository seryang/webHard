//package com.teamcloud.model;
//
//import com.teamcloud.model.vo.DirectoryVO;
//import com.teamcloud.model.vo.FileVO;
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
//public class FileDao {
//    @Autowired
//    @Qualifier("sessionFactory")
//    private SessionFactory sessionFactory;
//
//    protected Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    // 파일 리스트
//    public List<FileVO> selectFileList(DirectoryVO dvo) throws Exception {
//            Session session = getSession();
//            List<FileVO> fileList = session.createCriteria(FileVO.class).add( Restrictions.eq("dir_Id", dvo)).list();
//        System.out.println("파일 리스트 : " + fileList.toString());
//        return fileList;
//    }
//
//    // 파일 저장
//    public void insertFile(FileVO file) throws Exception {
//        getSession().save(file);
//    }
//
//    // 파일 수정 (덮어쓰기? 최신)
//    public void updateFile(FileVO file) throws Exception {
//        getSession().update(file);
//    }
//
//    // 파일 ID 구하기
//    public int selectFileID(DirectoryVO dir_id, String fileName) throws Exception {
//        System.out.println("파일 ID 구하기 시작!! ");
//        Session session = getSession();
//        FileVO fvo = (FileVO) session.createCriteria(FileVO.class).add( Restrictions.eq("dir_Id", dir_id)).add( Restrictions.eq("name", fileName) ).uniqueResult();
//        System.out.println("통과함?");
//        return fvo.getId();
//    }
//
//    // 파일 삭제
//
//    // 파일 이름 수정
//
//    // 파일 이동? ( 추후 구현 )
//
//}
