package com.geleves.app.views;

import java.time.LocalDate;

import javax.annotation.security.PermitAll;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.geleves.app.data.service.GelevesService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "activites", layout = MainLayout.class)
@PageTitle("Activites | Geleves")
@PermitAll

public class ActiviteView extends VerticalLayout{
	
	@SuppressWarnings("deprecation")
	public ActiviteView(GelevesService service) {
		
		addClassName("calendar");
		setSizeFull();
		// Create a new calendar instance and attach it to our layout
		FullCalendar calendar = FullCalendarBuilder.create().build();

		// Create a initial sample entry
		Entry entry = new Entry();
		entry.setTitle("Some event");
		entry.setColor("#ff3333");

		// the given times will be interpreted as utc based - useful when the times are fetched from your database
		entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0));
		entry.setEnd(entry.getStart().plusHours(2));

		calendar.addEntry(entry);
		
		calendar.setHeightByParent(); // calculate the height by parent
		calendar.getElement().getStyle().set("flex-grow", "1");

		// if parent is for instance a vertical layout, you may also use the dedicated java api here
		calendar.setHeightByParent();
		add(calendar);
		setFlexGrow(1, calendar);
		        
		calendar.setSizeFull();
		
		calendar.addTimeslotsSelectedListener((event) -> {
		    // react on the selected timeslot, for instance create a new instance and let the user edit it 
		    Entry entree = new Entry();

		    entree.setStart(event.getStart()); // also event times are always utc based
		    entree.setEnd(event.getEnd());
		    entree.setAllDay(event.isAllDay());

		    entree.setColor("dodgerblue");

		    // ... show and editor
		});
	}
	
	
	
}
