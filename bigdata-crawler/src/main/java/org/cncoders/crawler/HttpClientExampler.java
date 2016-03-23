package org.cncoders.crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientExampler {

	private HttpClientConnectionManager httpClientConnectionManager;
	private HttpClient httpclient;

	public void init() {
		//httpClientConnectionManager = new BasicHttpClientConnectionManager();
		HttpClientBuilder builder = HttpClientBuilder.create();
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(5000)
				.build();
		builder.setDefaultSocketConfig(socketConfig);
		builder.setMaxConnPerRoute(10);
		
		//builder.setRetryHandler();
		//builder.setConnectionManager(httpClientConnectionManager);
		
		
		httpclient = builder.build();
	}

	public String post(String url,Map<String,Object> params){
		HttpPost httpPost=new HttpPost(url);
		/**
		 * 设置user-agent
		 */
		httpPost.addHeader(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		int port=9999;// proxy  port
		HttpHost proxy=new HttpHost("your proxy hostName",port);
		
		/**
		 * 设置请求参数，例如超时设置,代理等
		 */
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(5000).setSocketTimeout(5000).setProxy(proxy).build();
		httpPost.setConfig(requestConfig);
		
		try {
			/**
			 * 请求的执行，返回HttpResponse
			 */
			HttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			/**
			 * 获取返回的内容String(官方不建议使用EntityUtils)
			 */
			String content = EntityUtils.toString(entity);
			return content;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public String get(String url) {

		HttpGet httpget = new HttpGet(url);

		/**
		 * 设置user-agent
		 */
		httpget.addHeader(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

		int port=9999;// proxy  port
		
		HttpHost proxy=new HttpHost("your proxy hostName",port);
		
		/**
		 * 设置请求参数，例如超时设置,代理等
		 */
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(5000).setSocketTimeout(5000).setProxy(proxy).build();
		
		httpget.setConfig(requestConfig);

		try {
			/**
			 * 请求的执行，返回HttpResponse
			 */
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			/**
			 * 获取返回的内容String(官方不建议使用EntityUtils)
			 */
			String content = EntityUtils.toString(entity);
			return content;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
