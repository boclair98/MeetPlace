package com.boclair.meetplace.domain;

public enum RecommendationMode {
    BALANCED("균형형", 2.1, 3.2, 4.0),
    SHORT_DISTANCE("짧은 이동 우선", 3.4, 1.5, 3.0),
    FAIRNESS("공정성 우선", 1.6, 5.2, 3.2);

    private final String label;
    private final double averageDistanceWeight;
    private final double fairnessGapWeight;
    private final double centerDistanceWeight;

    RecommendationMode(
            String label,
            double averageDistanceWeight,
            double fairnessGapWeight,
            double centerDistanceWeight
    ) {
        this.label = label;
        this.averageDistanceWeight = averageDistanceWeight;
        this.fairnessGapWeight = fairnessGapWeight;
        this.centerDistanceWeight = centerDistanceWeight;
    }

    public String getLabel() {
        return label;
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
