package com.dotversion.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.sforce.soap.metadata.RetrieveResult;

public class ZipUtils {
	public static ZipInputStream initializeInMemoryZip(byte[] result) throws Exception{
	    ByteArrayInputStream bais = new ByteArrayInputStream(result);
	    return new ZipInputStream(new BufferedInputStream(bais));
	}
	
	
	public static File writeZipToTmpFile(byte[] result,String orgId) throws Exception{
	    File tmpZip = new File(System.getProperty("java.io.tmpdir") + File.separator + orgId +".zip");
		FileOutputStream fileOut = new FileOutputStream(tmpZip);
		fileOut.write(result);
		fileOut.close();
		return tmpZip;
	}

}
