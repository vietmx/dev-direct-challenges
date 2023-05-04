package com.maixuanviet.internal.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

/**
 * @author VietMX
 */

@Slf4j
public class RequestHolder {

    public static String getRequestURI() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest().getRequestURI();
    }

    public static String getRequestId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String requestId = servletRequestAttributes.getRequest().getHeader("RequestId");
        return requestId == null? UUID.randomUUID().toString() : requestId;
    }

    public static String getCookie() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String cookie = servletRequestAttributes.getRequest().getHeader("Cookie");
        return cookie;
    }

    public static String getDomain() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String domain = servletRequestAttributes.getRequest().getHeader("Origin");
        return domain;
    }

    public static String getPlatform() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String platform = servletRequestAttributes.getRequest().getHeader("Platform");

        return platform == null? "NO_INFO" : platform;
    }

    public static String getRequestMethod() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return "";
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest().getMethod();
    }

    public static String getClientIP() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return "";
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

        String remoteAddr = "";

        remoteAddr = servletRequestAttributes.getRequest().getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = servletRequestAttributes.getRequest().getRemoteAddr();
        }

        return remoteAddr;
    }

    public static String getDevice() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String useMobile = servletRequestAttributes.getRequest().getHeader("Sec-CH-UA-Mobile");
        useMobile = useMobile == null? "?0" : useMobile;
        return useMobile.equals("?0")? "Laptop/PC" : "Mobile";
    }

    public static String getOS() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String os = servletRequestAttributes.getRequest().getHeader("Sec-CH-UA-Platform");
        os = os == null? "Linux" : os;
        return os.replace("\"", "").trim();
    }

    public static String getBrowser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String browser = servletRequestAttributes.getRequest().getHeader("Sec-CH-UA");
        if (browser != null) {
            browser = browser.replace("\"", "").replace(";", "_");
            browser = browser.substring(browser.indexOf(",") + 1).trim();
        } else {
            browser = "Firefox";
        }
        return browser;
    }

    public static AppAuthInfo getAuthInfo() {

        AppAuthInfo appAuthInfo = new AppAuthInfo();
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return appAuthInfo;
            }

            appAuthInfo.setRequestId(getRequestId());
            appAuthInfo.setRequestURL(getRequestURI());
            appAuthInfo.setClientIP(getClientIP());
            appAuthInfo.setMethod(getRequestMethod());

        } catch (Exception ex) {
            log.warn("getAuthInfo failed", ex);
        }
        return appAuthInfo;
    }

}
