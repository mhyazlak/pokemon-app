package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@Table(name = "Authority", schema = "dbo")
public class AuthorityDTO implements Serializable {

    private static final long serialVersionUID = 4869392400353269847L;

    @Id
    @Getter
    @JsonProperty("id")
    @Column(name = "[id]", nullable = false)
    private long id;

    @JsonProperty("user_id")
    @Column(name = "[user_id]", nullable = false)
    private long userId;

    @JsonProperty("authority")
    @Column(name = "[authority]", nullable = false)
    private String authority;



}