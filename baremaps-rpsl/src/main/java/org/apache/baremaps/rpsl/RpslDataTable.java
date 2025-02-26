/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.baremaps.rpsl;


import java.io.InputStream;
import java.util.*;
import org.apache.baremaps.store.*;
import org.apache.baremaps.store.DataColumn.Cardinality;
import org.apache.baremaps.store.DataColumn.Type;

/**
 * A DataTable implementation that reads RPSL data using RpslReader.
 */
public class RpslDataTable implements DataTable {

  private final DataSchema schema;
  private final InputStream inputStream;

  /**
   * Constructs a RpslDataTable with the given InputStream.
   *
   * @param inputStream the input stream containing RPSL data
   */
  public RpslDataTable(InputStream inputStream) {
    this.inputStream = inputStream;
    this.schema = createSchema();
  }

  /**
   * Creates the DataSchema for the RPSL data.
   *
   * @return the data schema
   */
  private DataSchema createSchema() {
    List<DataColumn> columns = new ArrayList<>();
    columns.add(new DataColumnFixed("type", Cardinality.REQUIRED, Type.STRING));
    columns.add(new DataColumnFixed("id", Cardinality.REQUIRED, Type.STRING));
    columns.add(new DataColumnFixed("inetnum", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("inet6num", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("netname", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("descr", Cardinality.REPEATED, Type.STRING));
    columns.add(new DataColumnFixed("country", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("admin-c", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("tech-c", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("status", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("mnt-by", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("created", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("last-modified", Cardinality.OPTIONAL, Type.STRING));
    columns.add(new DataColumnFixed("changed", Cardinality.REPEATED, Type.STRING));
    return new DataSchemaImpl("RpslObject", columns);
  }

  @Override
  public DataSchema schema() {
    return schema;
  }

  @Override
  public long size() {
    // Size is unknown since we read from a stream
    return -1;
  }

  @Override
  public Iterator<DataRow> iterator() {
    return new RpslDataRowIterator(inputStream, schema);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Clear operation is not supported.");
  }
}
