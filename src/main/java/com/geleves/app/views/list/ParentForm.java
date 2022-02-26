package com.geleves.app.views.list;

import com.geleves.app.data.entity.Parent;
import com.geleves.app.data.entity.Eleve;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ParentForm extends FormLayout {
	private Parent parent;

  TextField prenom = new TextField("Prenom");
  TextField nom = new TextField("Nom");
  TextField address = new TextField("Address");
  EmailField email = new EmailField("Email");
  TextField contact = new TextField("Contact");
  //ComboBox<Eleve> enfants = new ComboBox<>("Eleve");
  Binder<Parent> binder = new BeanValidationBinder<>(Parent.class);

  Button save = new Button("Sauvegarder");
  Button delete = new Button("Supprimer");
  Button close = new Button("Annuler");

  public ParentForm(List<Eleve> eleves) {
    addClassName("contact-form");
    binder.bindInstanceFields(this);

    //enfants.setItems(eleves);
    //enfants.setItemLabelGenerator(Eleve::toString);
    add(nom,
        prenom,
        contact,
        address,
        email,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, parent)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setParent(Parent parent) {
    this.parent = parent;
    binder.readBean(parent);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(parent);
      fireEvent(new SaveEvent(this, parent));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ParentFormEvent extends ComponentEvent<ParentForm> {
    private Parent parent;

    protected ParentFormEvent(ParentForm source, Parent parent) {
      super(source, false);
      this.parent = parent;
    }

    public Parent getParent() {
      return parent;
    }
  }

  public static class SaveEvent extends ParentFormEvent {
    SaveEvent(ParentForm source, Parent parent) {
      super(source, parent);
    }
  }

  public static class DeleteEvent extends ParentFormEvent {
    DeleteEvent(ParentForm source, Parent parent) {
      super(source, parent);
    }

  }

  public static class CloseEvent extends ParentFormEvent {
    CloseEvent(ParentForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}