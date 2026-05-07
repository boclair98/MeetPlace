package com.boclair.meetplace.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class ParticipantRequest {

    @NotBlank(message = "참가자 이름을 입력해주세요.")
    private String name;

    @DecimalMin(value = "33.0", message = "위도는 대한민국 범위 안에서 입력해주세요.")
    @DecimalMax(value = "39.5", message = "위도는 대한민국 범위 안에서 입력해주세요.")
    private double latitude;

    @DecimalMin(value = "124.0", message = "경도는 대한민국 범위 안에서 입력해주세요.")
    @DecimalMax(value = "132.0", message = "경도는 대한민국 범위 안에서 입력해주세요.")
    private double longitude;

    public ParticipantRequest() {
    }

    public ParticipantRequest(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
