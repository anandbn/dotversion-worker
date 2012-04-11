package com.dotversion.workers;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dotversion.common.util.JedisPoolFactory;
import com.dotversion.models.SalesforceOrg;

public class OrgBackupWorker {

	private static String BACKUP_QUEUE_NAME="backup_queue";
	private static JedisPoolFactory poolFactory = new JedisPoolFactory();
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws InterruptedException, JsonParseException, JsonMappingException, IOException {

		try {

			JedisPool pool = poolFactory.getPool();
			Jedis jedis = pool.getResource();
			while (true) {
				String orgToBackup = jedis.lpop(BACKUP_QUEUE_NAME);
				if(orgToBackup !=null){
					ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
					SalesforceOrg org = mapper.readValue(orgToBackup.getBytes(), SalesforceOrg.class);
					if (org != null) {
					}
				
				}
				Thread.sleep(200);
			}
		} finally {

		}
	}

}
