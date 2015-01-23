package net.floodlightcontroller.statics;

import java.util.HashMap;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/*
 * 
 *@author yangshuai
 */ 

public class PktInHistoryResource extends ServerResource{
	@Get("json")
	
	public HashMap<String, String> retrieve(){
		IPktinHistoryService pihr = (IPktinHistoryService) getContext()
				.getAttributes().get(IPktinHistoryService.class.getCanonicalName());
		long count = pihr.getPackINCount();
		HashMap<String, String> resp = new HashMap<String, String>() ;
		resp.put("Total", Long.toString(count)) ;
		return resp;
	}
}
