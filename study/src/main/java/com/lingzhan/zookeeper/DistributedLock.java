package com.lingzhan.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by 凌战 on 2020/2/23
 */
public class DistributedLock implements Lock, Watcher {


    private ZooKeeper zooKeeper;
    private String parentPath;

    private CountDownLatch latch = new CountDownLatch(1);

    private static ThreadLocal<String> currentNodePath = new ThreadLocal<>();

    DistributedLock(String url, int sessionTimeout, String path) {
        try {
            this.parentPath = path;
            this.zooKeeper = new ZooKeeper(url, sessionTimeout, this);
            // 初始化Zookeeper,等待连接
            latch.wait();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void lock() {

        if (tryLock()){
            //获取到锁
            System.out.println(Thread.currentThread().getName() + "成功获取锁! ");
        }else {
            String myPath = currentNodePath.get();
            synchronized (myPath){
                try{
                    myPath.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + "等待锁完成! ");

            lock();
        }


    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    // 尝试获取锁
    @Override
    public boolean tryLock() {

        try {
            String myPath = currentNodePath.get();
            //还没有创建子节点
            if (myPath == null) {
                myPath = zooKeeper.create(parentPath + "/", "dist_lock".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                currentNodePath.set(myPath);

                System.out.println(Thread.currentThread().getName() + "已经创建" + myPath);
            }

            final String currentPath = myPath;
            // 查找该节点下所有子路径
            List<String> allNodes = zooKeeper.getChildren(parentPath, false);
            // 对子节点排序
            Collections.sort(allNodes);
            String nodeName = currentPath.substring((parentPath + "/").length());
            // 子节点中最小的,等于创建的当前新路径
            if (allNodes.get(0).equals(nodeName)) {
                System.out.println(Thread.currentThread().getName() + "tryLock 成功! ");
                return true;
            } else {
                // 注册watch监听路径
                String targetNodeName = parentPath + "/" + allNodes.get(0);

                for (String node:allNodes){
                    if (nodeName.equals(node)){
                        break;
                    }else {
                        targetNodeName=node;
                    }
                }
                targetNodeName=parentPath + "/" + targetNodeName;

                System.out.println(Thread.currentThread().getName() + "需要等待删除节点" + targetNodeName);

                zooKeeper.exists(targetNodeName, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {

                        System.out.println("收到事件: " + event);

                        if (event.getType() == Event.EventType.NodeDeleted) {

                            synchronized (currentPath) {
                                currentPath.notify();
                            }

                            System.out.println(Thread.currentThread().getName() + "获取到NodeDeleted通知,请重新尝试获取锁!");

                        }

                    }
                });

            }


        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    //释放锁
    @Override
    public void unlock() {
        String myPath = currentNodePath.get();
        if (myPath!=null){
            System.out.println(Thread.currentThread().getName() + "释放锁");
            try {
                zooKeeper.delete(myPath,-1);
                currentNodePath.remove();
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent event) {

        System.out.println("收到事件: " + event);

        if (event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.SyncConnected) {
            // 连接上之后
            latch.countDown();
            System.out.println("已经连接成功!");

        }


    }
}
