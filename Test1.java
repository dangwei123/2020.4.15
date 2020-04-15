给定一个包含正整数、加(+)、减(-)、乘(*)、除(/)的算数表达式(括号除外)，计算其结果。

表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格  。 整数除法仅保留整数部分。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/calculator-lcci
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack=new Stack<>();
        char sign='+';
        int num=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c>='0'){
                num=num*10+(c-'0');
            }
            if((c<'0'&&c!=' ')||i==s.length()-1){
                if(sign=='+'){
                    stack.push(num);
                }else if(sign=='-'){
                    stack.push(-num);
                }else if(sign=='*'){
                    stack.push(stack.pop()*num);
                }else if(sign=='/'){
                    stack.push(stack.pop()/num);
                }
                num=0;
                sign=c;
            }
        }
        int res=0;
        while(!stack.isEmpty()){
            res+=stack.pop();
        }
        return res;
    }
}

实现一个基本的计算器来计算一个简单的字符串表达式的值。

字符串表达式可以包含左括号 ( ，右括号 )，加号 + ，减号 -，非负整数和空格  。
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack=new Stack<>();
        int sign=1;
        int num=0;
        int res=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c>='0'){
                num=num*10+(c-'0');
            }else if(c=='+'){
                res+=num*sign;
                sign=1;
                num=0;
            }else if(c=='-'){
                res+=num*sign;
                sign=-1;
                num=0;
            }else if(c=='('){
                stack.push(res);
                stack.push(sign);
                res=0;
                num=0;
                sign=1;
            }else if(c==')'){
                res+=num*sign;
                res=stack.pop()*res+stack.pop();
                sign=1;
                num=0;
            }
        }
        return res+sign*num;
    }
}

给定数组 A，我们可以对其进行煎饼翻转：我们选择一些正整数 k <= A.length，然后反转 A 的前 k 个元素的顺序。我们要执行零次或多次煎饼翻转（按顺序一次接一次地进行）以完成对数组 A 的排序。

返回能使 A 排序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * A.length 范围内的有效答案都将被判断为正确。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/pancake-sorting
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

class Solution {
    private int[] copy;
    public List<Integer> pancakeSort(int[] A) {
        copy=Arrays.copyOf(A,A.length);
        Arrays.sort(copy);
        List<Integer> res=new ArrayList<>();
        for(int i=A.length-1;i>0;i--){
            int maxindex=findMax(A,i);
            if(maxindex!=-1){
                if(maxindex!=0){
                    reverse(A,0,maxindex);
                    res.add(maxindex+1);
                }
                reverse(A,0,i);
                res.add(i+1);
            }
        }
        return res;
    }
    private int findMax(int[] A,int index){
        int max=copy[index];
        int maxindex=0;
        for(int i=index;i>=0;i--){
           if(A[i]==max){
               maxindex=i;
               break;
           } 
        }
        if(maxindex==index){
            return -1;
        }
        return maxindex;
    }
    private void reverse(int[] A,int left,int right){
        while(left<right){
            int tmp=A[left];
            A[left++]=A[right];
            A[right--]=tmp;
        }
    }
}

给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer,Integer> map=new HashMap<>();
        map.put(0,1);
        int res=0;
        int pre=0;
        for(int i=0;i<nums.length;i++){
            pre+=nums[i];
            int cur=pre-k;
            if(map.containsKey(cur)){
                res+=map.get(cur);
            }
            map.put(pre,map.getOrDefault(pre,0)+1);
        }
        return res;
    }
}