package org.noear.solon.extend.data.tran;

import org.noear.solon.core.Tran;
import org.noear.solon.ext.RunnableEx;

public class TranNeverImp implements Tran {
    public TranNeverImp() {

    }

    @Override
    public void apply(RunnableEx runnable) throws Throwable {
        //获取当前事务
        //
        if (DbTranUtil.current() != null) {
            //绝不能有事务
            throw new RuntimeException("Never support transactions");
        } else {
            runnable.run();
        }
    }
}

