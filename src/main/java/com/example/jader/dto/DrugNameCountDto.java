package com.example.jader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor を含む
@NoArgsConstructor
@AllArgsConstructor
public class DrugNameCountDto {
	private String drugName;
	private Long count; // COUNT(*) の結果は Long 型で受けるのが一般的
}