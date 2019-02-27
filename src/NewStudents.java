import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NewStudents{

    private ObservableList<Abiturient> abiturients = FXCollections.observableArrayList();;
    private int count;
    private int registration_number;
    public NewStudents(){
        registration_number=1;
        count = 0;
    }
    public NewStudents(Items items, ObservableList<Abiturient> list){
        abiturients = list;
        registration_number=items.getRegistration_number();
        count = items.getCount();
    }
    public void create(String first_name, String second_name,
                String third_name, int birth_year,
                int certificate_average_mark,
                int exam_first_rating,
                int exam_second_rating,
                int exam_third_rating){
        boolean space_finded=false;

        for(Abiturient all_abiturients:abiturients){
            space_finded=true;
            for(Abiturient abiturient:abiturients){
                if(all_abiturients.getRegistration_number()+1 ==
                        abiturient.getRegistration_number())space_finded=false;
            }
            if(space_finded){
                registration_number=all_abiturients.getRegistration_number()+1;
                break;
            }
        }
        if(!space_finded){registration_number=count+1;}
        else{System.out.println("finded");}
        abiturients.add(
                        new Abiturient(
                                        first_name, second_name,
                                        third_name, birth_year,
                                        registration_number,
                                        certificate_average_mark,
                                        exam_first_rating,
                                        exam_second_rating,
                                        exam_third_rating));
        count++;
    }
    public void delete(int registration_number){
        for (Abiturient item : abiturients){
            if(item.getRegistration_number()==registration_number){
                count--;
                abiturients.remove(abiturients.indexOf(item));
                break;
            }
        }
    }
    public void deleteAll(){
        count=0;
        registration_number=1;
        abiturients.clear();
        System.out.println("Все записи удалены.");
    }
    public void deleteByAverageMark(int average_mark){
        for (int i = 0; i < count; i++){
            if(abiturients.get(i).getCertificate_average_mark()<average_mark+1){
                abiturients.remove(i);
                count--;
                i--;
            }
        }
        System.out.println("Записи удалены.");
    }
    public ObservableList getList(){
        return this.abiturients;
    }
    public Items getItems(){
        return new Items(this.count, this.registration_number);
    }
    public void setItems(Items items){
        this.count = items.getCount();
        this.registration_number=items.getRegistration_number();
    }
    public List<Abiturient> findName(String name){
        List<Abiturient> names = new ArrayList<>();
        for(Abiturient abiturient:abiturients){
            if(abiturient.getFirst_name().toLowerCase().equals(name)||
               abiturient.getSecond_name().toLowerCase().equals(name)||
               abiturient.getThird_name().toLowerCase().equals(name))names.add(abiturient);
        }
        return names;
    }
    public List<Abiturient> findYear(short year){
        List<Abiturient> years = new ArrayList<>();
        for(Abiturient abiturient:abiturients){
            if(abiturient.getBirth_year()==year)years.add(abiturient);
        }
        return years;
    }
    public List<Abiturient> findRegistrationNumber(short number){
        List<Abiturient> numbers = new ArrayList<>();
        for(Abiturient abiturient:abiturients){
            if(abiturient.getRegistration_number()==number)numbers.add(abiturient);
        }
        return numbers;
    }
    public List<Abiturient> findMark(byte mark){
        List<Abiturient> marks = new ArrayList<>();
        for(Abiturient abiturient:abiturients){
            if(abiturient.getCertificate_average_mark()==mark ||
               abiturient.getExam_first_rating()==mark ||
               abiturient.getExam_second_rating()==mark ||
               abiturient.getExam_third_rating()==mark)marks.add(abiturient);
        }
        return marks;
    }
    public void sort(){
        ObservableList<Abiturient> temp = FXCollections.observableArrayList();
        while (abiturients.size()!=0){
            int min=abiturients.get(0).getRegistration_number(), index=0;
            for(Abiturient abiturient:abiturients){
                if(abiturient.getRegistration_number()<min){
                    min=abiturient.getRegistration_number();
                    index=abiturients.indexOf(abiturient);
                }
            }
            temp.add(abiturients.get(index));
            abiturients.remove(index);
        }
        abiturients=temp;
    }
}
class Items implements Serializable{
    private int count;
    private int registration_number;
    Items(int count, int registration_number){
        this.count=count;
        this.registration_number = registration_number;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }
    public int getRegistration_number() {
        return registration_number;
    }
}