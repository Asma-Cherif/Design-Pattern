/* Singleton Pattern is a creational Pattern
Singleton ensures that only one instance of its kind exists
It provides a single point of access to it

Examples: 
- 1 preseident per country
- 1 DB instance to create objects related to tables (no need to create many instance each time we want to 
    access our tables)
*/

class Singleton {
	// Step1. Create a STATIC member to hold the single instance
	private static Singleton instance = null; 

    // Step 2. create a PRIVATE constructor to force use of getInstance() to create Singleton object
    private Singleton() 
    { 
        System.out.printf("hi from singleton 1"); 
        
    }
	
	//Step 3. create a public static method to access the single instance
	public static Singleton getInstance()
	{
	    // Check if an instance exists. 
	    // Why? to ensure there is only one instance
	    if(instance == null){
  	          instance = new Singleton();
    	  }
	
	return instance; 
	}
}
public class SingletonNonThreadSafe {
	public static void main(String[] args)
	{
		Singleton newInstance1 = Singleton.getInstance();
		//What happens if we create a new instance
		Singleton newInstance2 = Singleton.getInstance();
		
	}
}
