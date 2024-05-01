package com.smartscore.board.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData<T> {





    @Schema(description = "상태 코드", nullable = false, example = "200")
    private String status;

    @Schema(description = "상태 메세지", nullable = false, example = "성공하였습니다.")
    private String message;

    @Schema(description = "데이터", nullable = false)
    private T data;

    @Schema(description = "Access Token", nullable = false)
    private String accessToken;




//	private String name;
//	private List<ResponseDataChild> children;

}
