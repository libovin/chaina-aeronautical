package com.hiekn.china.aeronautical.util;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//多线程保存
public class ExecuteSave implements Runnable{
    private List<String> list;
    private CountDownLatch countDownLatch;
    private HttpSession session; //记录成功失败个数
    private String prefixStr;

    public ExecuteSave(List<String> phoneList,CountDownLatch countDownLatch, HttpSession session,String prefixStr) {
        this.list=phoneList;
        this.countDownLatch=countDownLatch;
        this.session = session ;
        this.prefixStr=prefixStr;
    }

    /**
     * request对成功和失败的记录进行记录
     */
    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(1000);
            if (list!=null||list.size()!=0) {
                System.out.println(list);
            }
            int success = Integer.parseInt(session.getAttribute(prefixStr+"Succ").toString());
            session.setAttribute(prefixStr+"Succ", success+=list.size());
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
            countDownLatch.countDown();
            //记录异常日志
            session.setAttribute(prefixStr+"isSuccessed",false); //发生失败记录

        }

    }

}

