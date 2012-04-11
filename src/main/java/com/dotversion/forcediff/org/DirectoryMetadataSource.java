package com.dotversion.forcediff.org;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryMetadataSource implements MetadataSource {
    
    private String sourceName;
    private String metadataBaseDir;
    private List<String> metadataTypes;
    
    public DirectoryMetadataSource(String sourceName, String metadataBaseDir) {
        this.sourceName = sourceName;
        File nodeDir = new File(metadataBaseDir);
        assert nodeDir.isDirectory();
        File[] subdirs = nodeDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        
        this.metadataBaseDir = metadataBaseDir;
        List<String> metadataTypes = new ArrayList<String>();
        
        for (File subdir: subdirs) {
            metadataTypes.add(subdir.getName());
        }
        
        this.metadataTypes = Collections.unmodifiableList(metadataTypes);
        
    }
    
    @Override
    public String getName() {
        return this.sourceName;
    }

    @Override
    public List<String> getSupportedMetadataTypes() {
        return this.metadataTypes;
    }

    @Override
    public List<File> getContentList(String metadataType) {
        assert this.metadataTypes.contains(metadataType);
        File nodeDir = new File(this.metadataBaseDir + File.separator + metadataType);
        assert nodeDir.isDirectory();
        List<File> contentList = new ArrayList<File>();
        for (File f: nodeDir.listFiles()) {
            contentList.add(f);
        }
        return contentList;
    }

    
    @Override
    public MetadataXmlFile getNextMetadataObject() throws Exception{
    	return null;
    }
}
