package com.renjin.meituan;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Id;

import java.io.IOException;

/**
 * @author:任锦 Java
 */
public class shopServer {

    private ZooKeeper zkClient;

    private void connectServer() throws IOException {
        String connectString = "192.168.74.128:2181,192.168.74.128:2181,192.168.74.128:2181";
        int sessionTime = 60*1000;
        zkClient = new ZooKeeper(connectString, sessionTime, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("处于监听");
                System.out.println(watchedEvent.getType());
            }
        });
    }

    public void register(String shopName) throws InterruptedException, KeeperException {
        String s = zkClient.create("/meituan/shop", shopName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        shopServer shopServer = new shopServer();
        shopServer.connectServer();
//        String[] arrs = {"baoziShop","baozi"};
        shopServer.register(args[0]);
        shopServer.business(args[0]);
    }

    public void business(String name) throws IOException {
        System.out.println("开始营业"+name);
        System.in.read();
    }
}
