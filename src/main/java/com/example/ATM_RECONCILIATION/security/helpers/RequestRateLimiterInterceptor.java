package com.example.ATM_RECONCILIATION.security.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.ATM_RECONCILIATION.security.enums.SecurityResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestRateLimiterInterceptor implements HandlerInterceptor {

    @Value("${numOfReq}")
    private int numOfReq;

    @Value("${timeForReq}")
    private long timeForReq;

    private final Map < String, Long > requestCounts = new ConcurrentHashMap<>();
    
    public String getIPaddress(HttpServletRequest request) throws SocketException {
        return HttpRequestUtils.getRequestIP(request);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = getIPaddress(request);
        if (isRateLimited(ipAddress)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.sendError(429, SecurityResponseMessage.Many_Requests.getMessage());
            return false;
        }
        return true;
    }

    public boolean isRateLimited(String key) {
        Long currentCount = requestCounts.get(key);
        if (currentCount == null) {
            requestCounts.put(key, 1L);
        } else {
            requestCounts.put(key, currentCount + 1);
        }

        return currentCount != null && currentCount >= numOfReq;
    }


    @Scheduled(fixedDelayString = "${timeForReq}")
    public void clearRequestCounts() {
        requestCounts.clear();
    }
}