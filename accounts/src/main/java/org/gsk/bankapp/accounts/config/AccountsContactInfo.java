package org.gsk.bankapp.accounts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Data
public class AccountsContactInfo {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
