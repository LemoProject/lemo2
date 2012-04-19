package de.lemo.dms.connectors;

import org.apache.log4j.Logger;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

/**
 * dummy connector with sleep function for connector tests
 * @author Boris Wenzlaff
 *
 */
public class ConnectorDummy implements IConnector {
	private final int SLEEP = (60*100);
	private Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setMiningDBConfig(DBConfigObject dbConf, IDBHandler dbHandler) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean testConnections() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getData() {
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {

			logger.warn("connector dummy throws exception at getData()");
		}
	}

	@Override
	public void updateData(long fromTimestamp) {
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			logger.warn("connector dummy throws exception at updateData()");
		}
	}

}
