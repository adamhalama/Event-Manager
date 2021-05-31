package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Event.Event;
import client.Model.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class CreateEventViewModel
{
  private IntegerProperty idProperty;
  private StringProperty titleProperty;
  private StringProperty descriptionProperty;

  private IntegerProperty startHour;
  private IntegerProperty startMin;
  private IntegerProperty endHour;
  private IntegerProperty endMin;

  private ObservableList<Number> roomProperty;
  private StringProperty platformProperty;
  private StringProperty linkProperty;


  private ObservableList<EmployeeViewModel> employeeList;
  private StringProperty errorLabel;

  private Model model;

  public CreateEventViewModel(Model model)
  {
    this.model = model;
    this.idProperty = new SimpleIntegerProperty();
    this.titleProperty = new SimpleStringProperty();
    this.descriptionProperty = new SimpleStringProperty();
    this.startHour = new SimpleIntegerProperty();
    this.startMin = new SimpleIntegerProperty();
    this.endHour = new SimpleIntegerProperty();
    this.endMin = new SimpleIntegerProperty();


    this.roomProperty = FXCollections.observableArrayList();
    this.platformProperty = new SimpleStringProperty();
    this.linkProperty = new SimpleStringProperty();
    this.errorLabel = new SimpleStringProperty();

    this.employeeList = FXCollections.observableArrayList();
  }

  public void reset()
  {
      //todo make reset
  }

  public void clear()
  {
    employeeList.clear();
  }

  public ObservableList<EmployeeViewModel> update()
  {
    for (int i = 0; i < model.getParticipantsIDT().size(); i++)
    {
      int id = model.getParticipantsT().get(i).getId();
      String username = model.getParticipantsT().get(i).getName();
      String name = model.getParticipantsT().get(i).getFullName();
      String role = model.getParticipantsT().get(i).getRole();
      employeeList.add(i, new EmployeeViewModel(id, username, name, role));
    }
    return employeeList;
  }

  public void removeParticipant(int id)
  {
    for (int i = 0; i < employeeList.size(); i++)
    {
      if (employeeList.get(i).getUserIDProperty().get() == id)
      {
        employeeList.remove(i);
        model.removeEmployeeT(id);
      }
    }
  }

  public void addEvent(Event e)
  {
    model.add(e);
  }

  public StringProperty getTitleProperty()
  {
    return titleProperty;
  }

  public StringProperty getDescriptionProperty()
  {
    return descriptionProperty;
  }


  public boolean isOnline()
  {
    return model.isOnline();
  }

  public void setIsOnline(boolean isOnline)
  {
    model.setOnline(isOnline);
  }

  public void setErrorProperty(String errorLabel)
  {
    this.errorLabel.set(errorLabel);
  }

  public void setIdProperty(int idProperty)
  {
    this.idProperty.set(idProperty);
  }

  public int getID()
  {
    return model.getEvent_id();
  }

  public void add(String title, String des, int year, int month, int day,
      int hourS, int minuteS, int hourE, int minuteE, long startTime,
      long endTime, String platform, String link, Model model,
      ArrayList<Integer> paticipants)
  {
   /* Event e1 = new Event(title, des, year, month, day, hourS, minuteS, hourE,
        minuteE, true, platform, link, model,
        paticipants); // for local constructor
    addEvent(e1);
    setIdProperty(e1.getEvent_id());*/
  }

  public void add(String title, String des, int year, int month, int day,
      int hourS, int minuteS, int hourE, int minuteE, long startTime,
      long endTime, int id, Model model, ArrayList<Integer> paticipants)
  {
    /*Event e1 = new Event(title, des, year, month, day, hourS, minuteS, hourE,
        minuteE, false, id, model, paticipants); // for local constructor
    addEvent(e1);
    setIdProperty(e1.getEvent_id());*/
  }

  public ArrayList<String> getEmployeeList()
  {
    ArrayList<Employee> employees = null;
    try
    {
      employees = model.getEmployees();
    } catch (RemoteException e)
    {
      e.printStackTrace();
    }
    ArrayList<String> returnStrings = new ArrayList<>();

    for (Employee employee :
            employees)
    {
      returnStrings.add(employee.getName() + " " + employee.getSurname() + "(id " + employee.getId() + ")");
    }
    return returnStrings;
  }
}
