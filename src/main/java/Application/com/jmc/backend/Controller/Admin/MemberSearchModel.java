package Application.com.jmc.backend.Controller.Admin;

import com.mysql.cj.conf.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberSearchModel {
     IntegerProperty MemberID;
    StringProperty MemberFirstName;
    StringProperty MemberLastName;
    StringProperty MemberEmail;

    public MemberSearchModel(int account_id, String member_name, String member_email) {
        this.MemberID = new IntegerProperty(account_id);
        this.MemberLastName = new SimpleStringProperty(member_name);
        this.MemberEmail = new SimpleStringProperty(member_email);
    }

    public IntegerProperty getMemberID() {
        return MemberID;
    }

    public void setMemberID(IntegerProperty memberID) {
        MemberID = memberID;
    }

    public StringProperty getMemberFirstName() {
        return MemberFirstName;
    }

    public void setMemberFirstName(StringProperty memberFirstName) {
        MemberFirstName = memberFirstName;
    }

    public StringProperty getMemberEmail() {
        return MemberEmail;
    }

    public void setMemberEmail(StringProperty memberEmail) {
        MemberEmail = memberEmail;
    }

    public StringProperty getMemberLastName() {
        return MemberLastName;
    }

    public void setMemberLastName(StringProperty memberLastName) {
        MemberLastName = memberLastName;
    }
}
