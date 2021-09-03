package com.renjin.meituan;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:任锦 Java
 */
public class Customer {

    static ZooKeeper zkClient;

    public void connectZK() throws IOException {
        String connectString = "192.168.74.128:2181,192.168.74.128:2181,192.168.74.128:2181";
        int SessionTime = 60*1000;
        zkClient = new ZooKeeper(connectString, SessionTime, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听开始");
                System.out.println(watchedEvent.getType());
                try {
                    getShopList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Customer customer = new Customer();
        customer.connectZK();
        customer.getShopList();
        customer.business();
    }

//    获取商家
//    获取子节点，并对父节点监听
    public void getShopList() throws InterruptedException, KeeperException, IOException {
        List<String> shops = zkClient.getChildren("/meituan", true);
        ArrayList<String> list = new ArrayList<>();
        for (String shop : shops) {
            byte[] data = zkClient.getData("/meituan/" + shop, false, new Stat());
            list.add(new String(data));
        }
        System.out.println(list);
    }

    public void business() throws IOException {
        System.out.println("用户在浏览");
        System.in.read();
    }
}
