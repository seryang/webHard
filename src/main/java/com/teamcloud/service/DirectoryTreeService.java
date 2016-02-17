package com.teamcloud.service;

import com.teamcloud.model.vo.FolderTreeVO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seryang on 2016. 2. 10..
 */
@Service
public class DirectoryTreeService {

    public FolderTreeVO getDirectoryTree(FolderTreeVO node) throws Exception {
        File dir = new File(node.getId());
        List<FolderTreeVO> children = new ArrayList<FolderTreeVO>();

        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                String dirName = file.getName();
                String path = file.getPath();
                FolderTreeVO folderTreeNode = new FolderTreeVO(dirName, path);
                children.add(getDirectoryTree(folderTreeNode));
            }
        }

        if (children.isEmpty() == false) {
            node.setChildren(children);
        }

        return node;
    }
}
