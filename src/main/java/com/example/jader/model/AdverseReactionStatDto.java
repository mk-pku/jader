package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdverseReactionStatDto {
    private String reactionTerm;
    private Long count;
    private Double percentage;
}
