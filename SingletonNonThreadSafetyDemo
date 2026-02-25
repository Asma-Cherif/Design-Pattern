
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

class NonThreadSafeSingleton {
    // Static instance reference
    private static NonThreadSafeSingleton instance;
    
    // Some state to demonstrate the issue
    private int value;
    
    // Private constructor to prevent direct instantiation
    private NonThreadSafeSingleton() {
        // Simulate some initialization work
        try {
            Thread.sleep(5);  // Makes the race condition more likely to occur
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = (int)(Math.random() * 5);  // Random value to identify different instances
    }
    
    // Non-thread-safe getInstance method
    public static NonThreadSafeSingleton getInstance() {
        if (instance == null) {
            // This critical section is not protected
            instance = new NonThreadSafeSingleton();
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

public class SingletonNonThreadSafetyDemo {
    public static void main(String[] args) {
        // Create a set to store unique instances
        final Set<NonThreadSafeSingleton> instances = Collections.synchronizedSet(new HashSet<>());
        
        // Create a countdown latch to ensure all threads start roughly at the same time
        final CountDownLatch startLatch = new CountDownLatch(1);
        
        // Create a countdown latch to wait for all threads to finish
        final CountDownLatch endLatch = new CountDownLatch(5);
        
        // Create and start 5 threads
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                try {
                    // Wait for the start signal
                    startLatch.await();
                    
                    // Get the singleton instance
                    NonThreadSafeSingleton singleton = NonThreadSafeSingleton.getInstance();
                    
                    // Add the instance to our set
                    instances.add(singleton);
                    
                    // Print the instance
                    System.out.println(Thread.currentThread().getName() + " got instance: " + singleton);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Signal that this thread is done
                    endLatch.countDown();
                }
            });
            thread.setName("Thread-" + i);
            thread.start();
        }
        
        // Start all threads at once
        startLatch.countDown();
        
        try {
            // Wait for all threads to finish
            endLatch.await();
            
            // Check if we have multiple instances
            System.out.println("\nNumber of unique instances created: " + instances.size());
            
            if (instances.size() > 1) {
                System.out.println("\nTHREAD SAFETY VIOLATION: Multiple singleton instances were created!");
                System.out.println("Instances:");
                for (NonThreadSafeSingleton instance : instances) {
                    System.out.println(" - " + instance);
                }
            } else {
                System.out.println("\nOnly one instance was created (we got lucky).");
                System.out.println("Try running the program again to see the thread safety issue.");
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
