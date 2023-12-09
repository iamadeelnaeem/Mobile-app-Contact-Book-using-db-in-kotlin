package com.example.madassignment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.ui.theme.MadAssignmentTheme
import  com.example.madassignment.DataBaseHandler
class DetailActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
       // val intent=Intent()

        val name=intent.getStringExtra("name");
        val phone=intent.getStringExtra("phone");
        val id=intent.getStringExtra("id");
        val firstChar = if (!name.isNullOrEmpty()) name[0] else ' '

        Toast.makeText(this,name+phone,Toast.LENGTH_SHORT).show();
        val nameTextView:TextView=findViewById(R.id.nameTextView)
        val phoneTextView:TextView=findViewById(R.id.numberTextView)
        val heading:TextView=findViewById(R.id.heading);
        heading.text = firstChar.toString()
        nameTextView.setText(name);
        phoneTextView.setText(phone);
        val callBtn=findViewById<Button>(R.id.call);
        callBtn.setOnClickListener {
            val phoneNumber =phone;
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
        val deleteBtn=findViewById<Button>(R.id.delete);

        deleteBtn.setOnClickListener{
            val db=DataBaseHandler(this);
            val ID=id?.toInt();
            val result= db.deleteData(ID);
            if(result==true)
            {
                val intent=Intent(this,MainActivity::class.java);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"error in deletion",Toast.LENGTH_SHORT).show();
            }

        }
        val updateBtn=findViewById<Button>(R.id.update);
        updateBtn.setOnClickListener{
          val intent=Intent(this,UpdateContact::class.java);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}