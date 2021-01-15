import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

// class is generic so it can accept any type of function and parameters
abstract public class Memoizer<Key,Data>  {

    // have the constants for the remove policies
    public static final String fifo="FIFO";
    public static final String lfu="LFU";
    public static final String lru="LRU";
    // control the memory size of the cache when creating it
    private  int maxSize=0;
    // add the policy 
    private  String removePolicy="";

    // use a concurrent hashmap as it is more safe for asynchronous calls
  Map<Key, Data> cache = new ConcurrentHashMap<>();
  Stack<Key> fifoStack=new Stack<>();
  Queue<Key> queue= new LinkedList<>();
//   Map<Key, Future<Data>> futureCache = new ConcurrentHashMap<>();  tried to implement but didnt understand it enough

  public Memoizer(){
  }

  public Memoizer(int maxSize,String removePolicy){
      this.maxSize=maxSize;
      this.removePolicy=removePolicy;
  }

  public Map<Key, Data> getCache(){
      return cache;
  }
  
       // change to aynchronous
   Function<Key, Data> doMemoize( Function<Key, Data> function){
        return (key)-> key!=null?cache.computeIfAbsent(key, function):null;
  }

  private void removeLRU(){
      // TODO this works like the stack
     Key key=queue.poll();
     cache.remove(key);
  }

  private void removeLFU(){
// TODO implementation 
  }

  private void removeFIFO(){
    Key key= fifoStack.pop();
    cache.remove(key);
  }

  public void add(Key key){
      remove();
    if(this.removePolicy.toUpperCase().equals("FIFO")){
           fifoStack.add(key);
       }
        else{
          queue.add(key);
     }
     
  }

  public void remove(){    
     /* remove from the hashmap if the size is equal to the maxsize
     based on the remove policy call the correspoding method
     */
    if(cache.size()==(maxSize)){
      if(this.removePolicy.toUpperCase().equals("LRU")){
           removeLRU();
      }else if(this.removePolicy.toUpperCase().equals("LFU")){
           removeLFU();
      }else if(this.removePolicy.toUpperCase().equals("FIFO")){
          removeFIFO();
    }
  }
}
}