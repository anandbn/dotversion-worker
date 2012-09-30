package com.dotversion.workers;

import java.io.IOException;
import java.util.ServiceLoader;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dotversion.common.DotVersionConstants;
import com.dotversion.common.util.JedisPoolFactory;
import com.dotversion.common.util.OrgMetadataUtils;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.services.OrgBackupDataService;

public class OrgBackupWorker {

	private static JedisPoolFactory poolFactory = new JedisPoolFactory();
	private static Logger logger = LoggerFactory.getLogger(OrgBackupWorker.class);
	private static final OrgBackupDataService dbUtils;
	static{
		dbUtils = ServiceLoader.load(OrgBackupDataService.class).iterator().next();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){

		JedisPool pool=null;
		Jedis jedis=null;
		try {

			pool = poolFactory.getPool();
			jedis = pool.getResource();
			while (true) {
				String orgToBackup = jedis.lpop(DotVersionConstants.BACKUP_QUEUE_NAME);
				if(orgToBackup !=null){
					ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
					SalesforceOrg org=null;
					try {
						org = mapper.readValue(orgToBackup.getBytes(), SalesforceOrg.class);
					} catch (JsonParseException e) {
						logger.info(String.format("[dv-bkup-error] - Could not parse message from Backup Queue : %s",e.getMessage()));
						e.printStackTrace();
					} catch (JsonMappingException e) {
						logger.info(String.format("[dv-bkup-error] - Could not parse message from Backup Queue : %s",e.getMessage()));
						e.printStackTrace();
					} catch (IOException e) {
						logger.info(String.format("[dv-bkup-error] - Could not parse message from Backup Queue : %s",e.getMessage()));
						e.printStackTrace();
					}
					if (org != null) {
						org = dbUtils.findOrganizationById(org.getId());
						logger.info(String.format("[dv-bkup-start] - org_id=%s",org.getOrgId()));
						try {
							OrgMetadataUtils.backupOrg(org);
						} catch (Exception e) {
							logger.info(String.format("[dv-bkup-error] - Backup failed for org : %s",org.getId()));
							e.printStackTrace();
						}
						logger.info(String.format("[dv-bkup-end] - org_id=%s",org.getOrgId()));
					}
				
				}
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			logger.info(String.format("[dv-bkup-error] - Error in Backup Worker : %s",e.getMessage()));
			e.printStackTrace();
		} finally {
			if(pool!=null && jedis!=null){
				pool.returnResource(jedis);
			}

		}
	}

}
