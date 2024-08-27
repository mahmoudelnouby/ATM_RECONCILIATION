package com.example.ATM_RECONCILIATION.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.ATM_RECONCILIATION.security.enums.SecurityResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class RefererCheckFilter extends OncePerRequestFilter {

    @Value("${referer.name}")
    private String refererName;

    @Value("${mode}")
    private String mode;
    
    private final String[] allowedEndings = {
            "/api/auth",
            "/assets",
            ".js",
            ".js.map",
            ".json",
            ".ico",
            ".html",
            ".css",
            ".woff",
            ".woff2",
            ".ttf"
        };

    private static final Logger logger = LoggerFactory.getLogger(RefererCheckFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {


        if (mode.equals("dev")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String uri = request.getRequestURI();
            String requestReferer = getRequestRefererValue(request.getHeader("Referer"));
                        
            boolean isValidReferer = isValidRefererFn(requestReferer, uri, refererName);
            logger.info("isValidReferer : " + isValidReferer);
//            filterChain.doFilter(request, response);
            if (isValidReferer || endsWithAny(uri, allowedEndings)) {
            	filterChain.doFilter(request, response);
                return;
            }
            response.sendError(HttpStatus.UNAUTHORIZED.value(), SecurityResponseMessage.Invalid_Referer.getMessage());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), SecurityResponseMessage.Invalid_Referer.getMessage());
        }


    }
    
    private String getRequestRefererValue(String requestReferer) {
    	if(requestReferer == null || requestReferer.isEmpty() || requestReferer.equals("null")) {
    		return null;
    	}
    	return requestReferer;
    }

    private boolean endsWithAny(String str, String[] suffixes) {
        for (String suffix: suffixes) {
            if (str.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidRefererFn(String requestReferer, String uri, String refererName) {
    	
        logger.info("requestReferer : " + requestReferer);
        logger.info("refererName : " + refererName);
        logger.info("uri : " + uri);
        logger.info("compareWithoutSlashes(requestReferer, requestReferer) : " + compareWithoutSlashes(refererName, requestReferer));
        logger.info("compareWithoutSlashes(requestReferer, uri) : " + compareWithoutSlashes(refererName, uri));
        
        return	
    		 compareWithoutSlashes(refererName, requestReferer) 
    		 ||  (requestReferer == null && compareWithoutSlashes(refererName, uri)) 
    		 ||  isAssetsFile(uri)   		 
    		 ;
    }
    
    private boolean isAssetsFile(String uri) {
    	return (uri != null &&  ( uri.contains(refererName) || uri.contains("/assets") ) );
    }
    
    private boolean compareWithoutSlashes(String refererName, String requestReferer) {
    	if (refererName == null || requestReferer == null) {
            return false;
        }
        String refererNameWithoutSlashes = refererName.replaceAll("/", "");
        String requestRefererWithoutSlashes = requestReferer.replaceAll("/", "");
        
        return refererNameWithoutSlashes.equals(requestRefererWithoutSlashes);
    }

}