package com.zhs.server;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhs.util.ConfigureHelper;

import edu.kit.aifb.dbe.hermes.AsyncCallbackRecipient;
import edu.kit.aifb.dbe.hermes.IRequestHandler;
import edu.kit.aifb.dbe.hermes.Request;
import edu.kit.aifb.dbe.hermes.Response;

public class UpdateHander 
implements IRequestHandler{

	public Response handleRequest(Request req) {
		// TODO Auto-generated method stub
		
		List<Serializable> items = new ArrayList<Serializable>();
		items = req.getItems();
		String key = (String) items.get(0);
		@SuppressWarnings("unchecked")
		ArrayList<String> value = (ArrayList<String>) items.get(1);
		System.out.println("Update " + value + " for key " + key);
		Storage.getInstance().update(key, value);
		Response resp = new Response(Storage.getInstance().read(key), "Result for update:", true, req);
		System.out.println("Result for Update is :" + Storage.getInstance().read(key));
		
		if(!ConfigureHelper.servermap.containsKey(req.getOriginator()))
		{
		TransRequestToOtherServers.getInstance().sendRequest("delete", items);
		}
		
		return null;
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

