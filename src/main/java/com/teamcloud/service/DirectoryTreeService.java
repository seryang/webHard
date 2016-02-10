package com.teamcloud.service;

import com.teamcloud.model.vo.FolderTreeNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seryang on 2016. 2. 10..
 */
@Service
public class DirectoryTreeService {
    private boolean findParent = false;

    public FolderTreeNode getDirectoryTree0(FolderTreeNode node, String selected) throws Exception {
        File dir = new File(node.getId());
        File parent = new File(selected).getParentFile();
        List<FolderTreeNode> children = new ArrayList<FolderTreeNode>();
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                String dirName = file.getName();
                String path = file.getPath();
                FolderTreeNode folderTreeNode = new FolderTreeNode(dirName, path);
                if (findParent == false && selected != "DataStore") {
                    folderTreeNode.setOpened(true);
                }
                if (parent != null && parent.getPath().equals(path)) {
                    findParent = true;
                }
                children.add(getDirectoryTree0(folderTreeNode, selected));
            }
        }
        if (children.isEmpty() == false) {
            node.setChildren(children);
        }
        return node;
    }
}
