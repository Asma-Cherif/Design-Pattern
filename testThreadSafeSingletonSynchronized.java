
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

// Thread-safe Singleton using double-checked locking
class ThreadSafeSingleton {
    // volatile ensures visibility across threads
    private static volatile ThreadSafeSingleton instance;
    
    private int value;
    
    private ThreadSafeSingleton() {
        // Simulate some initialization work
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = (int)(Math.random() * 5);
    }
    
    // Thread-safe getInstance method using double-checked locking
    public static ThreadSafeSingleton getInstance() {
        // First check (not synchronized)
        if (instance == null) {
            // Synchronize only when instance might be null
            synchronized (ThreadSafeSingleton.class) {
                // Second check (synchronized)
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return "Singleton Instance #" + value;
    }
}

public class SingletonThreadSafetyDemo {
    public static void main(String[] args) {
        
        System.out.println("\n\n=== Now testing thread-safe version ===\n");
        
        // Then test the thread-safe version
        testThreadSafeSingleton();
    }
    
    
    private static void testThreadSafeSingletonSynchronized() {
        final Set<ThreadSafeSingleton> instances = Collections.synchronizedSet(new HashSet<>());
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(5);
        
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                try {
                    startLatch.await();
                    ThreadSafeSingleton singleton = ThreadSafeSingleton.getInstance();
                    instances.add(singleton);
                    System.out.println(Thread.currentThread().getName() + " got instance: " + singleton);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            });
            thread.setName("Thread-" + i);
            thread.start();
        }
        
        startLatch.countDown();
        
        try {
            endLatch.await();
            System.out.println("\nThread-safe version - Number of unique instances created: " + instances.size());
            
            if (instances.size() > 1) {
                System.out.println("\nTHREAD SAFETY VIOLATION: Multiple singleton instances were created!");
                System.out.println("Instances:");
                for (ThreadSafeSingleton instance : instances) {
                    System.out.println(" - " + instance);
                }
            } else {
                System.out.println("\nSuccess! Only one instance was created.");
                System.out.println("Instance: " + instances.iterator().next());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



