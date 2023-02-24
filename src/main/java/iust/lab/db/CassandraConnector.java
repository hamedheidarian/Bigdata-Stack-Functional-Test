package iust.lab.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnector {

    private Session session;

    public void connect(String node, Integer port) {
        Cluster.Builder b = Cluster.builder().addContactPoint(node).withCredentials("cassandra", "cassandra");
        if (port != null) {
            b.withPort(port);
        }
        Cluster cluster = b.build();
        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

}
