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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PublicBusMain2 {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ArrayList<BusInfo> busInfoList = null;
		boolean flag = false;
		while (!flag) {
			System.out.println("1. 웹 정보 가져오기, 2. 저장하기, 3. 테이블 읽어오기, 4. 수정하기, 5. 삭제하기, 6. 종료");
			System.out.print("메뉴 선택>> ");
			int count = Integer.parseInt(sc.nextLine());
			switch (count) {
			case 1:
				busInfoList = webConnection();
				break;
			case 2:
				if (busInfoList.size() < 1) {
					System.out.println("공공데이터로부터 가져온 자료가 없습니다");
					continue;
				}
				insertBusInfo(busInfoList);
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				flag = true;
				break;
			}

		} // while
		System.out.println("end");
	}// main

	// 2. 공공데이터를 테이블에 저장하기
	public static void insertBusInfo(ArrayList<BusInfo> busInfoList) {
		if (busInfoList.size() < 1) {
			System.out.println("입력할 데이터가 없습니다");
			return;
		}
		for (BusInfo data : busInfoList) {

		}
		String sql = "insert into businfo values( ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, svo.getS_num());
//			pstmt.setString(2, svo.getS_name());
//			int value = pstmt.executeUpdate();
//
//			if (value == 1) {
//				System.out.println(svo.getS_name() + "학과 등록완료");
//			} else {
//				System.out.println(svo.getS_name() + "학과 등록실패");
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 1. 공공데이터 호출
	public static ArrayList<BusInfo> webConnection() {
		// 1. 요청 url 생성
		ArrayList<BusInfo> list = new ArrayList<>();
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnNoList");
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
		// 2.서버주소 Connection con
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlBuilder.toString()); // 웹서버주소 action
			conn = (HttpURLConnection) url.openConnection(); // 접속요청
			conn.setRequestMethod("GET"); // get방식
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 3. 요청내용을 전송 및 응답처리
		BufferedReader br = null;
		try {
			// conn.getResponseCode() 서버에서 상태코드를 알려주는 값
			int statusCode = conn.getResponseCode();
			System.out.println(statusCode);
			if (statusCode >= 200 && statusCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			Document doc = parseXML(conn.getInputStream());
			// a. Item 태그객체 목록으로 가져온다.
			NodeList descNodes = doc.getElementsByTagName("item");
			// b. BusInfo List객체 생성 -> 맨 앞으로

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
		return list;
	}

	public static Document parseXML(InputStream inputStream) {
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
