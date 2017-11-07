package socket;


import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by kenan on 17/10/19.
 * socket io
 * 1.心跳包
 * 2.授权验证
 * 3.业务逻辑
 *
 *
 * 报文头的字段取值定义：
 编码方式 byte：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1，我们统一使用 0
 加密类型 byte：0表示不加密，1加密，我们统一使用 0
 扩展1 int：取值0
 扩展2 int：取值0
 会话ID int：以cuid+时间戳为因子生成
 命令 int: 1心跳，2授权验证，3新单提醒
 数据包长度 int: 计算后面业务数据长度得到

 */

public class SocketIo {
    int a= 10_000;
    int b= 10_000;
}
