package com.dotversion.git;

import com.dotversion.common.util.Configuration;
import com.dotversion.forcediff.core.CombinedNodeDifference;
import com.dotversion.forcediff.org.MetadataXmlFile;
import com.heroku.api.Heroku;
import com.heroku.api.HerokuAPI;
import com.heroku.api.connection.HttpClientConnection;
import com.heroku.api.exception.RequestFailedException;
import com.heroku.api.App;
import com.heroku.api.request.key.KeyAdd;
import com.heroku.api.request.key.KeyRemove;
import com.heroku.api.request.sharing.SharingAdd;
import com.heroku.api.request.sharing.SharingRemove;
import com.heroku.api.request.sharing.SharingTransfer;
import com.heroku.api.response.Unit;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import org.apache.commons.io.FileUtils;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GitUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
    String emailAddress;
    String gitUrl;

    public Throwable exception;
    private static Logger logger = LoggerFactory.getLogger(GitUtils.class);

    public static void main(String[] args) throws Exception{
        /*ZipInputStream zipIn = new ZipInputStream(new FileInputStream("/var/folders/_x/74kz_jr14pq10flqg1trfjqr0000gn/T/00D70000000JSvzEAG.zip"));
        createOrgMetadataRepo("00D70000000JSvzEAG", zipIn);
        
    	HerokuAPI herokuAPI = new HerokuAPI(System.getenv("HEROKU_API_KEY"));
        
    	App app = herokuAPI.getApp("fdc-"+"00D70000000JSvzEAG".toLowerCase());
    	System.out.println(String.format("getapp(): %s,gitUrl=%s ",app.getName(),app.getGitUrl()));
        for(App app1: herokuAPI.listApps()){
        	System.out.println(String.format("listapp(): %s,gitUrl=%s ",app1.getName(),app1.getGitUrl()));
        }
        */
    	
    	
    	String destDir = "/Users/anandbashyamnarasimhan/git/fdc-00d70000000jsvzeag";
    	Repository repo = new FileRepositoryBuilder().readEnvironment().findGitDir(new File(destDir)).build();
		Git git = new Git(repo);
		Map<String,String> deletes = new HashMap<String,String>();
		deletes.put("unpackaged/objects/XmlElement__c.object","XmlElement__c.object");
        deleteFileFromGitRepo(destDir,deletes,repo);
 		CommitCommand commit ;
 		commit = git.commit();
 		commit.setMessage("Deleting file");
 		commit.call();

 		pushToRemote(new File(destDir));
       

    }
    public GitUtils(String emailAddress, String gitUrl) {
        this.emailAddress = emailAddress;
        this.gitUrl = gitUrl;
    }

    public static String updateOrgMetadataRepo(String orgId,ZipInputStream zipIn) throws Exception {
        HerokuAPI herokuAPI = new HerokuAPI(System.getenv("HEROKU_API_KEY"));
        App app=null;
        try{
        	app = herokuAPI.getApp("fdc-"+orgId.toLowerCase());
        }catch(RequestFailedException ex){
        	if(ex.getStatusCode()==404){
        		app = herokuAPI.createApp(new App().on(Heroku.Stack.Cedar).named("fdc-"+orgId.toLowerCase()));
        		if (!app.getCreateStatus().equals("complete")) {
                    throw new RuntimeException("Could not create the Heroku app");
                }
        	}
        }
    	File localSrc = new  File(System.getProperty("java.io.tmpdir") + File.separator + orgId);
    	pullFromRemote(localSrc,app.getGitUrl());
    	String versionId=null;
    	return versionId;
    }

    public static BackupStatus updateOrgMetadataRepo(String orgId,ZipInputStream zipIn,String destDir,Map<String, Map<String, String>> whatsChanged) throws Exception {
    	
    	ZipEntry currEntry;
    	Integer totalChanges=0;
    	Map<String,String> changes = whatsChanged.get("MODIFIED");
    	Map<String,String> adds = whatsChanged.get("ADDED");
    	Map<String,String> deletes = whatsChanged.get("DELETED");
    	String changesMsg = "Changes:",addMsg="Additions:",deleteMsg="Deletions:";
        while((currEntry=zipIn.getNextEntry()) !=null){
        	//If it's modified or a newly added file extract it to the Git Repo
    		if(changes!=null && changes.containsKey(currEntry.getName())){
            	extractFileFromZip(zipIn, destDir,currEntry.getName());
            	totalChanges++;
    			changesMsg+=getNameFromZipEntry(currEntry.getName())+";";
    		}else if((adds!=null && adds.containsKey(currEntry.getName()))){
            	extractFileFromZip(zipIn, destDir,currEntry.getName());
    			addMsg+=getNameFromZipEntry(currEntry.getName())+";";
            	totalChanges++;
    			
    		}
        }
		Repository repo = new FileRepositoryBuilder().readEnvironment().findGitDir(new File(destDir)).build();
		Git git = new Git(repo);
        if(deletes!=null && !deletes.isEmpty()){
	        totalChanges +=deletes.size();
	        deleteMsg = deleteFileFromGitRepo(destDir,deletes,repo);
        }
        
        AddCommand gitAdd;
		gitAdd = git.add();
		gitAdd.addFilepattern(".");
		gitAdd.call();
		log("[dv-git]","Added/Updated changed files");
		CommitCommand commit ;
		commit = git.commit();
		commit.setAmend(true);
		commit.setMessage(changesMsg+'\n'+addMsg+'\n'+deleteMsg);
		commit.call();
		pushToRemote(new File(destDir));
		log("[dv-git]","Pushed to remote");
		return new BackupStatus(git.log().call().iterator().next().getId().name(),
								changesMsg+'\n'+addMsg+'\n'+deleteMsg,
								true,
								totalChanges);


    }
    public static String pullFromRemote(String orgId) throws Exception {
        HerokuAPI herokuAPI = new HerokuAPI(System.getenv("HEROKU_API_KEY"));
        App app = herokuAPI.getApp("fdc-"+orgId.toLowerCase());
    	File localSrc = new  File(System.getProperty("java.io.tmpdir") + File.separator + orgId);
    	if(localSrc.exists()){
    		FileUtils.deleteDirectory(localSrc);
    	}
    	pullFromRemote(localSrc,app.getGitUrl());
    	return localSrc.getAbsolutePath();
    }
    
    
    public static BackupStatus createOrgMetadataRepo(String orgId,ZipInputStream zipIn) throws Exception {
        App app = null;
        HerokuAPI herokuAPI = new HerokuAPI(System.getenv("HEROKU_API_KEY"));
        File localSrc = null;
        try {
            
            try{
            	app = herokuAPI.getApp("fdc-"+orgId.toLowerCase());
            }catch(RequestFailedException ex){
            	if(ex.getStatusCode()==404){
            		app = herokuAPI.createApp(new App().on(Heroku.Stack.Cedar).named("fdc-"+orgId.toLowerCase()));
            		if (!app.getCreateStatus().equals("complete")) {
                        throw new RuntimeException("Could not create the Heroku app");
                    }
            	}
            }
            
            log("[dv-app-init]",String.format("app=%s,orgid=%s,gitUrl=%s",app.getName(),orgId,app.getGitUrl()));
            
            

        	localSrc = initializeGitRepo(orgId);
            log("[dv-git-init]",String.format("Intialized git repo at :%s",localSrc.getAbsolutePath()));
            Integer fileCount = extractContentsToTmp(localSrc,orgId, zipIn);
            log("[dv-git-extract]",String.format("Extracted metadata repo at :%s",localSrc.getAbsolutePath()));
            String version = addAndCommitFilesToGit(localSrc,String.format("Backup-%s",DATE_FORMAT.format(new java.util.Date())),orgId);
            log("[dv-git-commit]",String.format("Committed metadata repo at :%s",localSrc.getAbsolutePath()));
            
            addRemote(localSrc,app.getGitUrl());
            log("[dv-git-remote]",String.format("Added '%s' to remote",app.getGitUrl()));
            pushToRemote(localSrc);
            log("[dv-git-push]",String.format("Pushed local git repo to Remote Repo : %s",app.getGitUrl()));
            return new BackupStatus(version,"Created initial backup",true,fileCount);
            
        } catch (Exception e) {
        	log("[dv-error]","Exception when create org metadata repository:"+e.getMessage());
          	if (app !=null){
        		herokuAPI.destroyApp(app.getName());	
        	}
  
        	throw new RuntimeException(e);
           
        }finally{
        	if(localSrc!=null){
        		localSrc.delete();
        		logger.info(String.format("[dv-git-delete] - Deleted local git repo at '%s'",localSrc.getAbsolutePath()));
        	}
        }
    }

    
    private static File initializeGitRepo(String orgId) throws Exception{
		InitCommand init = Git.init();
		File src = new  File(System.getProperty("java.io.tmpdir") + File.separator + orgId);
		if(!src.exists()){
			src.mkdirs();
		}
		init.setBare(false).setDirectory(src);
		init.call();
		return src;

    }
    
    private static String addAndCommitFilesToGit(File baseDir,String commitMessage,String orgId) throws Exception{
    	//Create template pom.xml
    	
		File pomXml = new File(baseDir,"pom.xml");
		String pomXmlStr = Configuration.templatePOM();
		FileOutputStream pom = new FileOutputStream(pomXml);
		pom.write(pomXmlStr.replace("ORGID",orgId).getBytes());
		pom.close();

    	AddCommand gitAdd;
		Repository repo = new FileRepositoryBuilder().readEnvironment().findGitDir(baseDir).build();
		Git git = new Git(repo);
		gitAdd = git.add();
		gitAdd.addFilepattern(".");
		gitAdd.call();
		log("[dv-git-pom]","Added template pom.xml to Git repo");
		CommitCommand commit ;
		commit = git.commit();
		commit.setMessage(commitMessage);
		commit.call();
		
		return git.log().call().iterator().next().getId().name();
		
	}
    
    private static void addRemote(File baseDir,String gitUrl) throws Exception{
		Repository repo = new FileRepositoryBuilder().readEnvironment()
				.findGitDir(baseDir).build();
		StoredConfig config = repo.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, "heroku");
		URIish uri = new URIish(gitUrl);
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();
		log("[dv-git-remote]",String.format("Added git URL: %s  to config at %s",gitUrl,config));
	}
    
    private static void pushToRemote(File baseDir) throws Exception{
		Repository repo = new FileRepositoryBuilder().readEnvironment()
				.findGitDir(baseDir).build();
		Git theGit = new Git(repo);
		PushCommand push = theGit.push();
		push.setForce(true);
		push.setRemote("heroku");
		for(PushResult result : push.call()){
			log("[dv-git-push-remote]",result.getMessages());
		}

    }
    
    private static void pullFromRemote(File baseDir,String gitUrl) throws Exception{
		CloneCommand clone = new CloneCommand();
		clone.setRemote("heroku");
		clone.setURI(gitUrl).setDirectory(baseDir).call();
		return;
    }

    public static Integer extractContentsToTmp(File localSrc, String orgId,ZipInputStream zipIn) throws Exception{
    	ZipEntry currEntry;
    	Integer fileCount=0;
        File currFile;
        String destDir = localSrc.getAbsolutePath();
        while((currEntry=zipIn.getNextEntry()) !=null){
    		currFile = new java.io.File(destDir + java.io.File.separator + currEntry.getName());
			if (currEntry.isDirectory() || currEntry.getName().contains("package.xml")) {
				currFile.mkdir();
				continue;
			} else {
				fileCount++;
				extractFileFromZip(zipIn, destDir,currEntry.getName());
			}
        }
        return fileCount;
    }
    
	private static void extractFileFromZip(ZipInputStream zipIn,String destDir,String entryName) throws Exception{
		int count;
		String subDirName = getDirectoryName(entryName);
		File subDir = new File(destDir+File.separator+"unpackaged"+File.separator+subDirName);
		if(!subDir.exists()){
			logger.info(String.format("[dv-meta-extract] - Creating directory '%s'",subDir.getName()));
			subDir.mkdirs();
		}
		File destFile = new File(destDir+File.separator+entryName);
		
		FileOutputStream fileOut = new FileOutputStream(destFile); 
        byte data[] = new byte[2048];
        while ((count = zipIn.read(data, 0, 2048)) != -1) {
        	fileOut.write(data, 0, count);
        }		
        zipIn.closeEntry();
		logger.info(String.format("[dv-meta-extract] - Extracted file '%s' to '%s'",destFile.getName(),destDir));
        fileOut.close();
		
	}
	
	private static String deleteFileFromGitRepo(String destDir,Map<String,String> deletes,Repository repo) throws Exception{
		String deleteMsg = "Deletes:";
		Git git = new Git(repo);
		RmCommand gitRm = git.rm();
		for(Entry<String, String> toDelete: deletes.entrySet()){
			deleteMsg +=getNameFromZipEntry(toDelete.getKey())+";";
			gitRm.addFilepattern(toDelete.getKey());
    		String subDirName = getDirectoryName(toDelete.getKey());
			File subDir = new File(destDir+File.separator+"unpackaged"+File.separator+subDirName);
			if(!subDir.exists()){
				logger.info(String.format("[dv-meta-delete] - Creating directory '%s'",subDir.getName()));
				subDir.mkdirs();
			}
			
			File destFile = new File(destDir+File.separator+toDelete.getKey());
			logger.info(String.format("[dv-meta-delete] - Deleting file '%s'",destFile.getAbsolutePath()));
			
			destFile.delete();
   		 }
		gitRm.call();
    	logger.info(String.format("[dv-meta-delete] - git rm complete"));
    	return deleteMsg;
	}

    
    private static void log(String event,String logMsg){
    	logger.info(String.format("%s - %s",event,logMsg));
    }

    
	private static String getDirectoryName(String zipEntryName) throws Exception{
		String objName="";
		try{
			if(zipEntryName.indexOf('.')>-1){
		
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
			return objName.substring(0,objName.indexOf('/'));
			
		}else{
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/'));
			objName=zipEntryName.substring(0,objName.length()-1);
		}
		}catch(Exception ex){
			throw ex;
		}
		return objName;
	}
	
	private static String getNameFromZipEntry(String zipEntryName){
		String objName="";
		if(zipEntryName.indexOf('.')>-1){
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
			objName = objName.substring(objName.indexOf('/'));
			objName = objName.substring(1,objName.indexOf("."));
		}else{
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/'));
			objName=zipEntryName.substring(0,objName.length()-1);
		}
		return objName;
	}
}


