package com.lingzhan.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

/**
 * 节点数据监控
 * 实现状态回调接口
 * Created by 凌战 on 2020/2/14
 */
public class DataMonitor implements AsyncCallback.StatCallback {

    private ZooKeeper zk;
    private String znode;
    boolean dead;
    private DataMonitorListener listener;
    private byte[] prevData;


    public DataMonitor(ZooKeeper zk, String znode, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.listener = listener;
        // 判断节点是否存在
        zk.exists(znode, true, this, null);
    }


    // WatchedEvent: 表示一个标准的事件处理器,其定义了事件通知相关的逻辑,包含
    // 1.KeeperState: 通知状态
    // 2.EventType: 事件类型

    public void handle(WatchedEvent event) {
        // 处理监听事件
        String path = event.getPath();
        // 事件类型
        if (event.getType() == Watcher.Event.EventType.None) {
            // 判定状态
            switch (event.getState()) {
                case SyncConnected:
                    break;
                //过期
                case Expired:
                    dead = true;
                    listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
                    break;
            }
        } else {
            if (path != null && path.equals(znode)) {
                zk.exists(znode, true, this, null);
            }
        }

    }

    // 实现 StatCallback 接口,实现方法处理结果
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case KeeperException.Code.Ok:
                exists = true;
                break;
            case KeeperException.Code.NoNode:
                exists = false;
                break;
            case KeeperException.Code.SessionExpired:
            case KeeperException.Code.NoAuth:
                dead = true;
                listener.closing(rc);
                return;
            default:
                zk.exists(znode, true, this, null);
                return;
        }

        byte[] b = null;
        if (exists) {

            try {
                b = zk.getData(znode, false, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }

        if ((b == null && b != prevData) || (b != null && !Arrays.equals(prevData, b))) {
            listener.exists(b);
            prevData = b;
        }
    }


    // 使用 DataMonitor 需实现DataMonitorListener接口
    public interface DataMonitorListener {
        // 发生改变的节点的状态
        void exists(byte[] data);

        // session 不再合法
        void closing(int rc);

    }
}

