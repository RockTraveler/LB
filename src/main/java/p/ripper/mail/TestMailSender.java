package p.ripper.mail;

import javax.mail.MessagingException;

public class TestMailSender {

	
	 public static void main(String[] args)   {
		 
		 MailSetting ms = new MailSetting();
		 
		 System.out.println(ms.getUser()+" : " +ms.getPassword());
		
		 MailSender mailSender = new MailSender(ms);
		 String[] destation= new String[]{"2658182@qq.com","atk2009@126.com"};
		 try {
			 mailSender.sentMessage("Interview Test for a  replay","<a href='www.baidu.com'>Confirmation </a>",destation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
