package com.smartscore.board.dto;

import java.util.List;

public record User(String email, String password, List<String> roles) {
}