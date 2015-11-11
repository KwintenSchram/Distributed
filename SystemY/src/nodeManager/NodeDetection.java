package nodeManager;

import java.net.DatagramPacket;

import fileManagers.FileOwnershipT;
import neworkFunctions.Multicast;
import nodeP.NodeData;

public class NodeDetection extends Thread 
{
	NodeData nodedata1;
	Multicast multi;
	
	public NodeDetection(NodeData nodedata1,Multicast multi)
	{
		this.nodedata1=nodedata1;
		this.multi=multi;
	}
	
	public void run()
	{
		multi.joinMulticastGroup();
		while(nodedata1.getToLeave() == 0)
		{
			DatagramPacket messageIn = multi.receiveMulticast();
			System.out.println("Node communication detected");
			
			if(nodedata1.getToLeave() == 0)
			{
				NodeOrderThread c =new NodeOrderThread(messageIn,nodedata1);
				c.start();

				FileOwnershipT COT =new FileOwnershipT(nodedata1);
				COT.start();
			}
		}
	}

}
