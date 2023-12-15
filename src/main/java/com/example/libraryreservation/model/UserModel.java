package com.example.libraryreservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="user")
public class UserModel {
    @Id
    private String userId;

    @Indexed(unique = true)
    @Field("phoneNumber")
    private String phoneNumber;

    @Field("password")
    private String password;

    @Field("name")
    private String name;

    @Field("jwt")
    private TokenModel tokenModel = new TokenModel("","");

    @Field("created_at")
    @CreatedDate
    private LocalDateTime createAt = LocalDateTime.now();

    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updateAt = LocalDateTime.now();
}
