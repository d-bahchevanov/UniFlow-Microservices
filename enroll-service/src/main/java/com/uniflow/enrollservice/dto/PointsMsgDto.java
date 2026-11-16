package com.uniflow.enrollservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointsMsgDto {
    private Long studentId;
    private int points;
}
