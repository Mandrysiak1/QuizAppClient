package com.example.quizzapp.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.R
import com.example.quizzapp.objects.Responses.SocietiesEntity
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.vm.JoinSocietyViewModel
import kotlinx.android.synthetic.main.activity_add_society.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_your_societies.*
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter

class JoinSocietyActivity : AppCompatActivity()
{
    lateinit var viewModel : JoinSocietyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_society)

        viewModel = ViewModelProviders.of(this).get(JoinSocietyViewModel::class.java)


        val SocietiesObserver = Observer<SocietyResponse>{

            (viewModel.societies.value as SocietyResponse).societiesEntities

            var options =  ArrayList((viewModel.societies.value as SocietyResponse).societiesEntities)

            allSocietiesList.adapter = AllSocietyListAdapter(this,options)
        }

        allSocietiesList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            run {

                viewModel.selectedSociety.value = allSocietiesList.adapter.getItem(position) as SocietiesEntity

            }
        }

        JoinSocietyInside.setOnClickListener { viewModel.JoinScoiety() }

        viewModel.societies.observe(this,SocietiesObserver)

        viewModel.getAllSocieties()


    }
}
class AllSocietyListAdapter(private val context: Activity,
                         private val dataSource: ArrayList<SocietiesEntity>) : ArrayAdapter<SocietiesEntity>(context,
    R.layout.society_item) {


    override fun getCount(): Int {
        return dataSource.count()
    }


    override fun getItem(position: Int): SocietiesEntity? {
        return dataSource[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.society_item, null, true)


        val name: TextView = rowView.findViewById(R.id.SocName) as TextView

        name.text = dataSource[position].name

        return rowView

    }
}