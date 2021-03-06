package laz.com.laz.design.mode.factory;

/**
 * 工厂模式
 * @author laz
 *
 */
public class Factory {
	public static void main(String[] args) {
		new Factory().new Customer().run();
	}
	public class Customer {  
	    public void run() {  
	        FactoryBMW320 factoryBMW320 = new FactoryBMW320();  
	        BMW320 bmw320 = factoryBMW320.createBMW();  
	  
	        FactoryBMW523 factoryBMW523 = new FactoryBMW523();  
	        BMW523 bmw523 = factoryBMW523.createBMW();  
	    }  
	}  
	
	abstract class BMW {  
	    public BMW(){  
	          
	    }  
	}  
	public class BMW320 extends BMW {  
	    public BMW320() {  
	        System.out.println("制造-->BMW320");  
	    }  
	}  
	public class BMW523 extends BMW{  
	    public BMW523(){  
	        System.out.println("制造-->BMW523");  
	    }  
	} 
	
	interface FactoryBMW {  
	    BMW createBMW();  
	}  
	  
	public class FactoryBMW320 implements FactoryBMW{  
	  
	    @Override  
	    public BMW320 createBMW() {  
	  
	        return new BMW320();  
	    }  
	  
	}  
	public class FactoryBMW523 implements FactoryBMW {  
	    @Override  
	    public BMW523 createBMW() {  
	  
	        return new BMW523();  
	    }  
	}  
}
 