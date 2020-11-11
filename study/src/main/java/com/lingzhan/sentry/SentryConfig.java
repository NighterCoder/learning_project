package com.lingzhan.sentry;

import com.google.common.base.Strings;
import java.io.File;
import java.net.MalformedURLException;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by 凌战 on 2020/11/11
 */
public class SentryConfig {
  // Absolute path to the sentry-site.xml configuration file.
  private final String configFile_;

  // The Sentry configuration. Valid only after calling loadConfig().
  private final Configuration config_;

  public SentryConfig(String configFilePath) {
    configFile_ = configFilePath;
    config_ = new Configuration();
  }

  /**
   * Initializes the Sentry configuration.
   */
  public void loadConfig() {
    if (Strings.isNullOrEmpty(configFile_)) {
      throw new IllegalArgumentException("A valid path to a sentry-site.xml config " +
          "file must be set using --sentry_config to enable authorization.");
    }

    File configFile = new File(configFile_);
    if (!configFile.exists()) {
      String configFilePath = "\"" + configFile_ + "\"";
      throw new RuntimeException("Sentry configuration file does not exist: " +
          configFilePath);
    }

    if (!configFile.canRead()) {
      throw new RuntimeException("Cannot read Sentry configuration file: " +
          configFile_);
    }

    // Load the config.
    try {
      config_.addResource(configFile.toURI().toURL());
    } catch (MalformedURLException e) {
      throw new RuntimeException("Invalid Sentry config file path: " + configFile_, e);
    }
  }

  public Configuration getConfig() {
    return config_;
  }

  public String getConfigFile() {
    return configFile_;
  }
}
