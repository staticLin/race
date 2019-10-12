/**
  * Copyright 2019 bejson.com 
  */
package race.question.demo.pojo;
import java.util.List;

/**
 * Auto-generated: 2019-10-08 14:6:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private String UID;
    private String Name;
    private String Birthday;
    private String Sex;
    private boolean IsDeleted;
    private List<String> LoginHistory;
    private Info Info;
    public void setUID(String UID) {
         this.UID = UID;
     }
     public String getUID() {
         return UID;
     }

    public void setName(String Name) {
         this.Name = Name;
     }
     public String getName() {
         return Name;
     }

    public void setBirthday(String Birthday) {
         this.Birthday = Birthday;
     }
     public String getBirthday() {
         return Birthday;
     }

    public void setSex(String Sex) {
         this.Sex = Sex;
     }
     public String getSex() {
         return Sex;
     }

    public void setIsDeleted(boolean IsDeleted) {
         this.IsDeleted = IsDeleted;
     }
     public boolean getIsDeleted() {
         return IsDeleted;
     }

    public void setLoginHistory(List<String> LoginHistory) {
         this.LoginHistory = LoginHistory;
     }
     public List<String> getLoginHistory() {
         return LoginHistory;
     }

    public void setInfo(Info Info) {
         this.Info = Info;
     }
     public Info getInfo() {
         return Info;
     }

}