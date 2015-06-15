package com.zhs.server;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import com.zhs.util.ReplicationPath;

import edu.kit.aifb.dbe.hermes.IRequestHandler;
import edu.kit.aifb.dbe.hermes.Receiver;
import edu.kit.aifb.dbe.hermes.RequestHandlerRegistry;
import edu.kit.aifb.dbe.hermes.SimpleFileLogger;
import edu.kit.aifb.dbe.hermes.handlers.EchoRequestHandler;

import com.zhs.util.*;

public class HermesServer {
	private static RequestHandlerRegistry req = null;
	// log4j初始化
	static {
		PropertyConfigurator.configure("log4j.properties");
		SimpleFileLogger.getInstance();
		req = RequestHandlerRegistry.getInstance();
	}
	
	// 获取RequestHandlerRegistry实例
	public static RequestHandlerRegistry getRequestHandlerRegistry() {
		if(req == null) {
			req = RequestHandlerRegistry.getInstance();
		}
		return req;
	}
	
	public static void main(String[] args)  {
		
		PerserPathXml pxml = new PerserPathXml() ;
		
		ReplicationPath rpath = pxml.getPerserPathXml("E:/Eclipse/User/Replication4WWJ/ReplicationPath.xml");
		
		RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
		
		TransRequestToOtherServers.getInstance().setTransStrategy(rpath.getPath(ConfigureHelper.selftname));
		
		reg.registerHandler("create", new CreateHandler());
		reg.registerHandler("read", new ReadHandler());
		reg.registerHandler("update", new UpdateHander());
		reg.registerHandler("delete", new DeleteHandler());
		
		try
		{
			Receiver receiver = new Receiver(ConfigureHelper.PORT);
			
			receiver.start();
			
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
