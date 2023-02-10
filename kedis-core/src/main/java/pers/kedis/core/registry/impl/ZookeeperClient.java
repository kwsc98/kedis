package pers.kedis.core.registry.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import pers.kedis.core.dto.GroupDTO;
import pers.kedis.core.registry.RegistryClient;
import pers.kedis.core.registry.RegistryClientInfo;


import java.util.List;

/**
 * kedis
 * 2022/7/28 15:56
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class ZookeeperClient implements RegistryClient, CuratorCacheListener, ConnectionStateListener {


    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        try {
            log.info("开始初始化zookeeper注册中心");
            //创建curator客户端
            CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(registryClientInfo.getServerAddr(), 5000, 20000, new ExponentialBackoffRetry(1000, 100));
            curatorFramework.start();
            if (curatorFramework.checkExists().forPath("/" + ROOT_PATH) == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath("/" + ROOT_PATH);
            }
            curatorFramework = curatorFramework.usingNamespace(ROOT_PATH);
            curatorFramework.getConnectionStateListenable().addListener(this);
            CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/");
            CuratorCacheListener listener = CuratorCacheListener.builder().forAll(this).build();
            curatorCache.listenable().addListener(listener);
            curatorCache.start();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException();
        }
    }

    @Override
    public void doRefresh(List<GroupDTO> list) {

    }

    @Override
    public void event(Type type, ChildData oldData, ChildData data) {
     
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        
    }
}
