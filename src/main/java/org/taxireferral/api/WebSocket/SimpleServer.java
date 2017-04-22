package org.taxireferral.api.WebSocket;

import org.aopalliance.reflect.Class;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class SimpleServer extends WebSocketServer {

	public static WebSocket connection;


	public SimpleServer(InetSocketAddress address) {
        super(address);
	}


	public static Map<String,WebSocket> connections = new HashMap<>();




	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("new connection to " + conn.getRemoteSocketAddress());

		conn.send("Hello from Sumeet Server !");
		SimpleServer.connection = conn;

		System.out.println("Host Address : " + conn.getLocalSocketAddress().getAddress().getHostAddress());

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);

	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {

		System.err.println("an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
	}



	public static void main(String[] args) {

		String host = "localhost";
		String hostSimple = "0.0.0.0";
		int port = 8887;

		SimpleServer server = new SimpleServer(new InetSocketAddress(hostSimple, port));
		server.run();


		System.out.println(server.toString());
	}
}