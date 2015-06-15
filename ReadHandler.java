package com.zhs.server;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhs.util.ConfigureHelper;

import edu.kit.aifb.dbe.hermes.AsyncCallbackRecipient;
import edu.kit.aifb.dbe.hermes.IRequestHandler;
import edu.kit.aifb.dbe.hermes.Request;
import edu.kit.aifb.dbe.hermes.Response;

/**
 */
public class ReadHandler
implements IRequestHandler{
	public Response handleRequest(Request req) {
		// TODO Auto-generated method stub
		
		List<Serializable> items = new ArrayList<Serializable>();
		
		items = req.getItems();
		
		String key = (String) items.get(0);
		
		System.out.println("The result for Get is" + Storage.getInstance().read(key));
		
		if(!ConfigureHelper.servermap.containsKey(req.getOriginator()))
		{
			TransRequestToOtherServers.getInstance().sendRequest("delete", items);
		}
		
		return new Response(Storage.getInstance().read(key), "Result for Get:", true, req);
		
	}

	public boolean hasPriority() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requiresResponse() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
