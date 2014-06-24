import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.JSONObject;
import org.json.JSONArray;

public class TocHw3 {
	
	private String sentHttpRequest(String url) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		//con.setRequestProperty("Accept-Language", url);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputline = "";
		StringBuffer result = new StringBuffer();
		while((inputline = in.readLine()) != null)
			result.append(inputline + "\n");
		in.close();
		return result.toString();
	}
	
	public static void main(String[] args){
		TocHw3 jj = new TocHw3();
		String url = args[0];
		String result;
		int i;
		try{
			result = jj.sentHttpRequest(url);
			//JSONObject jsonobject = new JSONObject(result);
			//System.out.println(jsonobject);
			JSONArray array = new JSONArray(result);
			Pattern pattern1 = Pattern.compile(".*" + args[1] + ".*");
			Pattern pattern2 = Pattern.compile(".*" + args[2] + ".*");
			Pattern pattern3 = Pattern.compile(".*交易年月.*" + args[3] + ".*");
			int total = 0, numberOfResult = 0;
			for(i = 0; i < array.length(); i++){
				Matcher matcher1 = pattern1.matcher(array.get(i).toString());
				Matcher matcher2 = pattern2.matcher(array.get(i).toString());
				Matcher matcher3 = pattern3.matcher(array.get(i).toString());
				if(matcher1.matches() && matcher2.matches() && matcher3.matches()){
					numberOfResult++;
					total += array.getJSONObject(i).getInt("總價元");
				}
			}
			System.out.println(total / numberOfResult);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
}
