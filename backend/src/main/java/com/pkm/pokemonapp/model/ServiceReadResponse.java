package com.pkm.pokemonapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceReadResponse {

    //TODO currently not in use

    /**
     * Item list for current page
     */
    private List<?>	items;

    /**
     * Total item count.
     */
    private long totalCount;
}
