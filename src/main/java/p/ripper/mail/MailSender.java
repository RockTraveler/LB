package p.ripper.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Stony Zhang (Mybeautiful)
 * @Createdate 2010-8-12
 * @Emal:stonyz@live.com
 * @QQ:55279427
 */
public class MailSender {
	private MailSetting mSetting;

	private Session session;

	public MailSender(MailSetting mSetting) {
		this.mSetting = mSetting;
		
		final String username = mSetting.getUser();
		final String password = mSetting.getPassword();
		Properties props = new Properties();
		props.put("mail.smtp.host", mSetting.getSmtpHost());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port",587);
		session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public void sentMessage(String titile, String msg, String[] to) throws MessagingException {
		this.sentMessage(titile, msg, to, null);
	}

	public void sentMessage(String titile, String msg, String[] to, String[] cc) throws MessagingException {
		Message mailMessage = new MimeMessage(session);
		Address from = new InternetAddress(mSetting.getUser());
		mailMessage.setFrom(from);											//We could configured it to a excellent name
		// Address to = new InternetAddress(mSetting.getUser());

		Address[] tos = new Address[to.length];
		for (int i = 0; i < to.length; i++) {
			tos[i] = new InternetAddress(to[i]);
		}
		mailMessage.setRecipients(Message.RecipientType.TO, tos);

		if (cc != null && cc.length > 0) {
			Address[] ccs = new Address[cc.length];
			for (int i = 0; i < cc.length; i++) {
				ccs[i] = new InternetAddress(cc[i]);
			}
			mailMessage.setRecipients(Message.RecipientType.CC, ccs);
		}

		Address[] addresses = new Address[1];
		Address repAddress = new InternetAddress("xiaokan1900@126.com");
		addresses[0]=repAddress;
		mailMessage.setSubject(titile);
		mailMessage.setSentDate(new Date());
		mailMessage.setReplyTo(addresses);
		// mailMessage.setText(msg);

		BodyPart mdp = new MimeBodyPart();
		mdp.setContent(msg, "text/html;charset=gb2312");
		Multipart mm = new MimeMultipart();
		mm.addBodyPart(mdp);
		mailMessage.setContent(mm);
		mailMessage.saveChanges();

		Transport.send(mailMessage);
	}
}
