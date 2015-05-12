package de.lemo.persistence.umed.metamodels;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.lemo.persistence.umed.entities.Config;

import java.sql.Timestamp;

@StaticMetamodel(Config.class)
public class Config_{

	public static volatile SingularAttribute<Config, Timestamp> lastModified;
	public static volatile SingularAttribute<Config, Long> extractCycle;
	public static volatile SingularAttribute<Config, Long> elapsedTime;
	public static volatile SingularAttribute<Config, Long> platform;
	public static volatile SingularAttribute<Config, String> databaseModel;
	public static volatile SingularAttribute<Config, Long> latestTimestamp;

	
}