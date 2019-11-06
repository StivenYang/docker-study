from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = '863792359@qq.com'
authCode = 'aunhresokswobfci'


class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage, mobile:" + mobile + ", message:" + message)
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage, mail:" + email + ", message:" + message)
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('慕课网邮件', 'utf-8')
        try:
            smtpObj = smtplib.SMTP('smtp.qq.com')
            smtpObj.login(sender, authCode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
            print("send mail success")
            return True
        except smtplib.SMTPException as ex:
            print("send mail failed!")
            print(ex)
            return False


if __name__ == '__main__':
    # 定义处理器实现
    handler = MessageServiceHandler()
    # 定义处理器
    processor = MessageService.Processor(handler)
    # 指定thrift的主机和端口
    transport = TSocket.TServerSocket("localhost", "9090")
    # 传输的方式，一帧一帧的传输
    tfactory = TTransport.TFramedTransportFactory()
    # 传输的协议，二进制传输
    pfactory = TBinaryProtocol.TBinaryProtocolFactory

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print("python thrift server start")
    server.serve()
    print("python thrift server exit")
