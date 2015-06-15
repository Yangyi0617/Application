package com.zhs.server;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zhs.util.ReplicationPath;
import com.zhs.util.ReplicationPath.*;

public class PerserPathXml {

	public static ReplicationPath  getPerserPathXml(String url){
		
		ReplicationPath replicationPath = new ReplicationPath();
		File file = new File(url);
		DocumentBuilder dBuilder;
		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nodeList = doc.getElementsByTagName("path");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				MyPath mypath= replicationPath.new MyPath();
				
				Node node = nodeList.item(i).getAttributes().getNamedItem("start");
				
				mypath.setStart(node.getNodeValue());
				
				NodeList node1= nodeList.item(i).getChildNodes();
				
				for (int j = 0; j < node1.getLength(); j++) {
					
					
					Node linknode = node1.item(j) ;
					
					if(!linknode.getNodeName().equals("link"))
					{
						continue ;
					}
					
					Link link = replicationPath.new Link();
					
					link.setSrc(linknode.getAttributes().getNamedItem("src").getNodeValue());
					
					if(node1.item(j).getAttributes().getNamedItem("type").getNodeValue().equals("quorum")){
						
						NodeList nodeqparticipant = node1.item(j).getChildNodes();
						
						for(int m=0 ; m< nodeqparticipant.getLength(); m++)
						{
							
							Node qparticipant  = nodeqparticipant.item(m) ;
							
							if(qparticipant == null || !qparticipant.getNodeName().equals("qparticipant"))
							{
								continue ;
							}
							
							if(qparticipant.getAttributes().getNamedItem("name") != null)
							{
								Qparticipant qq = replicationPath.new Qparticipant();
								
								qq.setName(qparticipant.getAttributes().getNamedItem("name").getNodeValue() );
							
								link.addQparticipant(qq);
							}
							
						}
					}
					
					if(node1.item(j).getAttributes().getNamedItem("type") != null)
					{
						link.setSrc(node1.item(j).getAttributes().getNamedItem("type").getNodeValue());
					
					}
					
					if(node1.item(j).getAttributes().getNamedItem("target") != null)
					{
						link.setTarget(node1.item(j).getAttributes().getNamedItem("target").getNodeValue());
					}
					
					if(node1.item(j).getAttributes().getNamedItem("qsize") != null)
					{
						link.setQsize(Integer.parseInt(node1.item(j).getAttributes().getNamedItem("qsize").getNodeValue()));
					}
					
					
					mypath.addLink(link);
				}
				
				replicationPath.addPath(mypath);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return replicationPath;
	}
}
