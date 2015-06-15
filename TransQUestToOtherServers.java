package com.zhs.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhs.util.* ;
import com.zhs.util.ReplicationPath.*; 

import edu.kit.aifb.dbe.hermes.Request;
import edu.kit.aifb.dbe.hermes.Sender;

import org.apache.log4j.Logger;

public class TransRequestToOtherServers {

	private static TransRequestToOtherServers instance = null ; 

	private static final Logger log = Logger.getLogger(Storage.class);
	
	private ReplicationPath.MyPath transstrategy;  
	
	public static TransRequestToOtherServers getInstance() {
		
		if(instance == null)
		{
			instance = new TransRequestToOtherServers();
		}
		
		return instance;
	}
	
	public void setTransStrategy(ReplicationPath.MyPath tt)
	{
		transstrategy = tt; 
	}
	
	public void sendRequest(String operate, List<Serializable> details)
	{
		if(transstrategy != null)
		{
			for(int i=0; i<transstrategy.getLink().size(); i++)
			{
				if(transstrategy.getLink().get(i).getType().equals("async"))
				{
					if(transstrategy.getLink().get(i).getSrc().equals(ConfigureHelper.selftname))
					{
						Sender sender = new Sender(ConfigureHelper.servermap.get(transstrategy.getLink().get(i).getTarget()), ConfigureHelper.PORT);
						
						Request request = new Request(operate, ConfigureHelper.selftname);
						
						System.out.println("==="+sender.sendMessage(request, ConfigureHelper.TIME_OUT));
					}
				}
				else if(transstrategy.getLink().get(i).getType().equals("sync"))
				{
					Sender sender = new Sender(ConfigureHelper.servermap.get(transstrategy.getLink().get(i).getTarget()), ConfigureHelper.PORT);
					
					Request request = new Request(operate, ConfigureHelper.selftname);
					
					sender.sendMessageAsync(request, new MyAsyncCallBack()) ;
					
				}
				else if(transstrategy.getLink().get(i).getType().equals("quorum"))
				{
					Integer count = 0 ;
					
					for(int j=0; i<transstrategy.getLink().get(i).getQparticipant().size(); j++)
					{
						Sender sender = new Sender(ConfigureHelper.servermap.get(transstrategy.getLink().get(i).getQparticipant().get(j).getName()), ConfigureHelper.PORT);
						
						Request request = new Request(operate, ConfigureHelper.selftname);
						
						sender.sendMessageAsync(request, new MyAsyncCallBack(count, transstrategy.getLink().get(i).getQsize())) ;
						
					}
				}
			}
		}
	}
}
