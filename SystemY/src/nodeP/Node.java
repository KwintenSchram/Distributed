package nodeP;
import java.net.*;
import java.rmi.RemoteException;

import fileManagers.*;
import neworkFunctions.*;
import nodeManager.*;

public class Node 
{	TCP tcp=new TCP();
	Multicast multi=new Multicast("228.5.6.7", 6789);
	
	public static void main(String[] args) throws Exception
	{		String name="3";
		Node node1=new Node();
		final NodeData nodedata1=new NodeData();
		node1.startNieuweNode(name,nodedata1);
	}
	public void startNieuweNode(String nodeNaam,NodeData nodedata1)
	{
		nodedata1.setNodeName(nodeNaam);
		System.out.println("My name is: "+nodedata1.getNodeName());
		System.out.println("My id is: "+nodedata1.getMyNodeID());

		multi.joinMulticastGroup();
		multi.sendMulticast("0"+"-"+nodedata1.getNodeName());
		multi.LeaveMulticast();

		int numberOfNodes=getNameServerRespons(nodedata1);
		if (numberOfNodes>1)
		{
			String nodes = tcp.receiveTextWithTCP(6770, 5000)[0];
			String[] node = nodes.split("-");
			nodedata1.setPrevNode(Integer.parseInt(node[0]));
			nodedata1.setNextNode(Integer.parseInt(node[1]));
			System.out.println("My: "+nodedata1.getMyNodeID()+" Next: "+nodedata1.getNextNode()+" prev: "+nodedata1.getPrevNode());
		}
		else if(numberOfNodes==1)
		{
			System.out.println("I am the first node");
			 nodedata1.setPrevNode(nodedata1.getMyNodeID());
			 nodedata1.setNextNode(nodedata1.getMyNodeID());
		}
		else if(numberOfNodes==0)
		{
			System.out.println("this node name already exists, please try again with a different name");
			return;
		}
		else
		{
			System.out.println("no nameserver was found");
			return;
		}	
		try {
			RMICommunication rmiCom=new RMICommunication(nodedata1);
			rmiCom.setUpRMI();
		} catch (RemoteException e1) {e1.printStackTrace();}
		FileDetectionT CLFQ =new FileDetectionT(nodedata1);
		CLFQ.start();
		Remover rem =new Remover(nodedata1);
		rem.start();
		 Receiver RQT = new Receiver(nodedata1);
		RQT.start();
		Sender SRFT = new Sender(nodedata1);
		SRFT.start();
		ShutdownT rm = new ShutdownT(nodedata1,CLFQ,rem,RQT,SRFT,multi);
		rm.start();
		NodeDetection nd =new NodeDetection(nodedata1,multi);
		nd.start();
	}

		
	public int getNameServerRespons(NodeData nodedata1) 
		{
		int nodes=-1;
		String[] received=tcp.receiveTextWithTCP(6790, 5000);
		nodedata1.setNameServerIP(received[1]);
		nodes=Integer.parseInt(received[0]);
			return nodes;
		}

}
