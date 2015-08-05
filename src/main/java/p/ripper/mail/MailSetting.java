package p.ripper.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Stony Zhang  
 * @E-Mail stonyz@live.com 
 * @QQ 55279427 
 * @Createdate 2009-9-5 
 *
 * @Copyright, everyone can use the code freely, but you must keep the author's information.
 */
public class MailSetting {
	private String smtpHost;
	private String pop3Host;
	private String user ;
	private String password;

	
	public MailSetting(String configFile){
		try {
			InputStream in = new FileInputStream(configFile);
			
			Properties prop = new Properties();
			prop.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MailSetting(Properties pr){
		setPropertiesAttri();
	}
	public MailSetting(){
		setPropertiesAttri();
	}
	
	public MailSetting(String host,String u,String p){
		this.smtpHost=host;
		this.user=u;
		this.password=p;
	}
	
	//private void setPropertiesAttri(Properties prop) {
		private void setPropertiesAttri() {
		try {
//			String conf = AppContest.getStartupPath() + "config/MailSetting.properties";


			this.setSmtpHost("smtp.ym.163.com");
			this.setPop3Host("pop.ym.163.com");
			this.setUser("ripper.jin@cssinco.cn");
			this.setPassword("5cwKdj9qMU");
		} catch (Exception ex) {
			System.err.println("ex1 in MailSetting:" + ex.toString());
		}
	}
	
	
	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getPop3Host() {
		return pop3Host;
	}
	
	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
