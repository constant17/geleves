package com.geleves.app.views;

import javax.annotation.security.PermitAll;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.geleves.app.data.service.GelevesService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Component
@Scope("prototype")
@Route(value="bulletins", layout = MainLayout.class)
@PageTitle("Notes et Bulletin | Gélèves")
@PermitAll
public class NotesBulletinView extends VerticalLayout{
	
	private GelevesService service;
	
	public NotesBulletinView(GelevesService service) {
		this.service = service;
	}

}
