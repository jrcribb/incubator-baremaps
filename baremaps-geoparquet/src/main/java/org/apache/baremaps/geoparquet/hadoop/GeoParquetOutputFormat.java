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

package org.apache.baremaps.geoparquet.hadoop;

import org.apache.baremaps.geoparquet.data.GeoParquetGroupImpl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.parquet.hadoop.ParquetOutputFormat;
import org.apache.parquet.hadoop.util.ContextUtil;
import org.apache.parquet.schema.MessageType;

/**
 * An example output format
 *
 * must be provided the schema up front
 * 
 * @see GeoParquetOutputFormat#setSchema(Job, MessageType)
 * @see GeoParquetGroupWriteSupport#PARQUET_EXAMPLE_SCHEMA
 */
public class GeoParquetOutputFormat extends ParquetOutputFormat<GeoParquetGroupImpl> {

  /**
   * set the schema being written to the job conf
   * 
   * @param job a job
   * @param schema the schema of the data
   */
  public static void setSchema(Job job, MessageType schema) {
    GeoParquetGroupWriteSupport.setSchema(schema, ContextUtil.getConfiguration(job));
  }

  /**
   * retrieve the schema from the conf
   * 
   * @param job a job
   * @return the schema
   */
  public static MessageType getSchema(Job job) {
    return GeoParquetGroupWriteSupport.getSchema(ContextUtil.getConfiguration(job));
  }

  public GeoParquetOutputFormat() {
    super(new GeoParquetGroupWriteSupport());
  }
}
