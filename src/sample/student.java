package sample;

public class student {
    private int id ;
    private  String name ;
    private String address ;
    private String title;

    public student(int id , String name , String address , String title){
        this.id = id ;
        this.name = name;
        this.address = address ;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
