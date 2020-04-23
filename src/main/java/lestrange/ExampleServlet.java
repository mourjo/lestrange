package lestrange;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

public class ExampleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpStatus.OK_200);
		resp.getWriter().println("Use the POST method.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String cl = req.getParameter("classname");
			String m = req.getParameter("methodname");

			InputStream x = req.getPart("file").getInputStream();
			HelloClassLoader hc = new HelloClassLoader(x);
			Class<?> ccc = hc.loadClass(cl);
			
			Object result = null;
			Object k = ccc.newInstance();

			for (Method i : ccc.getDeclaredMethods()) {
				if (i.getName().equals(m)) {
					result = i.invoke(k);
					break;
				}
			}
			
			resp.setStatus(HttpStatus.OK_200);
			resp.getWriter().println(result == null ? "Result is null" : result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			resp.getWriter().println("That's too bad...");
		}
	}
}
