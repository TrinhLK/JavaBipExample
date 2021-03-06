package trinhlk.bip.pubsub;

import java.io.IOException;
import java.net.ServerSocket;

import org.bip.engine.*;
import org.bip.engine.api.*;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.junit.*;
import org.junit.Before;

import com.typesafe.config.ConfigFactory;

import trinhlk.bip.api.*;
import trinhlk.bip.glue.TwoSynchronGlueBuilder;
import trinhlk.bip.spec.pubsub.typed.*;
import akka.actor.*;

/*
import org.bip.engine.BIPCoordinatorImpl;
import org.bip.engine.DataCoordinatorKernel;
import org.bip.engine.api.EngineFactory;
import org.bip.executor.impl.akka.OrchestratedExecutorFactory;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.pubsub.typed.ClientProxy;
import org.bip.spec.pubsub.typed.ClientProxyInterface;
import org.bip.spec.pubsub.typed.CommandBuffer;
import org.bip.spec.pubsub.typed.CommandHandler;
import org.bip.spec.pubsub.typed.TCPReader;
import org.bip.spec.pubsub.typed.Topic;
import org.bip.spec.pubsub.typed.TopicInterface;
import org.bip.spec.pubsub.typed.TopicManager;
import org.bip.spec.pubsub.typed.TopicManagerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

import com.typesafe.config.ConfigFactory;
*/
public class NewTCPAcceptor {

	
	ActorSystem system;
	OrchestratedExecutorFactory factory;
	EngineFactory engineFactory;

	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem", ConfigFactory.load());
		factory = new OrchestratedExecutorFactory(system);
		engineFactory = new EngineFactory(system);
	}

	@After
	public void cleanup() {

		system.shutdown();

	}

	@Test
	public void main() {
		int BUFFER_SIZE = 10;

		try {
			ServerSocket tcpacceptor = new ServerSocket(7676);
		BIPGlue temp = (BIPGlue) new DataCoordinatorKernel(new BIPCoordinatorImpl(system));

		BIPEngine engine = engineFactory.create("myEngine", temp);

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

					 synchron(TCPReader.class, "giveCommandToBuffer").to(CommandBuffer.class,
					 "putCommand");
					synchron(CommandBuffer.class, "getCommand").to(CommandHandler.class, "handleCommand");

					 data(TCPReader.class, "readerInput").to(CommandBuffer.class, "input");
					data(CommandBuffer.class, "command").to(CommandHandler.class, "command");
			}

		}.build();

			// bipGlue.toXML(System.out);

			CommandBuffer buffer = new CommandBuffer(BUFFER_SIZE);
			BIPActor actorBuffer = engine.register(buffer, "buffer", true);

			Topic topic1 = new Topic("epfl");
			TopicInterface proxyForTopic1 = (TopicInterface) engine.register(topic1, "topic1", true);

			Topic topic2 = new Topic("concurrence");
			TopicInterface proxyForTopic2 = (TopicInterface) engine.register(topic2, "topic2", true);

			TopicManager top_manager = new TopicManager(proxyForTopic1, proxyForTopic2);
			TopicManagerInterface proxyForManager = (TopicManagerInterface) engine.register(top_manager,
					"topicManager", true);

			CommandHandler handler1 = new CommandHandler(proxyForManager);
			BIPActor commandHandler1 = engine.register(handler1, "commandHandler1", true);

			CommandHandler handler2 = new CommandHandler(proxyForManager);
			BIPActor commandHandler2 = engine.register(handler2, "commandHandler2", true);

			CommandHandler handler3 = new CommandHandler(proxyForManager);
			BIPActor commandHandler3 = engine.register(handler3, "commandHandler3", true);

			CommandHandler handler4 = new CommandHandler(proxyForManager);
			BIPActor commandHandler4 = engine.register(handler4, "commandHandler4", true);

			CommandHandler handler5 = new CommandHandler(proxyForManager);
			BIPActor commandHandler5 = engine.register(handler5, "commandHandler5", true);

			Thread tr = new Thread(new TestPubSub(true));
			tr.start();

			// Thread tr2 = new Thread(new TestPubSub(true));
			// tr2.start();
			//
			// Thread tr3 = new Thread(new TestPubSub(true));
			// tr3.start();

			ClientProxy client1 = new ClientProxy(1, tcpacceptor);
			ClientProxyInterface proxyForClient1 = (ClientProxyInterface) engine.register(client1, "client1", true);

			// ClientProxy client2 = new ClientProxy(2, tcpacceptor);
			// ClientProxyInterface proxyForClient2 = (ClientProxyInterface)
			// engine.register(client2, "client2", true);
			//
			// ClientProxy client3 = new ClientProxy(3, tcpacceptor);
			// ClientProxyInterface proxyForClient3 = (ClientProxyInterface)
			// engine.register(client3, "client3", true);

			TCPReader reader1;
			try {
				reader1 = new TCPReader(client1.getSocket(), 1, buffer, proxyForClient1);
				BIPActor actorReader1 = engine.register(reader1, "tcpReader1", true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TCPReader reader2;
			// try {
			// reader2 = new TCPReader(client2.getSocket(), 2, buffer, proxyForClient2);
			// BIPActor actorReader2 = engine.register(reader2, "tcpReader2", true);
			// } catch (IOException e2) {
			// e2.printStackTrace();
			// }
			//
			// TCPReader reader3;
			// try {
			// reader3 = new TCPReader(client3.getSocket(), 3, buffer, proxyForClient3);
			// BIPActor actorReader3 = engine.register(reader3, "tcpReader3", true);
			// } catch (IOException e3) {
			// e3.printStackTrace();
			// }



		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();

		try {
				Thread.sleep(3000);
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}

			// int transitions = client1.noOfTransitions + client2.noOfTransitions +
			// client3.noOfTransitions;
			// // System.out.println("Number of transitions: " + transitions);
			// assertTrue("Correct number of transitions for client proxys", client1.noOfTransitions
			// == 12);

		engine.stop();
		engineFactory.destroy(engine);
		} catch (IOException e11) {
			System.err.println("Fail to listen on port 7676");
			System.exit(-1);
		}

	}

}
