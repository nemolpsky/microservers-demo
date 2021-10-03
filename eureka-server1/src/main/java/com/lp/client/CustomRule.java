package com.lp.client;

import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomRule extends ClientConfigEnabledRoundRobinRule {
    private Logger logger = LoggerFactory.getLogger(CustomRule.class);
    private LoadBalancerStats loadBalancerStats;

    public CustomRule() {
    }

    public Server choose(Object key) {
        if (this.loadBalancerStats == null) {
            return super.choose(key);
        } else {
            List<Server> serverList = this.getLoadBalancer().getAllServers();
            logger.info("invoke choose method, and serverList is {}.",serverList);
            Server chosen = null;
            int minimalConcurrentConnections = 2147483647;
            long currentTime = System.currentTimeMillis();
            if (!serverList.isEmpty()){
                Server server = serverList.get(0);
                ServerStats serverStats = this.loadBalancerStats.getSingleServerStat(server);
                if (!serverStats.isCircuitBreakerTripped(currentTime)) {
                    int concurrentConnections = serverStats.getActiveRequestsCount(currentTime);
                    if (concurrentConnections < minimalConcurrentConnections) {
                        chosen = server;
                    }
                }
            }
            if (chosen == null) {
                return super.choose(key);
            } else {
                return chosen;
            }
        }
    }

    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
        if (lb instanceof AbstractLoadBalancer) {
            this.loadBalancerStats = ((AbstractLoadBalancer)lb).getLoadBalancerStats();
        }
    }
}
