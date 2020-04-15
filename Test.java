链接：https://www.nowcoder.com/questionTerminal/50959b5325c94079a391538c04267e15
来源：牛客网

在一组数的编码中，若任意两个相邻的代码只有一位二进制数不同， 则称这种编码为格雷码(Gray Code)，请编写一个函数，使用递归的方法生成N位的格雷码。

给定一个整数n，请返回n位的格雷码，顺序为从0开始。
import java.util.*;

public class GrayCode {
    public String[] getGray(int n) {
        if(n==1){
            return new String[]{"0","1"};
        }
        String[] pre=getGray(n-1);
        String[] cur=new String[pre.length*2];
        for(int i=0;i<pre.length;i++){
            cur[i]="0"+pre[i];
            cur[cur.length-1-i]="1"+pre[i];
        }
        return cur;
    }
}

给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。

两个相邻元素间的距离为 1 。
class Solution {
    private int row;
    private int col;
    public int[][] updateMatrix(int[][] matrix) {
        row=matrix.length;
        col=matrix[0].length;
        int[][] res=new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(matrix[i][j]==0){
                    res[i][j]=0;
                }else{
                    res[i][j]=bfs(matrix,i,j);
                }
                
            }
        }
        return res;
    }
    private int bfs(int[][] matrix,int i,int j){
        Set<int[]> set=new HashSet<>();
        Queue<int[]> queue=new LinkedList<>();
        queue.offer(new int[]{i,j});
        set.add(new int[]{i,j});
        int res=0;
        while(!queue.isEmpty()){
            int size=queue.size();
            while(size--!=0){             
                int[] arr=queue.poll();
                int x=arr[0];
                int y=arr[1];
                if(matrix[x][y]==0){
                    return res;
                }
                if(x<row-1&&!set.contains(new int[]{x+1,y})){
                    queue.offer(new int[]{x+1,y});
                    set.add(new int[]{x+1,y});
                }
                if(x>0&&!set.contains(new int[]{x-1,y})){
                    queue.offer(new int[]{x-1,y});
                    set.add(new int[]{x-1,y});
                }
                if(y<col-1&&!set.contains(new int[]{x,y+1})){
                    queue.offer(new int[]{x,y+1});
                    set.add(new int[]{x,y+1});
                }
                if(y>0&&!set.contains(new int[]{x,y-1})){
                    queue.offer(new int[]{x,y-1});
                    set.add(new int[]{x,y-1});
                }
            }     
            res++;     
        }
        return res;
    }
}

设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：

postTweet(userId, tweetId): 创建一条新的推文
getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
follow(followerId, followeeId): 关注一个用户
unfollow(followerId, followeeId): 取消关注一个用户

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/design-twitter
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Twitter {
    private static int timestamp;
    //推特
    private static class Passage{
        private int tweetId;
        private int time;
        private Passage next;
        public Passage(int tweetID,int time){
            this.tweetId=tweetID;
            this.time=time;
        }
    }

    //用户
    private static class User{
        private int id;
        private Set<Integer> followSet;
        private Passage head;
        public User(int id){
            this.id=id;
            followSet=new HashSet<>();
            this.head=null;
            followSet.add(id);
        }

        private void follow(int followerID){
            followSet.add(followerID);
        }

        private void unfollow(int followerID){
            if(this.id!=followerID)
               followSet.remove(followerID);
        }

        private void post(int tweetID){
            Passage p=new Passage(tweetID,timestamp);
            timestamp++;
            p.next=head;
            head=p;
        }
    }

    private Map<Integer,User> map=new HashMap<>();
    /** Initialize your data structure here. */
    public Twitter() {

    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!map.containsKey(userId)){
            //User user=new User(userId);
            map.put(userId,new User(userId));
        }
        //User u=map.get(userId);
        //u.post(tweetId);
        map.get(userId).post(tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res=new ArrayList<>();
        if(!map.containsKey(userId)){
            return res;
        }
        Set<Integer> set=map.get(userId).followSet;
        PriorityQueue<Passage> queue=new PriorityQueue<>(set.size(),new Comparator<Passage>(){
            public int compare(Passage a,Passage b){
                return b.time-a.time;
            }
        });
        for(Integer id:set){
            Passage p=map.get(id).head;
            if(p==null){
                continue;
            }
            queue.offer(p);
        }
        while(!queue.isEmpty()){
            if(res.size()==10){
                break;
            }
            Passage p=queue.poll();
            res.add(p.tweetId);
            if(p.next!=null){
                queue.offer(p.next);
            }
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId)){
            User u=new User(followerId);
            map.put(followerId,u);
        }
        if(!map.containsKey(followeeId)){
            User u=new User(followeeId);
            map.put(followeeId,u);
        }
        //map.get(followerId).followSet.add(followeeId);
        map.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(map.containsKey(followerId)){
            //map.get(followerId).followSet.remove(followeeId);
            map.get(followerId).unfollow(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */