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

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

/**
 * UT for {@link ChunkReader}
 */
public class ChunkReaderTest
{
  @Test
  public void simple() throws IOException {
    final FileWriter writer = new FileWriter("/Users/cstamas/tmp/maven-indexer/nexus-maven-repository-index.dump");
    final ChunkReader chunkReader = new ChunkReader("full", new FileInputStream("/Users/cstamas/tmp/maven-indexer/nexus-maven-repository-index.gz"));
    try {
      writer.write("getVersion=" + chunkReader.getVersion() + "\n");
      writer.write("getTimestamp=" + chunkReader.getTimestamp() + "\n");
      writer.write("= = = = = = \n");
      for (Record record : chunkReader) {
        writer.write(record.getExpanded() + "\n");
        //writer.write("--------- \n");
        //writer.write(record.getRaw() + "\n");
      }
    }
    finally {
      chunkReader.close();
      writer.close();
    }
  }
}
