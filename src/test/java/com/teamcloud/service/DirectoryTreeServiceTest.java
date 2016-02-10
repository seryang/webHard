package com.teamcloud.service;

import com.teamcloud.model.vo.FolderTreeNode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Seryang on 2016. 2. 10..
 */
public class DirectoryTreeServiceTest {
    private DirectoryTreeService directoryTreeService = new DirectoryTreeService();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testGetDirectoryTree0() throws Exception {
        FolderTreeNode resultTreeNodes = directoryTreeService.getDirectoryTree0(new FolderTreeNode("DataStore", "DataStore"), "DataStore");
        logger.debug("Result Folder Tree : {}", resultTreeNodes);
    }
}