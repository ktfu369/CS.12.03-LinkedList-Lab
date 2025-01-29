package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

import java.util.function.DoubleToIntFunction;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 *
 */
public class ListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        // TODO Auto-generated method stub
        int cnt = 0;
        if(head == null) return 0;
        if(head.next == head) return 1;
        ListNode cur = head;
        while(cur != null){
            cnt++;
            cur = cur.next;
            if(cur == head) break;
        }
        return cnt;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        // TODO Auto-generated method stub
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        // TODO Auto-generated method stub
        ListNode cur = head;
        while(cur != null){
            if(element == cur.element) return true;
            cur = cur.next;
            if(cur == head) break;
        }
        return false;
    }

    @Override
    public int count(ListNode head, Object element) {
        // TODO Auto-generated method stub
        int cnt = 0;
        ListNode cur = head;
        while(cur != null){
            if(element == cur.element) cnt++;
            cur = cur.next;
            if(cur == head) break;
        }
        return cnt;
    }

    @Override
    public String convertToString(ListNode head) {
        // TODO Auto-generated method stub
        String str = "";
        ListNode cur = head;
        while(cur != null){
            if(cur != head){
                str += ",";
            }
            str += cur.element;
            cur = cur.next;
            if(cur == head) break;
        }
        return str;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(n >= size(head) || head == null) throw new InvalidIndexException();
        int cnt = 0;
        ListNode cur = head;
        while(cur != null){
            if(cnt == n) return cur.element;
            cnt++;
            cur = cur.next;
            if(cur == head) break;
        }
        return null;
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(n >= size(head) || head == null) throw new InvalidIndexException();
        ListNode cur = head.previous;
        int cnt=0;
        while(cur != null){
            if(cnt == n) return cur.element;
            cnt++;
            cur = cur.previous;
            if(cur == head.previous) break;
        }
        return null;
    }

    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        ListNode cur1 = head1, cur2 = head2;
        if(size(cur1) != size(cur2)) return false;
        while(cur1!=null || cur2 != null){
            if(!cur1.element.equals(cur2.element)){
                return false;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
            if(cur1 == head1 || cur2 == head2) break;
        }
        return true;

    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        // TODO Auto-generated method stub
        ListNode cur = head;
        if(cur!=null && cur.next == cur) return false;
        while(cur != null){
            ListNode cur2 = cur.next;
            while(cur2!=null && cur2!=head){
                if(cur2.element == cur.element) return true;
                cur2 = cur2.next;
            }
            cur = cur.next;
            if(cur == head) break;
        }
        return false;
    }

    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        // TODO Auto-generated method stub
        node.next = head;
        node.previous = head.previous;
        head.previous.next = node;
        head.previous = node;
        head = node;
        return head;
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        if(head1 == null) return head2;
        if(head2 == null) return head1;
        head1.previous.next = head2;
        head2.previous.next = head1;
        ListNode temp = head1.previous;
        head1.previous = head2.previous;
        head2.previous = temp;
        return head1;
    }

    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(n > size(head)) throw new InvalidIndexException();

        ListNode cur = head;
        int cnt = 0;
        if(n == size(head)){
            node.next = head;
            node.previous = head.previous;
            head.previous.next = node;
            head.previous = node;
            return head;
        }
        while(cur != null){
            if(cnt == n){
                if(n == 0) head = node;
                node.next = cur;
                node.previous = cur.previous;
                cur.previous.next = node;
                cur.previous = node;
            }
            cnt++;
            cur = cur.next;
            if(cur == head) break;
        }
        return head;
    }

    public ListNode delete(ListNode head, Object elem) {
        // TODO Auto-generated method stub
        ListNode cur = head;
        while(cur!=null){
            if(cur.element == elem){
                if(size(head) == 1) return null;
                cur.previous.next = cur.next;
                cur.next.previous = cur.previous;
                if(cur == head) head = cur.next;
                break;
            }
            cur = cur.next;
            if(cur == head) break;
        }

        return head;
    }

    @Override
    public ListNode reverse(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null) return null;
        ListNode cur = head.previous;
        ListNode newHead = cur;
        while(cur != null){
            newHead.next = cur.previous;
            newHead.previous = cur.next;
            cur = cur.previous;
            newHead = newHead.next;
            if(cur == head.previous) break;
        }
        return newHead;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        // TODO Auto-generated method stub
        if(head == null || node == null) throw new InvalidListException();

        ListNode cur = head;
        ListNode last = head.previous;
        ListNode head2 = null;

        while(cur != null){
            if(cur == node){
                if(cur == head) throw new InvalidListException();
                cur.previous.next = head;
                head.previous = cur.previous;

                head2 = cur;
                head2.previous = last;
                last.next = head2;
                break;
            }
            cur = cur.next;
            if(cur == head) break;
        }
        if(head2 == null) throw new InvalidListException();

        ListNode listOfLists;
        listOfLists = new ListNode(null);
        listOfLists.next = new ListNode(null);
        listOfLists.next.previous = listOfLists;
        listOfLists.next.next = listOfLists;
        listOfLists.previous = listOfLists.next;

        ListNode list1,list14;

        list1 = head; list1.next = list1; list1.previous = list1;
        list14 = head2; list14.next = list14; list14.previous = list14;

        listOfLists.element = list1;
        listOfLists.next.element = list14;

        return listOfLists;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        ListNode cur = head;
        while(cur != null){
            cur.element = transformation.transform(cur.element);
            cur = cur.next;
            if(cur == head){
                break;
            }
        }
        return head;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        Object sum = initial;
        ListNode cur = head;
        while(cur!=null){
            sum = operator.operate(sum,cur.element);
            cur = cur.next;
            if(cur == head) break;
        }
        return sum;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        if(head == null) return null;

        ListNode cur = head.previous;
        while(cur!=null){
            if(!condition.isSatisfied(cur.element)){
                head = delete(head,cur.element);
            }
            cur = cur.previous;
            if(head == null) return null;
            if(cur == head.previous) break;
        }

        return head;
    }


}
