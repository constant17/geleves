package com.geleves.app.views.list;

import com.geleves.app.data.entity.Eleve;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class NotesForm extends FormLayout {
  private Eleve eleve;


  Button save = new Button("Sauvegarder");
  Button delete = new Button("Supprimer");
  Button close = new Button("Annuler");

  public NotesForm() {
	
    addClassName("eleve-form");
    //binder.bindInstanceFields(this);
    
    DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
	singleFormatI18n.setDateFormat("dd-MM-yyyy");
	
	
    
    add(createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    //delete.addClickListener(event -> fireEvent(new DeleteEvent(this, eleve)));
    //close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    //binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setEleve(Eleve eleve) {
    this.eleve = eleve;
    //binder.readBean(eleve);
  }

  private void validateAndSave() {
    //try {
      //binder.writeBean(eleve);
      //fireEvent(new SaveEvent(this, eleve));
   // } catch (ValidationException e) {
   //   e.printStackTrace();
    //}
  }

  // Events
  public static abstract class EleveFormEvent extends ComponentEvent<EleveForm> {
    private Eleve eleve;

    protected EleveFormEvent(EleveForm source, Eleve eleve) {
      super(source, false);
      this.eleve = eleve;
    }

    public Eleve getEleve() {
      return eleve;
    }
  }

  public static class SaveEvent extends EleveFormEvent {
    SaveEvent(EleveForm source, Eleve eleve) {
      super(source, eleve);
    }
  }

  public static class DeleteEvent extends EleveFormEvent {
    DeleteEvent(EleveForm source, Eleve eleve) {
      super(source, eleve);
    }

  }

  public static class CloseEvent extends EleveFormEvent {
    CloseEvent(EleveForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}