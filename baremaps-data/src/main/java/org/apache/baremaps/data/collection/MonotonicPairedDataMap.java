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

package org.apache.baremaps.data.collection;



import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.baremaps.data.type.LongDataType;
import org.apache.baremaps.data.type.PairDataType.Pair;

/**
 * A {@link DataMap} that can hold a large number of variable-size data elements. The elements must
 * be sorted by their key and inserted in a monotonic way. The elements cannot be removed or updated
 * once inserted.
 *
 * <p>
 * This code has been adapted from Planetiler (Apache license).
 *
 * <p>
 * Copyright (c) Planetiler.
 */
public class MonotonicPairedDataMap<E> implements DataMap<Long, E> {

  private final DataList<Long> offsets;
  private final MemoryAlignedDataList<Pair<Long, E>> values;

  private long lastChunk = -1;

  /**
   * Constructs a {@link MonotonicPairedDataMap}.
   */
  public MonotonicPairedDataMap(MemoryAlignedDataList<Pair<Long, E>> values) {
    this(new MemoryAlignedDataList<>(new LongDataType()), values);
  }

  /**
   * Constructs a {@link MonotonicPairedDataMap}.
   *
   * @param offsets the list of offsets
   * @param values the buffer of values
   */
  public MonotonicPairedDataMap(DataList<Long> offsets,
      MemoryAlignedDataList<Pair<Long, E>> values) {
    this.offsets = offsets;
    this.values = values;
  }

  /** {@inheritDoc} */
  public E put(Long key, E value) {
    long index = values.size();
    long chunk = key >>> 8;
    if (chunk != lastChunk) {
      while (offsets.size() <= chunk) {
        offsets.add(index);
      }
      lastChunk = chunk;
    }
    values.add(new Pair<>(key, value));
    return null;
  }

  /** {@inheritDoc} */
  public E get(Object keyObject) {
    long key = (long) keyObject;
    long chunk = key >>> 8;
    if (chunk >= offsets.size()) {
      return null;
    }
    long lo = offsets.get(chunk);
    long hi =
        Math.min(
            values.size(),
            chunk >= offsets.size() - 1
                ? values.size()
                : offsets.get(chunk + 1))
            - 1;
    while (lo <= hi) {
      long index = (lo + hi) >>> 1;
      Pair<Long, E> pair = values.get(index);
      long value = pair.left();
      if (value < key) {
        lo = index + 1;
      } else if (value > key) {
        hi = index - 1;
      } else {
        // found
        return pair.right();
      }
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Long> keyIterator() {
    return values.stream().map(Pair::left).iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<E> valueIterator() {
    return values.stream().map(Pair::right).iterator();
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<Entry<Long, E>> entryIterator() {
    return values.stream()
        .map(p -> Map.entry(p.left(), p.right()))
        .iterator();
  }

  /** {@inheritDoc} */
  @Override
  public long size() {
    return values.size();
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsKey(Object key) {
    return get(key) != null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsValue(Object value) {
    return values.stream().anyMatch(p -> p.right().equals(value));
  }

  /** {@inheritDoc} */
  @Override
  public void clear() {
    offsets.clear();
    values.clear();
  }
}
