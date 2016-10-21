package utils;

/**
 * 编辑距离算法
 * Created by ChienHo on 16/10/21.
 */
public class EditDistance{
    public static int distance(boolean[] src1,boolean[] src2){
        int[][] distance=new int[src1.length][src2.length];
        for(int i=0;i<src1.length;i++){
            distance[i][0]=i;
        }
        for(int i=0;i<src2.length;i++){
            distance[0][i]=i;
        }
        for(int i=1;i<src1.length;i++){
            for(int j=1;j<src2.length;j++){
                distance[i][j]=Math.min(Math.min(distance[i-1][j]+1,distance[i][j-1]+1),distance[i-1][j-1]+(src1[i]==src2[j]?0:1));
            }
        }
        return distance[src1.length-1][src2.length-1];
    }
}
