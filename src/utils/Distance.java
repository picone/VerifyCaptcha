package utils;

/**
 * 编辑距离算法
 * Created by ChienHo on 16/10/21.
 */
class Distance {
    /**
     * 编辑距离算法
     * @param src1
     * @param src2
     * @return 相差的距离
     */
    static int editDistance(boolean[] src1,boolean[] src2){
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

    /**
     * 余弦相似度算法
     * @param src1
     * @param src2
     * @return 相似度
     */
    static double cosDistance(boolean[] src1,boolean[] src2){
        if(src1.length!=src2.length)return -1;
        //求点乘
        long point_multi=0;
        for(int i=0;i<src1.length;i++){
            if(src1[i]&&src2[i])point_multi++;
        }
        //求平方和开根号
        long sqrt1=0,sqrt2=0;
        for(boolean val:src1){
            if(val)sqrt1++;
        }
        for(boolean val:src2){
            if(val)sqrt2++;
        }
        return point_multi/(Math.sqrt((double)sqrt1)*Math.sqrt((double)sqrt2));
    }
}
