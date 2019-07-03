package com.onchain.tademo.model;

import lombok.*;

@Data
@NoArgsConstructor
public class IssuerIdentityDto {

    private String ontid;

    private String name;

    private String password;

    private String salt;

    @Builder
    public IssuerIdentityDto(String ontid, String name, String password, String salt) {
        this.ontid = ontid;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }
}
