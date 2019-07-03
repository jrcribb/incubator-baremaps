package io.gazetteer.osm.osmxml;

import io.gazetteer.osm.model.Change;
import io.gazetteer.osm.model.Entity;
import io.gazetteer.osm.model.Node;
import io.gazetteer.osm.model.Relation;
import io.gazetteer.osm.model.Way;
import io.gazetteer.osm.database.NodeTable;
import io.gazetteer.osm.database.RelationTable;
import io.gazetteer.osm.database.WayTable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import org.apache.commons.dbcp2.PoolingDataSource;

public class ChangeConsumer implements Consumer<Change> {

  private final PoolingDataSource pool;

  public ChangeConsumer(PoolingDataSource pool) {
    this.pool = pool;
  }

  @Override
  public void accept(Change change) {
    try (Connection connection = pool.getConnection()) {
      Entity entity = change.getEntity();
      if (entity instanceof Node) {
        Node node = (Node) entity;
        switch (change.getType()) {
          case create:
            NodeTable.insert(connection, node);
            break;
          case modify:
            NodeTable.update(connection, node);
            break;
          case delete:
            NodeTable.delete(connection, node.getInfo().getId());
            break;
          default:
            break;
        }
      } else if (entity instanceof Way) {
        Way way = (Way) entity;
        switch (change.getType()) {
          case create:
            WayTable.insert(connection, way);
            break;
          case modify:
            WayTable.update(connection, way);
            break;
          case delete:
            WayTable.delete(connection, way.getInfo().getId());
            break;
          default:
            break;
        }
      } else if (entity instanceof Relation) {
        Relation relation = (Relation) entity;
        switch (change.getType()) {
          case create:
            RelationTable.insert(connection, relation);
            break;
          case modify:
            RelationTable.update(connection, relation);
            break;
          case delete:
            RelationTable.delete(connection, relation.getInfo().getId());
            break;
          default:
            break;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}