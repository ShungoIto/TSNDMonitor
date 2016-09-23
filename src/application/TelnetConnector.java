package application;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class TelnetConnector {
	private final static int DEFAULT_TELNET_PORT = 23;

	private final static byte IAC = (byte) 255;
	private final static byte DO = (byte) 253;
	private final static byte WONT = (byte) 252;
	/**
	 * NVTとしてのネゴシエーションを行う
	 * @param os
	 * @param is
	 * @throws IOException
	 */
	private void negotiation(OutputStream os, BufferedInputStream is) throws IOException {
		byte[] buff = new byte[3];
		while (true) {
			is.mark(buff.length);
			if (is.available() >= buff.length) {
				is.read(buff);
				// ネゴシエーション完了
				if (buff[0] != IAC) {
					is.reset();
					return;
				// DOコマンドに対してWONTで返答する
				} else if (buff[1] == DO) {
					buff[1] = WONT;
					os.write(buff);
				}
			}
		}
	}

	/**
	 * 実行メソッド
	 * @param host
	 * @param port
	 */
	static OutputStream os;
	static Socket socket;
	public void execute(String host, int port) {

		socket = null;
		os = null;
		BufferedInputStream is = null;

		try {
			socket = new Socket(host, port);
			os = socket.getOutputStream();
			is = new BufferedInputStream(socket.getInputStream());

			// 23番ポートへの接続の場合ネゴシエーションを行う
			if (port == DEFAULT_TELNET_PORT) {
				negotiation(os, is);
			}
			if(socket.isConnected()==true) Main.log = Main.log+"Connected\n";
			System.out.println("os = "+os);
			StreamConnector socketToLog = new StreamConnector(is, System.out);                  // ソケット→ログ

			Thread output = new Thread(socketToLog);

			System.out.println("connected");
			output.start();

		} catch (IOException e) {
			e.printStackTrace(System.out);
			System.err.println("接続中に例外が発生しました");
			System.exit(1);
			// コネクションはクローズしない
			// } finally {
			// try {
			// socket.close();
			// os.close();
			// is.close();
			// } catch (IOException e) {
			// }
		}
	}
	
	/**
	 * コマンドをセンサに送信するメソッド
	 * @throws IOException 
	 */
	public void CommandToSensor(String cmd, OutputStream os) throws IOException{
		System.out.println("cmd = "+cmd+", os = "+os);
		byte[] buff = new byte[1024];
		buff = cmd.getBytes();
		int cmdLen = buff.length;
		System.out.println("inputcmd = "+buff+ ", length = "+cmdLen+ ", command = \""+new String(buff)+"\"");
		os.write(buff, 0, cmdLen);
		os.flush();
		Main.log = Main.log + cmd;
		cmd = "";	
	}
	
	public void DisConnect() throws IOException{
			TelnetConnector.socket.close();
	}
	
	/**
	 * ストリームの読み書きクラス
	 */
	class StreamConnector implements Runnable {

		private InputStream is = null;
		private OutputStream os = null;
		String buffStr = new String();
		
		public StreamConnector(InputStream in, OutputStream out) throws UnsupportedEncodingException {
			this.is = in;
			this.os = out;
		}
		@Override
		public void run() {
			try {
				while (true) {
					byte[] buff = new byte[1024];
					int n = is.read(buff);
					if (n > 0) {
						System.out.println("os= ="+os+", n="+n);
						os.write(buff, 0, n);
						buffStr = new String(buff, "UTF-8");
						Main.log = Main.log+buffStr;
					}
				}
			} catch (IOException e) {
				e.printStackTrace(System.out);
				System.out.println("入出力の書き込み中に例外が発生しました");
				System.exit(1);
			}
		}
	}
}
