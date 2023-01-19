//package iust.lab.db;
//
//import com.datastax.driver.core.BoundStatement;
//import com.datastax.driver.core.PreparedStatement;
//import com.datastax.driver.core.SimpleStatement;
//import com.datastax.driver.core.querybuilder.Insert;
//import com.datastax.driver.core.querybuilder.QueryBuilder;
//import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
//
//import java.util.UUID;
//
//public class EntityRepository {
//
//    public UUID insertInteger(Integer integer, String keyspace) {
////        UUID videoId = UUID.randomUUID();
//
//        Insert insertInto = QueryBuilder.insertInto("test_tb")
//                .value("sparkRes", QueryBuilder.bindMarker());
//
//        SimpleStatement insertStatement = insertInto.build();
//
//        if (keyspace != null) {
//            insertStatement = insertStatement.setKeyspace(keyspace);
//        }
//
//        PreparedStatement preparedStatement = session.prepare(insertStatement);
//
//        BoundStatement statement = preparedStatement.bind()
//                .setUuid(0, integer.getId())
//                .setString(1, integer.getTitle())
//                .setInstant(2, integer.getCreationDate());
//
//        session.execute(statement);
//
//        return videoId;
//    }
//
//}
