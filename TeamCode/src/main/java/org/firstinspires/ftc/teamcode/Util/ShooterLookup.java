package org.firstinspires.ftc.teamcode.Util;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.Map;
import java.util.TreeMap;

public class ShooterLookup {
    // Distance (inches) -> Shooter velocity (RPS)
    private static final TreeMap<Double, Double> table = new TreeMap<>();

    //            feet  velocity(radians)  TODO
    static {
        table.put(3.0 / Constants.ConversionConstants.inchesToFeet,  10.6);
        table.put(3.2 / Constants.ConversionConstants.inchesToFeet, 10.8);
        table.put(3.3 / Constants.ConversionConstants.inchesToFeet, 11.0);
        table.put(3.5 / Constants.ConversionConstants.inchesToFeet, 11.2);
    }

    public static double getInterpolatedVelocity(double distanceInches) {
        Map.Entry<Double, Double> lower = table.floorEntry(distanceInches);
        Map.Entry<Double, Double> upper = table.ceilingEntry(distanceInches);

        if (lower == null) return upper.getValue();
        if (upper == null) return lower.getValue();
        if (lower.getKey().equals(upper.getKey())) return lower.getValue();

        double t = (distanceInches - lower.getKey()) / (upper.getKey() - lower.getKey());
        return lower.getValue() + (upper.getValue() - lower.getValue()) * t;
    }
}