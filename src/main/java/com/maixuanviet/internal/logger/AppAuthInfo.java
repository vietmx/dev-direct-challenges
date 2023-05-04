package com.maixuanviet.internal.logger;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author VietMX
 */

@Data
public class AppAuthInfo implements Serializable {

    private String requestId;
    private String clientIP;
    private String requestURL;
    private String method;

    public String getPrefixLog() {
        return String.format("RID=%s|IP=%s|URI=%s|Method=%s",
                requestId != null? requestId : UUID.randomUUID().toString(),
                clientIP != null ? clientIP : "127.0.0.1",
                requestURL != null ? requestURL : "NOT_AVAILABLE",
                method != null ? method : "NOT_AVAILABLE"
        );
    }
}
