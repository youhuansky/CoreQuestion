package XATest;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * @ClassName TranTest
 * @Description
 * @Author youhuan
 * @Date 2019/12/2 18:19
 **/
public class TranTest {

    static class MyXid implements Xid {

        //全局事务ID，不得超过64，建议使用十六进制数
        public int formatId;

        // 全局事务ID，不得超过64，建议使用十六进制数
        public byte gtrid[];

        // 分支限定符(branch qualifier)，如果没有提供bqual，那么默认值为空字符串''，长度不超过64，建议使用十六进制数
        public byte bqual[];

        public MyXid() {

        }

        public MyXid(int formatId, byte gtrid[], byte bqual[]) {
            this.formatId = formatId;
            this.gtrid = gtrid;
            this.bqual = bqual;
        }

        @Override
        public int getFormatId() {
            return formatId;
        }

        @Override
        public byte[] getGlobalTransactionId() {
            return gtrid;
        }

        @Override
        public byte[] getBranchQualifier() {
            return bqual;
        }
    }

    public static MysqlXADataSource GetDataSource(String connString, String user, String passwd) {
        try {
            MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
            mysqlXADataSource.setURL(connString);
            mysqlXADataSource.setUser(user);
            mysqlXADataSource.setPassword(passwd);
            return mysqlXADataSource;
        } catch (Exception e) {
            return null;
        }

    }

    public static void main(String[] args) {

        String url1 = "jdbc:mysql://第一个库的连接地址";
        String url2 = "jdbc:mysql://第二个库的连接地址";

        MysqlXADataSource ds1 = GetDataSource(url1, "root", "12345");
        MysqlXADataSource ds2 = GetDataSource(url2, "admin", "12345");
        Xid xid1 = new TranTest.MyXid(100, new byte[] {0x01}, new byte[] {0x02});

        Xid xid2 = new TranTest.MyXid(100, new byte[] {0x11}, new byte[] {0x12});

        XAResource xaRes1 = null;
        XAResource xaRes2 = null;
        try {
            //创建数据库1的事务连接器、资源管理器等
            XAConnection xaConn1 = ds1.getXAConnection();
            xaRes1 = xaConn1.getXAResource();
            Connection conn1 = xaConn1.getConnection();
            Statement stmt1 = conn1.createStatement();

            //创建数据库2的事务连接器、资源管理器等
            XAConnection xaConn2 = ds2.getXAConnection();
            xaRes2 = xaConn2.getXAResource();
            Connection conn2 = xaConn2.getConnection();
            Statement stmt2 = conn2.createStatement();

            // 数据库1进行准备
            xaRes1.start(xid1, XAResource.TMNOFLAGS);

            stmt1.execute("sql1");

            xaRes1.end(xid1, XAResource.TMSUCCESS);

            // 数据库2进行准备
            xaRes2.start(xid2, XAResource.TMNOFLAGS);

            stmt2.execute("sql2");

            xaRes2.end(xid2, XAResource.TMSUCCESS);

            // 获取两个数据库的准备结果
            int prepare1 = xaRes1.prepare(xid1);
            int prepare2 = xaRes2.prepare(xid2);

            // 都成功的话，就提交
            if (prepare1 == XAResource.XA_OK && prepare2 == XAResource.XA_OK) {
                xaRes1.commit(xid1, false);
                xaRes2.commit(xid2, false);
            } else {
                xaRes1.rollback(xid1);
                xaRes2.rollback(xid2);
            }
        } catch (Exception e) {
            try {
                xaRes1.rollback(xid1);
                xaRes2.rollback(xid2);
            } catch (XAException ex) {
                ex.printStackTrace();
            }
        }

    }
}
