package SMATP3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MailBox {
    private Hashtable<Integer, List<Message>> box;
    private static MailBox instance;
    
    public static MailBox getInstance() {
    	if (instance == null) {
    		instance = new MailBox();
    	}
    	return instance;
    }
    
    private MailBox() {
    	this.box = new Hashtable<Integer, List<Message>>();
    }
    
    public void addMailer(Agent a) {
    	this.box.put(a.getAgentId(), new ArrayList<Message>());
    }
    
    public void postMessage(Message message) {
    	this.box.get(message.getRecepteur().getAgentId()).add(message);
    }
    
    public List<Message> getMessages(Agent a) {
    	return this.box.get(a.getAgentId());
    }

}
