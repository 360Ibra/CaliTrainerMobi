package com.example.calitrainermobi;
import mmdeploy.PointF;
import mmdeploy.PoseTracker;

import org.opencv.core.*;
import org.opencv.imgproc.*;

// Using Utility static class to allow use for providing a functionality thats not tied to a specific instance

public class DrawSkeleton {

// Testing Git VCS between laptop and Desktop
    public static void drawPoseTrackerResult(org.opencv.core.Mat frame,mmdeploy.PoseTracker.Result[] results){

        int skeleton[][] = {{15, 13}, {13, 11}, {16, 14}, {14, 12}, {11, 12}, {5, 11}, {6, 12},
                {5, 6}, {5, 7}, {6, 8}, {7, 9}, {8, 10}, {1, 2}, {0, 1},
                {0, 2}, {1, 3}, {2, 4}, {3, 5}, {4, 6}};

        for(int i = 0; i < results.length; i++){
            System.out.println("KEYPOINTS ARray" +results[i].keypoints);

            PointF[] keypointsArray = results[i].keypoints;

            float[] scores = results[i].scores;

            System.out.println(" Keypoint Prediction Scores"+ results[i].scores);



            for (int j=0; j < keypointsArray.length;j++){
//  Keypoint array length 17

                Point pt = new Point(keypointsArray[j].x,keypointsArray[j].y);

                Imgproc.circle(frame,pt,3,new Scalar(255,0,0),4);
                System.out.println("Look at 9this point"+ pt);

//                TODO
//                Add threshold for keypoints scores
//                System.out.printf("Array Length[%d] Keypoint Coordinates: [%f] [%f]",keypointsArray.length,keypointsArray[j].x,keypointsArray[j].y);
            }

        }
        System.out.println("RESULT LENGTH" + results.length);
//        PointF pt = new PointF(res);




    }
}
