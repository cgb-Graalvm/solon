package org.noear.solon.extend.data.tran;

import org.noear.solon.core.Tran;
import org.noear.solon.core.XEventBus;

import java.util.ArrayList;
import java.util.List;

public abstract class DbTranNode implements Tran {
    protected DbTranNode parent;
    protected List<DbTranNode> children = new ArrayList<>();

    @Override
    public void add(Tran slave) {
        if (slave instanceof DbTranNode) {
            DbTranNode node = (DbTranNode) slave;

            node.parent = this;
            this.children.add(node);
        }
    }

    public void commit() throws Throwable {
        for (DbTranNode n1 : children) {
            n1.commit();
        }
    }

    public void rollback() throws Throwable {
        //确保每个子处事，都有机会回滚
        //
        for (DbTranNode n1 : children) {
            try {
                n1.rollback();
            } catch (Throwable ex) {
                XEventBus.push(ex);
            }
        }
    }

    public void close() throws Throwable {
        //确保每个子处事，都有机会关闭
        //
        for (DbTranNode n1 : children) {
            try {
                n1.close();
            } catch (Throwable ex) {
                XEventBus.push(ex);
            }
        }
    }
}
