/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.serializers;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPBase64Serializer extends AsciiSerializer
{
    public static final GZIPBase64Serializer instance = new GZIPBase64Serializer();

    public static String B64_GZIP_MAGIC = "H4sI";

    private GZIPBase64Serializer(){}

    private String decode(String value){
        if(!value.startsWith(B64_GZIP_MAGIC)){
            return value;
        }
        final byte[] decodeBase64 = Base64.decodeBase64(value.getBytes());
        try {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(decodeBase64));
            return IOUtils.toString(gzipInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(String value)
    {
        return super.toString(decode(value));
    }

}
