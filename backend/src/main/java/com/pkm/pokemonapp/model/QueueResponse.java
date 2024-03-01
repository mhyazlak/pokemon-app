package com.pkm.pokemonapp.model;

import com.pkm.pokemonapp.enums.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueResponse {

    private QueueStatus queueStatus;

    private String sessionId;

}
