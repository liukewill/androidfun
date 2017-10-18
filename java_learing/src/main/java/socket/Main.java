package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kenan on 17/9/28.
 *
 * Socket 应用层
 * Tcp    传输层  通过端口把数据传到目的主机的目的进程，来实现进程与进程之间的通信
 * Udp    传输层
 *
 * IP     网络层：根据IP找到地址主机
 *        数据链路层
 *        物理层
 *
 * TCP ：Transmission Control Protocol  传输控制协议
 * 基于TCP协议有 ：FTP,HTTP，DNS 特征:面向连接，面向字节流，全双工通信，可靠。
 *
 * 面向连接：使用TCP传输数据，必须先建立TCP连接，传输完成后释放
 *
 * 全双工通信：一旦建立TCP连接，双方可以在任何时候发送和接受数据
 *
 * 可靠：TCP传输数据，无差错，不丢失，不重复，按照序列到达。
 *
 * 面向字节流：流，流入，流出到进程的字符序列。 文件长度有限制，不能一次传输完，分为好几个数据块，由可靠性保证。
 * 接收方按照顺序重组。
 *
 * TCP建立连接  三次握手  A（Client） 要与B（Server）进行连接
 * 第一次：客户端请求连接。     A->B (SYN=1,ACK=0) A向B发送数据包，等待服务端确认。
 * 第二次：服务端收到连接请求。  B->A (SYN=1,ACK=1) B告诉A可以通讯了，你再发个确认数据包。
 * 第三次：客户端确认连接。     A->B (SYN=0,ACK=1) 连接已确认。
 * TCP三次握手建立连接和关闭，都是通过请求-响应模式完成  C/S架构。
 *
 * SYN:同步标志  表示建立连接 三次握手时有效
 * ACK:确认标志 表示响应
 * FIN: 结束标志  表示关闭连接
 *
 * SourcPort DestinationPort :Port 区别主机中的进程。Ip：用于区别不同主机。
 * 目标和源 IP+PORT 能确定一次TCP连接。实现跨主机进程间通信。
 *
 * Sequence Number：发->收 报文段中第一个数据字节在数据流中的序号。解决数据乱序问题。
 * Acknowledgment Number：
 *
 * 为什么进行三次握手？
 * 防止服务器端 因为接受了 已失效的连接请求报文 一直等待客户请求 浪费资源。
 *
 * Tcp释放连接  四次握手 A，B都可以释放连接
 * 第一次挥手：A发送释放信息到B；（发出去之后，A->B发送数据这条路径就断了）
 *
 * 第二次挥手：B收到A的释放信息之后，回复确认释放的信息：我同意你的释放连接请求
 *
 * 第三次挥手：B发送“请求释放连接“信息给A
 *
 * 第四次挥手：A收到B发送的信息后向B发送确认释放信息：我同意你的释放连接请求
 *
 * 为什么进行四次握手？
 * 保证双方都能通知对方需要释放连接。
 *
 *
 * UDP: 无连接，不可靠，面向报文，没有堵塞控制
 *
 * SOCKET: TCP/IP 协议，网络编程调用接口 传输层，解决数据如何在网络中传输
 * HTTP:应用层，解决的是如何包装数据。
 *
 * StreamSocket 流套接字 基于TCP 流方式                可靠字节流服务。
 * DatagramSocket 数据包套接字 基于UDP协议 数据报文方式  提供数据打包发送服务
 *
 */

public class Main {

    private static final int PORT=9999;
    private static final String EXIT="exit";
    private List<Socket> mList=new ArrayList<>();
    private ServerSocket server=null;
    private ExecutorService mExecutorService=null;//thred pool


    public static void main(String []args){
        new Main();
    }


    public Main(){
        try{
            server =new ServerSocket();
            server.bind(new InetSocketAddress("172.16.76.153",PORT));
            //创建线程池
            mExecutorService= Executors.newCachedThreadPool();

            System.out.println("**********");
            System.out.println("服务已经启动"+server.getInetAddress()+":"+server.getLocalPort());
            System.out.println("**********");

            //客户端
            Socket client=null;

            //循环开启接受socket
            while(true){
                client=server.accept();
                mList.add(client);
                mExecutorService.execute(new Service(client));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    class Service implements Runnable{

        private Socket socket;
        private BufferedReader in=null;
        private String msg="";

        public Service(Socket socket) {
            this.socket = socket;

            try{
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg="客户端地址："+this.socket.getInetAddress()+"\n总连接数:"+mList.size();
                this.sendMsg();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if ((msg = in.readLine()) != null) {
                        if (msg.equalsIgnoreCase(EXIT)) {
                            //当客户端发送的信息为：exit时候，关闭连接
                            System.out.println(socket.getInetAddress() + "**客户端请求退出");
                            mList.remove(socket);
                            in.close();
                            msg = this.socket.getInetAddress() + "**客户端退出：" + "\n总连接数:" + mList.size();
                            socket.close();
                            this.sendMsg();
                            break;
                        } else {
                            msg = "收到:" + socket.getInetAddress() + "**消息:" + msg;
                            this.sendMsg();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 循环遍历客户端集合 发送消息
         */
        public void sendMsg(){
            System.out.println(msg);
            for (Socket socket1 : mList) {
                OutputStream os;
                try{
                    os=socket1.getOutputStream();
                    os.write((msg+"\n").getBytes("utf-8"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
}
