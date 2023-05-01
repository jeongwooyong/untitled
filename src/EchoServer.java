import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        BufferedReader in = null;
        ServerSocket listener = null;
        Socket socket = null;
        System.out.println("서버입니다. 클라이언트를 기다립니다...");
        try {
            listener = new ServerSocket(9999); // 서버 소켓 생성
            socket = listener.accept(); // 클라이언트로부터 연결 요청 대기
            System.out.println("연결되었습니다.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 클라이언트로부터의 입력 스트림
            String inputMessage;
            while ((inputMessage = in.readLine()) != null) {
                System.out.println("주문된 메뉴: "+ inputMessage); // 클라이언트가 보낸 메시지 화면에 출력
                if (inputMessage.equals("끝")) { // 클라이언트가 "끝"를 보내면 연결 종료
                    System.out.println("접속을 종료합니다.");
                    break;
                }
            }
            socket.close(); // 클라이언트와 통신용 소켓 닫기
            listener.close(); // 서버 소켓 닫기
        } catch (IOException e) {
            System.out.println("입출력 오류가 발생했습니다.");
        }
    }
}
