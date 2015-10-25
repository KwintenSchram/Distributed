package project;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;

public class NodeThread extends Thread {
	DatagramPacket messageIn;
	String MyNextNode;
	String myPrevNode;
	String MyNodeID;
	public NodeThread(DatagramPacket messageIn, String MyNextNode,String myPrevNode,String MyNodeID)
	{
		this.messageIn=messageIn;
		this.MyNextNode=MyNextNode;
		this.myPrevNode=myPrevNode;
		this.MyNodeID=MyNodeID;
		
	}
	
	public void run() {
		String msg = new String(messageIn.getData(), messageIn.getOffset(), messageIn.getLength());
		InetAddress addr=messageIn.getAddress();
		String nodeIP = addr.getHostAddress().toString();
		
		//TODO hier moet bepaald worden of de nieuwe node een verandering veroorzaakt voor de bestaande node die de thread geopend heeft
		//Indien deze node zijn id < nieuwe id < volgende id
			//1) Update volgende id met de hash van de nieuwe node
			//2) Antwoordt aan de opstartende node met de originele huidige en volgende id met onderstaande code
		/*
		Socket clientSocket;
		try {
		//mss ffkes sleepe zoda nieuwe node eerst tijd heeft gekrege om eerst aantal nodes op te vragen bij nameserver
		
			clientSocket = new Socket(nodeIP,6770);
			DataOutputStream outToNode = new DataOutputStream(clientSocket.getOutputStream());
			outToNode.writeBytes(MyNextNode + "\n");
			clientSocket.close();
		} catch (IOException e) {e.printStackTrace();}	
		*/
		
		//Indien deze node zijn id > nieuwe id > volgende id
			//i. Update vorige id met ID van nieuwe host

	}

}
