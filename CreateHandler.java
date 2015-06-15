package com.zhs.server;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhs.util.ConfigureHelper;

import edu.kit.aifb.dbe.hermes.AsyncCallbackRecipient;
import edu.kit.aifb.dbe.hermes.IRequestHandler;
import edu.kit.aifb.dbe.hermes.Request;
import edu.kit.aifb.dbe.hermes.Response;


public class CreateHandler
		implements IRequestHandler {


	public Response handleRequest(Request req) {
		// TODO Auto-generated method stub
		
		List<Serializable> items = new ArrayList<Serializable>();
		items = req.getItems();
		String key = (String) items.get(0);
		@SuppressWarnings("unchecked")
		ArrayList<String> value = (ArrayList<String>) items.get(1);
		System.out.println("Got " + value + " for key " + key);
		Storage.getInstance().create(key, value);
		Response resp = new Response(Storage.getInstance().read(key), "Result for create:", true, req);
		System.out.println("Result for create is :" + Storage.getInstance().read(key));
		
		if(!ConfigureHelper.servermap.containsKey(req.getOriginator()))
		{
			TransRequestToOtherServers.getInstance().sendRequest("create", items);
		}
		
		
		// TODO Forward the request according to the replication path
		
		// forward the create request to another server
		//Sender sender = new Sender("localhost", 6000);
		//sender.sendMessageAsync(req, createHandler);

		return resp;
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
