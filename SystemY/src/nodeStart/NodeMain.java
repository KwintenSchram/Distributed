package nodeStart;

import java.io.IOException;
import nameServer.StartNameServer;
import nodeGUI.NodeGUI;

public class NodeMain {

	public static void main(String[] args) 
	{
		String name = "test";
		String mode = "gui";
		final StartNode node1;
		StartNameServer nameserver = null;
		
		for(int i = 0; i < args.length; i++)
		{
			if (args[i].equals("-mode"))
				mode=args[i+1];
			if (args[i].equals("-name"))
				name=args[i+1];
		}
		if (mode.equals("nogui"))
		{
			node1=new StartNode(name);
			node1.startNewNode();
		}
		else if (mode.equals("server"))
		{
			try {
				nameserver = new StartNameServer();
				nameserver.startNameServer();
			} catch (IOException e) {}
		}
		else
		{
			new NodeGUI();
			
		}
	}
}
