package testre;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.JSONObject;
import org.json.JSONArray;

public class testjson {
	
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
		testjson jj = new testjson();
		String tt = "http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
		String result;
		int i;
		try{
			result = jj.sentHttpRequest(tt);
			//JSONObject jsonobject = new JSONObject(result);
			//System.out.println(jsonobject);
			JSONArray array = new JSONArray(result);
			Pattern pattern1 = Pattern.compile(".*文山區.*辛亥路.*103.*");
			//Pattern pattern2 = Pattern.compile(".*�_���n��.*");
			//Pattern pattern3 = Pattern.compile(".*103.*");
			int total = 0, numberOfResult = 0;
			System.out.println(array.length());
			for(i = 0; i < array.length(); i++){
				Matcher matcher1 = pattern1.matcher(array.get(i).toString());
				//Matcher matcher2 = pattern2.matcher(array.get(i).toString());
				//Matcher matcher3 = pattern3.matcher(array.get(i).toString());
				if(matcher1.matches()){
					System.out.println(matcher1.group(0));
					numberOfResult++;
					total += array.getJSONObject(i).getInt("總價元");
				}
			}
			System.out.println(i);
			System.out.println(total / numberOfResult);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
}
