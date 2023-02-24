package pers.kedis.core.registry.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.yaml.snakeyaml.Yaml;
import pers.kedis.core.KedisProperties;
import pers.kedis.core.KedisService;
import pers.kedis.core.dto.KedisConfig;
import pers.kedis.core.exception.KedisException;
import pers.kedis.core.registry.RegistryClient;
import pers.kedis.core.registry.RegistryClientInfo;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;


/**
 * @author kwsc98
 */
@Slf4j
public class ConfigurationClient extends FileAlterationListenerAdaptor implements RegistryClient {


    private final KedisService kedisService;

    private String configFileName = "";


    public ConfigurationClient(KedisService kedisService) {
        this.kedisService = kedisService;
    }


    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        log.info("ConfigurationClient Init Start");
        try {
            String filePath = registryClientInfo.getServerAddr();
            String[] pathArray = filePath.split("/");
            configFileName = pathArray[pathArray.length - 1];
            File file = new File(filePath);
            KedisConfig kedisConfig = getKedisConfig(file);
            doRefresh(kedisConfig);
            StringBuilder stringBuilder = new StringBuilder("/");
            for (int i = 0; i < pathArray.length - 1; i++) {
                stringBuilder.append(pathArray[i]).append("/");
            }
            FileAlterationObserver observer = new FileAlterationObserver(new File(stringBuilder.toString()));
            observer.addListener(this);
            FileAlterationMonitor monitor = new FileAlterationMonitor(1000, observer);
            monitor.start();
        } catch (Exception e) {
            log.error("ConfigurationClient Monitor Error",e);
            throw new KedisException();
        }
        log.info("ConfigurationClient Init Done");
    }


    @Override
    public void doRefresh(KedisConfig kedisConfig) {
        kedisService.refresh(kedisConfig);
    }


    private KedisConfig getKedisConfig(File file) {
        try {
            return new KedisConfig();
        } catch (Exception e) {
            log.error("registrationGroupList Error : {}", e.toString(), e);
            throw new RuntimeException();
        }
    }

    /**
     * File changed Event.
     *
     * @param file The file changed (ignored)
     */
    @Override
    public void onFileChange(final File file) {
        if (!file.isFile() || !file.getName().equals(configFileName)) {
            log.info("ConfigurationClient Monitor Change But Not ConfigFile");
            return;
        }
        log.info("ConfigurationClient Monitor Change Start");
        KedisConfig kedisConfig = getKedisConfig(file);
        doRefresh(kedisConfig);
        log.info("ConfigurationClient Monitor Change Done");
    }


}
