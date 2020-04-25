import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class CallableAPI implements Callable<String> {

	public String call() throws Exception {
		URL url = new URL("http://artii.herokuapp.com/make?text=Lestrange");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setDoOutput(true);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine+"\n");
		}
		
		in.close();
		con.disconnect();
		return content.toString();
	}
}
