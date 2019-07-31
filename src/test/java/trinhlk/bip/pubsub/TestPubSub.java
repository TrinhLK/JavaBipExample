package trinhlk.bip.pubsub;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
import lsr.concurrence.provided.tests.InputChecker;

public class TestPubSub implements Runnable {
	
	boolean runTest;

	public TestPubSub(boolean b) {
		this.runTest = b;
	}

	public void run() {

		String host = "localhost";
		int port = 7676;

		ArrayList<String> topics = new ArrayList<String>();
		ArrayList<String> msgs = new ArrayList<String>();
		topics.add("epfl");
		topics.add("concurrence");
		msgs.add("bonjour");
		msgs.add("hello");
		int error = 0;

		try {
			Socket connection = new Socket(host, port);

			ClientOutputWriter output = new ClientOutputWriter(connection.getOutputStream());
			InputChecker inputCheck = new InputChecker(new ClientInputReader(connection.getInputStream()));

			// with two topics
			// System.out.println("**** TEST 2 ***");
			// error = 0;
			for (int i = 0; i < 100; i++) {
			output.subscribeTo(topics.get(0));
			output.subscribeTo(topics.get(1));

			output.publish(topics.get(0), msgs.get(0));
			output.publish(topics.get(1), msgs.get(1));

			output.unsubscribeTo(topics.get(0));

			output.publish(topics.get(0), msgs.get(1)); // no check as we are not supposed to
			// receive anything
			output.publish(topics.get(1), msgs.get(0));

			output.unsubscribeTo(topics.get(1));
			}


		} catch (IOException e) {
			System.err.println("Fail to accept client connection");

		}
	}

	public static void main(String[] args) {
		TestPubSub tps = new TestPubSub(true);
		tps.run();
	}
}
