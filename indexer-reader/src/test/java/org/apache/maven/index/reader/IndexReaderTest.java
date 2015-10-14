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

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.maven.index.reader.Record.Type;
import org.junit.Test;

/**
 * UT for {@link IndexReader}
 */
public class IndexReaderTest
{
  @Test
  public void simple() throws IOException {
    final Writer writer = new OutputStreamWriter(System.out);
    final IndexReader indexReader = new IndexReader(
        null,
        new DirectoryResourceHandler(new File("/Users/cstamas/tmp/maven-indexer/")));
    try {
      writer.write("indexRepoId=" + indexReader.getRepositoryId() + "\n");
      writer.write("indexLastPublished=" + indexReader.getPublishedTimestamp() + "\n");
      writer.write("isIncremental=" + indexReader.isIncremental() + "\n");
      writer.write("indexRequiredChunkNames=" + indexReader.getChunkNames() + "\n");
      for (ChunkReader chunkReader : indexReader) {
        writer.write("chunkName=" + chunkReader.getName() + "\n");
        writer.write("chunkVersion=" + chunkReader.getVersion() + "\n");
        writer.write("chunkPublished=" + chunkReader.getTimestamp() + "\n");
        writer.write("= = = = = = \n");
        for (Record record : chunkReader) {
          //if (Type.ARTIFACT_REMOVE == record.getType()) {
          if (Type.ARTIFACT_ADD == record.getType() && record.getExpanded().get("_packaging") == null && record.getExpanded().get("_classifier") != null) {
            writer.write(record.getExpanded() + "\n");
            writer.write(record.getRaw() + "\n");
          }
          //writer.write(record.getExpanded() + "\n");
          //writer.write("--------- \n");
          //writer.write(record.getRaw() + "\n");
        }
      }
    }
    finally {
      indexReader.close();
      writer.close();
    }
  }

  @Test
  public void central() throws Exception {
    final Writer writer = new OutputStreamWriter(System.out);
    final IndexReader indexReader = new IndexReader(
        new DirectoryResourceHandler(new File("/Users/cstamas/tmp/maven-indexer/")),
        new HttpResourceHandler(new URL("http://repo1.maven.org/maven2/.index/"))
    );
    try {
      writer.write("indexRepoId=" + indexReader.getRepositoryId() + "\n");
      writer.write("indexLastPublished=" + indexReader.getPublishedTimestamp() + "\n");
      writer.write("isIncremental=" + indexReader.isIncremental() + "\n");
      writer.write("indexRequiredChunkNames=" + indexReader.getChunkNames() + "\n");
      for (ChunkReader chunkReader : indexReader) {
        writer.write("chunkName=" + chunkReader.getName() + "\n");
        writer.write("chunkVersion=" + chunkReader.getVersion() + "\n");
        writer.write("chunkPublished=" + chunkReader.getTimestamp() + "\n");
        writer.write("= = = = = = \n");
        //for (Record record : chunkReader) {
        //  writer.write(record.getExpanded() + "\n");
        //writer.write("--------- \n");
        //writer.write(record.getRaw() + "\n");
        //}
      }
    }
    finally {
      indexReader.close();
      writer.close();
    }
  }
}
