package com.example.calitrainermobi;

import java.lang.Math;
import java.util.Vector;

import org.opencv.core.Point;

public class Trigonometry {

    public static double calcAngle(Point A, Point B, Point C){

    double result =  Math.atan2(C.y - A.y, C.x - A.x) - Math.atan2(B.y - A.y, B.x - A.x);

    double resultToDegree = result * (180/Math.PI);
    return resultToDegree;
    }


    public static double calcAngle2(Point A, Point B, Point C){

        double result =  Math.atan2(C.y - B.y, C.x - B.x) - Math.atan2(A.y - B.y, A.x - B.x);

        double resultToDegree = Math.abs(result * (180/Math.PI));

        if (resultToDegree > 180.0 ){
            resultToDegree = 360 - resultToDegree;
        }
        return resultToDegree;

    }
}
