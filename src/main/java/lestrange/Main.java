package lestrange;

import javax.servlet.MultipartConfigElement;
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

		ServletContextHandler handler = new ServletContextHandler(server, "/loadclass");

		handler.addServlet(LoaderServlet.class, "/").getRegistration()
				.setMultipartConfig(new MultipartConfigElement(""));

		server.start();
		server.join();
	}
}
