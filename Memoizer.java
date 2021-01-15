import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import javax.swing.text.MaskFormatter;

// class is generic so it can accept any type of function and parameters
public class Memoizer<Key,Data> {

    // control the memory size of the cache 
    private  int maxSize=0;
    private  String removePolicy="";

    // use a concurrent hashmap as the data structure
  Map<Key, Data> cache = new LinkedHashMap <>();

  public Memoizer(){
  }

  public Memoizer(int maxSize,String removePolicy){
      this.maxSize=maxSize;
      this.removePolicy=removePolicy;
  }
  
   Function<Key, Data> doMemoize( Function<Key, Data> function) {
       // change to aynchronous
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