package com.api.cardlink.dto.response;

import lombok.Data;

@Data
public class TenantResponse {
    private String id;
    private String name;
    private String subdomain;

    public TenantResponse(String id, String name, String subdomain) {
        this.id = id;
        this.name = name;
        this.subdomain = subdomain;
    }
}