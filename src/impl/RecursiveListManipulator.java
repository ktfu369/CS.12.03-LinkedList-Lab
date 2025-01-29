package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null || head.element == null){
            return 0;
        }

        head.previous.next = null;
        int cnt = 1 + size(head.next);
        head.previous.next = head;
        return cnt;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        // TODO Auto-generated method stub
        return (head == null);
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        // TODO Auto-generated method stub
        if(head == null) return false;
        if(head.element.equals(element)){
            return true;
        }

        head.previous.next = null;
        Boolean x = contains(head.next,element);
        head.previous.next = head;
        return x;
    }

    @Override
    public int count(ListNode head, Object element) {
        // TODO Auto-generated method stub
        if(head==null) return 0;
        int count = 0;
        if(head.element == element){
            count = 1;
        }
        head.previous.next = null;
        int f = count(head.next,element);
        head.previous.next = head;
        return f + count;
    }

    @Override
    public String convertToString(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null) return "";
        String x;
        if(head.next == null || head.next == head){
            // only 1 element left
            x = head.element + "";
        }else{
            x = head.element + ",";
        }
        head.previous.next = null;
        String next = convertToString(head.next);
        head.previous.next = head;
        return x + next;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(head == null){
            throw new InvalidIndexException();
        }
        if(n==0){
            return head.element;
        }
        head.previous.next = null;
        Object x = getFromFront(head.next,n-1);
        head.previous.next = head;
        return x;
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(n > size(head) || head == null) throw new InvalidIndexException();
        if(n==0){
            return head.previous.element;
        }
        return getFromBack(head.previous,n-1);
    }

    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        if(size(head1) != size(head2)) return false;
        if(head1 == null || head2 == null) return true;
        if(head1.element != head2.element) return false;
        head1.previous.next = null;
        head2.previous.next = null;
        boolean check = equals(head1.next,head2.next);
        head1.previous.next = head1;
        head2.previous.next = head2;
        return check;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null) return false;

        ListNode cur = head.next;
        while(cur!=head && cur != null){
            if(cur.element == head.element){
                return true;
            }
            cur = cur.next;
        }

        head.previous.next = null;
        boolean x= containsDuplicates(head.next);
        head.previous.next = head;
        return x;

    }
    
    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        // TODO Auto-generated method stub

        node.next = head;
        node.previous = head.previous;
        head.previous.next = node;
        head.previous = node;

        return node;
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

    @Override
    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if(head == null) return head;
        if(n > size(head)) throw new InvalidIndexException();
        if(n == 0){
            return addHead(head,node);
        }
        if(n == size(head)){
            head = append(head,node);
            return head;
        }

        if(n == 1){
            node.next = head.next;
            node.previous = head;
            head.next.previous = node;
            head.next = node;
            return head;
        }

        return insert(head.next,node,n-1);
    }

    @Override
    public ListNode delete(ListNode head, Object elem) {
        // TODO Auto-generated method stub
        if(head.element == elem && size(head)==1) return null;
        if(!contains(head,elem)) return head;
        if(head.element == elem){
            head.previous.next = head.next;
            head.next.previous = head.previous;
            return head.next;
        }
        return delete(head.next,elem);
    }


    @Override
    public ListNode reverse(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null) return null;
        if(head.element == null) return head.next;
        ListNode next = head.next;
        ListNode prev = head.previous;
        head.previous = next;
        head.next = prev;

        Object elem = head.element;
        head.element = null;
        ListNode ans = reverse(head.previous);
        head.element = elem;
        return ans;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        if(head == null) return null;
        if(head.previous == null) return head;
        head.element = transformation.transform(head.element);
        ListNode store = head.previous;
        head.previous = null;
        ListNode ans = map(head.next,transformation);
        head.previous = store;
        return ans;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        if(head == null) return initial;
        head.previous.next = null;
        Object re = reduce(head.next,operator,operator.operate(initial,head.element));
        head.previous.next = head;
        return re;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        if (head == null) return null;

        if (head.next == head) { // only 1 node left
            if (condition.isSatisfied(head.element)) return head;
            return null;
        }
        if (!condition.isSatisfied(head.element)) {
            ListNode next = head.next;
            head.previous.next = head.next;
            head.next.previous = head.previous;
            return filter(next, condition);
        }
        head.next = filter(head.next, condition);
        if (head.next == null) return head;

        head.next.previous = head;
        return head;
    }

}
