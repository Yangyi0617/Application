package com.zhs.client;

import org.apache.log4j.Logger;

import com.zhs.util.ConfigureHelper;

import edu.kit.aifb.dbe.hermes.Request;
import edu.kit.aifb.dbe.hermes.Sender;

public class HermesClient {
	private static Logger logger = Logger.getLogger(HermesClient.class);
	
	public static void main(String[] args) {
		Sender sender = new Sender(ConfigureHelper.IP, ConfigureHelper.PORT);
		Request request = new Request("targetA", "targetB");
		
		
		System.out.println("==="+sender.sendMessage(request, ConfigureHelper.TIME_OUT));
//		sender.sendMessageAsync(request, new AsyncCallbackRecipient() {
//			
//			public void callback(Response resp) {
//				System.out.println("异步Request请求成功返回");
//				logger.info("resp.getRequestId()：" + resp.getRequestId());
//			}
//			
//		});	// 异步发送request请求
		
//		sender.setDetailledlogging(true);
	}
}
