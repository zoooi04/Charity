
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Volunteer extends Person implements Serializable {    
    
    private String id;

    public Volunteer() {
        super();
    }

    public Volunteer(String id, String name, int age, LocalDate birthday, Gender gender, String phoneNo) {
        super(name, age, birthday, gender, phoneNo);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o){ // check whether same instance
            return true;
        }
        if(o == null || getClass() != o.getClass()){ // check whether different class
            return false;
        }
        Volunteer volunteer = (Volunteer) o;
        return id != null && id.equals(volunteer.id); // id same        
    }

    @Override
    public int hashCode() {
        // generate a hash code based on id
        if(id != null){
            return id.hashCode();
        }else{
            return 0;
        }
        
    }
    
    @Override
    public String toString() {
        return String.format("%-10s | %-30s | %-10s | %-20s | %-10s | %-20s | %-30s",
            id,
            getName(),
            getAge(),
            getBirthday(),
            getGender(),
            getPhoneNo(),
            getRegisterDate()
        );
    }
    
    
//    @Override
//    public String toString(){
//        return String.format("%-10s%-30s%-10s%-20s%-10s%-20s%-30s%-20s", 
//                id, this.name, this.age, this.birthday, this.gender, this.phoneNo, this.registerDate);
//    }

//    @Override
//    public String toString() {
//        return String.format("%-10s% %s", id,super.toString());
//    }
    
//    @Override
//    public String toString() {
//        return String.format("%s%-10s%-20s", super.toString(), id, eventType);
//    }
    

}
