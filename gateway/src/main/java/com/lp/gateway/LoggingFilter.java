package com.lp.gateway;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 这个日志过滤器只有在进行转发的时候才会走到，也就是说如果请求没有正确的在配置文件中配置，这里是不打印任何东西的。
 */
@Component
@Slf4j
public class LoggingFilter implements GlobalFilter {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("1. localAddress:{}.",exchange.getRequest().getLocalAddress());
        log.info("2. path:{}.",exchange.getRequest().getPath());
        log.info("3. attr:{}.", Optional.ofNullable(exchange.getAttribute(GATEWAY_ROUTE_ATTR)).map(Object::toString).orElse("attr is empty."));
        return chain.filter(exchange);
    }
}
