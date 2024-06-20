package com.example.newswave.data.db

import androidx.room.TypeConverter
import com.example.newswave.data.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name, name)
    }
}


/**Notes:
The TypeConverter in Room is used to convert custom data types into a format that Room can persist
in the database. Room supports only a limited set of data types, so if your entity contains a
custom data type, you need to define a way to convert this type into a supported type
(e.g., from Source to String) and vice versa.

Example Explained:
fromSource: Converts a Source object to a String (e.g. storing the name of the source).
toSource: Converts a name of type String back to a Source object. */



/**
 C2
 fun fromSource:
 Define a function to return the name of the source instead of Source object.
 Annotate it with TypeConverter annotation.

 When we are saving favorite news articles data to the Articles table using Articles entity class,
 Room will receive an instance of Source object.
 Since we have created type converter class, Room will use this function and only save the name
 of the resource to the Articles table.

 fun toSource:
 Creating another fun to get an source instance from the room database table when retrieving data from.
 This fun created and we it to return Source instance. create and returned Source object and pass
 name and id but resource id is not used so it will not be a problem if we don't return that.
 Annotate this with @Type Converter annotation.
 */