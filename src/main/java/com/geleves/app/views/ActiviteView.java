package com.geleves.app.views;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.PermitAll;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.geleves.app.data.entity.Activite;
import com.geleves.app.data.service.GelevesService;
import com.geleves.app.views.list.ActiviteForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "activites", layout = MainLayout.class)
@PageTitle("Activités | Gélèves")
@PermitAll

public class ActiviteView extends VerticalLayout{

	FullCalendar calendar;
	ActiviteForm form;
    GelevesService service;
    TextField rechercherActivite = new TextField();
    Entry activite;
    FlexLayout mainContent;
    
	@SuppressWarnings("deprecation")
	public ActiviteView(GelevesService service) {
		MainLayout.tabs.setSelectedIndex(MainLayout.ACTIVITES_TAB);
		this.service = service;
		form = new ActiviteForm(service.findAllActeurs());
		
        form.setWidth("25em");
        form.addListener(ActiviteForm.SaveEvent.class, this::saveActivite);
        form.addListener(ActiviteForm.DeleteEvent.class, this::deleteActivite);
        form.addListener(ActiviteForm.CloseEvent.class, e ->annuler());

		addClassName("list-view");
		setSizeFull();
		// Create a new calendar instance and attach it to our layout
		calendar = FullCalendarBuilder.create().build();
		// Adding all activities to calendar
		addCalendarEntries();
		calendar.setHeightByParent(); // calculate the height by parent
		calendar.getElement().getStyle().set("flex-grow", "1");
		calendar.setSizeFull();
		
		calendar.addTimeslotsSelectedListener((event) -> {
		    // react on the selected timeslot, for instance create a new instance and let the user edit it 
			activite = new Entry();

		    activite.setStart(event.getStart()); // also event times are always utc based
		    activite.setEnd(event.getEnd());
		    activite.setAllDay(event.isAllDay());
		    activite.setColor("dodgerblue");
		    editActivite(new Activite());
		    form.setStartingDate(event.getStart().toLocalDate());
		    form.setEndingDate(event.getEnd().toLocalDate());
		    form.setStartingTime(event.getStart().toLocalTime());
		    form.setEndingTime(event.getEnd().toLocalTime());
		    //calendar.addEntry(entree);

		});
		
		mainContent = new FlexLayout(calendar, form);
		mainContent.setFlexGrow(2, calendar);
		mainContent.setFlexGrow(1, form);
		mainContent.setFlexShrink(0, form);
		mainContent.addClassNames("content", "gap-m");
		mainContent.setSizeFull();
        add(getToolbar(), mainContent);
        closeEditor();
	}
	
	private void addCalendarEntries() {
		List<Activite> activities = service.findAllActivites();
		
		for(Activite activity: activities) {
			
			Entry entry = new Entry();
			entry.setTitle(activity.toString());
			entry.setColor("#ff3333");

			entry.setStart(activity.getStartingDate().atTime(activity.getStartingTime()));
			entry.setEnd(activity.getEndingDate().atTime(activity.getEndingTime()));
			 // addValueChangeListener(event ->
            // editActivite(event.getValue()));
			this.calendar.addEntry(entry);
		}
		
	}
	
	private HorizontalLayout getToolbar() {
		rechercherActivite.setWidth("24em");
		rechercherActivite.setPlaceholder("Rechercher une activité..");
		rechercherActivite.setClearButtonVisible(true);
		rechercherActivite.setValueChangeMode(ValueChangeMode.LAZY);

        
        HorizontalLayout toolbar = new HorizontalLayout(rechercherActivite);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
	
	 private void saveActivite(ActiviteForm.SaveEvent event) {
		 	this.calendar.addEntry(this.activite);
	        service.saveActivite(event.getActivite());
	        closeEditor();
	        Notification notification = Notification.show("Changement enregistré!");
	        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
	    }

	 private void deleteActivite(ActiviteForm.DeleteEvent event) {
	    	
	    	/*warningDialog = new ConfirmDialog();
	    	
	    	warningDialog.setHeader("Confirmer La Suppression!");
	    	warningDialog.setText(new Html("<p>Vous &ecirc;tes sur le point de supprimer <b>"+event.getEleve().toString()
	    			+"</b> de la liste des &eacute;l&egrave;ves inscrits. <br>Voulez-vous vraiment supprimer cet &eacute;l&egrave;ve?</p>"));

	    	warningDialog.setCancelable(true);
	    	warningDialog.setCancelText("Annuler");

	    	warningDialog.setConfirmText("Supprimer");
	    	
	    	warningDialog.addConfirmListener(e -> {
	    		service.deleteEleve(event.getEleve());
	            updateList();
	            closeEditor();
	            Notification notification = Notification.show("Eleve supprime!");
	            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
	            
	    	});
	    	warningDialog.open();*/
	        
	    }
	    
	public void editActivite(Activite activite) {
        if (activite == null) {
            closeEditor();
            UI.getCurrent().getPage().reload();
        } else {
            form.setActivite(activite);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addActivite() {
        //.asSingleSelect().clear();
        //editActivite(new Activite());
    }

    private void closeEditor() {
        form.setActivite(null);
        form.setVisible(false);
        removeClassName("editing");
    }
	
    private void annuler() {
    	closeEditor();
    	UI.getCurrent().getPage().reload();
    }
	
}
