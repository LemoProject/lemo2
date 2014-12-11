package de.lemo.launcher;

public interface FileInstallConfig {
	
	public final static String FILENAME = "felix.fileinstall.filename";
	public final static String POLL = "felix.fileinstall.poll";
	public final static String DIR = "felix.fileinstall.dir";
	public final static String LOG_LEVEL = "felix.fileinstall.log.level";
	public final static String LOG_DEFAULT = "felix.fileinstall.log.default";
	public final static String TMPDIR = "felix.fileinstall.tmpdir"; 
	public final static String FILTER = "felix.fileinstall.filter";
	public final static String START_NEW_BUNDLES = "felix.fileinstall.bundles.new.start";
	public final static String USE_START_TRANSIENT = "felix.fileinstall.bundles.startTransient";
	public final static String USE_START_ACTIVATION_POLICY = "felix.fileinstall.bundles.startActivationPolicy";
	public final static String NO_INITIAL_DELAY = "felix.fileinstall.noInitialDelay";
	public final static String DISABLE_CONFIG_SAVE = "felix.fileinstall.disableConfigSave";
	public final static String ENABLE_CONFIG_SAVE = "felix.fileinstall.enableConfigSave";
	public final static String START_LEVEL = "felix.fileinstall.start.level";
	public final static String ACTIVE_LEVEL = "felix.fileinstall.active.level";
	public final static String UPDATE_WITH_LISTENERS = "felix.fileinstall.bundles.updateWithListeners";
	public final static String OPTIONAL_SCOPE = "felix.fileinstall.optionalImportRefreshScope";
	public final static String FRAGMENT_SCOPE = "felix.fileinstall.fragmentRefreshScope";
	public final static String DISABLE_NIO2 = "felix.fileinstall.disableNio2";
	public final static String SCOPE_NONE = "none";
	public final static String SCOPE_MANAGED = "managed";
	public final static String SCOPE_ALL = "all";
	public final static String LOG_STDOUT = "stdout";
	public final static String LOG_JUL = "jul";
	
}
