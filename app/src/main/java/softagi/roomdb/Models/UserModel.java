package softagi.roomdb.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class UserModel
{
    @PrimaryKey(autoGenerate = true)
    public int uId;
    @ColumnInfo(name = "first_name")
    private String firstname;
    @ColumnInfo(name = "last_name")
    private String lastname;

    // insert
    public UserModel(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Ignore
    public UserModel(int uId, String firstname, String lastname)
    {
        this.uId = uId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
