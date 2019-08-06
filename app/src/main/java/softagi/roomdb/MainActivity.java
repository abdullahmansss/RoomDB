package softagi.roomdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import softagi.roomdb.Models.UserModel;

public class MainActivity extends AppCompatActivity
{
    AppDatabase appDatabase;

    EditText firstname_field,lastname_field;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "abdo").build();
        new get().execute();
    }

    public void initViews()
    {
        firstname_field = findViewById(R.id.firstname_field);
        lastname_field = findViewById(R.id.lastname_field);
        recyclerView = findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void adduser(View view)
    {
        String f = firstname_field.getText().toString();
        String l = lastname_field.getText().toString();

        if (f.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "enter first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (l.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        UserModel userModel = new UserModel(f,l);
        new insert().execute(userModel);
        firstname_field.setText("");
        firstname_field.requestFocus();
        lastname_field.setText("");
    }

    class get extends AsyncTask<Void, Void, List<UserModel>>
    {
        List<UserModel> uu;

        @Override
        protected List<UserModel> doInBackground(Void... voids)
        {
            uu = appDatabase.userDao().getAll();
            return uu;
        }

        @Override
        protected void onPostExecute(List<UserModel> userModels)
        {
            userAdapter adapter = new userAdapter(userModels);
            recyclerView.setAdapter(adapter);
        }
    }

    class insert extends AsyncTask<UserModel , Void, Void>
    {

        @Override
        protected Void doInBackground(UserModel... userModels)
        {
            appDatabase.userDao().insert(userModels);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            new get().execute();
        }
    }

    class userAdapter extends RecyclerView.Adapter<userAdapter.userVH>
    {
        List<UserModel> models;

        public userAdapter(List<UserModel> models)
        {
            this.models = models;
        }

        @NonNull
        @Override
        public userVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.user_item, parent, false);
            return new userVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull userVH holder, int position)
        {
            UserModel userModel = models.get(position);

            int id = userModel.getuId();
            String f = userModel.getFirstname();
            String l = userModel.getLastname();

            holder.id.setText(String.valueOf(id));
            holder.fisrt.setText(f);
            holder.last.setText(l);
        }

        @Override
        public int getItemCount()
        {
            return models.size();
        }

        class userVH extends RecyclerView.ViewHolder
        {
            TextView id,fisrt,last;

            public userVH(@NonNull View itemView)
            {
                super(itemView);

                id = itemView.findViewById(R.id.id_txt);
                fisrt = itemView.findViewById(R.id.first_txt);
                last = itemView.findViewById(R.id.last_txt);
            }
        }
    }
}
