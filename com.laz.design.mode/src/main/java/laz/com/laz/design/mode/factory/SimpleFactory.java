package laz.com.laz.design.mode.factory;


/**
 * 简单工厂模式
 * @author laz
 *
 */
public class SimpleFactory {
	public BMW createBMW(int type) {  
        switch (type) {  
          
        case 320:  
            return new BMW320();  
  
        case 523:  
            return new BMW523();  
  
        default:  
            break;  
        }  
        return null;  
    }  
	public static void main(String[] args) {
		new SimpleFactory().new Customer().run();
	}
	
	class Customer {  
		public void run() {
			SimpleFactory factory = new SimpleFactory();  
	        BMW bmw320 = factory.createBMW(320);  
	        BMW bmw523 = factory.createBMW(523);  
		}
	}  
	abstract class BMW {  
	    public BMW(){  
	          
	    }  
	}  

	class BMW320 extends BMW {  
	    public BMW320() {  
	        System.out.println("制造-->BMW320");  
	    }  
	}  
	 class BMW523 extends BMW{  
	    public BMW523(){  
	        System.out.println("制造-->BMW523");  
	    }  
	}  
}

 