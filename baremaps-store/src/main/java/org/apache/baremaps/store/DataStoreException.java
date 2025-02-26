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

package org.apache.baremaps.store;

/** Signals that an exception occurred in a {@link DataStore}. */
public class DataStoreException extends RuntimeException {

  /** Constructs a {@link DataStoreException} with {@code null} as its error detail message. */
  public DataStoreException() {}

  /**
   * Constructs an {@link DataStoreException} with the specified detail message.
   *
   * @param message the message
   */
  public DataStoreException(String message) {
    super(message);
  }

  /**
   * Constructs a {@link DataStoreException} with the specified cause.
   *
   * @param cause the cause
   */
  public DataStoreException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a {@link DataStoreException} with the specified detail message and cause.
   *
   * @param message the message
   * @param cause the cause
   */
  public DataStoreException(String message, Throwable cause) {
    super(message, cause);
  }
}
