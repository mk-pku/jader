package com.example.jader.repository;

import java.util.List;

import com.example.jader.model.NameStatsDto;

public interface ReactionRepositoryCustom {
    List<NameStatsDto> statsBy(
        FilterField filterField,
        GroupField  groupField,
        String      filterValue,
        int         limit);
}
