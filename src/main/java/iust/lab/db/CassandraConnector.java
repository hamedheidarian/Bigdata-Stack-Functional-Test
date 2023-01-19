package iust.lab.db;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Authenticator;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AuthenticationException;

import java.net.InetSocketAddress;

public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    public void connect(String node, Integer port) {
        AuthProvider authProvider = new AuthProvider() {
            @Override
            public Authenticator newAuthenticator(InetSocketAddress inetSocketAddress, String s) throws AuthenticationException {
                return newAuthenticator(inetSocketAddress, s);
            }
        };
        Cluster.Builder b = Cluster.builder().addContactPoint(node).withCredentials("cassandra", "cassandra");
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();
        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public void createKeyspace(
            String keyspaceName, String replicationStrategy, int replicationFactor) {
        StringBuilder sb =
                new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                        .append(keyspaceName).append(" WITH replication = {")
                        .append("'class':'").append(replicationStrategy)
                        .append("','replication_factor':").append(replicationFactor)
                        .append("};");

        String query = sb.toString();
        session.execute(query);
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append("test_tb").append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("sparkRes bigint");

        String query = sb.toString();
        session.execute(query);
    }
}
