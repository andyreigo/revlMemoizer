import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collector;

import javax.swing.text.MaskFormatter;

// class is generic so it can accept any type of function and parameters
public class Memoizer<Key,Data> {

    public static final String fifo="FIFO";
    public static final String lfu="LFU";
    public static final String lru="LRU";

    // control the memory size of the cache 
    private  int maxSize=0;
    private  String removePolicy="";

    // use a concurrent hashmap as the data structure
  Map<Key, Data> cache = new ConcurrentHashMap<>();
  LinkedHashSet<Key> queue = new LinkedHashSet<Key>();
  Stack<Key> stack = new Stack<>();

  public Memoizer(){
  }

  public Memoizer(int maxSize,String removePolicy){
      this.maxSize=maxSize;
      this.removePolicy=removePolicy;
  }
  
       // change to aynchronous
   Function<Key, Data> doMemoize( Function<Key, Data> function) {
       if(cache.size()==maxSize){
           removePolicy();
       }
    return input -> cache.computeIfAbsent(input, function::apply);
  }

  private void removePolicy(){
       
    // create constants somewhere so to remove values 
      if(this.removePolicy.toUpperCase().equals("LRU")){
           
      }else if(this.removePolicy.toUpperCase().equals("LFU")){
      
      }else if(this.removePolicy.toUpperCase().equals("FIFO")){
    }
  }

  
/* memory management  
    it should accept various eviction policies options to swap out LRU(remove using timestamp),LFU,FIFO(stack pop ) */
  


}