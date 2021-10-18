package Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity


class Person {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    @ColumnInfo()
    var name:String? = null

    var number:String? = null



    constructor(id: Int?, name: String?, number: String?) {
        this.id = id
        this.name = name
        this.number = number
    }

    constructor()

    override fun toString(): String {
        return "Person(id=$id, name=$name, number=$number)"
    }
}