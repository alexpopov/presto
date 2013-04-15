package com.facebook.presto.metadata;

import com.facebook.presto.spi.ColumnHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class NativeColumnHandle
        implements ColumnHandle
{
    private final String columnName;
    private final long columnId;

    @JsonCreator
    public NativeColumnHandle(@JsonProperty("columnName") String columnName, @JsonProperty("columnId") long columnId)
    {
        this.columnName = checkNotNull(columnName, "columnName is null");
        checkArgument(columnId > 0, "columnId must be greater than zero");
        this.columnId = columnId;
    }

    @JsonProperty
    public String getColumnName()
    {
        return columnName;
    }

    @JsonProperty
    public long getColumnId()
    {
        return columnId;
    }

    @Override
    public String toString()
    {
        return "native:" + columnName + ":" + columnId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NativeColumnHandle that = (NativeColumnHandle) o;

        if (columnId != that.columnId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return (int) (columnId ^ (columnId >>> 32));
    }
}
