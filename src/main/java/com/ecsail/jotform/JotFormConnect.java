package com.ecsail.jotform;

import com.ecsail.sql.select.SqlApi_key;
import com.ecsail.structures.ApiKeyDTO;

public class JotFormConnect {
    public JotFormConnect() {
        ApiKeyDTO thisApi = SqlApi_key.getApiKeyByName("Jotform API");
        System.out.println(thisApi.getKey());
    }
}
