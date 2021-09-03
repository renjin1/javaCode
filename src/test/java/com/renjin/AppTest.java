package com.renjin;

import org.apache.zookeeper.*;
import org.apache.zookeeper.client.ZKClientConfig;
import org.apache.zookeeper.data.Stat;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.List;


/**
 * Unit test for simple App.
 */
public class AppTest 
{

//    zookeeper的端口和集群
    private String connectString = "192.168.74.128:2181,192.168.74.128:2181,192.168.74.128:2181";
//    session超时时间
    private int sessionTimeout = 60*1000;
//    zookeeper对象
    ZooKeeper zkClient;

    @BeforeTest
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈");
                System.out.println(watchedEvent.getType());
            }
        });
    }

//
    @Test
    public void createNode() throws InterruptedException, KeeperException {
        String king = zkClient.create("/English", "king".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(king);
    }

    @Test
    public void getNodeData() throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        byte[] data = zkClient.getData("/English", true, stat);
        System.out.println(new String(data));
        System.out.println(stat);
    }

    @Test
    public void update() throws InterruptedException, KeeperException {
        Stat stat = zkClient.setData("/English", "king1".getBytes(), 0);
        System.out.println(stat);
    }

    @Test
    public void watch() throws InterruptedException, KeeperException, IOException {
        List<String> children = zkClient.getChildren("/", true);
        System.out.println(children);
        System.in.read();

    }

    @Test
    public void delete() throws Exception {
        zkClient.delete("/English",1);
    }

    @Test
    public void exists() throws Exception {
        Stat exists = zkClient.exists("/china", true);
        System.out.println(exists);
        System.in.read();
    }
}