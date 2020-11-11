package com.lingzhan.leetcode;

/**
 * Created by 凌战 on 2020/9/23
 */
public class TwoNumsAdd {

  public static void main(String[] args) {
    ListNode l1=new ListNode(2);
    l1.next=new ListNode(4);
    l1.next.next=new ListNode(3);

    ListNode l2=new ListNode(5);
    l2.next=new ListNode(6);
    l2.next.next=new ListNode(4);

    System.out.println(addTwoNumbers(l1,l2));

  }

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int num1=list2Num(l1);
    int num2=list2Num(l2);
    int res=num1+num2;
    return num2ListNode(res);
  }

  private static int list2Num(ListNode l){
    int i=0;
    int j=0;
    while(l!=null){
      i=i+(int)java.lang.Math.pow(10,j)*l.val;
      j=j+1;
      l=l.next;
    }
    return i;
  }

  private static ListNode num2ListNode(int num){

    ListNode node=new ListNode(num%10);
    ListNode node1=node.next;
    int n=num/10;
    while(n!=0){
      int val=n%10;
      n=n/10;
      ListNode nextNode=new ListNode(val);
      node1=nextNode;
      node1.next=nextNode;
      nextNode.next=node1;
    }
    return node;
  }


}

class ListNode {
  int val;
  com.lingzhan.leetcode.ListNode next;
  ListNode(int x) { val = x; }
}