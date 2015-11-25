package p.ripper.beanutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilsTest {
	
	public static void main(String args[])throws Exception{
		
	/*	   Class clss = User.class;
		   User user =(User) clss.newInstance();
		   Field[] fields = user.getClass().getFields();
		   StringBuffer  sb= new StringBuffer();
		   for(Field field: fields){
			   BeanUtils.setProperty(user, field.getName(), "55");
			   sb.append(field.getName()).append("\t : \t").append(BeanUtils.getProperty(user, field.getName())).append("\n");
		   }
		   //using the beanUtils grant the properties
		  System.out.println(sb);*/
		
		User user = new User();
		user.setId("1000");
		user.setUsername("Ripper");
		user.setAge(135);
		getObjectString(user);
		
	}
	
	public static void getObjectString(Object object) throws Exception{
		   Class clazz = User.class;
		   User user =(User) clazz.newInstance();
		   Field[] fields = user.getClass().getFields();
		   StringBuffer  sb= new StringBuffer();
		   for(Field field: fields){
			   if (object instanceof User) {
				  // User u =(User)object;
				   Method method = object.getClass().getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length()));
				   BeanUtils.setProperty(user, field.getName(), method.invoke(object, null) );
				   sb.append(field.getName()).append("\t : \t").append(BeanUtils.getProperty(user, field.getName())).append("\n");
			   }
			   
			   
		   }
		   System.out.println(sb);
		   
	}

}
