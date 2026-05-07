package com.boclair.meetplace.domain;

public enum RecommendationMode {
    BALANCED("균형형", "거리와 공정성을 함께 봅니다.", 2.1, 3.2, 4.0),
    SHORT_DISTANCE("짧은 이동 우선", "전체 평균 이동거리를 더 강하게 줄입니다.", 3.4, 1.5, 3.0),
    FAIRNESS("공정성 우선", "한 사람만 멀어지는 후보지를 낮게 평가합니다.", 1.6, 5.2, 3.2);

    private final String label;
    private final String description;
    private final double averageDistanceWeight;
    private final double fairnessGapWeight;
    private final double centerDistanceWeight;

    RecommendationMode(
            String label,
            String description,
            double averageDistanceWeight,
            double fairnessGapWeight,
            double centerDistanceWeight
    ) {
        this.label = label;
        this.description = description;
        this.averageDistanceWeight = averageDistanceWeight;
        this.fairnessGapWeight = fairnessGapWeight;
        this.centerDistanceWeight = centerDistanceWeight;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageDistanceWeight() {
        return averageDistanceWeight;
    }

    public double getFairnessGapWeight() {
        return fairnessGapWeight;
    }

    public double getCenterDistanceWeight() {
        return centerDistanceWeight;
    }
}
