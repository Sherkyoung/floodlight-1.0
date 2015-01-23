package net.floodlightcontroller.statics;

import net.floodlightcontroller.restserver.RestletRoutable;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/*
 * 
 *@author yangshuai
 */ 

public class PktInHistoryWebRoutable implements RestletRoutable{

	public Restlet getRestlet(Context context){
		Router router =new Router(context) ;
		router.attach("/pktinhistory/json", PktInHistoryResource.class) ;
		return router ;
	}
	
	public String basePath(){
		return "/vm/statics" ;
	}
}
