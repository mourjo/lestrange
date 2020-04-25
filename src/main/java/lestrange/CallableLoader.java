package lestrange;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

public class CallableLoader extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpStatus.OK_200);
		resp.getWriter().println("Use the POST method.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String cl = req.getParameter("classname");

			InputStream x = req.getPart("file").getInputStream();
			SimpleClassLoader hc = new SimpleClassLoader(x);

			@SuppressWarnings("unchecked")
			Class<Callable<Object>> callable = (Class<Callable<Object>>) hc.loadClass(cl);

			Object result = callable.newInstance().call();

			resp.setStatus(HttpStatus.OK_200);
			resp.getWriter().println(result == null ? "Result is a null" : result.toString());

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			resp.getWriter().println("That's too bad... " + e.getMessage());
		}
	}

}
