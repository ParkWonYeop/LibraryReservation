package com.example.libraryreservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TokenModel {
    @Field("access_token")
    private String accessToken;
    @Field("refresh_token")
    private String refreshToken;
}
