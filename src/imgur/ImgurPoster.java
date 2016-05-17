package imgur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.*;

public class ImgurPoster {

	private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
	private static final String CLIENT_ID = "e871dcf76d86147";
	private static final String IMGUR_VIEW_URL = "http://imgur.com/";
	private static final String NOT_JSON = "Imgur did not respond with JSON.";
	
	public static String postToImgur(String fileName){
		File image = new File(fileName);
		HttpURLConnection conn = null;
		InputStream responseIn = null;
		OutputStream out;
		InputStream in = null;
		
		try{
			conn = (HttpURLConnection) new URL(IMGUR_UPLOAD_URL).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
			out = conn.getOutputStream();
			in = new FileInputStream(image);
			copy(in, out);
			out.flush();
			out.close();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	responseIn = conn.getInputStream();
            	return onInput(responseIn);
			} else{
				responseIn = conn.getErrorStream();
				StringBuilder sb = new StringBuilder();
				Scanner scanner = new Scanner(responseIn);
				while (scanner.hasNext()) {
					sb.append(scanner.next());
				}
				scanner.close();
				String error = sb.toString();
				return formatErrorResponse(error);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			return "Post failed";
		} finally{
			try {
				responseIn.close();
			} catch (Exception ignore) {}
			try {
				conn.disconnect();
			} catch (Exception ignore) {}
			try {
				in.close();
			} catch (Exception ignore) {}
		}
		
	}
	
	private static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[8192];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	
	private static String onInput(InputStream in) throws Exception {
		StringBuilder sb = new StringBuilder();
		Scanner scanner = new Scanner(in);
		while (scanner.hasNext()) {
			sb.append(scanner.next());
		}
		scanner.close();
		String imgurResponse = sb.toString();
		return formatImgurResponse(imgurResponse);
	}
	
	private static String formatImgurResponse(String imgurResponse){
		try{
			JSONObject obj = new JSONObject(imgurResponse);
			String id = obj.getJSONObject("data").getString("id");
			return IMGUR_VIEW_URL + id;
		} catch(JSONException e){
			return NOT_JSON;
		}
	}
	
	private static String formatErrorResponse(String errorResponse){
		try{
			JSONObject obj = new JSONObject(errorResponse);
			String error = obj.getJSONObject("data").getString("error");
			return error;
		} catch(JSONException e){
			return NOT_JSON;
		}
	}
}
