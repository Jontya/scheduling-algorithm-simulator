import java.util.*;

public class ProcessQueue<T extends Process>{
    private int size;
    private Queue<T> queue;

    public ProcessQueue(){
        size = 0;
        queue = new LinkedList<>();
    }

    public void push(T process){
        queue.add(process);
        size++;
    }

    public T pop(){
        if(getSize() != 0){
            T temp = queue.remove();
            size--;
            return temp;
        }
        return null;
    }

    public T peek(){
        return queue.peek();
    }

    public int getSize(){
        return size;
    }

}
