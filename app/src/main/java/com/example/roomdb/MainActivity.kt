package com.example.roomdb

import Adapter.RvAdapter
import Adapter.RvItemClick
import Db.AppDatabase
import Entity.Person
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.databinding.ItemDialogBinding

class MainActivity : AppCompatActivity() {

    lateinit var  binding:ActivityMainBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)

        binding.btnSave.setOnClickListener {
            val person = Person()
            person.name = binding.txtName.text.toString()
            person.number = binding.txtNumber.text.toString()

            appDatabase.personDao().addPerson(person)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            onResume()

            binding.txtName.text.clear()
            binding.txtNumber.text.clear()
        }

    }

    override fun onResume() {
        super.onResume()
        appDatabase = AppDatabase.getInstance(this)

        val list = ArrayList<Person>()
        list.addAll(appDatabase.personDao().getAllPerson())

        rvAdapter = RvAdapter(list, this, object : RvItemClick{
            override fun itemDelete(person: Person, position: Int) {

                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Do you want to Delete this contact?")
                dialog.setIcon(R.drawable.ic_baseline_delete_sweep_24)
                dialog.setMessage("Haqiqatdan ham o'chirilsinmi?")

                dialog.setPositiveButton("Ha", DialogInterface.OnClickListener { dialog, which ->

                    Toast.makeText(this@MainActivity, "O'chirildi!", Toast.LENGTH_SHORT).show()
                appDatabase.personDao().deletePerson(person)
                list.remove(person)
                rvAdapter.notifyItemRemoved(position)
                rvAdapter.notifyItemRangeChanged(position, list.size)

                })

                dialog.setNegativeButton("Yo'q", DialogInterface.OnClickListener { dialog, which ->
                    onResume()
                    Toast.makeText(this@MainActivity, "Bekor qilindi", Toast.LENGTH_SHORT).show()
                })
                dialog.show()
            }

            override fun itemEdit(person: Person, position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                val dialogBinding = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(dialogBinding.root)
                dialogBinding.edt1.setText(person.name)
                dialogBinding.edt2.setText(person.number)

                dialog.setPositiveButton("Ok", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val title1 = dialogBinding.edt1.text.toString()
                        val desc1 = dialogBinding.edt2.text.toString()

                        person.name = title1
                        person.number = desc1
                        appDatabase.personDao().updatePerson(person)
                        rvAdapter.notifyItemChanged(position)
                    }
                })
                dialog.show()

            }

        })
        binding.rv.adapter = rvAdapter

    }
}