package com.kissedcode.finance.model

import android.arch.persistence.room.TypeConverter
import com.kissedcode.finance.model.OperationType
import java.util.*

class Converters {

    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun operationTypeFromOrdinal(value: Int?): OperationType? {
        return if (value == null) null else OperationType.values()[value]
    }

    @TypeConverter
    fun operationTypeToOrdinal(operationType: OperationType?): Int? {
        return operationType?.ordinal
    }

}