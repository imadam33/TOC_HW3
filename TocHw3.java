/* ******************
 * 姓名：黃奕璁			*
 * 學號：F74002191 	*
 * 					*
 *******************/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.JSONArray;

public class TocHw3 {
	
	private String sentHttpRequest(String url) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputline = "";
		StringBuffer result = new StringBuffer();
		while((inputline = in.readLine()) != null)
			result.append(inputline + "\n");
		in.close();
		return result.toString();
	}
	
	public static void main(String[] args){
		if(args.length != 4)
			System.out.println("arg個數不對");
		TocHw3 hw3 = new TocHw3();
		String url = args[0];
		String result;
		int i;
		try{
			result = hw3.sentHttpRequest(url);
			JSONArray array = new JSONArray(result);
			Pattern pattern1 = Pattern.compile(".*" + args[1] + ".*");
			Pattern pattern2 = Pattern.compile(".*" + args[2] + ".*");
			int total = 0, numberOfResult = 0;
			int year = Integer.valueOf(args[3] + "00");
			for(i = 0; i < array.length(); i++){
				Matcher matcher1 = pattern1.matcher(array.get(i).toString());
				Matcher matcher2 = pattern2.matcher(array.get(i).toString());
				int getyear = array.getJSONObject(i).getInt("交易年月");
				if(matcher1.matches() && matcher2.matches() && getyear >= year){
					numberOfResult++;
					total += array.getJSONObject(i).getInt("總價元");
				}
			}
			if(numberOfResult != 0)
				System.out.println(total / numberOfResult);
			else
				System.out.println("沒有找到人和一筆資料符合要求");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
