package com.dotversion.workers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ServiceLoader;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dotversion.common.DotVersionConstants;
import com.dotversion.common.util.JedisPoolFactory;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.services.OrgBackupDataService;

public class BackupJobScheduler {
	private static JedisPoolFactory poolFactory = new JedisPoolFactory();
	private static final OrgBackupDataService dbUtils;
	private static Logger logger = LoggerFactory.getLogger(BackupJobScheduler.class);
	static{
		dbUtils = ServiceLoader.load(OrgBackupDataService.class).iterator().next();
	}

	/**
	 * This class will be run every hour (at the top of the hour) 
	 * Will simply query for all Organizations where the Hour_PST is equal
	 * to the current hour and post those org information in the REDIS Queue
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		JedisPool pool=null;
		Jedis jedis=null;
		try {

			pool = poolFactory.getPool();
			jedis = pool.getResource();
			GregorianCalendar local = new GregorianCalendar();
			local.setTimeInMillis(System.currentTimeMillis());
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			for(SalesforceOrg orgToBkup : dbUtils.getOrgsToBackup(local.get(Calendar.HOUR_OF_DAY))){
				String msgJson = mapper.writeValueAsString(orgToBkup);
				jedis.rpush(DotVersionConstants.BACKUP_QUEUE_NAME, msgJson);
				logger.info("[dv-bkup-queue] - "+msgJson);
			}
			
		} finally {
			if(pool!=null && jedis!=null){
				pool.returnResource(jedis);
			}
		}

	}

}
