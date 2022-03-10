package com.geleves.app.views.list;

import com.geleves.app.data.entity.Acteur;
import com.geleves.app.data.entity.Activite;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ActiviteForm extends FormLayout {
  private Activite activite;

  TextField nom = new TextField("Nom de l'activité:");
  TextArea description = new TextArea("Description de L'activité");
  TimePicker startingTime = new TimePicker("Heure de demarage:");
  TimePicker endingTime = new TimePicker("Heure de fin:");
  DatePicker startingDate = new DatePicker("Date:");
  DatePicker endingDate = new DatePicker("Date de fin:");
  ComboBox<Acteur> responsable = new ComboBox<>("Responsable");
  Binder<Activite> binder = new BeanValidationBinder<>(Activite.class);

  Button save = new Button("Sauvegarder");
  Button delete = new Button("Supprimer");
  Button close = new Button("Annuler");

  public ActiviteForm(List<Acteur> respos) {
    addClassName("contact-form");
    binder.bindInstanceFields(this);
    
    
    responsable.setItems(respos);
    responsable.setItemLabelGenerator(Acteur::toString);
    
    HorizontalLayout starting = new HorizontalLayout();
    starting.add(startingDate); starting.add(startingTime);
    
    HorizontalLayout ending = new HorizontalLayout();
    ending.add(endingDate); ending.add(endingTime);
    
    //description.setLabel("Comment");
    description.setMaxLength(200);
    description.setValueChangeMode(ValueChangeMode.EAGER);
    description.addValueChangeListener(e -> {
      e.getSource().setHelperText(e.getValue().length() + "/" + 200);
    });

    add(
    	nom,
    	description,
    	starting,
    	ending,
    	responsable,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, activite)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setActivite(Activite activite) {
    this.activite = activite;
    binder.readBean(activite);
  }
  
  public void setStartingDate(LocalDate startDate) {
	  this.startingDate.setValue(startDate);
  }
  
  public void setEndingDate(LocalDate endDate) {
	  this.endingDate.setValue(endDate);
  }
  
  public void setStartingTime(LocalTime startTime) {
	  this.startingTime.setValue(startTime);
  }
  
  public void setEndingTime(LocalTime endTime) {
	  this.endingTime.setValue(endTime);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(activite);
      fireEvent(new SaveEvent(this, activite));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ActiviteFormEvent extends ComponentEvent<ActiviteForm> {
    private Activite activite;

    protected ActiviteFormEvent(ActiviteForm source, Activite activite) {
      super(source, false);
      this.activite = activite;
    }

    public Activite getActivite() {
      return activite;
    }
  }

  public static class SaveEvent extends ActiviteFormEvent {
    SaveEvent(ActiviteForm source, Activite activite) {
      super(source, activite);
    }
  }

  public static class DeleteEvent extends ActiviteFormEvent {
    DeleteEvent(ActiviteForm source, Activite activite) {
      super(source, activite);
    }

  }

  public static class CloseEvent extends ActiviteFormEvent {
    CloseEvent(ActiviteForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}