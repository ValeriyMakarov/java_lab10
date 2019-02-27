import java.io.Serializable;

public class Abiturient implements Serializable {
    private String first_name;
    private String second_name;
    private String third_name;
    private short birth_year;
    private short registration_number;
    private byte certificate_average_mark;
    private byte exam_first_rating;
    private byte exam_second_rating;
    private byte exam_third_rating;
    Abiturient(int registration_number){
        this.first_name="";
        this.second_name="";
        this.third_name="";
        this.birth_year=0;
        this.registration_number = (short) registration_number;
        this.certificate_average_mark=0;
        this.exam_first_rating=0;
        this.exam_second_rating=0;
        this.exam_third_rating=0;
    }
    Abiturient(String first_name,
               String second_name,
               String third_name,
               int birth_year,
               int registration_number,
               int certificate_average_mark,
               int exam_first_rating,
               int exam_second_rating,
               int exam_third_rating){
        this.first_name=first_name;
        this.second_name=second_name;
        this.third_name=third_name;
        this.birth_year=(short) birth_year;
        this.registration_number=(short)registration_number;
        this.certificate_average_mark=(byte)certificate_average_mark;
        this.exam_first_rating=(byte)exam_first_rating;
        this.exam_second_rating=(byte)exam_second_rating;
        this.exam_third_rating=(byte)exam_third_rating;
    }
//----------
    public void setFirst_name(String name) {
    this.first_name = name;
}
    public void setSecond_name(String name){
        this.second_name = name;
    }
    public void setThird_name(String name){
        this.third_name = name;
    }
    public void setBirth_year(int number){
        this.birth_year=(short)number;
    }
    public void setRegistration_number (int number){
        this.registration_number=(short)number;
    }
    public void setCertificate_average_mark(int number){
        this.certificate_average_mark=(byte) number;
    }
    public void setExam_first_rating(int number){
        this.exam_first_rating=(byte) number;
    }
    public void setExam_second_rating(int number){
        this.exam_second_rating=(byte) number;
    }
    public void setExam_third_rating(int number){
        this.exam_third_rating=(byte) number;
    }
//---------
    public String getFirst_name(){
        return first_name;
    }
    public String getSecond_name(){
        return second_name;
    }
    public String getThird_name(){
        return third_name;
    }
    public short getBirth_year(){
        return birth_year;
    }
    public short getRegistration_number (){
        return registration_number;
    }
    public byte getCertificate_average_mark(){
        return certificate_average_mark;
    }
    public byte getExam_first_rating(){
        return exam_first_rating;
    }
    public byte getExam_second_rating(){
        return exam_second_rating;
    }
    public byte getExam_third_rating(){
        return exam_third_rating;
    }
//---------
    public void output_to_console(){
        try{
            System.out.println("\n\tАбитуриент №"+ registration_number+"\n\nФИО: "+
                               second_name + " " + first_name+ " " + third_name +
                               "\n" +"\t\t\tСредний балл атестата\t\tПервый экзамен"+
                               "\t\tВторой экзамен\t\tТретий экзамен\n" + "Рэйтинг:\t\t\t" +
                               certificate_average_mark + "\t\t\t\t|\t\t" + exam_first_rating +
                               "\t\t\t|\t\t"+ exam_second_rating+"\t\t\t|\t\t"+exam_third_rating+ "\n");
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}
