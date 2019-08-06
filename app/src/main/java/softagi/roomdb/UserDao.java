package softagi.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import softagi.roomdb.Models.UserModel;

@Dao
public interface UserDao
{
    @Insert
    void insert(UserModel... u);

    @Query("SELECT * FROM UserModel")
    List<UserModel> getAll();

    @Update
    void update(UserModel userModel);

    @Delete
    void delete(UserModel userModel);
}
