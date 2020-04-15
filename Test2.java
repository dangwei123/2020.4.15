给定两个由一些闭区间组成的列表，每个区间列表都是成对不相交的，并且已经排序。

返回这两个区间列表的交集。

（形式上，闭区间 [a, b]（其中 a <= b）表示实数 x 的集合，而 a <= x <= b。两个闭区间的交集是一组实数，要么为空集，要么为闭区间。例如，[1, 3] 和 [2, 4] 的交集为 [2, 3]。）

 

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/interval-list-intersections
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> list=new ArrayList<>();
        int i=0;
        int j=0;
        while(i<A.length&&j<B.length){
            int a1=A[i][0],a2=A[i][1];
            int b1=B[j][0],b2=B[j][1];
            if(!(a1>b2||b1>a2)){
                list.add(new int[]{Math.max(a1,b1),Math.min(a2,b2)});
            }
            if(a2<b2){
                i++;
            }else{
                j++;
            }
        }
        return list.toArray(new int[list.size()][2]);
    }
}

给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。

请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。

说明:
不允许旋转信封。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/russian-doll-envelopes
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes,new Comparator<int[]>(){
            public int compare(int[] a,int[] b){
                return a[0]==b[0]?b[1]-a[1]:a[0]-b[0];
            } 
        });
        int[] arr=new int[envelopes.length];
        int k=0;
        for(int i=0;i<envelopes.length;i++){
            int left=0;
            int right=k;
            while(left<right){
                int mid=left+(right-left)/2;
                if(arr[mid]<envelopes[i][1]){
                    left=mid+1;
                }else{
                    right=mid;
                }
            }
            arr[left]=envelopes[i][1];
            if(left==k){
                k++;
            }
        }
        return k;
    }
}

