package com.gating.toolconfig.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.springframework.stereotype.Service;
import com.gating.utility.InvalidInputException;

@Service
public class ThresholdConfigService {

  public ThresholdConfig getThresholds() throws IOException, InvalidInputException {

    FileInputStream fileInput = null;
    final File propFile = new File("src/main/resources/threshold.config.properties");

    if(propFile.exists()) {
      fileInput = new FileInputStream(propFile);
    }
    else {
      throw new InvalidInputException("Server Error : threshold config properties file not found", null);
    }

    final Properties prop = new Properties();
    prop.load(fileInput);
    final ThresholdConfig thresholdConfig = new ThresholdConfig();

    thresholdConfig
    .setCyclomaticComplexity(Integer.valueOf(prop.getProperty("cyclomaticComplexity")));
    thresholdConfig.setCodeCoverage(Float.valueOf(prop.getProperty("codeCoverage")));
    thresholdConfig.setTimeToRunTests(Float.valueOf(prop.getProperty("timeToRunTests")));
    thresholdConfig.setNoOfWarnings(Integer.valueOf(prop.getProperty("noOfWarnings")));
    thresholdConfig
    .setSecurityIssuesCount(Integer.valueOf(prop.getProperty("securityIssuesCount")));

    fileInput.close();
    return thresholdConfig;
  }



  public void setThresholds(ThresholdConfig newThresholds) throws IOException {


    final Properties properties = new Properties();
    properties.setProperty("cyclomaticComplexity",
        String.valueOf(newThresholds.getCyclomaticComplexity()));
    properties.setProperty("codeCoverage", String.valueOf(newThresholds.getCodeCoverage()));
    properties.setProperty("timeToRunTests", String.valueOf(newThresholds.getTimeToRunTests()));
    properties.setProperty("noOfWarnings", String.valueOf(newThresholds.getNoOfWarnings()));
    properties.setProperty("securityIssuesCount",
        String.valueOf(newThresholds.getSecurityIssuesCount()));

    final FileOutputStream fileOut =
        new FileOutputStream(new File("src/main/resources/threshold.config.properties"));
    properties.store(fileOut, null);
    fileOut.close();
  }

}
