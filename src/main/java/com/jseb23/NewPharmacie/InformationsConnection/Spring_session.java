package com.jseb23.NewPharmacie.InformationsConnection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Spring_session
{
    @Id
    Long spring_session;
    Long creationTime;
    Long expiry_time;

}
