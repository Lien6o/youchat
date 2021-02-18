package com.youchat.creative.factory.spring;


import org.springframework.beans.factory.annotation.Autowired;

public class InjectServiceImpl implements InjectService{
    /**
     * 这个注解 造成了这个问题。是祸根之所在！！！！
     *
     */
   // @Autowired(required = false)
    private RemoteClient remoteClient;

     public InjectServiceImpl(RemoteClient remoteClient) {
         this.remoteClient = remoteClient;
     }

    @Override
    public void doSomething() {
        System.out.println(remoteClient.toString());
    }
}
