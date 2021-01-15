import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.security.*;

public class User extends Memoizer {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    // functions to memoize definition
    Function<String, Future> findUser = this::getToken;
    Function<String, Future> findUserM = this.doMemoize(findUser);

    public User(int maxSize,String remPolicy){
        super(maxSize,remPolicy);
    }

    // add unit test
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        User user=new User(2,fifo);

        // first test case add a value and compute it once
        Future<String> future1=user.findUserM.apply("andy");
        user.add("andy");
        long startTime = System.currentTimeMillis();
        String token1=future1.get();
        long time1 = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        future1=user.findUserM.apply("andy");
        String token2=future1.get();
        long time2 = System.currentTimeMillis() - startTime;


        System.out.println(token1);
        System.out.println(token2);
        System.out.println(time1);
        System.out.println(time2);

          // second test case adding a null its going to break need to add error handling before calling this function
        //   Future<String> future2=user.findUserM.apply(null);
        //   token1=future2.get();
        //   System.out.println(token1);  
        
        // third test case  test the removal policy with fifo 
        Future<String> future2=user.findUserM.apply("John");
        user.add("John");
        startTime = System.currentTimeMillis();
        token2=future2.get();
        time1 = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        future1=user.findUserM.apply("andy");
        token1=future1.get();
        time2 = System.currentTimeMillis() - startTime;


        System.out.println(token2);
        System.out.println(token1);
        System.out.println(time1);
        System.out.println(time2);
    }

    // this function can be replaced or any other type of function this is only for t
    public Future<String> getToken(String userName){
        return executor.submit(()->  {
            Thread.sleep(1000);
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(new byte[20]);
            return bytes.toString();
        });
    }

    
}
