package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.Config;

import java.sql.Timestamp;

@StaticMetamodel(Config.class)
public class Config_{

	private static volatile SingularAttribute<Config, Timestamp> lastModified;
	private static volatile SingularAttribute<Config, Long> extractCycle;
	private static volatile SingularAttribute<Config, Long> elapsedTime;
	private static volatile SingularAttribute<Config, Long> platform;
	private static volatile SingularAttribute<Config, String> databaseModel;
	private static volatile SingularAttribute<Config, Long> latestTimestamp;

	
}