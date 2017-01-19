package com.lvshandian.partylive.moudles.start.bean;

/**
 * 阿里云oss 实体类
 * Created by sll on 2016/11/15.
 */

public class AliYunBean {

    /**
     * securityToken : CAESkwMIARKAAbTBpX5oh/DP5//4sq8ubIJ0qtU/Cgp/tTv2Afapa3AiCaVQBpLN3WGcN/efNBK5zwNhyKJw/fQcvP5E0ODHbzCbPJ/7l2A4mGlOeF68ftvyIU+nxO+6sIbjigUcRYi+fXyUst6sFvUHyjoutIZQR6GelXCZtK6hQGXEtNtpBv75Gh1TVFMuQ01QcWtTMmFqTUpDZWF3NmtjZUtXWHM2cSISMzcwMTU4NjYxMDU0MDQyMDY3KghqYXZhLTAwMTDNrua+his6BlJzYU1ENUJaCgExGlUKBUFsbG93Eh8KDEFjdGlvbkVxdWFscxIGQWN0aW9uGgcKBW9zczoqEisKDlJlc291cmNlRXF1YWxzEghSZXNvdXJjZRoPCg1hY3M6b3NzOio6KjoqShAxODYyMDU2MDE0ODExNjYyUgUyNjg0MloPQXNzdW1lZFJvbGVVc2VyYABqEjM3MDE1ODY2MTA1NDA0MjA2N3IbYWxpeXVub3NzdG9rZW5nZW5lcmF0b3Jyb2xleI6kwpr7sKcD
     * accessKeySecret : GUCMUzxrN9YAuuFVZ7ECafXKbEAAmHupDBhAG55H1J2M
     * accessKeyId : STS.CMPqkS2ajMJCeaw6kceKWXs6q
     * expiration : 2016-11-15T11:57:43Z
     */

    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
