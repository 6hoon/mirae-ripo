package publicBusData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PublicBusMain {

	public static void main(String[] args) {

		// 1.요청 url 생성 -> form의 get 방식(data를 url에 포함하여 전송)
		// StringBuilder -> 처음 생성된 객체에 append로 계속 추가(String 과 같이 새로이 생성하지 않음)
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnNoList?");
		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=JxE3k4DNjm3NTZHl84tOM72AIODxa5etP%2FYG7t8NPIaliz8Ny%2Fs6ODu5Neu%2BUnUy1QBi%2BYvAkd%2BTaDLZLME%2B0Q%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "=" + URLEncoder.encode("25", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("nodeNm", "UTF-8") + "=" + URLEncoder.encode("전통시장", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("nodeNo", "UTF-8") + "=" + URLEncoder.encode("44810", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 2.서버주소에 Connection 객체 생성 및 연결
		URL url = null;
		HttpURLConnection conn = null; // -> DBUtil의 connection 과 같다
		try {
			url = new URL(urlBuilder.toString()); // 웹 서버 주소 action 방식
			conn = (HttpURLConnection) url.openConnection(); // 접속요청(submit)
			conn.setRequestMethod("GET"); // -> get 방식으로
			conn.setRequestProperty("Content-type", "application/json"); // -> content type이 json(html header 와 같은 방식)
			System.out.println("Response code: " + conn.getResponseCode()); // -> 응답코드를 출력해준다
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3.요청내용을 전송 및 응답 처리
		BufferedReader br = null;
		try {
			int statusCode = conn.getResponseCode(); // conn.getResponseCode() : 서버에서 상태코드를 알려주는 값
			System.out.println(statusCode);
			if (statusCode >= 200 && statusCode <= 300) { // 해당 상태코드가 200~300 의 값이면 응답코드를 출력
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream())); // 아닐 경우 error 메세지
			}
			Document doc = parseXML(conn.getInputStream()); // parseXML : 넘어온 응답코드(XML)를 객체로 변환
			// -> Document : org~~ 로해야함

			// a. field 태그객체 목록으로 가져온다.
			NodeList descNodes = doc.getElementsByTagName("item"); // 공공데이터의 경우 item / field 중 하나로 태그 되있음
			// b. Corona19Data List객체 생성
			List<BusInfo> list = new ArrayList<>();
			// c. 각 item 태그의 자식태그에서 정보 가져오기
			for (int i = 0; i < descNodes.getLength(); i++) {
				// item
				Node item = descNodes.item(i);
				BusInfo data = new BusInfo();
				// item 자식태그에 순차적으로 접근
				for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()) {
					System.out.println(node.getNodeName() + " : " + node.getTextContent());

					switch (node.getNodeName()) {
					case "gpslati":
						data.setGpslati(Double.parseDouble(node.getTextContent()));
						break;
					case "gpslong":
						data.setGpslong(Double.parseDouble(node.getTextContent()));
						break;
					case "nodeid":
						data.setNodeid(node.getTextContent());
						break;
					case "nodenm":
						data.setNodenm(node.getTextContent());
						break;
					case "nodeno":
						data.setNodeno(Integer.parseInt(node.getTextContent()));
						break;
					}
				}
				// d. List객체에 추가
				list.add(data);
			}
			// e.최종확인
			for (BusInfo data : list) {
				System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static Document parseXML(InputStream inputStream) { // XML을 객체로 만들어 JAVA가 인식할 수 있게해주는 함수
		DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			doc = objDocumentBuilder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) { // Simple API for XML e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
