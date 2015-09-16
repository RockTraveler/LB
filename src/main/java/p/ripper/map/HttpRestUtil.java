package p.ripper.map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * @author sinaWeibo
 * 
 */
public class HttpRestUtil implements java.io.Serializable {

	static Logger log = Logger.getLogger(HttpRestUtil.class.getName());
	private static final long serialVersionUID = -176092625883595547L;
	private static final int OK 				   = 200;						// OK: Success!
	private static final int NOT_MODIFIED 		   = 304;						// Not Modified: There was no new data to return.
	private static final int BAD_REQUEST 		   = 400;						// Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.
	private static final int NOT_AUTHORIZED 	   = 401;						// Not Authorized: Authentication credentials were missing or incorrect.
	private static final int FORBIDDEN 			   = 403;						// Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why.
	private static final int NOT_FOUND             = 404;						// Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists.
	private static final int NOT_ACCEPTABLE        = 406;						// Not Acceptable: Returned by the Search API when an invalid format is specified in the request.
	private static final int INTERNAL_SERVER_ERROR = 500;						// Internal Server Error: Something is broken.
	private static final int BAD_GATEWAY           = 502;						// Bad Gateway
	private static final int SERVICE_UNAVAILABLE   = 503;						// Service Unavailable:
	private static final String CHARSET="UTF-8";								// URL's charset
	private static final String MAP_URL="http://dev.virtualearth.net/REST/v1/Locations?q=1%20";
	private static final String BING_MAP_KEY="Asbo5OPsckf7p9Qcjxf1GwyJ6pyP6ril9AriyNtFOmFft6C5ZtdyE1I9UUtBf58Q";
	private static final String URL_SURFIX="&o=json&key=";
	
	public static String get(String url) throws RestException{
		return get(url,null);
	}

	/**
	 * get方式请求数据
	 * @param url 请求的url 提供的文档中的RUI
	 * @param maps 请求的参数放到map中
	 * @return
	 * @throws RestException 
	 */
	public static String get(String url, Map<String,Object> maps) throws RestException{
		StringBuffer stringBuffer = new StringBuffer("");
		String getURL="";
		HttpURLConnection connection=null;
		BufferedReader reader =null;
		int responseCode = -1;
		try{
			System.out.println(getURL);
			getURL = MAP_URL+url;
			log.info("Request:");
			log.info("GET：" + getURL);
			System.out.println(getURL);
			URL getUrl = new URL(getURL);
			connection = (HttpURLConnection) getUrl.openConnection();
			responseCode=connection.getResponseCode();
			System.out.println("API Status："+responseCode);
			if ( responseCode== OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET));
				String lines;
				while ((lines = reader.readLine()) != null) {
					stringBuffer.append(lines);
				}
			}else {
				throw new RestException(getCause(responseCode),JSON.parseObject(stringBuffer.toString()), connection.getResponseCode());
			}

		}catch (Exception ioe) {
			throw new RestException(ioe.getMessage(), ioe, responseCode);
		}finally{
			try {
				if (Validator.isNotNull(reader)) {
					reader.close();
				}
				if (Validator.isNotNull(connection)) {
					connection.disconnect();
				}
			} catch (IOException e) {
				log.error("Invoke the API error:",e);
			}
		}
		return stringBuffer.toString();
	}


	/**
	 * POST
	 * @param 
	 * @return
	 * @throws RestException 
	 */
	public static String post(String url) throws RestException{
		return post(url,null);
	}

	/**
	 * use the post to get the data
	 * @param url
	 * @param maps 
	 * @return
	 * @throws RestException 
	 */
	public static String post(String url,Map<String,Object> maps) throws RestException{
		StringBuffer stringBuffer = new StringBuffer("");
		BufferedReader reader = null;
		DataOutputStream out=null;
		HttpURLConnection connection=null;
		int responseCode = -1;
		try{
			URL postUrl = new URL(MAP_URL+ url );
			log.info("Request:");
			log.info("POST：" + url);
			connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();  
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(encodeParameters(maps));
			out.flush();
			out.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET));
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
			responseCode=connection.getResponseCode();
			System.out.println("POST Status："+responseCode);
			if (responseCode != OK) {
				throw new RestException(getCause(responseCode),JSON.parseObject(stringBuffer.toString()), connection.getResponseCode());
			}
		}catch (Exception ioe) {
			throw new RestException(ioe.getMessage(), ioe, responseCode);
		}finally{
			try {
				if (Validator.isNotNull(reader)) {
					reader.close();
				}
				if (Validator.isNotNull(out)) {
					out.close();
				}
				if (Validator.isNotNull(connection)) {
					connection.disconnect();
				}
			} catch (IOException e) {
				log.error("Invoke the API error: ",e);
			}
		}
		return stringBuffer.toString();
	}


	/*
	 * Encode the parameters.
	 */
	public static String encodeParameters(Map<String, Object> paramMap) {
		if (Validator.isNull(paramMap)) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(Map.Entry<String, Object> en : paramMap.entrySet()){ 
			String param = en.getKey(); 
			String value = en.getValue()==null?"": en.getValue()+""; 
			try {
				buf.append(URLEncoder.encode(param, CHARSET))
				.append("=")
				.append(URLEncoder.encode(value,CHARSET)).append("&");
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
		}
		if(! "".equals(buf.toString()))
			buf.deleteCharAt(buf.length()-1);
		return buf.toString();
	}

	private static String getCause(int statusCode) {
		String cause = null;
		switch (statusCode) {
		case NOT_MODIFIED:
			break;
		case BAD_REQUEST:
			cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
			break;
		case NOT_AUTHORIZED:
			cause = "Authentication credentials were missing or incorrect.";
			break;
		case FORBIDDEN:
			cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
			break;
		case NOT_FOUND:
			cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
			break;
		case NOT_ACCEPTABLE:
			cause = "Returned by the Search API when an invalid format is specified in the request.";
			break;
		case INTERNAL_SERVER_ERROR:
			cause = "Something is broken.  Please post to the group so the Weibo team can investigate.";
			break;
		case BAD_GATEWAY:
			cause = "API is down or being upgraded.";
			break;
		case SERVICE_UNAVAILABLE:
			cause = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
			break;
		default:
			cause = "";
		}
		return statusCode + ":" + cause;
	}

	public static void main(String[] args) throws Exception {
		try {
/*			Map<String ,Object>  map = new HashMap<String, Object>();
			HttpRestUtil.post("/yqpt2/api/hotword/weibospot/rt",map);
			/WA/Redmond/1%20Microsoft%20Way?output=xml&key=BingMapsKey
			 http://dev.virtualearth.net/REST/v1/Locations/US/WA/Redmond/1%20Microsoft%20Way?output=xml&key=BingMapsKey**/
			String location="Boston MA".replaceAll(" ", "%20");
			StringBuffer urlStringBuffer = new StringBuffer();
			//urlStringBuffer.append(MAP_URL);
			urlStringBuffer.append(location);
			urlStringBuffer.append(URL_SURFIX);
			urlStringBuffer.append(BING_MAP_KEY);
			System.out.println(urlStringBuffer);
			String json=HttpRestUtil.get(urlStringBuffer.toString());
			System.out.println(json);
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
