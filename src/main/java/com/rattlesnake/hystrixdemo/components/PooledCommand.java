package com.rattlesnake.hystrixdemo.components;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

class PooledCommand extends HystrixCommand<String>
{

    protected PooledCommand() {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Download"))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(4)
                                .withMaxQueueSize(20)
                                .withQueueSizeRejectionThreshold(15)
                ));
    }

    @Override
    protected String run() throws Exception {
        return null;
    }
}