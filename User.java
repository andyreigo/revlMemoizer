import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.security.*;

public class User {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    // functions to memoize definition
    Function<String, Future> findUser = this::getToken;
    Function<String, Future> findUserM = new Memoizer(3,EvictionPolicyConstants.fifo).doMemoize(findUser);

    // add unit test
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        User user=new User();
        Future<String> futures=user.findUserM.apply("andy");
        long startTime = System.currentTimeMillis();
        String token1=futures.get();
        long time1 = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        String token2=futures.get();
        long time2 = System.currentTimeMillis() - startTime;
        System.out.println(token1);
        System.out.println(token2);
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
