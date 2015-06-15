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
 *
 *
 *
 */
public class DeleteHandler
implements IRequestHandler {

	public Response handleRequest(Request req) {
		// TODO Auto-generated method stub
		List<Serializable> items = new ArrayList<Serializable>();
		items = req.getItems();
		String key = (String) items.get(0);
		Storage.getInstance().delete(key);
		
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
