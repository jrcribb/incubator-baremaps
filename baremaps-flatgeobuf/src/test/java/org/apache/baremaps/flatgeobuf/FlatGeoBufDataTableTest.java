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

package org.apache.baremaps.flatgeobuf;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import org.apache.baremaps.testing.TestFiles;
import org.junit.jupiter.api.Test;

class FlatGeoBufDataTableTest {

  @Test
  void schema() {
    var file = TestFiles.resolve("baremaps-testing/data/samples/countries.fgb");
    var table = new FlatGeoBufDataTable(file);
    var rowType = table.schema();
    assertEquals("countries", rowType.name());
    assertEquals(2, rowType.columns().size());
  }

  @Test
  void read() {
    var file = TestFiles.resolve("baremaps-testing/data/samples/countries.fgb");
    var table = new FlatGeoBufDataTable(file);
    assertEquals(179, table.size());
    assertEquals(179, table.stream().count());
  }

  @Test
  void readAndWrite() throws IOException {
    var file = TestFiles.resolve("baremaps-testing/data/samples/countries.fgb");
    var tempFile = Files.createTempFile("countries", ".fgb");
    tempFile.toFile().deleteOnExit();
    var table = new FlatGeoBufDataTable(file);
    var tempTable = new FlatGeoBufDataTable(tempFile, table.schema());
    tempTable.write(table);
    var featureSet = new FlatGeoBufDataTable(tempFile);
    assertEquals(179, featureSet.stream().count());
  }
}
