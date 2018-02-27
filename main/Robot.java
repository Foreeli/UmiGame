// ｢海ゲーム｣クライアントプログラムRobot.java
// このプログラムは,海ゲームのクライアントプログラムです
// 決められた手順で海ゲームをプレイします
// 使い方java Robot 接続先サーバアドレスゲーム参加者名
// 起動後,指定したサーバと接続し,自動的にゲームを行います
// 起動後,指定回数の繰り返しの後,logoutします
// このプログラムはlogoutコマンドがありません
// プログラムを途中で停止するには,以下の手順を踏んでください
// （１）コントロールC を入力してRobotプログラムを停止します
// （２）T1.javaプログラムなど,別のクライアントを使ってRobotと同じ名前でloginします
// （３）logoutします
// 別クライアントからのlogout作業を省略すると,サーバ上に情報が残ってしまいます

// ライブラリの利用
import java.net.*;// ネットワーク関連
import java.io.*;
import java.util.*;
import java.awt.*;// グラフィックス
import java.awt.event.*;// イベント関連

// Robotクラス
public class Robot {
	// ロボットの動作タイミングを規定する変数sleeptime
	int sleeptime = 5 ;
	// ロボットがlogoutするまでの時間を規定する変数timeTolive
	int timeTolive = 10000 ;

	// コンストラクタ
	public Robot (String[] args)
	{
		int x=0,y=0,a=0,b=0,da=0,db=0,r=0;
		double distance[] = new double[10];
		String line="";

		login(args[0],args[1]) ;
		try{
			for(;timeTolive > 0; -- timeTolive){
				System.out.println("あと" + timeTolive + "回") ;

				// start receiving information
				out.println("stat");
				out.flush();

				// seek ship's information
				while(!"ship_info".equalsIgnoreCase(line))
					line = in.readLine();

				// print ship's information
				line = in.readLine();
				while(!".".equals(line)) {
					StringTokenizer st = new StringTokenizer(line);
					// get ship's name
					String obj_name = st.nextToken().trim();
					if(obj_name.equals(name)) {
						// get ship's location
						a = Integer.parseInt(st.nextToken());
						b = Integer.parseInt(st.nextToken());
						// print ship's location to console
						System.out.println("a : " +a);
						System.out.println("b : " +b);
					}
					line = in.readLine();
				}

				// seek energy's information
				while(!"energy_info".equalsIgnoreCase(line))
					line = in.readLine();
						System.out.println(line);

				// print energy's information
				line = in.readLine();
					System.out.println(line);
				while(!".".equals(line)) {
					StringTokenizer st = new StringTokenizer(line);
					// get energy's location
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());

					da = Math.abs(x-a);
					db = Math.abs(y-b);
					r = (da*da)+(db*db);
					distance[0] = Math.sqrt(r);

					// print energy's location to console
					System.out.println("x : " +x);
					System.out.println("y : " +y);
					System.out.println("d : " +distance[0]);
					System.out.println();
					line = in.readLine();
						System.out.println(line);
				}

				if(da<=5 && y>b && db>5) {
					Thread.sleep(sleeptime*100);
					out.println("up");
					out.flush();
				}

				if(da<=5 && y<b && db>5) {
					Thread.sleep(sleeptime*100);
					out.println("down");
					out.flush();
				}

				if(db<=5 && x>a && da>5) {
					Thread.sleep(sleeptime*100);
					out.println("right");
					out.flush();
				}

				if(db<=5 && x<a && da>5) {
					Thread.sleep(sleeptime*100);
					out.println("left");
					out.flush();
				}

				// energy on the left diagonally bottom
				if(a>x && b>y) {
					Thread.sleep(sleeptime*100);
					out.println("left");
					out.println("down");
					out.flush();
				}

				// energy on the right diagonally bottom
				if(a<x && b>y) {
					Thread.sleep(sleeptime*100);
					out.println("right");
					out.println("down");
					out.flush();
				}

				// energy on the left diagonally top
				if(a>x && b<y) {
					Thread.sleep(sleeptime*100);
					out.println("left");
					out.println("up");
					out.flush();;
				}

				// energy on the right diagonally top
				if(a<x && b<y) {
					Thread.sleep(sleeptime*100);
					out.println("right");
					out.println("up");
					out.flush();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// login関連のオブジェクト
	Socket server;// ゲームサーバとの接続ソケット
	int port = 10000;// 接続ポート
	BufferedReader in;// 入力ストリーム
	PrintWriter out;// 出力ストリーム
	String name;// ゲーム参加者の名前

	// loginメソッド
	// サーバへのlogin処理を行います
	void login(String host, String name){
		try {
			// サーバとの接続
			this.name = name;
			server = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(
			  server.getInputStream()));
			out = new PrintWriter(server.getOutputStream());

			// loginコマンドの送付
			out.println("login " + name);
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	// mainメソッド
	// Robotを起動します
	public static void main(String[] args){
		new Robot(args);
	}
}
