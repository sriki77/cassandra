package org.apache.cassandra.db.marshal;

import org.apache.cassandra.serializers.GZIPBase64Serializer;
import org.apache.cassandra.serializers.TypeSerializer;

public class GZIPBase64Type extends AsciiType {

    public static final GZIPBase64Type instance = new GZIPBase64Type();

    public TypeSerializer<String> getSerializer()
    {
        return GZIPBase64Serializer.instance;
    }

}
