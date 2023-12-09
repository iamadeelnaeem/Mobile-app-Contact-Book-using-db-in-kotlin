package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madassignment.ui.theme.MadAssignmentTheme
import com.example.madassignment.Product
import com.example.madassignment.MyCustomAdapter
import androidx.recyclerview.widget.RecyclerView

class addContact : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db=DataBaseHandler(this);
        setContentView(R.layout.add_contact)
        val name:EditText=findViewById(R.id.name);
        val phone:EditText=findViewById(R.id.phone);
        val addBtn:Button=findViewById(R.id.add_contact);

        addBtn.setOnClickListener{
            val name_value=name.text.toString().trim();
            val phone_value=phone.text.toString().trim();
            if(name_value.length>0 && phone_value.length>0)
            {
                val user_date=User(name_value,phone_value.toInt())
                db.insertData(user_date);
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"data inserted successfully",Toast.LENGTH_SHORT);
            }
            else
            {
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT);
            }
        }

    }
}