package com.example.jader.dto;

/**
 * 有害事象ごとの名称と件数を保持するDTO
 */
public class ReactionTermCountDto {
    private String reactionTerm;
    private long count;

    public ReactionTermCountDto(String reactionTerm, long count) {
        this.reactionTerm = reactionTerm;
        this.count = count;
    }

    public String getReactionTerm() { return reactionTerm; }
    public long getCount() { return count; }
}
