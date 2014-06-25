/*
 * 姓名：黃奕璁
 * 學號：F74002191
 * 
 * 程式執行方式： java -jar TocHw3.jar URL 鄉鎮市區 Road_Name year
 * 輸出結果： 符合要求的資料的總價元的平均值
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.JSONArray;

public class TocHw3 {
	
	//parse網頁的資料，會把資料存成String回傳
	private String sentHttpRequest(String url) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputline = "";
		
		//儲存網頁的資料
		StringBuffer result = new StringBuffer();
		while((inputline = in.readLine()) != null)
			result.append(inputline + "\n");
		in.close();
		return result.toString();
	}
	
	public static void main(String[] args){
		if(args.length != 4)
			System.out.println("arg個數不對");
		else{
			TocHw3 hw3 = new TocHw3();
			String url = args[0];
			String result;
			int i;
			try{
				result = hw3.sentHttpRequest(url);
				
				//把parse到的資料存成JSONArray
				JSONArray array = new JSONArray(result);
				Pattern pattern1 = Pattern.compile(".*" + args[1] + ".*");	//判斷鄉政市區
				Pattern pattern2 = Pattern.compile(".*" + args[2] + ".*");	//判斷土地區段位置或建物區門牌
				int total = 0, numberOfResult = 0;
				int year = Integer.valueOf(args[3] + "00");		//判斷交易年月
				for(i = 0; i < array.length(); i++){
					Matcher matcher1 = pattern1.matcher(array.getJSONObject(i).getString("鄉鎮市區"));
					Matcher matcher2 = pattern2.matcher(array.getJSONObject(i).getString("土地區段位置或建物區門牌"));
					int getyear = array.getJSONObject(i).getInt("交易年月");
					if(matcher1.matches() && matcher2.matches() && getyear >= year){
						numberOfResult++;
						total += array.getJSONObject(i).getInt("總價元");
					}
				}
				if(numberOfResult != 0)
					System.out.println(total / numberOfResult);
				else
					System.out.println("沒有找到任和一筆資料符合要求");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
}
