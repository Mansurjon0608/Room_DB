package Adapter

import Entity.Person
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.R
import com.example.roomdb.databinding.ItemRvBinding


class RvAdapter(val list: List<Person>, val context: Context, val rvItemClick: RvItemClick) : RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh( var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {


        fun onBind(person: Person, position: Int) {
            itemRv.txtName.text = person.name
            itemRv.txtNumber.text = person.number

            val animation1 = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim1)
            itemRv.root.startAnimation(animation1)


            itemRv.delete.setOnClickListener {
                rvItemClick.itemDelete( person, position)
            }

            itemRv.edit.setOnClickListener {
                rvItemClick.itemEdit(person, position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}

interface RvItemClick {
    fun itemDelete(person: Person, position: Int)
    fun itemEdit(person: Person, position: Int)
}