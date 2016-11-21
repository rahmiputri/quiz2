package com.example.pustikom.adapterplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pustikom.adapterplay.com.example.pustikom.adapter.StudentArrayAdapter;
import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;
import com.example.pustikom.adapterplay.com.example.pustikom.user.StudentList;

import java.util.ArrayList;

/**
 * Created by pustikom on 07/10/16.
 */

public class StudentActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    private StudentArrayAdapter studentArrayAdapter;
    private ListView listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        studentArrayAdapter = new StudentArrayAdapter(this,new StudentList());
        listItem = (ListView) findViewById(R.id.list_item);
        listItem.setAdapter(studentArrayAdapter);

        //register button
        addButton  = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, StudentFormActivity.class);
                intent.putExtra("mode",0);
                startActivity(intent);
            }
        });

        //set listener for each list item
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentActivity.this, StudentFormActivity.class);
                intent.putExtra("mode",1);
                Student student = Student.getStudentList().get(position);
                intent.putExtra("Student",student);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        //call datasync to resynchronize the data
        StudentList list = Student.getStudentList();
        new DataSyncTask().execute(list);
    }

    private StudentList populateStudentDummies(){
        StudentList studentList = new StudentList();
        studentList.add(new Student("3145136188","TRI FEBRIANA SIAMI",1,"tri@mhs.unj.ac.id","0858xxxxxx"));
        studentList.add(new Student("3145136192","Ummu Kultsum",1,"ummu@mhs.unj.ac.id","0813xxxxxx"));
        return studentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.createDummyItem:
                StudentList students = populateStudentDummies();
                studentArrayAdapter = new StudentArrayAdapter(this,students);
                listItem.setAdapter(studentArrayAdapter);
                Student.setStudentList(students);
                return true;
            case R.id.clearListItem:
                studentArrayAdapter = new StudentArrayAdapter(this, new StudentList());
                listItem.setAdapter(studentArrayAdapter);
                Student.setStudentList(new StudentList());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DataSyncTask extends AsyncTask<StudentList, Void, StudentArrayAdapter>{
        @Override
        protected StudentArrayAdapter doInBackground(StudentList... params) {
            StudentArrayAdapter adapter = new StudentArrayAdapter(getApplicationContext(),params[0]);
            return adapter;
        }

        protected void onPostExecute(StudentArrayAdapter adapter){
            StudentActivity.this.listItem.setAdapter(adapter);
        }
    }
}
