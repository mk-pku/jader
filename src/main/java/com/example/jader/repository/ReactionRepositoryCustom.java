package com.example.jader.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;

public interface ReactionRepositoryCustom {
	Page<CountResultDto> countByFieldLike(
			String fieldName, String keyword, Pageable pageable);
	
    List<NameStatsDto> statsBy(
    		String filterField,
    		String  groupField,
        String      filterValue,
        int         limit);
}
