package com.example.calitrainermobi;
import android.icu.text.UFormat;

import mmdeploy.PointF;
import mmdeploy.PoseTracker;

import org.opencv.core.*;
import org.opencv.imgproc.*;

import com.example.calitrainermobi.Trigonometry;

// Using Utility static class to allow use for providing a functionality thats not tied to a specific instance

public class DrawSkeleton_ {

    // Testing Git VCS between laptop and Desktop
    public static void drawPoseTrackerResult(org.opencv.core.Mat frame,mmdeploy.PoseTracker.Result[] results) {

        int skeleton[][] = {
                {15, 13},
                {13, 11},
                {16, 14},
                {14, 12},
                {11, 12},
                {5, 11},
                {6, 12},
                {5, 6},
                {5, 7},
                {6, 8},
                {7, 9},
                {8, 10},
                {1, 2},
                {0, 1},
                {0, 2},
                {1, 3},
                {2, 4},
                {3, 5},
                {4, 6}};


        
        for (int i = 0; i < results.length; i++ ){
            PoseTracker.Result res = results[i];
            float scoreThreshold = 0.5f;
            Boolean used[] = new Boolean[res.keypoints.length * 2];
//            int used[] = new int[res.keypoints.length * 2];




            for(int j = 0; j < skeleton.length; j++) {
                int u = skeleton[j][0];
                int v = skeleton[j][1];




                if (res.scores[u] > scoreThreshold && res.scores[v] > scoreThreshold ){
//                    used[v] = used[u] = 1;
//                    used[u] = used[v] = 1;
                    used[u] = used[v] = Boolean.TRUE;

                    Point pointU = new Point(res.keypoints[u].x, res.keypoints[u].y);
                    Point pointV =  new Point(res.keypoints[v].x, res.keypoints[v].y);
                    Imgproc.line(frame,pointU,pointV,new Scalar(0,222,0),10);

                }
            }

            for (int j =0; j < res.keypoints.length; j++){

//             Getting Elbow join angle
                Point A = new Point(res.keypoints[8].x, res.keypoints[8].y );
                Point B = new Point(res.keypoints[6].x, res.keypoints[6].y );
                Point C = new Point(res.keypoints[10].x, res.keypoints[10].y );


                Point A1 = new Point(res.keypoints[7].x, res.keypoints[7].y );
                Point B2 = new Point(res.keypoints[5].x, res.keypoints[5].y );
                Point C3= new Point(res.keypoints[9].x, res.keypoints[9].y );

//                Imgproc.putText(frame," " + Trigonometry.calcAngle(A1,B2,C3) + "            ",A1,Imgproc.FONT_HERSHEY_COMPLEX,1,new Scalar(0, 0, 255), 1, Imgproc.LINE_AA);
//                Imgproc.putText(frame,"" + Trigonometry.calcAngle(A,B,C),A,Imgproc.FONT_HERSHEY_COMPLEX,1,new Scalar(0, 0, 255), 1, Imgproc.LINE_AA);
                Point pos = new Point(100,100);
                Point pos1 = new Point(100,50);

                Imgproc.putText(frame,"Lshoulder + Lelbow + Lwrist angle =  " + Trigonometry.calcAngle(A1,B2,C3),pos,Imgproc.FONT_HERSHEY_COMPLEX,1,new Scalar(0, 255, 0), 1, Imgproc.LINE_AA);
                Imgproc.putText(frame,"Rshoulder + Relbow + Rwrist angle =  " + Trigonometry.calcAngle(A,B,C),pos1 ,Imgproc.FONT_HERSHEY_COMPLEX,1,new Scalar(0, 255, 0), 1, Imgproc.LINE_AA);


                if (used[j] == Boolean.TRUE){
                    Point point = new Point(res.keypoints[j].x,res.keypoints[j].y);
                    Imgproc.circle(frame,point,3,new Scalar(255,0,0),12);
                }
            }




        }



    }




}
