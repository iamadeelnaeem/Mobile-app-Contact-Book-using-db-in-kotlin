package com.example.madassignment
import android.content.Intent
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

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var searchEditText: EditText
    private lateinit var myAdapter: MyCustomAdapter
    private val originalProductList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setContentView(R.layout.main_activity)

        // Getting the RecyclerView by its id
        recyclerView = findViewById(R.id.recyclerview)
        btnAdd = findViewById(R.id.addButton)
        searchEditText = findViewById(R.id.search)

        // This creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ArrayList of class Product
        originalProductList.addAll(addProductsToList())

        // This will pass the ArrayList to our Adapter
        myAdapter = MyCustomAdapter(originalProductList)

        // Setting the Adapter with the RecyclerView
        recyclerView.adapter = myAdapter

        btnAdd.setOnClickListener {
            val intent = Intent(this, addContact::class.java)
            startActivity(intent)
        }
        myAdapter.setOnItemClickListener(object : MyCustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val clickedItem =originalProductList [position]
                val intent=Intent(this@MainActivity,DetailActivity::class.java);
                intent.putExtra("name",clickedItem.name);
                intent.putExtra("phone",clickedItem.phone.toString());
                intent.putExtra("id",clickedItem.id.toString());
                startActivity(intent);
                Toast.makeText(this@MainActivity,"this is item"+clickedItem.name,Toast.LENGTH_SHORT).show();
                // Do something with the clicked item
            }
        })

        val resultTextView: TextView = findViewById(R.id.data);
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before the text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do something while the text is changing
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after the text has changed
                val searchText = s.toString()
                // Update the TextView with the text from the EditText
                resultTextView.text = searchText
                val filteredList = originalProductList.filter { user ->
                    user.name.contains(searchText, ignoreCase = true)
                }
                myAdapter.updateData(filteredList)

            }
        })

    }

    private fun addProductsToList(): ArrayList<User> {
        val list = ArrayList<User>()
        val db = DataBaseHandler(this)
        val data = db.readData()
        list.addAll(data)
        return list
    }
}
