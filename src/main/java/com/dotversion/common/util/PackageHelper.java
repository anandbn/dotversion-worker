package com.dotversion.common.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.metadata.Package;
import com.sforce.soap.metadata.PackageTypeMembers;

public class PackageHelper {
	public static Package getPackageConfig(String pkgMbrs) throws Exception{
		Package pkgConfig = new Package();
		ArrayList<PackageTypeMembers> pkgMbrList = new ArrayList<PackageTypeMembers>();
		for (String componentName : pkgMbrs.split(";")) {
			PackageTypeMembers mbr = new PackageTypeMembers();
			mbr.setMembers(new String[] { "*" });
			mbr.setName(componentName);
			pkgMbrList.add(mbr);
		}
		pkgConfig.setTypes(pkgMbrList.toArray(new PackageTypeMembers[] {}));
		pkgConfig.setVersion(System.getenv("SFDC_API_VERSION")==null?"23.0":System.getenv("SFDC_API_VERSION"));
		return pkgConfig;
	}
}
