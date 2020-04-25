package lestrange;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class Main {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		QueuedThreadPool tp = (QueuedThreadPool) server.getThreadPool();
		tp.setLowThreadsThreshold(0);
		tp.setReservedThreads(-1);
		tp.setMinThreads(1);
		tp.setMaxThreads(5);

		ServletContextHandler handler = new ServletContextHandler(server, "/");

		MultipartConfigElement mpc = new MultipartConfigElement("");

		handler.addServlet(ReflectiveLoader.class, "/loadclass/").getRegistration().setMultipartConfig(mpc);

		handler.addServlet(CallableLoader.class, "/callable/").getRegistration().setMultipartConfig(mpc);

		server.start();
		server.join();
	}
}
