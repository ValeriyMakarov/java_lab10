import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    final String help_info_standart = "Выберите файл, с которым хотите работать",
    help_info_create = "Введите название файла и нажмите кнопку \"Создать\"",
    file_search_help_message = "\t   Результатаов не найдено.\nДля продолжения "+
            "сотрите введённое\n\tимя файла и нажмите \"Enter\"",
    empty_table_with_files_message = "Файлов не найдено",
    empty_table_with_abiturients_message = "В файле нет записей",
    label_create = "Заполните поля, а затем нажмите \"сохранить\"",
    label_recreate = "Измените содержимое полей и нажмите \"сохранить\"",
    label_eror = "Некорриектно введены данные",
    direct_name = "saves",
    file_expansion = ".ab",
    file_old_expansion = ".oldab",
    clear_file = "Удалить все записи?",
    put_average_mark = "\t    Введите средний балл.\nВсе записи со средним баллом ниже\n\t  указаного будут удалены!"+
            "\n\tВведите от 1 до 100.";
    final File direct = new File(direct_name);
    File file_to_read, old_file;
    NewStudents students = null, data_for_search = null;
    boolean recreate=false;
    //--------------------------------------------------------------------------
    final FilenameFilter files_accepts = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(file_expansion);
        }
    };
    //--------------------------------------------------------------------------
    @FXML private AnchorPane page_one /*виден при запуске*/, page_two, page_three, file_control;

    private ObservableList<FileName> list_with_files = FXCollections.observableArrayList();;
    @FXML private TextField file_search;
    @FXML private TableView<FileName> table_with_files;
    @FXML private TableColumn<FileName, String> table_with_files_column;
    @FXML private Button create_new_file_button;

    @FXML private Label help_info;
    @FXML private HBox file_name_input_box;
    @FXML private TextField file_name_input_field;
    @FXML private Button file_name_input_button;
    @FXML private Button file_name_undo_button;

    @FXML private TextField table_with_abiturients_search;
    @FXML private Button table_with_abiturients_search_button;
    @FXML private Button table_with_abiturients_delete_file_button;
    @FXML private Button table_with_abiturients_clear_button;
    @FXML private Button table_with_abiturients_delete_by_mark_button;
    @FXML private Button table_with_abiturients_create_button;
    @FXML private Button table_with_abiturients_undo_button;
    @FXML private Button table_with_abiturients_save_button;
    @FXML private Button table_with_abiturients_restore_old_button;

    @FXML private TableView<Abiturient> table_with_abiturients;
    @FXML private TableColumn<Abiturient, Short> table_with_abiturients_number;
    @FXML private TableColumn<Abiturient, String> table_with_abiturients_first_name;
    @FXML private TableColumn<Abiturient, String> table_with_abiturients_second_name;
    @FXML private TableColumn<Abiturient, String> table_with_abiturients_third_name;
    @FXML private TableColumn<Abiturient, Short> table_with_abiturients_birth_year;
    @FXML private TableColumn<Abiturient, Byte> table_with_abiturients_exam_first_rating;
    @FXML private TableColumn<Abiturient, Byte> table_with_abiturients_exam_second_rating;
    @FXML private TableColumn<Abiturient, Byte> table_with_abiturients_exam_third_rating;
    @FXML private TableColumn<Abiturient, Byte> table_with_abiturients_certificate_average_mark;

    @FXML private TextField first_name_field;
    @FXML private TextField second_name_field;
    @FXML private TextField third_name_field;
    @FXML private TextField year_field;
    @FXML private TextField certificate_average_mark_field;
    @FXML private TextField exam_first_field;
    @FXML private TextField exam_second_field;
    @FXML private TextField exam_third_field;
    @FXML private Button page_three_clear_button;
    @FXML private Button page_three_undo_button;
    @FXML private Button page_three_save_button;
    @FXML private Label page_three_label;
    //--------------------------------------------------------------------------
    @FXML void initialize(){

//-------------------
        table_with_abiturients_number.setCellValueFactory(new PropertyValueFactory<>("registration_number"));
        table_with_abiturients_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        table_with_abiturients_second_name.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        table_with_abiturients_third_name.setCellValueFactory(new PropertyValueFactory<>("third_name"));
        table_with_abiturients_birth_year.setCellValueFactory(new PropertyValueFactory<>("birth_year"));
        table_with_abiturients_exam_first_rating.setCellValueFactory(new PropertyValueFactory<>("exam_first_rating"));
        table_with_abiturients_exam_second_rating.setCellValueFactory(new PropertyValueFactory<>("exam_second_rating"));
        table_with_abiturients_exam_third_rating.setCellValueFactory(new PropertyValueFactory<>("exam_third_rating"));
        table_with_abiturients_certificate_average_mark.setCellValueFactory(new PropertyValueFactory<>("certificate_average_mark"));
        table_with_abiturients.setPlaceholder(new Label(empty_table_with_abiturients_message));

        checkDirect();
        String[] files = direct.list(files_accepts);
        for(String temp: files) list_with_files.add(new FileName(temp));
        table_with_files_column.setCellValueFactory(new PropertyValueFactory<>("file_name"));
        table_with_files.setItems(list_with_files);
        table_with_files.setPlaceholder(new Label(empty_table_with_files_message));

        file_name_input_button.setOnAction(event -> {
            String text = file_name_input_field.getText();
            File file_name = new File(direct_name + "//" + text + file_expansion);
            if(!(text==null) && !(text.length()==0) && !file_name.exists()){
                try{
                    file_name.createNewFile();
                    list_with_files.add(new FileName(file_name.getName()));
                    table_with_files.refresh();
                    help_info.setText(help_info_standart);
                    file_name_input_box.setVisible(false);
                    file_name_input_field.setText(null);
                    create_new_file_button.setDisable(false);
                    file_search.setDisable(false);
                }catch (Exception ex){System.exit(1);}
            }
        });
        file_search.setOnAction(event -> {
            FilenameFilter new_filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {

                    if(name.toLowerCase().contains(file_search.getText().toLowerCase()))
                    return name.endsWith(file_expansion);
                    else return false;
                }
            };
            String[] temp_list_with_files = direct.list(new_filter);
            list_with_files.remove(0, list_with_files.size());
            for(String temp: temp_list_with_files){
                list_with_files.add(new FileName(temp));
            }
            if(temp_list_with_files.length == 0)
                table_with_files.setPlaceholder(new Label(file_search_help_message));
            table_with_files.refresh();
        });
        create_new_file_button.setOnAction(event -> {
            help_info.setText(help_info_create);
            file_name_input_box.setVisible(true);
            file_name_undo_button.setDisable(false);
        });
        file_name_undo_button.setOnAction(event -> {
            help_info.setText(help_info_standart);
            file_name_input_box.setVisible(false);
            file_name_input_field.setText(null);
        });
        table_with_files.setOnMouseClicked(event -> {
            if(page_one.isVisible()==true && table_with_files.getSelectionModel().getSelectedItem()!=null) {
                page_one.setVisible(false);
                page_two.setVisible(true);
                file_control.setDisable(true);
                file_to_read = new File(direct_name + "//" +
                        table_with_files.getSelectionModel().getSelectedItem().getFile_name());
                old_file = new File(direct_name + "//" +
                        table_with_files.getSelectionModel().getSelectedItem().getFile_name().substring(
                        0,table_with_files.getSelectionModel().getSelectedItem().getFile_name().length()-3)
                        +"(old)"+file_old_expansion);
                if(old_file.exists())table_with_abiturients_restore_old_button.setDisable(false);
                if (file_to_read.exists()) {
                    students = readFile(file_to_read);
                    if (students != null){
                        data_for_search = new NewStudents(students.getItems(), students.getList());
                        table_with_abiturients.setItems(data_for_search.getList());
                        if(students.getItems().getCount()!=0)table_with_abiturients_delete_by_mark_button.setDisable(false);
                        if(students.getItems().getCount()!=0)table_with_abiturients_clear_button.setDisable(false);
                    }
                    else {table_with_abiturients_delete_by_mark_button.setDisable(true);
                        table_with_abiturients_clear_button.setDisable(true);
                        students = new NewStudents();
                        data_for_search = new NewStudents(students.getItems(), students.getList());
                        table_with_abiturients.setItems(data_for_search.getList());
                    }
                }
                table_with_files.getSelectionModel().select(null);
            }
        });

        //--------------------------------------------------------------------
        table_with_abiturients_clear_button.setOnAction(event -> {
            table_with_abiturients_search.setText(null);
            try{
                AskingWindow.setLabel(clear_file);
                Parent parent = FXMLLoader.load(getClass().getResource("askingWindow.fxml"));
                Stage stage = new Stage();
                AskingWindow.setStage(stage);
                showAskingWindow(stage, parent);
                if(AskingWindow.getAnswer()){
                    students.deleteAll();
                    data_for_search = new NewStudents(students.getItems(),students.getList());
                    table_with_abiturients.refresh();
                    table_with_abiturients_clear_button.setDisable(true);
                    table_with_abiturients_delete_by_mark_button.setDisable(true);
                    table_with_abiturients_save_button.setDisable(false);
                }
                AskingWindow.setLabel(null);
                AskingWindow.setStage(null);
            }catch (Exception ex){}
        });
        table_with_abiturients_delete_by_mark_button.setOnAction(event -> {
            try{
                table_with_abiturients_search.setText(null);
                AskingWindow.setTextFieldVisible(true);
                AskingWindow.setNoButtonVisible(false);
                AskingWindow.setYesButtonVisible(false);
                AskingWindow.setUndoButtonVisible(true);
                AskingWindow.setLabel(put_average_mark);
                Parent parent = FXMLLoader.load(getClass().getResource("askingWindow.fxml"));
                Stage stage = new Stage();
                AskingWindow.setStage(stage);
                showAskingWindow(stage, parent);
                AskingWindow.getText_field();
                if(AskingWindow.getAnswer()){
                    int count = students.getItems().getCount();
                    students.deleteByAverageMark(AskingWindow.getText_field());
                    data_for_search = new NewStudents(students.getItems(),students.getList());
                    table_with_abiturients.refresh();System.out.println("!");
                    if(students.getItems().getCount()<1) {
                        table_with_abiturients_clear_button.setDisable(true);
                        table_with_abiturients_delete_by_mark_button.setDisable(true);
                    }
                    if(count!=students.getItems().getCount())
                    table_with_abiturients_save_button.setDisable(false);
                }
                AskingWindow.setLabel(null);
                AskingWindow.setStage(null);
                AskingWindow.setText_field((byte)0);
                AskingWindow.setTextFieldVisible(false);
                AskingWindow.setNoButtonVisible(true);
                AskingWindow.setYesButtonVisible(true);
                AskingWindow.setUndoButtonVisible(false);
            }catch (Exception ex){}
        });
        table_with_abiturients_delete_file_button.setOnAction(event -> {
            table_with_abiturients_search.setText(null);
            page_two.setVisible(false);
            page_one.setVisible(true);
            file_control.setDisable(false);
            if(files.length==0){
                file_name_input_box.setVisible(true);
                help_info.setText(help_info_create);
            }
            try{
                FileName temp;
                for(int i = 0; i<list_with_files.size(); i++){
                    temp = list_with_files.get(i);
                    if(temp.getFile_name().equals(file_to_read.getName()))list_with_files.remove(i);
                }
            table_with_files.refresh();
            students.deleteAll();
            data_for_search.deleteAll();
            file_to_read.delete();
            if(old_file.exists())old_file.delete();
            table_with_abiturients.refresh();
            }catch (Exception ex){}
        });
        table_with_abiturients_undo_button.setOnAction(event -> {
            table_with_abiturients_search.setText(null);
            page_two.setVisible(false);
            page_one.setVisible(true);
            file_control.setDisable(false);
            table_with_abiturients_save_button.setDisable(true);
            table_with_abiturients_restore_old_button.setDisable(true);
            table_with_abiturients_search_button.setDisable(true);
            table_with_abiturients_delete_by_mark_button.setDisable(true);
            table_with_abiturients_clear_button.setDisable(true);
            try{
                students.deleteAll();
                data_for_search.deleteAll();
            }catch(Exception ex){}
            table_with_abiturients.refresh();
        });
        table_with_abiturients_create_button.setOnAction(event -> {
            table_with_abiturients_search.setText(null);
            page_two.setVisible(false);
            page_three.setVisible(true);
            page_three_label.setText(label_create);
        });
        table_with_abiturients_restore_old_button.setOnAction(event -> {
            table_with_abiturients_search.setText(null);
            students = readFile(old_file);
            if(students==null|| students.getItems().getCount()==0){
                students = new NewStudents();
                table_with_abiturients_delete_by_mark_button.setDisable(true);
                table_with_abiturients_clear_button.setDisable(true);
            }
            data_for_search = new NewStudents(students.getItems(),students.getList());
            table_with_abiturients.setItems(data_for_search.getList());
            table_with_abiturients.refresh();
            table_with_abiturients_restore_old_button.setDisable(true);
            old_file.delete();
        });
        table_with_abiturients_save_button.setOnAction(event -> {
            if(!old_file.exists()) {
                try {
                    old_file.createNewFile();
                    NewStudents temp=readFile(file_to_read);
                    if(temp==null)temp=new NewStudents();
                    writeFile(old_file, temp);
                    writeFile(file_to_read,students);
                } catch (Exception e) {}
            }
            else{
                NewStudents temp=readFile(file_to_read);
                if(temp==null)temp=new NewStudents();
                writeFile(old_file, temp);
                writeFile(file_to_read,students);
            }
            table_with_abiturients_restore_old_button.setDisable(true);
        });
        table_with_abiturients_search_button.setOnAction(event -> {
            try {
                students.delete(table_with_abiturients.getSelectionModel().getSelectedItem().getRegistration_number());
                data_for_search = new NewStudents(students.getItems(),students.getList());
                table_with_abiturients.refresh();
                table_with_abiturients_search_button.setDisable(true);
                table_with_abiturients.getSelectionModel().select(null);
                table_with_abiturients_search.setText(null);
                table_with_abiturients_save_button.setDisable(false);
            }catch (Exception ex){}
        });
        table_with_abiturients_search.setOnAction(event -> {
            List<String> words = scanText(" "+table_with_abiturients_search.getText()+" ");
            ObservableList<Abiturient> temp = FXCollections.observableArrayList();
            if(words.size()!=0) {
                for (String word : words) {
                    if (word.matches("[0-9]{1,3}"))
                        for (Abiturient abiturient : students.findMark(Byte.parseByte(word))) {
                            if (!temp.contains(abiturient))
                                temp.add(abiturient);
                        }
                    else if (word.matches("[a-zA-Zа-яА-Я]{2,}"))
                        for (Abiturient abiturient : students.findName(word.toLowerCase())) {
                            if (!temp.contains(abiturient))
                                temp.add(abiturient);
                        }
                    else if (word.matches("\\d{4}г"))
                        for (Abiturient abiturient : students.findYear(
                                Short.parseShort(word.substring(0, word.length() - 1)))) {
                            if (!temp.contains(abiturient))
                                temp.add(abiturient);
                        }
                    else if (word.matches("№\\d+"))
                        for (Abiturient abiturient : students.findRegistrationNumber(
                                Short.parseShort(word.substring(1, word.length())))) {
                            if (!temp.contains(abiturient))
                                temp.add(abiturient);
                        }
                }
                data_for_search = new NewStudents(new Items(temp.size(), 1), temp);
                if(temp.size()==0)data_for_search=new NewStudents();
            }
            else{
                data_for_search = new NewStudents(students.getItems(), students.getList());
            }
            table_with_abiturients.setItems(data_for_search.getList());
            table_with_abiturients.refresh();
        });
        table_with_abiturients.setOnMouseClicked(event -> {
            Abiturient abiturient = table_with_abiturients.getSelectionModel().getSelectedItem();
            if(abiturient!=null) {
                table_with_abiturients_search.setText(
                        "№" + abiturient.getRegistration_number() + " " +
                                abiturient.getFirst_name() + " " + abiturient.getSecond_name() +
                                " " + abiturient.getThird_name() + " " + abiturient.getBirth_year()
                                + "г"
                );
                table_with_abiturients_search_button.setDisable(false);
                if (event.getClickCount() == 2) {
                    recreate=true;
                    first_name_field.setText(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getFirst_name());
                    second_name_field.setText(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getSecond_name());
                    third_name_field.setText(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getThird_name());
                    year_field.setText(String.valueOf(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getBirth_year()));
                    certificate_average_mark_field.setText(String.valueOf(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getCertificate_average_mark()));
                    exam_first_field.setText(String.valueOf(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getExam_first_rating()));
                    exam_second_field.setText(String.valueOf(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getExam_second_rating()));
                    exam_third_field.setText(String.valueOf(
                            table_with_abiturients.getSelectionModel().getSelectedItem().getExam_third_rating()));
                    page_three.setVisible(true);
                    page_two.setVisible(false);
                    page_three_label.setText(label_recreate);
                }
            }
        });

        //----------------------------------------------------------------
        page_three_clear_button.setOnAction(event -> {
            first_name_field.setText(null);
            second_name_field.setText(null);
            third_name_field.setText(null);
            year_field.setText(null);
            certificate_average_mark_field.setText(null);
            exam_first_field.setText(null);
            exam_second_field.setText(null);
            exam_third_field.setText(null);
        });
        page_three_save_button.setOnAction(event -> {
            if(first_name_field.getText()!=null&&
            second_name_field.getText()!=null&&
            third_name_field.getText()!=null&&
            year_field.getText()!=null&&
            certificate_average_mark_field.getText()!=null&&
            exam_first_field.getText()!=null&&
            exam_second_field.getText()!=null&&
            exam_third_field.getText()!=null) {
                String first_name = first_name_field.getText(),
                        second_name = second_name_field.getText(),
                        third_name = third_name_field.getText(),
                        year = year_field.getText(),
                        certificate_average_mark = certificate_average_mark_field.getText(),
                        exam_first = exam_first_field.getText(),
                        exam_second = exam_second_field.getText(),
                        exam_third = exam_third_field.getText();
                if (first_name.matches("^[a-zA-Zа-яА-Я]{2,30}$") &&
                        second_name.matches("^[a-zA-Zа-яА-Я]{2,30}$") &&
                        third_name.matches("^[a-zA-Zа-яА-Я]{2,30}$") &&
                        year.matches("^[0-9]{4}$") &&
                        Short.parseShort(year) >= 1900 && Short.parseShort(year) <= 2019 &&
                        certificate_average_mark.matches("^[0-9]{1,3}$") &&
                        Short.parseShort(certificate_average_mark) <= 100 &&
                        exam_first.matches("^[0-9]{1,3}$") &&
                        Short.parseShort(certificate_average_mark) <= 100 &&
                        exam_second.matches("^[0-9]{1,3}$") &&
                        Short.parseShort(certificate_average_mark) <= 100 &&
                        exam_third.matches("^[0-9]{1,3}$") &&
                        Short.parseShort(certificate_average_mark) <= 100) {
                    if (recreate) {
                        Abiturient temp = new Abiturient(first_name_field.getText(),
                                second_name_field.getText(),
                                third_name_field.getText(),
                                Short.parseShort(year_field.getText()),
                                table_with_abiturients.getSelectionModel().getSelectedItem().getRegistration_number(),
                                Byte.parseByte(certificate_average_mark_field.getText()),
                                Byte.parseByte(exam_first_field.getText()),
                                Byte.parseByte(exam_second_field.getText()),
                                Byte.parseByte(exam_third_field.getText()));
                        students.delete(table_with_abiturients.getSelectionModel().getSelectedItem().getRegistration_number());
                        students.getList().add(temp);
                        students.setItems(new Items(students.getItems().getCount() + 1, 0));
                    } else {
                        students.create(first_name,
                                second_name,
                                third_name,
                                Short.parseShort(year),
                                Byte.parseByte(certificate_average_mark),
                                Byte.parseByte(exam_first),
                                Byte.parseByte(exam_second),
                                Byte.parseByte(exam_third));
                    }
                    recreate = false;
                    page_three.setVisible(false);
                    page_two.setVisible(true);
                    students.sort();
                    data_for_search = new NewStudents(students.getItems(), students.getList());
                    table_with_abiturients.setItems(data_for_search.getList());
                    table_with_abiturients.refresh();
                    table_with_abiturients_clear_button.setDisable(false);
                    table_with_abiturients_delete_by_mark_button.setDisable(false);
                    table_with_abiturients_save_button.setDisable(false);
                } else {
                    page_three_label.setText(label_eror);
                }
                first_name_field.setText(null);
                second_name_field.setText(null);
                third_name_field.setText(null);
                year_field.setText(null);
                certificate_average_mark_field.setText(null);
                exam_first_field.setText(null);
                exam_second_field.setText(null);
                exam_third_field.setText(null);
            }
            else page_three_label.setText(label_eror);
        });
        page_three_undo_button.setOnAction(event -> {
            page_two.setVisible(true);
            page_three.setVisible(false);
            recreate=false;
            first_name_field.setText(null);
            second_name_field.setText(null);
            third_name_field.setText(null);
            year_field.setText(null);
            certificate_average_mark_field.setText(null);
            exam_first_field.setText(null);
            exam_second_field.setText(null);
            exam_third_field.setText(null);
        });
    }

    //--------------------------------------------------------------------------
    private void checkDirect(){
        String[] files_in_dir = direct.list(files_accepts);
        if(!direct.exists()){
            try{
                direct.mkdir();
                help_info.setText(help_info_create);
                create_new_file_button.setDisable(true);
                file_search.setDisable(true);
                file_name_input_box.setVisible(true);
            }catch (Exception ex){System.exit(2);}
        }
        else if(files_in_dir.length == 0){
            help_info.setText(help_info_create);
            file_name_input_box.setVisible(true);
            create_new_file_button.setDisable(true);
            file_search.setDisable(true);
        }
    }
    private NewStudents readFile(File file_to_read_from){
        NewStudents read_obj = null;
        ObservableList<Abiturient> temp = FXCollections.observableArrayList();
        FileInputStream file_in=null;
        ObjectInputStream obj_in= null;
        try {
            Abiturient abiturient=null;
            Items items=null;
            file_in = new FileInputStream(file_to_read_from);
            obj_in = new ObjectInputStream(file_in);
            try {items = (Items) obj_in.readObject();}catch (Exception ex){}
            if(items!=null)
            for (int i=0; i<items.getCount(); i++){
                try{
                    abiturient = (Abiturient) obj_in.readObject();
                    temp.add(abiturient);
                }catch (Exception ex){/*игнорировать ошибку*/}
            }
            read_obj = new NewStudents(items, temp);
            obj_in.close();
            file_in.close();
        }catch (Exception ex){}
        finally {
            if(obj_in!=null)try {
                obj_in.close();
            }catch (Exception e){}
            if(file_in!=null)try {
                file_in.close();
            }catch (Exception e){}
        }
        return read_obj;
    }
    private void writeFile(File file_to_write_into, NewStudents obj_to_write){
        try {
            FileOutputStream file_out = new FileOutputStream(file_to_write_into);
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(obj_to_write.getItems());
            for(int i=0; i<obj_to_write.getList().size();i++){
                obj_out.writeObject(obj_to_write.getList().get(i));
            }
            obj_out.close();
            file_out.close();
        }catch (Exception ex){
            System.out.println(ex);
            System.exit(4);
        }
    }
    private void showAskingWindow(Stage stage, Parent parent){
        stage.initOwner(Main.getPrimaryStage());
        stage.setResizable(false);
        stage.setScene( new Scene(parent,250,150));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
    private List<String> scanText(String text){
        List<String> words = new ArrayList<>();
        Pattern for_number = Pattern.compile("№\\d+"),
                for_year = Pattern.compile("\\d{4}г"),
                for_name = Pattern.compile("[a-zA-Zа-яА-Я]{2,}"),
                for_mark = Pattern.compile("[0-9]{1,3}");
        Matcher matcher = for_year.matcher(text);
        while (matcher.find()){
            if(text.substring(matcher.start()-1,matcher.end()+1).matches("\\s+\\d{4}г\\s+") &&
                    Integer.parseInt(text.substring(matcher.start(),matcher.end()-1))<=2019 &&
                    Integer.parseInt(text.substring(matcher.start(),matcher.end()-1))>=1900){
                words.add(matcher.group());}
        }
        matcher=for_number.matcher(text);
        while (matcher.find()){
            if(text.substring(matcher.start()-1,matcher.end()+1).matches("\\s+№\\d+\\s+"))
                words.add(matcher.group());
        }
        matcher=for_name.matcher(text);
        while (matcher.find()){

            if(text.substring(matcher.start()-1,matcher.end()+1).matches("\\s+[a-zA-Zа-яА-Я]{2,}\\s+"))
                words.add(matcher.group());
        }
        matcher=for_mark.matcher(text);
        while (matcher.find()){
            if(text.substring(matcher.start()-1,matcher.end()+1).matches("\\s+[0-9]{1,3}\\s+") &&
                    Integer.parseInt(text.substring(matcher.start(),matcher.end()))<=100)
                words.add(matcher.group());
        }
        return words;
    }
}