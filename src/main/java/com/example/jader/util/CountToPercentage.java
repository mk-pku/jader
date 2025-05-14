package com.example.jader.util;

import java.util.List;
import java.util.stream.Collectors;

import com.example.jader.model.NameStatsDto;

public class CountToPercentage {

	public static List<NameStatsDto> process(List<NameStatsDto> rawList) {
		long total = rawList.stream()
			.mapToLong(NameStatsDto::getCount)
			.sum();

		return rawList.stream()
			.map(dto -> {
				String name = dto.getName();
				if (name == null || name.isBlank()) {
					dto.setName("元データ未入力");
				}

				double pct = total == 0
					? 0.0
					: Math.floor((double)dto.getCount() * 100000.0 / total) / 1000.0;
				dto.setPercentage(pct);
				return dto;
			})
			.collect(Collectors.toList());
	}
}
