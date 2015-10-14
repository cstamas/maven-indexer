package org.apache.maven.index.reader;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Map;

/**
 * Maven 2 Index record.
 *
 * @since 5.1.2
 */
public class Record
{
  /**
   * Types of returned records returned from index.
   */
  public enum Type
  {
    /**
     * Descriptor record. Can be safely ignored.
     */
    DESCRIPTOR,

    /**
     * Artifact ADD record. Records of this type should be added to your indexing system.
     */
    ARTIFACT_ADD,

    /**
     * Artifact REMOTE record. In case of incremental updates, notes that this artifact was removed. Records of this
     * type shoild be removed from your indexing system.
     */
    ARTIFACT_REMOVE,

    /**
     * Special record, containing all the Maven "groupId"s that are enlisted on the index. Can be safely ignored.
     */
    ALL_GROUPS,

    /**
     * Special record, containing all the root groups of Maven "groupId"s that are enlisted on the index. Can be safely
     * ignored.
     */
    ROOT_GROUPS
  }

  private final Type type;

  private final Map<String, String> raw;

  private final Map<String, Object> expanded;

  public Record(final Type type, final Map<String, String> raw, final Map<String, Object> expanded) {
    this.type = type;
    this.raw = raw;
    this.expanded = expanded;
  }

  /**
   * Returns the {@link Type} of this record. Usually users would be interested in {@link Type#ARTIFACT_ADD} and {@link
   * Type#ARTIFACT_REMOVE} types only to maintain their own index. Still, indexer offers extra records too, see {@link
   * Type} for all existing types.
   */
  public Type getType() {
    return type;
  }

  /**
   * Returns the "raw", Maven Indexer specific record as a {@link Map}.
   */
  public Map<String, String> getRaw() {
    return raw;
  }

  /**
   * Returns the expanded (processed and expanded synthetic fields) record as {@link Map} ready for consumption.
   */
  public Map<String, Object> getExpanded() {
    return expanded;
  }
}
